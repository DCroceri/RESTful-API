package net.awsomerecipes.ws.api.rest.facades.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.awsomerecipes.ws.api.rest.beans.User;
import net.awsomerecipes.ws.api.rest.beans.security.UserAuthority;
import net.awsomerecipes.ws.api.rest.daos.RoleDao;
import net.awsomerecipes.ws.api.rest.daos.UserDao;
import net.awsomerecipes.ws.api.rest.facades.UserFacade;

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public List<User> listAllUsers() {
		return userDao.findAll();
	}

	@Override
	public void saveUser(User user) {
		if (user.getRole().getId()== null) {
			user.setRole(roleDao.findByName(user.getRole().getName()));
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userDao.save(user);
	}

	@Override
	public User getUser(Long id) {
		return userDao.findById(id).get();
	}

	@Override
	public void deleteUser(Long id) {
		userDao.deleteById(id);
	}


	@Override
	public void newUser(User user) {
		if (user.getRole()== null) {
			user.setRole(roleDao.findByName(UserAuthority.ROLE_USER.getName()));
		} else if (user.getRole().getId() != null) {
			user.setRole(roleDao.findById(user.getRole().getId()).get());
		} else if (user.getRole().getName() != null) {
			user.setRole(roleDao.findByName(user.getRole().getName()));
			
		}
		user.setEnabled(true);
		this.saveUser(user);

		// TODO send email
	}
	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {

		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager != null) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			return;
		}

		User user = (User) userDao.findByUsername(username);

		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		userDao.save(user);

	}

}

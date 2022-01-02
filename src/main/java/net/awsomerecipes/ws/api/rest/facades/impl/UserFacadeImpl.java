package net.awsomerecipes.ws.api.rest.facades.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.awsomerecipes.ws.api.rest.beans.User;
import net.awsomerecipes.ws.api.rest.daos.UserDao;
import net.awsomerecipes.ws.api.rest.facades.UserFacade;

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> listAllUsers() {
		return userDao.findAll();
	}

	@Override
	public void saveUser(User user) {
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
	public User getUserByUsername(String username) {
		return userDao.findByUsername(username);
	}

}

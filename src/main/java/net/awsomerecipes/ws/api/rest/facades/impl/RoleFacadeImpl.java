package net.awsomerecipes.ws.api.rest.facades.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.awsomerecipes.ws.api.rest.beans.Role;
import net.awsomerecipes.ws.api.rest.daos.RoleDao;
import net.awsomerecipes.ws.api.rest.facades.RoleFacade;

@Service
@Transactional
public class RoleFacadeImpl implements RoleFacade {

	@Autowired
	private RoleDao roleDao;

	@Override
	public void saveRole(Role role) {
		roleDao.save(role);
	}
}

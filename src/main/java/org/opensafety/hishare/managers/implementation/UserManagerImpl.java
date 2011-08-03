package org.opensafety.hishare.managers.implementation;

import java.util.UUID;

import org.opensafety.hishare.managers.interfaces.UserManager;
import org.opensafety.hishare.model.User;

import org.opensafety.hishare.dao.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class UserManagerImpl implements UserManager
{
	@Autowired
	UserDao userDao;
	
	public boolean userExists(String username)
    {
	    return userDao.userExists(username);
    }
	
	public void persistUser(User user)
    {
		userDao.addUser(user);
    }
	
	public void updateUser(User user)
	{
		userDao.updateUser(user);
	}

	public String renewUserAuthentication(String username)
    {
		User authenticatee = userDao.getByName(username);
		authenticatee.setAuthenticationId(UUID.randomUUID().toString());
		userDao.updateUser(authenticatee);
		return authenticatee.getAuthenticationId();
    }

	public boolean verifyAuthentication(String username, String authenticationId)
    {
	    User user = userDao.getByName(username);
	    return user.getAuthenticationId().equals(authenticationId);
    }

	public User getByUsername(String username)
    {
	    return userDao.getByName(username);
    }
}

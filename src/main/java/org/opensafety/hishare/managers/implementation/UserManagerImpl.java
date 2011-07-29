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

	private static String createAuthenticationId()
	{
		return UUID.randomUUID().toString();
	}
	
	public boolean userExists(String username)
    {
	    return userDao.userExists(username);
    }

	public User addUser(String username)
    {
		User newUser = new User(username);
		
		newUser = userDao.addUser(newUser);
		
	    return newUser;
    }

	public String authenticateUser(String username)
    {
		User authenticatee = userDao.getByName(username);
		authenticatee.setAuthenticationId(UUID.randomUUID().toString());
		userDao.updateUser(authenticatee);
		return authenticatee.getAuthenticationId();
    }

	public boolean verifyUser(String username, String authenticationId)
    {
	    User user = userDao.getByName(username);
	    return user.getAuthenticationId().equals(authenticationId);
    }

	public User getByUsername(String username)
    {
	    return userDao.getByName(username);
    }
}

package org.opensafety.hishare.managers.implementation.http;

import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.dao.interfaces.UserDao;
import org.opensafety.hishare.managers.interfaces.http.UserManager;
import org.opensafety.hishare.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import sun.util.logging.resources.logging;

public class UserManagerImpl implements UserManager
{
	@Autowired
	UserDao userDao;
	
	Log log = LogFactory.getLog(this.getClass());
	
	private int authenticationExpiration;
	
	public UserManagerImpl()
	{
		this(24);
	}
	
	public UserManagerImpl(int authenticationExpiration)
	{
		this.authenticationExpiration = authenticationExpiration;
	}
	
	public User getByUsername(String username)
	{
		return userDao.getByName(username);
	}
	
	public void persistUser(User user)
	{
		userDao.addUser(user);
	}
	
	public String renewUserAuthentication(String username)
	{
		Calendar expirationDate = Calendar.getInstance();
		expirationDate.add(Calendar.HOUR, authenticationExpiration);
		
		User authenticatee = userDao.getByName(username);
		authenticatee.setAuthenticationId(UUID.randomUUID().toString());
		authenticatee.setAuthenticationExpiration(expirationDate.getTime());
		
		userDao.updateUser(authenticatee);
		
		return authenticatee.getAuthenticationId();
	}
	
	public void updateUser(User user)
	{
		userDao.updateUser(user);
	}
	
	public boolean userExists(String username)
	{
		return userDao.userExists(username);
	}
	
	public boolean verifyAuthentication(String username, String authenticationId)
	{
		User user = userDao.getByName(username);
		
		Date expiration = user.getAuthenticationExpiration();
		Date now = Calendar.getInstance().getTime();
		
		return (user.getAuthenticationId().equals(authenticationId)) && now.before(expiration);
	}
}

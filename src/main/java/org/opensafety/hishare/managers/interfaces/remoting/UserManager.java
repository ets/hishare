package org.opensafety.hishare.managers.interfaces.remoting;

import org.opensafety.hishare.model.User;

public interface UserManager
{
	User getByUsername(String username);
	
	/* Persistence */
	void persistUser(User user);
	
	/* Authentication */
	String renewUserAuthentication(String username);
	
	void updateUser(User owner);
	
	boolean userExists(String username);
	
	boolean verifyAuthentication(String username, String authenticationId);
}

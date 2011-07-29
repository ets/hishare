package org.opensafety.hishare.managers.interfaces;

import org.opensafety.hishare.model.User;

public interface UserManager
{
	/*Persistence*/
	void persistUser(User user);
	
	void updateUser(User owner);

	User getByUsername(String username);
	
	boolean userExists(String username);
	
	/*Authentication*/
	String renewUserAuthentication(String username);
	
	boolean verifyAuthentication(String username, String authenticationId);
}

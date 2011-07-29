package org.opensafety.hishare.managers.interfaces;

import org.opensafety.hishare.model.User;

public interface UserManager
{
	boolean userExists(String username);
	User addUser(String username);
	String authenticateUser(String username);
	boolean verifyUser(String username, String authenticationId);
	User getByUsername(String username);
}

package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.User;

public interface UserDao
{
	void addUser(User user);
	
	User getByName(String username);
	
	void updateUser(User user);
	
	boolean userExists(Long id);
	
	boolean userExists(String username);
}

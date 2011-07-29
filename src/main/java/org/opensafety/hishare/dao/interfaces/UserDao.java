package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.User;

public interface UserDao
{
	User getByName(String username);
	boolean userExists(String username);
	User addUser(User user);
	void updateUser(User user);
}

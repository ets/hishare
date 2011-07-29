package org.opensafety.hishare.model.factories;

import org.opensafety.hishare.model.User;

public class UserFactory
{
	public User createUser(String username)
	{
		User newUser = new User(username);
		return newUser;
	}
}

package org.opensafety.hishare.model.factories;

import java.util.UUID;

import org.opensafety.hishare.model.User;

public class UserFactory
{
	public static Long createUserId()
	{
		return UUID.randomUUID().getLeastSignificantBits();
	}
	
	public User createUser(String username)
	{
		User newUser = new User(createUserId(), username);
		return newUser;
	}
}

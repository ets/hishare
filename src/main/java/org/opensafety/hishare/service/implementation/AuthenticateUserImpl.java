package org.opensafety.hishare.service.implementation;

import org.opensafety.hishare.managers.interfaces.UserManager;
import org.opensafety.hishare.service.interfaces.AuthenticateUser;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticateUserImpl implements AuthenticateUser
{
	@Autowired
	private UserManager userManager;
	
	public AuthenticateUserImpl()
	{
	}

	public String justAuthenticateMe(String username)
    {
		if(!userManager.userExists(username))
		{
			userManager.addUser(username);
		}
		
		String authId = userManager.authenticateUser(username);
		
		return authId;
    }
	
}

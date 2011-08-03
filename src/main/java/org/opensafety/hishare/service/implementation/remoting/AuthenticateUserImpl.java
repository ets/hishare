package org.opensafety.hishare.service.implementation.remoting;

import org.opensafety.hishare.managers.interfaces.remoting.UserManager;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.model.factories.UserFactory;
import org.opensafety.hishare.service.interfaces.remoting.AuthenticateUser;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticateUserImpl implements AuthenticateUser
{
	@Autowired
	private UserManager userManager;
	@Autowired
	private UserFactory userFactory;
	
	public AuthenticateUserImpl()
	{
	}
	
	public String justAuthenticateMe(String username)
	{
		if(!userManager.userExists(username))
		{
			User newUser = userFactory.createUser(username);
			userManager.persistUser(newUser);
		}
		
		String authId = userManager.renewUserAuthentication(username);
		
		return authId;
	}
	
}

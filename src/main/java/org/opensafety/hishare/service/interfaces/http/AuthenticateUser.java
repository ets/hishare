package org.opensafety.hishare.service.interfaces.http;

public interface AuthenticateUser
{
	/* THIS IS DEFINITELY JUST FOR DEV TESTING */
	String authenticate(String username, String authenticationServerName,
                        String authenticationServerPassword);
}

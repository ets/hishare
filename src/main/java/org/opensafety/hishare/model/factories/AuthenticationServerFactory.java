package org.opensafety.hishare.model.factories;

import java.util.UUID;

import org.opensafety.hishare.model.AuthenticationServer;

public class AuthenticationServerFactory
{
	private static String createAuthenticationServerId()
    {
	    return UUID.randomUUID().toString();
    }

	public static AuthenticationServer
            createAuthenticationServer(String authenticationServerName, byte[] hashedPassword,
                                       byte[] salt)
    {
	    return new AuthenticationServer(createAuthenticationServerId(), authenticationServerName, hashedPassword, salt);
    }
}

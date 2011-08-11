package org.opensafety.hishare.managers.interfaces.http;

import org.opensafety.hishare.model.AuthenticationServer;

public interface AuthenticationServerManager
{

	boolean authenticationServerExists(String authenticationServerDomain);

	byte[] getSalt(String authenticationServerDomain);

	boolean authenticationServerVerify(String authenticationServerDomain, byte[] hashedPassword);

	void changePassword(String authenticationServerName, byte[] hashedNewPassword, byte[] salt);

	void persistServer(AuthenticationServer server);
	
}

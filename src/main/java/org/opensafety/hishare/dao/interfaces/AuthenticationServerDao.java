package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.AuthenticationServer;

public interface AuthenticationServerDao
{

	boolean serverExists(String authenticationServerName);

	byte[] getSalt(String authenticationServerName);

	byte[] getPassword(String authenticationServerName);

	AuthenticationServer getByName(String authenticationServerName);

	void updateServer(AuthenticationServer server);

	void addServer(AuthenticationServer server);
	
}

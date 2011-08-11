package org.opensafety.hishare.managers.implementation.http;

import java.util.Arrays;
import java.util.UUID;

import org.opensafety.hishare.dao.implementation.HibernateAuthenticationServerDao;
import org.opensafety.hishare.dao.interfaces.AuthenticationServerDao;
import org.opensafety.hishare.managers.interfaces.http.AuthenticationServerManager;
import org.opensafety.hishare.model.AuthenticationServer;
import org.opensafety.hishare.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationServerManagerImpl implements AuthenticationServerManager
{
	@Autowired
	AuthenticationServerDao authenticationServerDao;
	
	public boolean authenticationServerExists(String authenticationServerName)
    {
		return authenticationServerDao.serverExists(authenticationServerName);
    }

	public byte[] getSalt(String authenticationServerName)
    {
	    return authenticationServerDao.getSalt(authenticationServerName);
    }

	public boolean authenticationServerVerify(String authenticationServerName,
                                              byte[] hashedPassword)
    {
		return Arrays.equals(authenticationServerDao.getPassword(authenticationServerName), hashedPassword);
    }

	public void changePassword(String authenticationServerName, byte[] hashedNewPassword, byte[] salt)
    {
		AuthenticationServer server = authenticationServerDao.getByName(authenticationServerName);
		server.setPassword(hashedNewPassword);
		server.setSalt(salt);
		authenticationServerDao.updateServer(server);
    }

	public void persistServer(AuthenticationServer server)
    {
		authenticationServerDao.addServer(server);
    }
	
}

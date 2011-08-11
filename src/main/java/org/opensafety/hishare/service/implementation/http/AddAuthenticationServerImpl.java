package org.opensafety.hishare.service.implementation.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.managers.interfaces.http.AuthenticationServerManager;
import org.opensafety.hishare.model.AuthenticationServer;
import org.opensafety.hishare.model.factories.AuthenticationServerFactory;
import org.opensafety.hishare.service.interfaces.http.AddAuthenticationServer;
import org.opensafety.hishare.util.interfaces.Encryption;
import org.opensafety.hishare.util.interfaces.Encryption.CryptographyException;
import org.springframework.beans.factory.annotation.Autowired;

public class AddAuthenticationServerImpl implements AddAuthenticationServer
{
	@Autowired
	AuthenticationServerManager authenticationServerManager;
	@Autowired
	Encryption encryption;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private static final String addSucceeded = "Authentication Server Added Successfully";
	private static final String addFailed = "Adding Authentication Server Failed";
	
	
	public String addAuthenticationServer(String authenticationServerName)
    {
		if(authenticationServerManager.authenticationServerExists(authenticationServerName))
		{
			return addFailed;
		}
		
		String authenticationServerPassword = "Secure Password";
		byte[] hashedPassword;
		byte[] salt;
            
		try
        {
			salt = encryption.createSalt();
            hashedPassword = encryption.hashPassword(authenticationServerPassword, salt);
        }
        catch(CryptographyException e)
        {
        	log.error("Authentication Server's password could not be hashed");
            e.printStackTrace();
            return addFailed;
        }
		
		AuthenticationServer server = AuthenticationServerFactory.createAuthenticationServer(authenticationServerName, hashedPassword, salt);
		
		authenticationServerManager.persistServer(server);
		
		return addSucceeded;
    }
	
}

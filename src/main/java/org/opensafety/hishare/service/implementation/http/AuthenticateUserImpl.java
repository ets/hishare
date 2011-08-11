package org.opensafety.hishare.service.implementation.http;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.managers.interfaces.http.AuthenticationServerManager;
import org.opensafety.hishare.managers.interfaces.http.UserManager;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.model.factories.UserFactory;
import org.opensafety.hishare.service.interfaces.http.AuthenticateUser;
import org.opensafety.hishare.util.interfaces.Encryption;
import org.opensafety.hishare.util.interfaces.Encryption.CryptographyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class AuthenticateUserImpl extends HibernateDaoSupport implements AuthenticateUser
{
	@Autowired
	private UserManager userManager;
	@Autowired
	private UserFactory userFactory;
	@Autowired
	private AuthenticationServerManager authenticationServerManager;
	@Autowired
	private Encryption encryption;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private static final String errorAuthenticatingServer = "Error: Failed to authenticate server";
	
	public AuthenticateUserImpl()
	{
	}
	
	public String authenticate(String username, String authenticationServerName,
	                           String authenticationServerPassword)
	{
		log.info((new Date())+" Authenticate User ");
		log.debug("username: "+username);
		log.debug("Authentication Server: "+authenticationServerName);
		
		if(authenticationServerManager.authenticationServerExists(authenticationServerName))
		{
			byte[] salt = authenticationServerManager.getSalt(authenticationServerName);
			byte[] hashedPassword;
            
			try
            {
	            hashedPassword = encryption.hashPassword(authenticationServerPassword, salt);
            }
            catch(CryptographyException e)
            {
            	log.error("Authentication Server's password could not be hashed");
	            e.printStackTrace();
	            return errorAuthenticatingServer;
            }
			
			if(authenticationServerManager.authenticationServerVerify(authenticationServerName, hashedPassword))
			{
				if(!userManager.userExists(username))
				{
					User newUser = userFactory.createUser(username, authenticationServerName);
					userManager.persistUser(newUser);
				}
				
				String authId = userManager.renewUserAuthentication(username);
				
				return authId;
			}
			else
			{
				log.info((new Date())+" Authentication Failed: Authentication Server could not be verified ");
			}
		}
		else
		{
			log.info((new Date())+" Authentication Failed: Authentication Server Not Recognized ");
		}
		
		return errorAuthenticatingServer;
	}
}
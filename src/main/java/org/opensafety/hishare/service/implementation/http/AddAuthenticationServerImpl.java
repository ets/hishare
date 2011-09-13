/*******************************************************************************
 * Copyright 2011 Pascal Metrics
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
        	log.error("Authentication Server's password could not be hashed", e);
            return addFailed;
        }
		
		AuthenticationServer server = AuthenticationServerFactory.createAuthenticationServer(authenticationServerName, hashedPassword, salt);
		
		authenticationServerManager.persistServer(server);
		
		return addSucceeded;
    }
	
}

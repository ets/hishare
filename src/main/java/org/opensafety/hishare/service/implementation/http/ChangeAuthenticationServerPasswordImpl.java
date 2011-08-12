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

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.managers.interfaces.http.AuthenticationServerManager;
import org.opensafety.hishare.service.interfaces.http.ChangeAuthenticationServerPassword;
import org.opensafety.hishare.util.interfaces.Encryption;
import org.opensafety.hishare.util.interfaces.Encryption.CryptographyException;
import org.springframework.beans.factory.annotation.Autowired;

public class ChangeAuthenticationServerPasswordImpl implements ChangeAuthenticationServerPassword
{
	@Autowired
	AuthenticationServerManager authenticationServerManager;
	@Autowired
	Encryption encryption;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private static final String changeSucceeded = "Password Changed Successfully";
	private static final String changeFailed = "Password Change Failed";
	
	public String changePassword(String authenticationServerName, String authenticationServerPassword,
                                 String newAuthenticationServerPassword)
    {
		log.info((new Date())+" Authentication Server Password Change ");
		log.debug("Authentication Server: " + authenticationServerName);
		
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
	            return changeFailed;
            }
			
			if(authenticationServerManager.authenticationServerVerify(authenticationServerName, hashedPassword))
			{
				byte[] hashedNewPassword;
				
				try
				{
					salt = encryption.createSalt();
					hashedNewPassword = encryption.hashPassword(newAuthenticationServerPassword, salt);
				}
				catch(CryptographyException e)
	            {
	            	log.error("Authentication Server's password could not be hashed");
		            e.printStackTrace();
		            return changeFailed;
	            }
				
				authenticationServerManager.changePassword(authenticationServerName, hashedNewPassword, salt);
				log.info((new Date())+" Authentication Server Password Change Succeeded ");
				return changeSucceeded;
			}
			else
			{
				log.info((new Date())+" Authentication Server Password Change Failed: Authentication Server could not be verified ");
			}
		}
		else
		{
			log.info((new Date())+" Authentication Server Password Change Failed: Authentication Server Not Recognized ");
		}
		
		return changeFailed;
    }
	
}

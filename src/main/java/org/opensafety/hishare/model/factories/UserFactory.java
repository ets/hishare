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
package org.opensafety.hishare.model.factories;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.util.interfaces.Encryption;
import org.opensafety.hishare.util.interfaces.Encryption.CryptographyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFactory
{
	@Autowired
	private Encryption encryption;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private Long createUserId(String username, String authenticationServerName) throws CryptographyException
	{
		return encryption.hash(username+authenticationServerName);
	}
	
	public User createUser(String username, String authenticationServerName)
	{
		try
		{
			return new User(createUserId(username, authenticationServerName), username, authenticationServerName);
		}
		catch(CryptographyException e)
		{
			log.error("Failed to encrypt user Id", e);
			return null;
		}
	}
}

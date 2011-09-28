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
package org.opensafety.hishare.managers.implementation.http;

import java.util.Arrays;
import org.opensafety.hishare.dao.interfaces.AuthenticationServerDao;
import org.opensafety.hishare.managers.interfaces.AuthenticationServerManager;
import org.opensafety.hishare.model.AuthenticationServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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

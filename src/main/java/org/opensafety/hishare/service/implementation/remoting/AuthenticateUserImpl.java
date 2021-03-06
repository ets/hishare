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
package org.opensafety.hishare.service.implementation.remoting;

import org.opensafety.hishare.managers.interfaces.UserManager;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.model.factories.UserFactory;
import org.opensafety.hishare.service.interfaces.AuthenticateUser;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticateUserImpl implements AuthenticateUser
{
	@Autowired
	private UserManager userManager;
	@Autowired
	private UserFactory userFactory;

	public String justAuthenticateMe(String username)
    {
	    // TODO Auto-generated method stub
	    return null;
    }

	public String authenticate(String username,
			String authenticationServerName, String authenticationServerPassword) {
		// TODO Auto-generated method stub
		return null;
	}
}

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
package org.opensafety.hishare.managers.implementation.remoting;

import java.util.UUID;

import org.opensafety.hishare.dao.interfaces.UserDao;
import org.opensafety.hishare.managers.interfaces.remoting.UserManager;
import org.opensafety.hishare.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserManagerImpl implements UserManager
{
	@Autowired
	UserDao userDao;
	
	public User getByUsername(String username)
	{
		return userDao.getByName(username);
	}
	
	public void persistUser(User user)
	{
		userDao.addUser(user);
	}
	
	public String renewUserAuthentication(String username)
	{
		User authenticatee = userDao.getByName(username);
		authenticatee.setAuthenticationId(UUID.randomUUID().toString());
		userDao.updateUser(authenticatee);
		return authenticatee.getAuthenticationId();
	}
	
	public void updateUser(User user)
	{
		userDao.updateUser(user);
	}
	
	public boolean userExists(String username)
	{
		return userDao.userExists(username);
	}
	
	public boolean verifyAuthentication(String username, String authenticationId)
	{
		User user = userDao.getByName(username);
		return user.getAuthenticationId().equals(authenticationId);
	}
}

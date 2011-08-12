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
import org.opensafety.hishare.managers.interfaces.http.ParcelManager;
import org.opensafety.hishare.managers.interfaces.http.PermissionManager;
import org.opensafety.hishare.managers.interfaces.http.UserManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.model.factories.PermissionFactory;
import org.opensafety.hishare.model.factories.UserFactory;
import org.opensafety.hishare.service.interfaces.http.AuthorizeUser;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorizeUserImpl implements AuthorizeUser
{
	@Autowired
	private ParcelManager parcelManager;
	@Autowired
	private PermissionManager permissionManager;
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private PermissionFactory permissionFactory;
	@Autowired
	private UserFactory userFactory;
	
	Log log = LogFactory.getLog(this.getClass());
	
	private static final String genericCredentialsError = "The credentials supplied do not permit you to alter permissions.";
	private static final String permissionAddedSuccessfully = "Permission added successfully";
	
	public String authorizeUser(String authorizingUser, String authenticationId, String parcelId,
	                        String parcelPassword, String userToAuthorize,
	                        PermissionLevel permissionLevel)
	{
		log.info((new Date())+" Authorize User ");
		log.debug("authorizer: "+authorizingUser);
		log.debug("parcel Id: "+parcelId);
		log.debug("authorizee: "+userToAuthorize);
		log.debug("Permission Level: "+permissionLevel.toString());
		
		if(userManager.verifyAuthentication(authorizingUser, authenticationId))
		{
			if(parcelManager.verifyParcelAvailable(parcelId, parcelPassword))
			{
				User authorizer = userManager.getByUsername(authorizingUser);
				Parcel parcel = parcelManager.getParcel(parcelId, parcelPassword);
				
				if(permissionManager.hasAuthorizePermission(authorizer, parcel))
				{
					if(!userManager.userExists(userToAuthorize))
					{
						userManager.persistUser(userFactory.createUser(userToAuthorize, authorizer.getAuthorizingServerName()));
					}
					
					User toBeAuthorized = userManager.getByUsername(userToAuthorize);
					
					Permission permission = permissionFactory.createPermission(parcel, toBeAuthorized,
					                                                           permissionLevel);
					permissionManager.persistPermission(permission);
					
					log.info((new Date())+" Authorization Successfull ");
					return permissionAddedSuccessfully;
				}
				else
				{
					log.info((new Date())+" Authorization Unsuccessfull: No Permissions for Authorization ");
				}
			}
			else
			{
				log.info((new Date())+" Authorization Unsuccessfull: Parcel Not Available ");
			}
		}
		else
		{
			log.info((new Date())+" Authorization Unsuccessfull: User Not Verified ");
		}
		return genericCredentialsError;
	}
	
}

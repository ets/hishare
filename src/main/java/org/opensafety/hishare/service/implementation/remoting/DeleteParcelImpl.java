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

import org.opensafety.hishare.managers.interfaces.remoting.ParcelManager;
import org.opensafety.hishare.managers.interfaces.remoting.PermissionManager;
import org.opensafety.hishare.managers.interfaces.remoting.UserManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.service.interfaces.remoting.DeleteParcel;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteParcelImpl implements DeleteParcel
{
	@Autowired
	UserManager userManager;
	@Autowired
	ParcelManager parcelManager;
	@Autowired
	PermissionManager permissionManager;
	
	private static final String genericCredentialsError = "The credentials supplied do not permit you to delete.";
	private static final String deletedSuccessfully = "The parcel was deleted successfully";
	private static final String unsuccessfullDelete = "The parcel could not be deleted";
	
	public String deleteParcel(String username, String authenticationId, String parcelId,
	                           String parcelPassword)
	{
		if(userManager.verifyAuthentication(username, authenticationId))
		{
			if(parcelManager.verifyParcelAvailable(parcelId, parcelPassword))
			{
				User user = userManager.getByUsername(username);
				Parcel parcel = parcelManager.getParcel(parcelId, parcelPassword);
				
				if(permissionManager.hasDeletePermission(user, parcel))
				{
					;
				}
				{
					boolean deletePermissions = permissionManager.deletePermissions(parcel);
					boolean deleteParcel = parcelManager.deleteParcel(parcel);
					if(deletePermissions && deleteParcel)
					{
						return deletedSuccessfully;
					}
					return unsuccessfullDelete;
				}
			}
		}
		return genericCredentialsError;
	}
	
}

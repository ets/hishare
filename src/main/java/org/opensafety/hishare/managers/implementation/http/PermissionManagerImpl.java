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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.dao.interfaces.PermissionDao;
import org.opensafety.hishare.managers.interfaces.http.PermissionManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class PermissionManagerImpl implements PermissionManager
{
	@Autowired
	PermissionDao permissionDao;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private PermissionLevel[] authorizePermissions = { PermissionLevel.OWNER };
	
	private PermissionLevel[] downloadPermissions = { PermissionLevel.OWNER,
	                                                 PermissionLevel.RECEIVER };
	
	private PermissionLevel[] deletePermissions = { PermissionLevel.OWNER };
	
	public boolean deletePermissions(Parcel parcel)
	{
		return permissionDao.deleteAllWithParcel(parcel);
	}
	
	public boolean hasAuthorizePermission(User user, Parcel parcel)
	{
		return permissionDao.hasEither(user, parcel, authorizePermissions);
	}
	
	public boolean hasDeletePermission(User user, Parcel parcel)
	{
		return permissionDao.hasEither(user, parcel, deletePermissions);
	}
	
	public boolean hasDownloadPermission(User user, Parcel parcel)
	{
		return permissionDao.hasEither(user, parcel, downloadPermissions);
	}
	
	public void persistPermission(Permission permission)
	{
		if(!permissionDao.hasEither(permission.getUser(), permission.getParcel(),
		                            new PermissionLevel[] { permission.getPermission() }))
		{
			permissionDao.addPermission(permission);
		}
	}
}

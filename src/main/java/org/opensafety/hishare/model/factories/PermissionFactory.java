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

import java.util.UUID;

import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;
import org.springframework.stereotype.Component;

@Component
public class PermissionFactory
{
	private Long createPermissionId()
	{
		return UUID.randomUUID().getLeastSignificantBits();
	}
	
	public Permission createPermission(Parcel parcel, User user, PermissionLevel permissionLevel)
	{
		Permission permission = new Permission(createPermissionId(), parcel, user, permissionLevel);
		return permission;
	}
}

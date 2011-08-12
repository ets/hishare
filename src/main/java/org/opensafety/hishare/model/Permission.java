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
package org.opensafety.hishare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "permissions")
public class Permission
{
	@Id
	Long id;
	
	@ManyToOne
	User user;
	
	@ManyToOne
	Parcel parcel;
	
	@Column
	PermissionLevel permission;
	
	public Permission()
	{
	}
	
	public Permission(Long id, Parcel parcel, User user, PermissionLevel permission)
	{
		this.id = id;
		this.user = user;
		this.parcel = parcel;
		this.permission = permission;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public Parcel getParcel()
	{
		return parcel;
	}
	
	public PermissionLevel getPermission()
	{
		return permission;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public void setParcel(Parcel parcel)
	{
		this.parcel = parcel;
	}
	
	public void setPermission(PermissionLevel permission)
	{
		this.permission = permission;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
}

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

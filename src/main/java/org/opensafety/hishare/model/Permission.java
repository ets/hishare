package org.opensafety.hishare.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="permissions")
public class Permission
{
	@Id
	@GeneratedValue
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
	
	public Permission(Parcel parcel, User user, PermissionLevel permission)
	{
		this.user = user;
		this.parcel = parcel;
		this.permission = permission;
	}

	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public User getUser()
    {
    	return user;
    }

	public void setUser(User user)
    {
    	this.user = user;
    }

	public Parcel getParcel()
    {
    	return parcel;
    }

	public void setParcel(Parcel parcel)
    {
    	this.parcel = parcel;
    }

	public PermissionLevel getPermission()
    {
    	return permission;
    }

	public void setPermission(PermissionLevel permission)
    {
    	this.permission = permission;
    }
}

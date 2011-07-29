package org.opensafety.hishare.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "parcels")
public class Parcel
{
	@Id
	private String id;
	
	@Column
	private String name;
	
	@Column
	private String password;
	
	@Column
	private String payloadLocation;
	
	@Column
	private Date expirationDate;
	
	@Column
	private boolean dead;
	
	@OneToMany
	private Set<Permission> permissions;
	
	public static String createParcelId()
	{
		return UUID.randomUUID().toString();
	}
	
	public Parcel()
	{
		id = null;
		name = "";
		password = "";
		payloadLocation = "";
		expirationDate = new Date();
		dead = false;
		permissions = new HashSet<Permission>();
	}
	
	public Parcel(String id, String name, Date expirationDate, String password, String payloadLocation)
	{
		this.id = id;
		this.name = name;
		this.password = password;
		this.payloadLocation = payloadLocation;
		this.expirationDate = expirationDate;
		this.dead = false;
		permissions = new HashSet<Permission>();
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getPayloadLocation()
	{
		return payloadLocation;
	}
	
	public void setPayloadLocation(String location)
	{
		this.payloadLocation = location;
	}
	
	public Date getExpirationDate()
	{
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate)
	{
		this.expirationDate = expirationDate;
	}
	
	public boolean getDead()
	{
		return dead;
	}
	
	public void setDead(boolean dead)
	{
		this.dead = dead;
	}
	
	public Set<Permission> getPermissions()
	{
		return permissions;
	}
	
	public void setPermissions(Set<Permission> permissions)
	{
		this.permissions = permissions;
	}
	
	public void addPermission(Permission permission)
	{
		this.permissions.add(permission);
	}
}
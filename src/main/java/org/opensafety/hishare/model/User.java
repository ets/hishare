package org.opensafety.hishare.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User
{
	@Id
	Long id;
	
	@Column
	String authorizingServerName;
	
	@Column
	String username;
	
	@Column
	String authenticationId;
	
	@Column
	Date authenticationExpiration;
	
	public User()
	{
	}
	
	public User(Long id, String username, String authorizingServerName)
	{
		this.id = id;
		this.authorizingServerName = authorizingServerName;
		this.username = username;
		this.authenticationExpiration = null;
	}
	
	public User(User u)
	{
		id = u.getId();
		username = u.getUsername();
		authorizingServerName = u.getAuthorizingServerName();
		authenticationId = u.getAuthenticationId();
		authenticationExpiration = u.getAuthenticationExpiration();
	}
	
	public Long getId()
    {
    	return id;
    }

	public void setId(Long id)
    {
    	this.id = id;
    }

	public String getAuthenticationId()
	{
		return authenticationId;
	}
	
	public String getAuthorizingServerName()
    {
    	return authorizingServerName;
    }

	public void setAuthorizingServerName(String authorizingServerName)
    {
    	this.authorizingServerName = authorizingServerName;
    }

	public String getUsername()
	{
		return username;
	}
	
	public void setAuthenticationId(String authenticationId)
	{
		this.authenticationId = authenticationId;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}

	public Date getAuthenticationExpiration()
    {
    	return authenticationExpiration;
    }

	public void setAuthenticationExpiration(Date authenticationExpiration)
    {
    	this.authenticationExpiration = authenticationExpiration;
    }
}

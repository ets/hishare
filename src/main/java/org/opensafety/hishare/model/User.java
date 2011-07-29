package org.opensafety.hishare.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User
{
	@Id
	@GeneratedValue
	Long id;
	
	@Column(unique=true)
	String username;
	
	@Column
	String authenticationId;
	
	public User()
	{
	}
	
	public User(String username)
	{
		this.username = username;
	}
	
	public User(User u)
	{
		this.id = u.getId();
		this.username = u.getUsername();
		this.authenticationId = u.getAuthenticationId();
	}
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUsername()
    {
    	return username;
    }

	public void setUsername(String username)
    {
    	this.username = username;
    }

	public String getAuthenticationId()
    {
    	return authenticationId;
    }

	public void setAuthenticationId(String authenticationId)
    {
    	this.authenticationId = authenticationId;
    }
}

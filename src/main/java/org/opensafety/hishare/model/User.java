package org.opensafety.hishare.model;

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
	
	@Column(unique = true)
	String username;
	
	@Column
	String authenticationId;
	
	public User()
	{
	}
	
	public User(Long id, String username)
	{
		this.id = id;
		this.username = username;
	}
	
	public User(User u)
	{
		id = u.getId();
		username = u.getUsername();
		authenticationId = u.getAuthenticationId();
	}
	
	public String getAuthenticationId()
	{
		return authenticationId;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setAuthenticationId(String authenticationId)
	{
		this.authenticationId = authenticationId;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
}

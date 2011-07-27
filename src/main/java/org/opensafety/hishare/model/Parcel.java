package org.opensafety.hishare.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name="parcels")
public class Parcel {
 
	private Long id;
	private String name;
	private String password;
	private String location;
	private Date expirationDate;
 
	public Parcel()
	{
		name = "Anon";
		password = "password";
		location = "/";
		expirationDate = new Date();
	}
 
	@Id
	@GeneratedValue
	public Long getId() {
		return this.id;
	}
 
	private void setId(Long id) {
		this.id = id;
	}

	@Column
	public String getName()
    {
    	return name;
    }

	public void setName(String name)
    {
    	this.name = name;
    }

	@Column
	public String getPassword()
    {
    	return password;
    }

	public void setPassword(String password)
    {
    	this.password = password;
    }

	@Column
	public String getLocation()
    {
    	return location;
    }

	public void setLocation(String location)
    {
    	this.location = location;
    }

	@Column
	public Date getExpirationDate()
    {
    	return expirationDate;
    }

	public void setExpirationDate(Date expirationDate)
    {
    	this.expirationDate = expirationDate;
    }

}
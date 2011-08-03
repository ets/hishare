package org.opensafety.hishare.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import javax.persistence.Transient;

@Entity
@Table(name = "parcels")
public class Parcel
{
	@Id
	private String id;
	
	@Column
	private String name;
	
	@Transient
	private String password;
	
	@Column
	private byte[] hashedPassword;
	
	@Column
	private byte[] salt;
	
	@Column
	private String payloadLocation;
	
	@Column
	private Date expirationDate;
	
	@Column
	private boolean dead;
	
	public Parcel()
	{
		id = null;
		name = "";
		password = "";
		hashedPassword = null;
		salt = null;
		payloadLocation = "";
		expirationDate = new Date();
		dead = false;
	}
	
	public Parcel(String id, String name, Date expirationDate, String payloadLocation,
	        String password, byte[] hashedPassword, byte[] salt)
	{
		this.id = id;
		this.name = name;
		this.password = password;
		this.hashedPassword = hashedPassword;
		this.salt = salt;
		this.payloadLocation = payloadLocation;
		this.expirationDate = expirationDate;
		this.dead = false;
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
	
	public byte[] getHashedPassword()
	{
		return hashedPassword;
	}
	
	public void setHashedPassword(byte[] hashedPassword)
	{
		this.hashedPassword = hashedPassword;
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
	
	public byte[] getSalt()
	{
		return salt;
	}
	
	public void setSalt(byte[] salt)
	{
		this.salt = salt;
	}
}
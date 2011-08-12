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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
		dead = false;
	}
	
	public boolean getDead()
	{
		return dead;
	}
	
	public Date getExpirationDate()
	{
		return expirationDate;
	}
	
	public byte[] getHashedPassword()
	{
		return hashedPassword;
	}
	
	public String getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getPayloadLocation()
	{
		return payloadLocation;
	}
	
	public byte[] getSalt()
	{
		return salt;
	}
	
	public void setDead(boolean dead)
	{
		this.dead = dead;
	}
	
	public void setExpirationDate(Date expirationDate)
	{
		this.expirationDate = expirationDate;
	}
	
	public void setHashedPassword(byte[] hashedPassword)
	{
		this.hashedPassword = hashedPassword;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setPayloadLocation(String location)
	{
		payloadLocation = location;
	}
	
	public void setSalt(byte[] salt)
	{
		this.salt = salt;
	}
}

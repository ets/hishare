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
import javax.persistence.Table;

@Entity
@Table(name = "authentication_servers")
public class AuthenticationServer
{
	@Id
	private String name;
	
	@Column
	private byte[] password;
	
	@Column
	private byte[] salt;
	
	public AuthenticationServer()
	{
		name = "";
		password = null;
		salt = null;
	}
	
	public AuthenticationServer(String id, String domain, byte[] password, byte[] salt)
	{
		this.name = domain;
		this.password = password;
		this.salt = salt;
	}

	public String getDomain()
    {
    	return name;
    }

	public void setDomain(String name)
    {
    	this.name = name;
    }

	public byte[] getPassword()
    {
    	return password;
    }

	public void setPassword(byte[] password)
    {
    	this.password = password;
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

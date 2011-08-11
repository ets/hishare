package org.opensafety.hishare.model.factories;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.model.User;
import org.opensafety.hishare.util.interfaces.Encryption;
import org.opensafety.hishare.util.interfaces.Encryption.CryptographyException;
import org.springframework.beans.factory.annotation.Autowired;

public class UserFactory
{
	@Autowired
	private Encryption encryption;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	private Long createUserId()
	{
		return UUID.randomUUID().getLeastSignificantBits();
	}
	
	private Long createUserId(String username, String authenticationServerName) throws CryptographyException
	{
		return encryption.hash(username+authenticationServerName);
	}
	
	public User createUser(String username, String authenticationServerName)
	{
		try
		{
			return new User(createUserId(username, authenticationServerName), username, authenticationServerName);
		}
		catch(CryptographyException e)
		{
			log.error("Failed to encrypt user Id");
			e.printStackTrace();
			return null;
		}
	}
}

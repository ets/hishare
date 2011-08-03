package org.opensafety.hishare.model.factories;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.UUID;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.util.interfaces.Encryption;
import org.opensafety.hishare.util.interfaces.Encryption.CryptographyException;
import org.springframework.beans.factory.annotation.Autowired;

public class ParcelFactory
{
	@Autowired
	Encryption encryption;
	
	private static Log log = LogFactory.getLog(ParcelFactory.class);
	
	private static final String RANDOM_GENERATOR = "SHA1PRNG";
	private static final int SALT_LENGTH = 64;
	
	public static String createParcelId()
	{
		return UUID.randomUUID().toString();
	}
	
	public static String createPayloadLocation()
	{
		return UUID.randomUUID().toString();
	}
	
	public Parcel createParcel(String parcelName, Integer daysToLive)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, daysToLive);
		Parcel newParcel = null;
		try
		{
			String password = encryption.createPassword();
			byte[] salt = encryption.createSalt();
			byte[] hashedPassword = encryption.hashPassword(password, salt);
			
			newParcel = new Parcel(createParcelId(), parcelName, calendar.getTime(),
			                       createPayloadLocation(), password, hashedPassword, salt);
		}
		catch(CryptographyException e)
		{
			log.error("Error: Parcel Creation Failed");
			e.printStackTrace();
		}
		return newParcel;
	}
}

package org.opensafety.hishare.dao.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.dao.interfaces.PayloadDao;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.util.interfaces.Encryption;
import org.opensafety.hishare.util.interfaces.Encryption.CryptographyException;
import org.springframework.beans.factory.annotation.Autowired;

public class FilePayloadDao implements PayloadDao
{
	private static String specifyLocation(String payloadLocation)
	{
		return "Parcels/" + payloadLocation + ".parcel";
	}
	
	@Autowired
	Encryption encryption;
	
	private Log log;
	
	public FilePayloadDao()
	{
		log = LogFactory.getLog(this.getClass());
	}
	
	public boolean deletePayload(String location)
	{
		String specificLocation = specifyLocation(location);
		File file = new File(specificLocation);
		return file.delete();
	}
	
	public byte[] retrievePayload(Parcel parcel)
	{
		log.info("RETRIEVING PAYLOAD: " + parcel.getPayloadLocation());
		
		String specificLocation = specifyLocation(parcel.getPayloadLocation());
		
		FileInputStream fileIn = null;
		byte[] encryptedPayload;
		
		try
		{
			File file = new File(specificLocation);
			fileIn = new FileInputStream(specificLocation);
			encryptedPayload = new byte[(int) file.length()];
			fileIn.read(encryptedPayload);
			fileIn.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		
		byte[] payload = null;
		try
		{
			payload = encryption.decryptPayload(parcel, encryptedPayload);
		}
		catch(CryptographyException e)
		{
			e.printStackTrace();
		}
		
		return payload;
	}
	
	public boolean savePayload(Parcel parcel, byte[] payload)
	{
		log.info("SAVING PAYLOAD: " + new String(payload) + " AT LOCATION: "
		        + parcel.getPayloadLocation());
		
		byte[] encryptedPayload;
		try
		{
			encryptedPayload = encryption.encryptPayload(parcel, payload);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		String specificLocation = specifyLocation(parcel.getPayloadLocation());
		
		FileOutputStream fileOut = null;
		try
		{
			fileOut = new FileOutputStream(specificLocation);
			fileOut.write(encryptedPayload);
			fileOut.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
}

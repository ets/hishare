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
			log.error("Encrypted file could not be found", e);
			return null;
		}
		catch(IOException e)
		{
			log.error("Could not read encrypted file", e);
			return null;
		}
		
		byte[] payload;
		try
		{
			payload = encryption.decryptPayload(parcel, encryptedPayload);
		}
		catch(CryptographyException e)
		{
			log.error("Payload could not be decrypted", e);
			return null;
		}
		
		return payload;
	}
	
	public boolean savePayload(Parcel parcel, byte[] payload)
	{
		byte[] encryptedPayload;
		try
		{
			encryptedPayload = encryption.encryptPayload(parcel, payload);
		}
		catch(CryptographyException e)
		{
			log.error("Payload could not be encrypted", e);
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
		catch(IOException e)
		{
			log.error("Could not write encrypted payload to file", e);
			return false;
		}
		
		return true;
	}
	
}

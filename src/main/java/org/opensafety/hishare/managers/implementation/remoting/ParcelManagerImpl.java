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
package org.opensafety.hishare.managers.implementation.remoting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.dao.interfaces.ParcelDao;
import org.opensafety.hishare.managers.interfaces.remoting.ParcelManager;
import org.opensafety.hishare.managers.interfaces.remoting.PayloadManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.util.interfaces.Encryption;
import org.opensafety.hishare.util.interfaces.Encryption.CryptographyException;
import org.springframework.beans.factory.annotation.Autowired;

public class ParcelManagerImpl implements ParcelManager
{
	@Autowired
	private ParcelDao parcelDao;
	@Autowired
	private PayloadManager payloadManager;
	@Autowired
	private Encryption encryption;
	
	private Map<String, Parcel> parcelUploads;
	private Map<String, Parcel> parcelDownloads;
	
	Log log = LogFactory.getLog(this.getClass());
	
	public ParcelManagerImpl()
	{
		parcelUploads = new HashMap<String, Parcel>();
	}
	
	public String beginDownload(Parcel parcel)
	{
		String transferKey = payloadManager.beginDownload(parcel);
		parcelDownloads.put(transferKey, parcel);
		return transferKey;
	}
	
	public String beginUpload(Parcel parcel)
	{
		String transferKey = payloadManager.beginUpload();
		parcelUploads.put(transferKey, parcel);
		return transferKey;
	}
	
	public Parcel closeUpload(String transferKey)
	{
		return parcelUploads.remove(transferKey);
	}
	
	public boolean deleteParcel(Parcel parcel)
	{
		boolean deletePayload = payloadManager.deletePayload(parcel.getPayloadLocation());
		boolean deleteParcel = parcelDao.deleteParcel(parcel);
		return deletePayload && deleteParcel;
	}
	
	public boolean downloadAvailable(String transferKey)
	{
		return payloadManager.downloadAvailable(transferKey);
	}
	
	public Integer getChunkSize()
	{
		return payloadManager.getChunkSize();
	}
	
	public Parcel getParcel(String parcelId, String parcelPassword)
	{
		if(parcelDao.verifyParcelAvailable(parcelId))
		{
			Parcel parcel = parcelDao.getById(parcelId);
			try
			{
				byte[] hashedPassword = encryption.hashPassword(parcelPassword, parcel.getSalt());
				if(Arrays.equals(hashedPassword, parcel.getHashedPassword()))
				{
					parcel.setPassword(parcelPassword);
					return parcel;
				}
				else
				{
					return null;
				}
			}
			catch(CryptographyException e)
			{
				log.error("Password Matching Threw Exception!");
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public void persistParcel(Parcel parcel, byte[] payload)
	{
		parcelDao.addParcel(parcel);
		payloadManager.persistPayload(parcel, payload);
	}
	
	public byte[] resolveUpload(String transferKey)
	{
		return payloadManager.resolveUpload(transferKey);
	}
	
	public void updateParcel(Parcel parcel)
	{
		parcelDao.updateParcel(parcel);
	}
	
	public Boolean uploadChunk(String transferKey, byte[] chunk)
	{
		return payloadManager.uploadChunk(transferKey, chunk);
	}
	
	/*
	 * Verify that this parcel id exists and that the password is correct
	 */
	public boolean verifyParcelAvailable(String parcelId, String parcelPassword)
	{
		if(parcelDao.verifyParcelAvailable(parcelId))
		{
			Parcel parcel = parcelDao.getById(parcelId);
			try
			{
				byte[] hashedPassword = encryption.hashPassword(parcelPassword, parcel.getSalt());
				return Arrays.equals(hashedPassword, parcel.getHashedPassword());
			}
			catch(CryptographyException e)
			{
				log.error("Password Matching Threw Exception!");
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}

package org.opensafety.hishare.managers.implementation.http;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.dao.interfaces.ParcelDao;
import org.opensafety.hishare.managers.interfaces.http.ParcelManager;
import org.opensafety.hishare.managers.interfaces.http.PayloadManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.util.interfaces.Encryption;
import org.springframework.beans.factory.annotation.Autowired;

public class ParcelManagerImpl implements ParcelManager
{
	@Autowired
	private ParcelDao parcelDao;
	@Autowired
	private PayloadManager payloadManager;
	@Autowired
	private Encryption encryption;
	
	Log log = LogFactory.getLog(this.getClass());
	
	public ParcelManagerImpl()
	{
	}
	
	public boolean deleteParcel(Parcel parcel)
	{
		boolean deletePayload = payloadManager.deletePayload(parcel.getPayloadLocation());
		boolean deleteParcel = parcelDao.deleteParcel(parcel);
		return deletePayload && deleteParcel;
	}
	
	public byte[] downloadPayload(Parcel parcel)
	{
		return payloadManager.downloadPayload(parcel);
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
			catch(Exception e)
			{
				log.error("Password Matching Threw Exception!");
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
	
	public void updateParcel(Parcel parcel)
	{
		parcelDao.updateParcel(parcel);
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
				log.info("VERIFYING PARCEL");
				log.info("Parcel Password: " + parcelPassword);
				log.info("Salt: " + parcel.getSalt());
				log.info("Expected Hash: " + new String(parcel.getHashedPassword()));
				log.info("  Actual Hash: " + new String(hashedPassword));
				
				return Arrays.equals(hashedPassword, parcel.getHashedPassword());
			}
			catch(Exception e)
			{
				log.error("Password Matching Threw Exception!");
				return false;
			}
		}
		log.info("Parcel not available");
		return false;
	}
}

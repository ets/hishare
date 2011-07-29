package org.opensafety.hishare.managers.implementation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.opensafety.hishare.dao.interfaces.ParcelDao;
import org.opensafety.hishare.managers.interfaces.ParcelManager;
import org.opensafety.hishare.model.Parcel;
import org.springframework.beans.factory.annotation.Autowired;

public class ParcelManagerImpl implements ParcelManager
{
	@Autowired
	private ParcelDao parcelDao;
	
	private Map<String, Parcel> parcelUploads;
	
	public static String createPayloadLocation()
	{
		return UUID.randomUUID().toString();
	}
	
	public static String createParcelPassword()
	{
		return Long.toHexString(UUID.randomUUID().getLeastSignificantBits());
	}
	
	public ParcelManagerImpl()
	{
		parcelUploads = new HashMap<String, Parcel>();
	}

	public Parcel createParcel(String parcelName, Integer daysToLive)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, daysToLive);
		Parcel newParcel = new Parcel(Parcel.createParcelId(), parcelName, calendar.getTime(), createParcelPassword(), createPayloadLocation());
		return newParcel;
	}
	
	public Parcel addParcel(Parcel parcel)
	{
		return parcelDao.addParcel(parcel);
	}

	public void beginUpload(String transferKey, Parcel parcel)
    {
		parcelUploads.put(transferKey, parcel);
    }
	
	public Parcel closeUpload(String transferKey)
	{
		return parcelUploads.remove(transferKey);
    }
}

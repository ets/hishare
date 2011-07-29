package org.opensafety.hishare.model.factories;

import java.util.Calendar;
import java.util.UUID;

import org.opensafety.hishare.model.Parcel;

public class ParcelFactory
{
	public static String createPayloadLocation()
	{
		return UUID.randomUUID().toString();
	}
	
	public static String createParcelPassword()
	{
		return Long.toHexString(UUID.randomUUID().getLeastSignificantBits());
	}
	
	public Parcel createParcel(String parcelName, Integer daysToLive)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, daysToLive);
		Parcel newParcel = new Parcel(Parcel.createParcelId(), parcelName, calendar.getTime(), createParcelPassword(), createPayloadLocation());
		return newParcel;
	}
}

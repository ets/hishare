package org.opensafety.hishare.managers.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface ParcelManager
{
	public Parcel createParcel(String parcelName, Integer daysToLive);

	public void beginUpload(String transferKey, Parcel parcel);
	
	public Parcel closeUpload(String transferKey);

	public Parcel addParcel(Parcel parcel);
}

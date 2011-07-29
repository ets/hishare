package org.opensafety.hishare.managers.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface ParcelManager
{
	public void persistParcel(Parcel parcel);
	
	public void updateParcel(Parcel parcel);
	
	public void beginUpload(String transferKey, Parcel parcel);
	
	public Parcel closeUpload(String transferKey);
}

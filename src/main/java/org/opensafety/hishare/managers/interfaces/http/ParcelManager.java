package org.opensafety.hishare.managers.interfaces.http;

import org.opensafety.hishare.model.Parcel;

public interface ParcelManager
{
	public boolean deleteParcel(Parcel parcel);
	
	public byte[] downloadPayload(Parcel parcel);
	
	public Parcel getParcel(String parcelId, String parcelPassword);
	
	public void persistParcel(Parcel parcel, byte[] payload);
	
	public void updateParcel(Parcel parcel);
	
	public boolean verifyParcelAvailable(String parcelId, String parcelPassword);
}

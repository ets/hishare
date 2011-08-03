package org.opensafety.hishare.managers.interfaces.remoting;

import org.opensafety.hishare.model.Parcel;

public interface ParcelManager
{
	public String beginDownload(Parcel parcel);
	
	public String beginUpload(Parcel parcel);
	
	public Parcel closeUpload(String transferKey);
	
	public boolean deleteParcel(Parcel parcel);
	
	public boolean downloadAvailable(String transferKey);
	
	public Integer getChunkSize();
	
	public Parcel getParcel(String parcelId, String parcelPassword);
	
	public void persistParcel(Parcel parcel, byte[] payload);
	
	public byte[] resolveUpload(String transferKey);
	
	public void updateParcel(Parcel parcel);
	
	public Boolean uploadChunk(String transferKey, byte[] chunk);
	
	public boolean verifyParcelAvailable(String parcelId, String parcelPassword);
}

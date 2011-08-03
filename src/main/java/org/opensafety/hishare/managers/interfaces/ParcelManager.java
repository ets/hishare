package org.opensafety.hishare.managers.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface ParcelManager
{
	public void persistParcel(Parcel parcel, byte[] payload);
	
	public void updateParcel(Parcel parcel);
	
	public String beginUpload(Parcel parcel);
	
	public Parcel closeUpload(String transferKey);

	public boolean verifyParcelAvailable(String parcelId, String parcelPassword);

	public Parcel getParcel(String parcelId, String parcelPassword);

	public String beginDownload(Parcel parcel);

	public boolean deleteParcel(Parcel parcel);

	public byte[] resolveUpload(String transferKey);

	public Integer getChunkSize();

	public Boolean uploadChunk(String transferKey, byte[] chunk);

	public boolean downloadAvailable(String transferKey);

	public byte[] downloadPayload(Parcel parcel);
}

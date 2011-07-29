package org.opensafety.hishare.managers.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface PayloadManager
{
	String beginUpload();

	Boolean uploadChunk(String transferKey, byte[] chunk);

	byte[] resolveUpload(String transferKey);

	Integer getChunkSize();

	boolean storePayload(Parcel parcel, byte[] payload);
	
}

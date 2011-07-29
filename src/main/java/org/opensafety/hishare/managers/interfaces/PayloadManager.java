package org.opensafety.hishare.managers.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface PayloadManager
{
	boolean persistPayload(Parcel parcel, byte[] payload);
	
	Integer getChunkSize();
	
	String beginUpload();
	
	Boolean uploadChunk(String transferKey, byte[] chunk);

	byte[] resolveUpload(String transferKey);
}

package org.opensafety.hishare.managers.interfaces.remoting;

import org.opensafety.hishare.model.Parcel;

public interface PayloadManager
{
	String beginDownload(Parcel parcel);
	
	String beginUpload();
	
	boolean deletePayload(String payloadLocation);
	
	boolean downloadAvailable(String transferKey);
	
	Integer getChunkSize();
	
	boolean persistPayload(Parcel parcel, byte[] payload);
	
	byte[] resolveUpload(String transferKey);
	
	Boolean uploadChunk(String transferKey, byte[] chunk);
}

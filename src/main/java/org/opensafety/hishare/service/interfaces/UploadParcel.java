package org.opensafety.hishare.service.interfaces;

public interface UploadParcel
{
	/**
	 * Size in bytes of the bytes to be uplaoded
	 */
	Integer getChunkSize();
	
	String beginParcelUpload(String username, String authenticationId, String parcelName,
	                         Integer daysToLive);
	
	Boolean uploadParcelChunk(String username, String authenticationId, String transferKey,
	                          byte[] chunk);
	
	String finishParcelUpload(String username, String authenticationId, String transferKey);
}

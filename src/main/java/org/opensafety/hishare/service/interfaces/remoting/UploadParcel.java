package org.opensafety.hishare.service.interfaces.remoting;

public interface UploadParcel
{
	String beginParcelUpload(String username, String authenticationId, String parcelName,
	                         Integer daysToLive);
	
	String finishParcelUpload(String username, String authenticationId, String transferKey);
	
	/**
	 * Size in bytes of the bytes to be uplaoded
	 */
	Integer getChunkSize();
	
	Boolean uploadParcelChunk(String username, String authenticationId, String transferKey,
	                          byte[] chunk);
}

package org.opensafety.hishare.service.interfaces.remoting;

public interface DownloadParcel
{
	String beginParcelDownload(String username, String authenticationId, String parcelId,
	                           String parcelPassword);
	
	boolean downloadAvailable(String username, String authenticationId, String parcelId,
	                          String parcelPassword, String transferKey);
	
	byte[] downloadParcelChunk(String username, String authenticationId, String parcelId,
	                           String parcelPassword, String transferKey);
	
	String finishParcelDownload(String username, String authenticationId, String parcelId,
	                            String parcelPassword, boolean success);
	
	/**
	 * Size in bytes of the bytes to be downloaded
	 */
	Integer getChunkSize();
}

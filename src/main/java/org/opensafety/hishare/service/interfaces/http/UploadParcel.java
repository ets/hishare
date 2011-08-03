package org.opensafety.hishare.service.interfaces.http;

public interface UploadParcel
{
	String[] uploadParcel(String username, String authenticationId, String parcelName,
	                      Integer daysToLive, byte[] payload);
}

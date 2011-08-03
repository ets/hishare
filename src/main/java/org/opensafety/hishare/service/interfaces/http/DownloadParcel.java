package org.opensafety.hishare.service.interfaces.http;

public interface DownloadParcel
{
	byte[] downloadParcel(String username, String authenticationId, String parcelId,
	                      String parcelPassword);
}

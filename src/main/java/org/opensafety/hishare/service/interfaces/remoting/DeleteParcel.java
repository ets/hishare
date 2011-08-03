package org.opensafety.hishare.service.interfaces.remoting;

public interface DeleteParcel
{
	
	String deleteParcel(String username, String authenticationId, String parcelId,
	                    String parcelPassword);
	
}

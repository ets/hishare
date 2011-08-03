package org.opensafety.hishare.managers.interfaces.http;

import org.opensafety.hishare.model.Parcel;

public interface PayloadManager
{
	boolean deletePayload(String payloadLocation);
	
	public byte[] downloadPayload(Parcel parcel);
	
	boolean persistPayload(Parcel parcel, byte[] payload);
}

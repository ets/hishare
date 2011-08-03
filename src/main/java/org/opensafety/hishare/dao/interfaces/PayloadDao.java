package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface PayloadDao
{
	boolean deletePayload(String location);
	
	byte[] retrievePayload(Parcel parcel);
	
	boolean savePayload(Parcel parcel, byte[] payload);
}

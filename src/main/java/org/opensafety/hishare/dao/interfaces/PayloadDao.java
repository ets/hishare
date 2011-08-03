package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface PayloadDao
{
	boolean savePayload(Parcel parcel, byte[] payload);
	byte[] retrievePayload(Parcel parcel);
	boolean deletePayload(String location);
}

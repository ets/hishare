package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface ParcelDao
{
	Parcel getById(String id);
	
	void addParcel(Parcel parcel);
	
	void updateParcel(Parcel parcel);
	
	boolean verifyParcelAvailable(String parcelId);
	
	boolean deleteParcel(Parcel parcel);
}

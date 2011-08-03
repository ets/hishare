package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface ParcelDao
{
	void addParcel(Parcel parcel);
	
	boolean deleteParcel(Parcel parcel);
	
	Parcel getById(String id);
	
	void updateParcel(Parcel parcel);
	
	boolean verifyParcelAvailable(String parcelId);
}

package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface ParcelDao
{
	Parcel getById(Long id);
	Parcel addParcel(Parcel parcel);
	void updateParcel(Parcel parcel);
}

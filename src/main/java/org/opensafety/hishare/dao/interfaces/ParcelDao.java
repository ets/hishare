package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface ParcelDao
{
	Parcel getById(Long id);
	void addParcel(Parcel parcel);
	void updateParcel(Parcel parcel);
}

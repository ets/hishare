package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.Parcel;

public interface ParcelDao
{
	Parcel getById(Long id);
	Parcel createParcel(Parcel parcel);
	void checkHPD();
}

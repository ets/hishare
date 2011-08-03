package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;

public interface PermissionDao
{
	void addPermission(Permission permission);
	
	boolean deleteAllWithParcel(Parcel parcel);
	
	boolean hasEither(User user, Parcel parcel, PermissionLevel[] permissions);
	
	void updatePermission(Permission permission);
}

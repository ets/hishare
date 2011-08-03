package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;

public interface PermissionDao
{
	void addPermission(Permission permission);
	void updatePermission(Permission permission);
	boolean hasEither(User user, Parcel parcel, PermissionLevel[] permissions);
	boolean deleteAllWithParcel(Parcel parcel);
}

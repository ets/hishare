package org.opensafety.hishare.managers.interfaces;

import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;

public interface PermissionManager
{
	void persistPermission(Permission permission);

	boolean hasDownloadPermission(User user, Parcel parcel);

	boolean hasDeletePermission(User user, Parcel parcel);

	boolean deletePermissions(Parcel parcel);
}

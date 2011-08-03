package org.opensafety.hishare.managers.interfaces.remoting;

import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.User;

public interface PermissionManager
{
	boolean deletePermissions(Parcel parcel);
	
	boolean hasDeletePermission(User user, Parcel parcel);
	
	boolean hasDownloadPermission(User user, Parcel parcel);
	
	void persistPermission(Permission permission);
}

package org.opensafety.hishare.model.factories;

import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;

public class PermissionFactory
{
	public Permission createPermission(Parcel parcel, User user, PermissionLevel permissionLevel)
	{
		Permission permission = new Permission(parcel, user, permissionLevel);
		return permission;
	}
}

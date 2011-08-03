package org.opensafety.hishare.model.factories;

import java.util.UUID;

import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;

public class PermissionFactory
{
	public static Long createPermissionId()
	{
		return UUID.randomUUID().getLeastSignificantBits();
	}
	
	public Permission createPermission(Parcel parcel, User user, PermissionLevel permissionLevel)
	{
		Permission permission = new Permission(createPermissionId(), parcel, user, permissionLevel);
		return permission;
	}
}

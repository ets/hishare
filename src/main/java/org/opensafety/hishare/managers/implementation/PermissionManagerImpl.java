package org.opensafety.hishare.managers.implementation;

import org.opensafety.hishare.dao.interfaces.ParcelDao;
import org.opensafety.hishare.dao.interfaces.PermissionDao;
import org.opensafety.hishare.dao.interfaces.UserDao;
import org.opensafety.hishare.managers.interfaces.PermissionManager;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class PermissionManagerImpl implements PermissionManager
{
	@Autowired
	PermissionDao permissionDao;
	@Autowired
	UserDao userDao;
	@Autowired
	ParcelDao parcelDao;

	public void addPermission(Parcel parcel, User user,
            PermissionLevel permissionLevel)
    {
		Permission permission = new Permission(parcel, user, permissionLevel);
		permission = permissionDao.addPermission(permission);
		parcel.addPermission(permission);
		user.addPermission(permission);
    }
}

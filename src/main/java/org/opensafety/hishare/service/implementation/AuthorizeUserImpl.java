package org.opensafety.hishare.service.implementation;

import org.opensafety.hishare.managers.interfaces.ParcelManager;
import org.opensafety.hishare.managers.interfaces.PermissionManager;
import org.opensafety.hishare.managers.interfaces.UserManager;
import org.opensafety.hishare.service.interfaces.AuthorizeUser;

import org.opensafety.hishare.dao.interfaces.ParcelDao;
import org.opensafety.hishare.dao.interfaces.PermissionDao;
import org.opensafety.hishare.dao.interfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorizeUserImpl implements AuthorizeUser
{
	@Autowired
	private ParcelManager parcelManager;
	@Autowired
	private PermissionManager permissionManager;
	@Autowired
	private UserManager userManager;
}

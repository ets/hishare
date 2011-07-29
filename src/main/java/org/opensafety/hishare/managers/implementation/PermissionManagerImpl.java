package org.opensafety.hishare.managers.implementation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	
	private Log log = LogFactory.getLog(this.getClass());

	public void persistPermission(Permission permission)
    {
		log.info("Persisting Permission");
		permissionDao.addPermission(permission);
    }
}

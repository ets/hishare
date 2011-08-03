package org.opensafety.hishare.managers.implementation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	
	private PermissionLevel[] downloadPermissions = { PermissionLevel.OWNER,
	                                                 PermissionLevel.RECEIVER };
	
	private PermissionLevel[] deletePermissions = { PermissionLevel.OWNER };
	
	public void persistPermission(Permission permission)
	{
		log.info("Persisting Permission");
		
		if(!permissionDao.hasEither(permission.getUser(), permission.getParcel(),
		                            new PermissionLevel[] { permission.getPermission() }))
		{
			permissionDao.addPermission(permission);
		}
	}
	
	public boolean hasDownloadPermission(User user, Parcel parcel)
	{
		return permissionDao.hasEither(user, parcel, downloadPermissions);
	}
	
	public boolean hasDeletePermission(User user, Parcel parcel)
	{
		return permissionDao.hasEither(user, parcel, deletePermissions);
	}
	
	public boolean deletePermissions(Parcel parcel)
	{
		return permissionDao.deleteAllWithParcel(parcel);
	}
}

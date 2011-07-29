package org.opensafety.hishare.dao.interfaces;

import org.opensafety.hishare.model.Permission;

public interface PermissionDao
{
	void addPermission(Permission permission);
	void updatePermission(Permission permission);
}

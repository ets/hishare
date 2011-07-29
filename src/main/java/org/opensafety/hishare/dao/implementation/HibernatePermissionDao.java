package org.opensafety.hishare.dao.implementation;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.dao.interfaces.PermissionDao;

@Repository
@Transactional
public class HibernatePermissionDao extends HibernateDaoSupport implements PermissionDao
{
	private Log log = LogFactory.getLog(this.getClass());

	public void addPermission(Permission permission)
    {
		log.info("Saving Permission");
		getSession().save(permission);
    }
	
	public void updatePermission(Permission permission)
	{
		log.info("Updating Permission");
		getSession().merge(permission);
	}
}

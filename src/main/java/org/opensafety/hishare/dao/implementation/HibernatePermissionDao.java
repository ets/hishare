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

	public Permission addPermission(Permission permission)
    {
	    // TODO Auto-generated method stub
	    return null;
    }
}

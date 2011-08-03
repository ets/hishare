package org.opensafety.hishare.dao.implementation;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;
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
	
	public boolean hasEither(User user, Parcel parcel, PermissionLevel[] permissions)
	{
		return getSession().createCriteria(Permission.class).add(Restrictions.in("permission",
		                                                                         permissions))
		                   .createAlias("user", "u")
		                   .createAlias("parcel", "p")
		                   .add(Restrictions.eq("u.id", user.getId()))
		                   .add(Restrictions.eq("p.id", parcel.getId()))
		                   .setMaxResults(1)
		                   .uniqueResult() != null;
	}

	public boolean deleteAllWithParcel(Parcel parcel)
    {
		String deleteQuery = "DELETE FROM Permission WHERE parcel = :parcel";
		
		Query delete = getSession().createQuery(deleteQuery);
		
		delete.setEntity("parcel", parcel);
		
		int rowCount = delete.executeUpdate();
		
		return rowCount > 0;
    }
}

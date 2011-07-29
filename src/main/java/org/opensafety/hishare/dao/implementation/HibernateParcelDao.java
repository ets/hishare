package org.opensafety.hishare.dao.implementation;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Restrictions;

import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.dao.interfaces.ParcelDao;

@Repository
@Transactional
public class HibernateParcelDao extends HibernateDaoSupport implements ParcelDao
{
	private Log log = LogFactory.getLog(this.getClass());
	
	public Parcel getById(Long id)
	{
		return (Parcel) getSessionFactory().getCurrentSession().createCriteria(Parcel.class)
				.add(Restrictions.eq("id", id));
	}
	
	public Parcel addParcel(Parcel parcel)
	{
		getSessionFactory().getCurrentSession().save(parcel);
		return parcel;
	}
	
	public void updateParcel(Parcel parcel)
	{
		getSession().merge(parcel);
	}
}

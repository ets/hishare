package org.opensafety.hishare.dao.implementation;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Restrictions;

import org.opensafety.hishare.dao.interfaces.ParcelDao;
import org.opensafety.hishare.model.Parcel;

@Repository
@Transactional
public class HibernateParcelDao extends HibernateDaoSupport implements ParcelDao
{
	private Log log = LogFactory.getLog(this.getClass());
	
	public Parcel getById(String id)
	{
		return (Parcel) getSession().createCriteria(Parcel.class)
									.add(Restrictions.idEq(id))
									.uniqueResult();
	}
	
	public void addParcel(Parcel parcel)
	{
		log.info("ADDING PARCEL");
		log.info("Password: "+parcel.getPassword());
		log.info("Salt: "+parcel.getSalt());
		log.info("Hashed Password: "+parcel.getHashedPassword());
		getSessionFactory().getCurrentSession().save(parcel);
	}
	
	public void updateParcel(Parcel parcel)
	{
		getSession().merge(parcel);
	}
	
	public boolean verifyParcelAvailable(String parcelId)
	{
		return getSession().createCriteria(Parcel.class)
						   .add(Restrictions.idEq(parcelId))
		                   .uniqueResult() != null;
	}

	public boolean deleteParcel(Parcel parcel)
    {
		getSession().delete(parcel);
	    return true;
    }
}

package org.opensafety.hishare.dao.implementation;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.dao.interfaces.ParcelDao;

@Repository
@Transactional
public class HibernateParcelDao extends HibernateDaoSupport implements ParcelDao
{
	private Log log = LogFactory.getLog(this.getClass());
	
	public Parcel getById(Long id)
	{
		return (Parcel) getSessionFactory().getCurrentSession().createQuery(
				"from Parcel parcel where parcel.id=?").setParameter(0, id)
				.uniqueResult();
	}
	
	public Parcel createParcel(Parcel parcel)
	{
		log.info("CREATING PARCEL");
		getSessionFactory().getCurrentSession().save(parcel);
		log.info("Done Creating Parcel");
		
		return null;
	}
	
	public void checkHPD()
	{
		log.info("Test call to HPD Succeeded");
	}
}

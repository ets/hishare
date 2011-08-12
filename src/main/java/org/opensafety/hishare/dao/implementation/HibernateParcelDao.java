/*******************************************************************************
 * Copyright 2011 Pascal Metrics
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.opensafety.hishare.dao.implementation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Restrictions;
import org.opensafety.hishare.dao.interfaces.ParcelDao;
import org.opensafety.hishare.model.Parcel;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HibernateParcelDao extends HibernateDaoSupport implements ParcelDao
{
	private Log log = LogFactory.getLog(this.getClass());
	
	public void addParcel(Parcel parcel)
	{
		getSessionFactory().getCurrentSession().save(parcel);
	}
	
	public boolean deleteParcel(Parcel parcel)
	{
		getSession().delete(parcel);
		return true;
	}
	
	public Parcel getById(String id)
	{
		return (Parcel) getSession().createCriteria(Parcel.class).add(Restrictions.idEq(id))
		                            .uniqueResult();
	}
	
	public void updateParcel(Parcel parcel)
	{
		getSession().merge(parcel);
	}
	
	public boolean verifyParcelAvailable(String parcelId)
	{
		return getSession().createCriteria(Parcel.class).add(Restrictions.idEq(parcelId))
		                   .uniqueResult() != null;
	}
}

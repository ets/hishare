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
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.opensafety.hishare.dao.interfaces.PermissionDao;
import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.model.Permission;
import org.opensafety.hishare.model.PermissionLevel;
import org.opensafety.hishare.model.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HibernatePermissionDao extends HibernateDaoSupport implements PermissionDao
{
	private Log log = LogFactory.getLog(this.getClass());
	
	public void addPermission(Permission permission)
	{
		getSession().save(permission);
	}
	
	public boolean deleteAllWithParcel(Parcel parcel)
	{
		String deleteQuery = "DELETE FROM Permission WHERE parcel = :parcel";
		
		Query delete = getSession().createQuery(deleteQuery);
		
		delete.setEntity("parcel", parcel);
		
		int rowCount = delete.executeUpdate();
		
		return rowCount > 0;
	}
	
	public boolean hasEither(User user, Parcel parcel, PermissionLevel[] permissions)
	{
		return getSession().createCriteria(Permission.class)
		                   .add(Restrictions.in("permission", permissions))
		                   .createAlias("user", "u")
		                   .createAlias("parcel", "p")
		                   .add(Restrictions.eq("u.id", user.getId()))
		                   .add(Restrictions.eq("p.id", parcel.getId()))
		                   .setMaxResults(1)
		                   .uniqueResult() != null;
	}
	
	public void updatePermission(Permission permission)
	{
		getSession().merge(permission);
	}
}

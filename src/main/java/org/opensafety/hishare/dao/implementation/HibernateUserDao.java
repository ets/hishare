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
import org.opensafety.hishare.dao.interfaces.UserDao;
import org.opensafety.hishare.model.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HibernateUserDao extends HibernateDaoSupport implements UserDao
{
	private Log log = LogFactory.getLog(this.getClass());
	
	public void addUser(User user)
	{
		getSession().save(user);
	}
	
	public User getByName(String username)
	{
		return (User) getSession().createCriteria(User.class)
		                          .add(Restrictions.eq("username", username)).setMaxResults(1)
		                          .uniqueResult();
	}
	
	public void updateUser(User user)
	{
		getSession().merge(user);
	}
	
	public boolean userExists(Long id)
	{
		return getSession().createCriteria(User.class).add(Restrictions.eq("id", id))
		                   .setMaxResults(1).uniqueResult() != null;
	}
	
	public boolean userExists(String username)
	{
		return getSession().createCriteria(User.class).add(Restrictions.eq("username", username))
		                   .setMaxResults(1).uniqueResult() != null;
	}
}

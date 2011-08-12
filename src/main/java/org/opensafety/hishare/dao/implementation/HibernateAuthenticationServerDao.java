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
import org.opensafety.hishare.dao.interfaces.AuthenticationServerDao;
import org.opensafety.hishare.dao.interfaces.UserDao;
import org.opensafety.hishare.model.AuthenticationServer;
import org.opensafety.hishare.model.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HibernateAuthenticationServerDao extends HibernateDaoSupport implements
        AuthenticationServerDao
{
	private Log log = LogFactory.getLog(this.getClass());
	
	public byte[] getSalt(String name)
	{
		AuthenticationServer authServer = (AuthenticationServer) getSession().createCriteria(AuthenticationServer.class)
		                                                                     .add(Restrictions.eq("name",
		                                                                                          name))
		                                                                     .setMaxResults(1)
		                                                                     .uniqueResult();
		return authServer.getSalt();
	}
	
	public boolean serverExists(String name)
	{
		return getSession().createCriteria(AuthenticationServer.class)
		                   .add(Restrictions.eq("name", name)).setMaxResults(1).uniqueResult() != null;
	}
	
	public byte[] getPassword(String authenticationServerName)
	{
		AuthenticationServer authServer = (AuthenticationServer) getSession().createCriteria(AuthenticationServer.class)
		                                                                     .add(Restrictions.eq("name",
		                                                                                          authenticationServerName))
		                                                                     .setMaxResults(1)
		                                                                     .uniqueResult();
		return authServer.getPassword();
	}
	
	public AuthenticationServer getByName(String authenticationServerName)
	{
		return (AuthenticationServer) getSession().createCriteria(AuthenticationServer.class)
		                                          .add(Restrictions.eq("name",
		                                                               authenticationServerName))
		                                          .setMaxResults(1).uniqueResult();
	}
	
	public void updateServer(AuthenticationServer server)
	{
		getSession().merge(server);
	}

	public void addServer(AuthenticationServer server)
	{
		getSession().save(server);
    }
}

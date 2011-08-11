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

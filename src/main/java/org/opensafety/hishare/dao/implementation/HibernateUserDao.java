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

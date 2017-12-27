package com.fatin.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fatin.model.User;

@Repository
public class IUserDaoImpl implements IUserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void save(User domain) {
		sessionFactory.getCurrentSession().save(domain);
	}
	
	public User findUserByUserName(String userName){
		String query = "from User where userName = :userName ";
		Query q = sessionFactory.getCurrentSession().createQuery(query);
		q.setString("userName", userName);
		
		return (User) q.uniqueResult();
	}
	

}

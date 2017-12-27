package com.fatin.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fatin.model.UserProfile;

@Repository
public class UserProfileDaoImpl implements IUserProfileDao {

	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void save(UserProfile domain) {
		sessionFactory.getCurrentSession().save(domain);
	}

}

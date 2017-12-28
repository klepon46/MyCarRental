package com.fatin.dao;

import java.util.List;

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
		sessionFactory.getCurrentSession().saveOrUpdate(domain);
	}

	public User findUserByUserName(String userName) {
		String query = "from User where userName = :userName ";
		Query q = sessionFactory.getCurrentSession().createQuery(query);
		q.setString("userName", userName);

		return (User) q.uniqueResult();
	}

	public User findByUserID(int userID) {
		String query = "from User where id = :userID ";
		Query q = sessionFactory.getCurrentSession().createQuery(query);
		q.setInteger("userID", userID);

		return (User) q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUserByRole(String role) {
		String query = "from User where role = :role ";
		Query q = sessionFactory.getCurrentSession().createQuery(query);
		q.setString("role", role);

		return q.list();
	}
}

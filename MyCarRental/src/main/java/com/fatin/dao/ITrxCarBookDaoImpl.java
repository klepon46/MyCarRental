package com.fatin.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fatin.model.TrxCarBook;

@Repository
public class ITrxCarBookDaoImpl implements ITrxCarBookDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(TrxCarBook domain) {
		sessionFactory.getCurrentSession().saveOrUpdate(domain);
	}

	public List<TrxCarBook> findByCustomerID(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

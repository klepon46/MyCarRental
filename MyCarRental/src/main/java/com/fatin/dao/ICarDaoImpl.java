package com.fatin.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fatin.model.Car;

@Repository
public class ICarDaoImpl implements ICarDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(Car domain) {
		sessionFactory.getCurrentSession().saveOrUpdate(domain);
	}

	@SuppressWarnings("unchecked")
	public List<Car> findCarsByCompanyId(int companyId) {
		String hql = "from Car where companyID = :companyId";
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		q.setInteger("companyId", companyId);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Car> findByTypeAndRate(String type, int rate) {
		String hql = "from Car where carType = :type or carRate <= :rate) ";
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		q.setString("type", type);
		q.setInteger("rate", rate);
		
		return q.list();
	}
	
}

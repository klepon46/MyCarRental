package com.fatin.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
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
		String hql = "from Car where carType = :type and carRate <= :rate) ";
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		q.setString("type", type);
		q.setInteger("rate", rate);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Car> findByTypeRateAndDate(String type, int rate, Date date) {
		
		String sql = "select a.* from Car a "
				+ "left join trx_car_Book b "
				+ "on a.CAR_ID = b.CAR_ID "
				+ "where b.END_DATE < :date OR END_DATE IS NULL ";
		SQLQuery sq = sessionFactory.getCurrentSession().createSQLQuery(sql);
		sq.setDate("date", date);
		sq.addEntity(Car.class);
		
		return sq.list();
	}
	
}

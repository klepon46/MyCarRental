package com.fatin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatin.dao.ICarDao;
import com.fatin.model.Car;

@Service(value="iCarSvc")
@Transactional
public class ICarSvcImpl implements ICarSvc {
	
	@Autowired
	private ICarDao dao;
	
	public void save(Car domain) {
		dao.save(domain);
	}

	public List<Car> findCarsByCompanyId(int companyId) {
		return dao.findCarsByCompanyId(companyId);
	}

	public List<Car> findByTypeAndRate(String type, int rate) {
		return dao.findByTypeAndRate(type, rate);
	}

	public List<Car> findByTypeRateAndDate(String type, int rate, Date date) {
		return dao.findByTypeRateAndDate(type, rate, date);
	}

}

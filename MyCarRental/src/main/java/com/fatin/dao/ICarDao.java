package com.fatin.dao;

import java.util.List;

import com.fatin.model.Car;

public interface ICarDao {

	public void save(Car domain);
	public List<Car> findCarsByCompanyId(int companyId);
	public List<Car> findByTypeAndRate(String type, int rate);
	
}

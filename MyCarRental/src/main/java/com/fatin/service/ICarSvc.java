package com.fatin.service;

import java.util.Date;
import java.util.List;

import com.fatin.model.Car;

public interface ICarSvc {
	
	public void save(Car domain);
	public List<Car> findCarsByCompanyId(int companyId);
	public List<Car> findByTypeAndRate(String type, int rate);
	public List<Car> findByTypeRateAndDate(String type, int rate, Date date);
}

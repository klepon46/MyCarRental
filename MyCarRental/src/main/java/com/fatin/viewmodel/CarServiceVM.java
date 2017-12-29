package com.fatin.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import com.fatin.model.Car;
import com.fatin.service.ICarSvc;
import com.fatin.util.AdrStringUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class CarServiceVM {

	@WireVariable
	private ICarSvc iCarSvc;
	
	private Car car;
	private List<Car> cars;
	
	@Init
	public void init(){
		car = new Car();
		
		int companyId = AdrStringUtil.getCurrentUserID();
		cars = iCarSvc.findCarsByCompanyId(companyId);
	}

	@Command
	@NotifyChange("cars")
	public void addCar(){
		int carID = Integer.parseInt(AdrStringUtil.generateIdSecond());
		int companyID = AdrStringUtil.getCurrentUserID();
		
		car.setCarID(carID);
		car.setCompanyID(companyID);
		iCarSvc.save(car);
		
		cars = iCarSvc.findCarsByCompanyId(companyID);
	}
	
	@Command
	public void back(){
		AdrStringUtil.navigate("dashboardCompany.zul");
	}
	
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}


	public List<Car> getCars() {
		return cars;
	}


	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
}

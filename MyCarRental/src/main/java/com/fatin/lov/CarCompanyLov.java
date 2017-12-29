package com.fatin.lov;

import java.util.List;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import com.fatin.model.Car;
import com.fatin.service.ICarSvc;

@VariableResolver(DelegatingVariableResolver.class)
public class CarCompanyLov {

	@WireVariable
	private ICarSvc iCarSvc;

	private List<Car> cars;

	@Init
	public void init(@ExecutionArgParam("ID") int id) {
		cars = iCarSvc.findCarsByCompanyId(id);

	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
}

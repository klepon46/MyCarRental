package com.fatin.viewmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import com.fatin.model.Car;
import com.fatin.model.User;
import com.fatin.service.ICarSvc;
import com.fatin.service.IUserSvc;
import com.fatin.util.AdrStringUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class UserAndCompanyListsVM {

	@WireVariable
	private IUserSvc iUserSvc;
	
	@WireVariable
	private ICarSvc iCarSvc;
	
	private List<User> users;
	private List<User> companies;
	private List<Car> cars;
	
	@Init
	public void init(){
		users = iUserSvc.findAllUserByRole("USER");
		companies = iUserSvc.findAllUserByRole("COMPANY");
	}
	
	@Command
	public void showCompanyCars(@BindingParam("me") User user){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", user.getId());
		
		BindUtils.postGlobalCommand(null, null, "onShowCars", map);
		AdrStringUtil.navigate("companyCars.zul");
	}
	
	@GlobalCommand
	@NotifyChange("cars")
	public void onShowCars(@BindingParam("ID")Integer id){
		cars = iCarSvc.findCarsByCompanyId(id);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getCompanies() {
		return companies;
	}

	public void setCompanies(List<User> companies) {
		this.companies = companies;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
}

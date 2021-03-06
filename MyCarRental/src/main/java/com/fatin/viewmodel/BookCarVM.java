package com.fatin.viewmodel;

import java.util.Date;
import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import com.fatin.model.Car;
import com.fatin.model.SearchParam;
import com.fatin.model.TrxCarBook;
import com.fatin.service.ICarSvc;
import com.fatin.service.ITrxCarBookSvc;
import com.fatin.util.AdrStringUtil;

@VariableResolver(DelegatingVariableResolver.class)
public class BookCarVM {

	@WireVariable
	private ITrxCarBookSvc iTrxCarBookSvc;

	@WireVariable
	private ICarSvc iCarSvc;

	private TrxCarBook trxCarBook;

	private int totalPassenger;
	private int ratePerDay;
	private int diffDays;

	private List<Car> cars;
	
	private Date startDate;
	private Date endDate;
	
	

	@Init
	public void init() {
		startDate = new Date();
		endDate = new Date();
		trxCarBook = new TrxCarBook();
	}

	@Command
	@NotifyChange("cars")
	public void find() {
		String carType = determineCarType();

		LocalDate jodaStart = new LocalDate(startDate);
		LocalDate jodaEnd = new LocalDate(endDate);

		diffDays = Days.daysBetween(jodaStart, jodaEnd).getDays();

		if (diffDays <= 0) {
			Clients.showNotification("End date must after start date", 
					"info", null, null, 1500);
			return;
		}
		
		cars = iCarSvc.findByTypeRateAndDate(carType, ratePerDay, startDate);
	}
	
	@Command
	public void back(){
		AdrStringUtil.navigate("dashboardUser.zul");
	}

	@Command
	@NotifyChange({"cars","trxCarBook","totalPassenger","ratePerDay"})
	public void bookTheCar(@BindingParam("me") Car car) {
		int bookID = Integer.parseInt(AdrStringUtil.generateIdSecond());

		trxCarBook.setBookID(bookID);
		trxCarBook.setCarID(car.getCarID());
		trxCarBook.setCustomerID(AdrStringUtil.getCurrentUserID());
		trxCarBook.setStartDate(startDate);
		trxCarBook.setEndDate(endDate);

		trxCarBook.setTotalPrice(diffDays * car.getCarRate());

		iTrxCarBookSvc.save(trxCarBook);
		Clients.showNotification("Car Successfully booked", "info", 
				null, null, 1500);
		
		clearField();
	}

	private String determineCarType() {
		SearchParam searchParam = new SearchParam();
		Facts facts = new Facts();
		facts.put("passengger", totalPassenger);
		facts.put("car", searchParam);

		Rule rule1 = new MVELRule().name("car rule").description("check passenger").priority(1)
				.when("passengger > 4 && passengger <= 7 ").then("car.carType = 'MPV'; ");

		Rule rule2 = new MVELRule().name("car rule 2").description("check passenger").priority(1)
				.when("passengger > 0 && passengger < 5 ").then("car.carType = 'SEDAN'; ");

		Rules rules = new Rules();
		rules.register(rule1);
		rules.register(rule2);

		RulesEngine engine = new DefaultRulesEngine();
		engine.fire(rules, facts);

		return searchParam.getCarType();

	}
	
	private void clearField(){
		totalPassenger = 0;
		ratePerDay = 0;
		trxCarBook = new TrxCarBook();
		cars.clear();
	}

	public TrxCarBook getTrxCarBook() {
		return trxCarBook;
	}

	public void setTrxCarBook(TrxCarBook trxCarBook) {
		this.trxCarBook = trxCarBook;
	}

	public int getTotalPassenger() {
		return totalPassenger;
	}

	public void setTotalPassenger(int totalPassenger) {
		this.totalPassenger = totalPassenger;
	}

	public int getRatePerDay() {
		return ratePerDay;
	}

	public void setRatePerDay(int ratePerDay) {
		this.ratePerDay = ratePerDay;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}

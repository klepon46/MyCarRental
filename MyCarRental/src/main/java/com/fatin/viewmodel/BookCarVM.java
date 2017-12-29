package com.fatin.viewmodel;

import java.util.List;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import com.fatin.model.Car;
import com.fatin.model.SearchParam;
import com.fatin.model.TrxCarBook;
import com.fatin.service.ICarSvc;
import com.fatin.service.ITrxCarBookSvc;

@VariableResolver(DelegatingVariableResolver.class)
public class BookCarVM {

	@WireVariable
	private ITrxCarBookSvc iTrxCarBookSvc;

	@WireVariable
	private ICarSvc iCarSvc;

	private TrxCarBook trxCarBook;

	private int totalPassenger;
	private int ratePerDay;
	private List<Car> cars;

	@Init
	public void init() {

	}

	@Command
	@NotifyChange("cars")
	public void find() {
		String carType = determineCarType();
		
		System.out.println("kuda " + carType);
		cars = iCarSvc.findByTypeAndRate(carType, ratePerDay);
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

}

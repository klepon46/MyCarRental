package com.fatin.util;

import java.util.Date;

import org.joda.time.LocalTime;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Timebox;

/**
 * Class Yang Berfungsi sebagai custom constraint
 * constraint yang ada pada kelas ini akan jalan
 * ketika waktu stoptime lebih kecil dari pada waktu starttime
 * @param startTimeBox
 */
public class StartStopConstraint implements Constraint{

	private Timebox tb;
	private String compAttProd;
	private String compAttLostTimeStart;
	private String compAttLostTimeStop;


	public StartStopConstraint(Timebox startTimeBox) {
		super();
		this.tb = startTimeBox;
	}

	public void validate(Component component, Object value)
			throws WrongValueException {

		if(component instanceof Timebox){

			Date dateStop = (Date) value;

			LocalTime stopTime = new LocalTime(dateStop);
			LocalTime startTime = new LocalTime(tb.getValue());

			compAttProd= (String) component.getAttribute("prod");
			compAttLostTimeStart= (String) component.getAttribute("lostStart");
			compAttLostTimeStop = (String) component.getAttribute("lostStop");

			if(stopTime.isBefore(startTime)){

				if(getCompAttProd().equalsIgnoreCase("prod")){
					throw new WrongValueException(component,"Jam Stop Lebih Kecil daripada Jam Start");
				}

				if(getCompAttLostTimeStart().equalsIgnoreCase("lostStart")){
					throw new WrongValueException(component
							,"Jam Start Consumption Lebih Kecil daripada Jam Start Production");
				}

			}

			if(getCompAttLostTimeStop().equalsIgnoreCase("lostStop")){
				if(startTime.isBefore(stopTime)){
					throw new WrongValueException(component
							,"Jam Stop Consumption Lebih Besar daripada Jam Stop Production");
				}
			}

		}
	}

	public String getCompAttProd() {
		if(compAttProd == null){
			compAttProd = "";
		}
		return compAttProd;
	}

	public String getCompAttLostTimeStart() {
		if(compAttLostTimeStart == null){
			compAttLostTimeStart = "";
		}
		return compAttLostTimeStart;
	}


	public String getCompAttLostTimeStop() {
		if(compAttLostTimeStop == null){
			compAttLostTimeStop = "";
		}
		return compAttLostTimeStop;
	}

}

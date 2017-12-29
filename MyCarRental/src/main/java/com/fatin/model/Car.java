package com.fatin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CAR")
public class Car {

	@Id
	@Column(name="CAR_ID")
	private Integer carID;
	
	@Column(name="COMPANY_ID")
	private Integer companyID;
	
	@Column(name="CAR_NAME")
	private String carName;
	
	@Column(name="CAR_RATE")
	private Integer carRate;
	
	@Column(name="CAR_TYPE")
	private String carType;

	public Integer getCarID() {
		return carID;
	}

	public void setCarID(Integer carID) {
		this.carID = carID;
	}

	public Integer getCompanyID() {
		return companyID;
	}

	public void setCompanyID(Integer companyID) {
		this.companyID = companyID;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public Integer getCarRate() {
		return carRate;
	}

	public void setCarRate(Integer carRate) {
		this.carRate = carRate;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carID == null) ? 0 : carID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (carID == null) {
			if (other.carID != null)
				return false;
		} else if (!carID.equals(other.carID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Car [carID=" + carID + "]";
	}
}

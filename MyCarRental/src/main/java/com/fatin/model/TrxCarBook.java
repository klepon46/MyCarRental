package com.fatin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TRX_CAR_BOOK")
public class TrxCarBook {
	
	@Id
	@Column(name="BOOK_ID")
	private Integer bookID;
	
	@Column(name="CAR_ID")
	private Integer carID;
	
	@Column(name="CUSTOMER_ID")
	private Integer customerID;
	
	@Column(name="START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name="END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@Column(name="TOTAL_PRICE")
	private Integer totalPrice;

	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}

	public Integer getCarID() {
		return carID;
	}

	public void setCarID(Integer carID) {
		this.carID = carID;
	}

	public Integer getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}
	
	public Date getStartDate() {
		if(startDate == null)
			startDate = new Date();
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		if(endDate == null)
			endDate = new Date();
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookID == null) ? 0 : bookID.hashCode());
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
		TrxCarBook other = (TrxCarBook) obj;
		if (bookID == null) {
			if (other.bookID != null)
				return false;
		} else if (!bookID.equals(other.bookID))
			return false;
		return true;
	}

}

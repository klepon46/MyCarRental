package com.fatin.service;

import java.util.List;

import com.fatin.model.TrxCarBook;

public interface ITrxCarBookSvc {

	public void save(TrxCarBook domain);
	public List<TrxCarBook> findByCustomerID(int id);
	
}

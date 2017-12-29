package com.fatin.dao;

import java.util.List;

import com.fatin.model.TrxCarBook;

public interface ITrxCarBookDao {

	public void save(TrxCarBook domain);
	public List<TrxCarBook> findByCustomerID(int id);
	
}

package com.fatin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatin.dao.ITrxCarBookDao;
import com.fatin.model.TrxCarBook;

@Service(value="iTrxCarBookSvc")
@Transactional
public class ITrxCarBookSvcImpl implements ITrxCarBookSvc {

	@Autowired
	private ITrxCarBookDao dao;

	public void save(TrxCarBook domain) {
		dao.save(domain);
	}

	public List<TrxCarBook> findByCustomerID(int id) {
		return null;
	};
	
}

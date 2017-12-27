package com.fatin.util;

import java.util.List;

public interface AdrGenericDao<T> {

	public void saveOrUpdate (final T domain, String user);
	public void save (final T domain, String user);
	public void update (final T domain, String user);
	public void delete (final T domain);
	public List<T> findAll ();
	public List<T> findByExample (final T domain);
	public List<T> findByCriteria (final T domain);
	public T findByPrimaryKey(final T domain);
	
}

package com.fatin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kelas util sebagai membantu pengelompokan
 * category problem tiap-tiap hopper
 * @author garya
 *
 */
public class CategoryProblemMapperUtil {

	private String categoryProblem;
	private List<Object[]> objectProblem;
	private Map<String, List<Object[]>> mapIsiHopper;
	
	public CategoryProblemMapperUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * mengisi map dengan collection berdasarkan hopper nya masing masing
	 */
	public Map<String, List<Object[]>> populateMap(){
		
		//populate all hopper
		List<String> l  = new ArrayList<String>();
		l.add("H1");
		l.add("H2");
		l.add("H3");
		l.add("H4");
		l.add("H5");
		l.add("H6");
		l.add("H7");
		
		//masukin hopper yang baru saja di populate ke dalam map
		for(String str : l){
			List<Object[]> objects = new ArrayList<Object[]>(); 
			getMapIsiHopper().put(str, objects);
		}
		
		//masukin data ke dalam masing masing map
		for(Object[] obj : getObjectProblem()){
			getMapIsiHopper().get(obj[3]).add(obj);
		}
		return getMapIsiHopper();
	}
	
	/**
	 * mengisi map dengan collection berdasarkan hopper yang sudah ditentukan
	 */
	public Map<String, List<Object[]>> populateMap(String hopper){
		
		//populate all hopper
		List<String> l  = new ArrayList<String>();
		l.add(hopper);
		
		//masukin hopper yang baru saja di populate ke dalam map
		for(String str : l){
			List<Object[]> objects = new ArrayList<Object[]>(); 
			getMapIsiHopper().put(str, objects);
		}
		
		//masukin data ke dalam masing masing map
		for(Object[] obj : getObjectProblem()){
			String hopperFromObjProblem = (String) obj[3];
			if(hopperFromObjProblem.equalsIgnoreCase(hopper)){
				getMapIsiHopper().get(hopper).add(obj);
			}
		}
		return getMapIsiHopper();
	}
	
	
	public String getCategoryProblem() {
		return categoryProblem;
	}
	public void setCategoryProblem(String categoryProblem) {
		this.categoryProblem = categoryProblem;
	}
	public List<Object[]> getObjectProblem() {
		if(objectProblem == null){
			objectProblem = new ArrayList<Object[]>();
		}
		return objectProblem;
	}
	public void setObjectProblem(List<Object[]> objectProblem) {
		this.objectProblem = objectProblem;
	}


	public Map<String, List<Object[]>> getMapIsiHopper() {
		if(mapIsiHopper == null){
			mapIsiHopper = new HashMap<String, List<Object[]>>();
		}
		return mapIsiHopper;
	}

	public void setMapIsiHopper(Map<String, List<Object[]>> mapIsiHopper) {
		this.mapIsiHopper = mapIsiHopper;
	}
	
	
	
}

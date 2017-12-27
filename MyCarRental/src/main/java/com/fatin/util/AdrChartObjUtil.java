package com.fatin.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class Pembantu untuk memasukkan data ke dalam chart 
 * CanvasJS
 * 
 * @author garya
 * Jun 16, 2016
 */
public class AdrChartObjUtil {

	private String name;
	private String type;
	private boolean showInLegend;
	private List<DataPointUtil> dataPoints;
	
	public AdrChartObjUtil() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<DataPointUtil> getDataPoints() {
		if(dataPoints==null)
			dataPoints = new ArrayList<DataPointUtil>();
		return dataPoints;
	}

	public void setDataPoints(List<DataPointUtil> dataPoints) {
		this.dataPoints = dataPoints;
	}

	public boolean isShowInLegend() {
		return showInLegend;
	}

	public void setShowInLegend(boolean showInLegend) {
		this.showInLegend = showInLegend;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AdrChartObjUtil other = (AdrChartObjUtil) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AdrChartObjUtil [name=" + name + "]";
	}
	
}

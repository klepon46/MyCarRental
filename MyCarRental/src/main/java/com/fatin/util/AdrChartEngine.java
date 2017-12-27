package com.fatin.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.zkoss.zkex.zul.impl.JFreeChartEngine;
import org.zkoss.zul.Chart;

public class AdrChartEngine extends JFreeChartEngine {

	private static final long serialVersionUID = 1L;

	private Map<Integer, Integer> color;

	private String type;

	public AdrChartEngine() {
		// TODO Auto-generated constructor stub
	}

	public AdrChartEngine(String type, Map<Integer, Integer> map) {
		this.type = type;
		color = map;
	}

	public AdrChartEngine(Map<Integer, Integer> map){
		color = map;
	}

	protected boolean prepareJFreeChart(JFreeChart jfchart, Chart chart) {

		CategoryPlot plot = jfchart.getCategoryPlot();

		//jika null maka type chart set ke bar
		if(type == null){
			type = "bar";
		}

		if(type.equalsIgnoreCase("line")){
			LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
			int x = 0;
			
			renderer.setSeriesShapesVisible(3, true);
			
			for(Map.Entry<Integer, Integer> entry : color.entrySet()){
				renderer.setSeriesShapesVisible(x++, true);
				renderer.setSeriesStroke(x++, new BasicStroke(2));
			}

		}
		
		if(type.equalsIgnoreCase("bar")){
			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			for(Map.Entry<Integer, Integer> entry : color.entrySet()){
				renderer.setSeriesPaint(entry.getKey(), new Color(entry.getValue()));
			}
		}

		return false;
	}	

}

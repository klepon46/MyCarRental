/**
 * 
 */

function onLoadChartYtd(crushing, outloading) {

	var dataCrushing = JSON.parse(crushing);
	var dataOutloading = JSON.parse(outloading);
	generateYtdChart(dataCrushing, dataOutloading);

}

function generateYtdChart(iData, iDataOutloading) {

	var iDataPoint = [];
	iData.forEach(function(item) {
		iDataPoint.push({
			label : item[0],
			y : item[1],
			tooltip : item[2]
		});
	});

	var iDataPointOutloading = [];
	iDataOutloading.forEach(function(item) {
		iDataPointOutloading.push({
			label : item[0],
			y : item[1],
			tooltip : item[2]
		});
	});

	var chart = new CanvasJS.Chart("chartYtd", {

		title : {
			text : "Production Year To Date",
			fontSize : 30
		},
		animationEnabled : true,
		axisX : {
			gridColor : "Silver",
			tickColor : "silver",
			valueFormatString : "DD/MMM"
		},
		toolTip : {
			shared : true,
			content : "{tooltip}"
		},
		theme : "theme2",
		axisY : {
			gridColor : "Silver",
			tickColor : "silver",
			title : "Tonnage"
		},
		legend : {
			verticalAlign : "center",
			horizontalAlign : "right"
		},
		data : [ {
			type : "spline",
			indexLabel : "{y}",
			showInLegend : true,
			name : "Crushing",
			indexLabelPlacement : "outside",
			indexLabelOrientation : "horizontal",
			dataPoints : iDataPoint
		}, {
			type : "spline",
			indexLabel : "{y}",
			showInLegend : true,
			name : "Outloading",
			indexLabelPlacement : "outside",
			indexLabelOrientation : "horizontal",
			dataPoints : iDataPointOutloading
		} ],
		legend : {
			cursor : "pointer",
			itemclick : function(e) {
				if (typeof (e.dataSeries.visible) === "undefined"
						|| e.dataSeries.visible) {
					e.dataSeries.visible = false;
				} else {
					e.dataSeries.visible = true;
				}
				chart.render();
			}
		}
	});

	chart.render();
}

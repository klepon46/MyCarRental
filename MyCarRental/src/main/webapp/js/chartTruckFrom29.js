/**
 * 
 */

function onLoad(truck) {

	var data = JSON.parse(truck);
	generatePassingTruckChart(data);

}

function generatePassingTruckChart(iData) {

	var iDataPoint = [];
	iData.forEach(function(item) {
		iDataPoint.push({
			label : item[0],
			y : item[1],
			tooltip : item[2]
		});
	});

	var chart = new CanvasJS.Chart("pass29", {

		title : {
			text : "Truck From Km 29 To Kelanis",
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
			title : "Truck Count"
		},
		legend : {
			verticalAlign : "center",
			horizontalAlign : "right"
		},
		data : [ {
			type : "column",
			indexLabel : "{y}",
			indexLabelPlacement : "outside",
			indexLabelOrientation : "horizontal",
			dataPoints : iDataPoint
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

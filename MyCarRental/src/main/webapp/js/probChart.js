/**
 * 
 */

function onLoad(problem) {

	var data = JSON.parse(problem);
	generateChart(data);
}

function generateChart(problem) {
	var chart = new CanvasJS.Chart("problem", {
		title : {
			text : "Delay Problem"
		},
		animationEnabled : true,
		axisY : {
			title : "Lost Time",
			titleFontSize : 20
		},
		theme : "theme1",
		toolTip : {
			shared : true,
		},
		data : problem,
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
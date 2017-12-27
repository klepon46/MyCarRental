/**
 * 
 */

function onLoad(stockGson){

	var data = JSON.parse(stockGson);
	console.log(data);

	data.forEach(function(item){
		var g = new JustGage({
			id: item.pk.reclaim,
			value: item.currentStock,
			min: 0,
			max: item.tblKctMstSplLine.maxStock,
			title: item.pk.reclaim + " Tonnage"
		});
	});

//	generateChart(data);
	generateCanvasJsChart(data);
}

function generateCanvasJsChart(data){
	var cvValue = [];
	var ashValue = [];
	var tmValue = [];
	var tsValue = [];

	data.forEach(function(item){
		cvValue.push({label :item.pk.reclaim, y: item.currentCv});
		ashValue.push({label :item.pk.reclaim, y: item.ash});
		tmValue.push({label :item.pk.reclaim, y: item.tm});
		tsValue.push({label :item.pk.reclaim, y: item.ts});
	});

	var cvChart = new CanvasJS.Chart("cv", {
		title:{
			text: "CV Value"              
		},
		data: [              
		       {
		    	   // Change type to "doughnut", "line", "splineArea", etc.
		    	   type: "column",
		    	   dataPoints: cvValue
		       }
		       ]
	});
	cvChart.render();
	
	var ashChart = new CanvasJS.Chart("ash", {
		title:{
			text: "ASH Value"              
		},
		data: [              
		       {
		    	   // Change type to "doughnut", "line", "splineArea", etc.
		    	   type: "column",
		    	   dataPoints: ashValue
		       }
		       ]
	});
	ashChart.render();
	
	var tmChart = new CanvasJS.Chart("tm", {
		title:{
			text: "TM Value"              
		},
		data: [              
		       {
		    	   // Change type to "doughnut", "line", "splineArea", etc.
		    	   type: "column",
		    	   dataPoints: tmValue
		       }
		       ]
	});
	tmChart.render();
	
	var tsChart = new CanvasJS.Chart("ts", {
		title:{
			text: "TS Value"              
		},
		data: [              
		       {
		    	   // Change type to "doughnut", "line", "splineArea", etc.
		    	   type: "column",
		    	   dataPoints: tsValue
		       }
		       ]
	});
	tsChart.render();

}
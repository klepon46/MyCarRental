/**
 * 
 */

function onLoadRomMine(truck){

	var data = JSON.parse(truck);
	generateRomMineChart(data);

}

function generateRomMineChart(iData){
	
	var iDataPoint = [];
	iData.forEach(function(item){
		iDataPoint.push({label :item[0], y: item[1]} );
	});
	
	var chart = new CanvasJS.Chart("romMine",
			{

		title:{
			text: "Rom Mine Stock",
			fontSize: 30
		},
		animationEnabled: true,
		axisX:{
			gridColor: "Silver",
			tickColor: "silver",
			valueFormatString: "DD/MMM"
		},                        
		toolTip:{
			shared:true,
			content: "{y}"
		},
		theme: "theme1",
		axisY: {
			gridColor: "Silver",
			tickColor: "silver",
			title: "Tonnage"
		},
		legend:{
			verticalAlign: "center",
			horizontalAlign: "right"
		},
		data: [
		       {
		    	   type:"column",
		    	   indexLabel: "{y}",
		           indexLabelPlacement: "outside",  
		           indexLabelOrientation: "horizontal",
		    	   dataPoints : iDataPoint
		       }
		       ],
		       legend:{
		    	   cursor:"pointer",
		    	   itemclick:function(e){
		    		   if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
		    			   e.dataSeries.visible = false;
		    		   }
		    		   else{
		    			   e.dataSeries.visible = true;
		    		   }
		    		   chart.render();
		    	   }
		       }
			});

	chart.render();
}

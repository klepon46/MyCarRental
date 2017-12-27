//variable map key value pair
//untuk menampung object line
var map = new Object();
var mapHopper = new Object();//object map untuk menampung kotak hopper
var mapStockSpl = new Object();//object map untuk menampung text stockpile
var mapBreakDownText = new Object();//object map untuk menampung text breakDown
var mapLastTrailerText = new Object();//object map untuk text last trailer entry
var mapBargeTextValue = new Object();//object map untuk simpan object text di barge
var mapLineBundle = new Object();//object untuk menampung bundle active line
var mapTotalProdText = new Object();//object untuk menampung text total produksi
var classLevelPaper;
var classLevelCwidth;
var classLevelCheight;

function onLoad(lJson) {
	var canvas = document.getElementById('canvas_container');

	//mendapatkan total lebar kanvas
	var cWidth = canvas.scrollWidth;
	classLevelCwidth = cWidth;

	//mendapatkan total tinggi kanvas
	var cHeight = canvas.scrollHeight;
	classLevelCheight = cHeight;

	//create object raphael
	var paper = new Raphael(canvas, cWidth, cHeight);
	classLevelPaper = paper;

	var myAttr = {warna:'#ff0000'};

	var anim = Raphael.animation({stroke:myAttr.warna,'stroke-width': 3},650,'linear');

	boatK1(paper, cWidth, cHeight, anim);
	createHopper1(paper, cWidth, cHeight, anim);
	createHopper2(paper, cWidth, cHeight, anim);
	createHopper7(paper, cWidth, cHeight, anim);
	createHopper5(paper, cWidth, cHeight, anim);
	createHopper6(paper, cWidth, cHeight, anim);
	createHopper4(paper, cWidth, cHeight, anim);
	createHopper3(paper, cWidth, cHeight, anim);

	createTextStokStpl(paper, cWidth, cHeight,lJson);//banyak memakan resources CPU karena Animasi = infinite
	createTextBreakDown(paper, cWidth, cHeight);//create text breakDown
	createBarge(paper, cWidth, cHeight)//create barge box and Text inside
	createLastEntryTrailerBox(paper, cWidth, cHeight);//create lastEntryTrailer Box and Text Inside
	createTextTotalProduksi(paper, cWidth, cHeight)

};

function activeLine(color,stopTime,line,hopper){

	var breakdDownText = getBreakDownText(hopper);
	if(breakdDownText != 'undefined'){
		breakdDownText.stop();
		breakdDownText.attr({opacity:0});
	}

	var showGreen = Raphael.animation({stroke:color, 'stroke-width':3},650);
	var lineFromMap = getLine(line);
	var hopper = getHopper(hopper);

	lineFromMap.toFront();
	lineFromMap.stop();
	lineFromMap.attr({stroke:"#000", "stroke-width":1});
	lineFromMap.animate(showGreen.repeat("Infinity"));

	hopper.animate({fill:'#00ff00'},1000);

	//mencek waktu setiap menit
	checkEveryMinutes(stopTime, lineFromMap,hopper);
}

//fungsi baru start-stop menjalan grafik hopper
function activeLineNew(color,line,hopper){

	var breakdDownText = getBreakDownText(hopper);
	if(breakdDownText != 'undefined'){
		breakdDownText.stop();
		breakdDownText.attr({opacity:0});
	}

	var showGreen = Raphael.animation({stroke:color, 'stroke-width':3},650);
	var lineFromMap = getLine(line);
	var hopper = getHopper(hopper);

	lineFromMap.toFront();
	lineFromMap.stop();
	lineFromMap.attr({stroke:"#000", "stroke-width":1});
	lineFromMap.animate(showGreen.repeat("Infinity"));

	hopper.animate({fill:'#00ff00'},1000);

}

//fungsi baru start-stop menghentikan grafik hopper
function stopHopperAndLine(line, hopper){
	var lineFromMap = getLine(line);
	var hpr = getHopper(hopper);
	
	lineFromMap.stop();
	lineFromMap.attr({stroke:"#000", "stroke-width":1});

	hpr.attr({fill:"none"});
}

//function untuk mengupdate angka text stockpile
function stockPileStock(splStock){
	var obj = JSON.parse(splStock);

	for(var i = 0 ; i < obj.length; i++){

		var reclaim = obj[i].pk.reclaim;
		var stockSpl = getStockSpl(reclaim);
		var stockSplCv = getStockSpl("CV"+reclaim);
		stockSpl.attr({text: obj[i].currentStock.toLocaleString()});
		stockSplCv.attr({text: obj[i].currentCv.toLocaleString()});
	}
}

function updateTotalProduct(totalProducts){

	var obj = JSON.parse(totalProducts);
	for(var i = 0 ; i < obj.length; i++){

		var totalTextProd = getTotalTextProduksi(obj[i].hopperCode);
//		totalTextProd.attr({text: obj[i].tonnage.toLocaleString()});
		var totalProd = obj[i].totalProduksi/1000;


		totalTextProd.attr({text: totalProd.toLocaleString()});
	}
}

//untuk menjalan function yang mengecek setiap satu menit
//agar bisa menstopkan status runnning conveyor
function checkEveryMinutes(stopTime,line,hopper){
//	var iInterval= setInterval(function(){stopLine(iInterval,stopTime,line,hopper)} ,60000);
	
	//jika ada ':' dalam stopTime
	if(stopTime.indexOf(":") != -1){
		var iInterval= setInterval(function(){stopLine(iInterval,stopTime,line,hopper)} ,60000);
	}else{
		var iInterval = setInterval(function(){
			console.log("sebelum waktu");
			stopTime--;
			if(stopTime===0){
				console.log("kuda");
				line.stop();
				line.attr({stroke:"#000", "stroke-width":1});

				hopper.attr({fill:"none"});
				clearInterval(iInterval);
			}

		}, 60000);
	}

}

function stopLine(iInterval, stopTime,line,hopper){

	var date = new Date();
	var min = date.getMinutes().toString();

	//tambahkan 0 didepan jika digit minute adalah 1
	if(min.length === 1){
		min = "0" + date.getMinutes();
	};

	var hourMin = date.getHours() + ":"+ min;

	if(stopTime == hourMin){
		line.stop();
		line.attr({stroke:"#000", "stroke-width":1});

		hopper.attr({fill:"none"});
		clearInterval(iInterval);//clear interval jadi dia tidak mencek lg, jika kondisi jam sudah sama
	}
}

//function yang dijalankan ketika ada inputan dari form CrushLog
function stoppedHopper(hopperName,problem, stopTime){

	var k =  mapLineBundle[hopperName];
	if (typeof k != 'undefined'){
		k.stop();
		k.attr({stroke:"#000", "stroke-width":1});
	}

	var hopper = getHopper(hopperName);
	var breakDownText = getBreakDownText(hopperName);
	var anima = Raphael.animation({opacity:1},1000);

	breakDownText.stop();
	breakDownText.attr({opacity:0});

	hopper.attr({fill: "#ff0000"});
	breakDownText.attr({text:problem});
	breakDownText.animate(anima.repeat(Infinity));

	checkEveryMinutesBreakDown(stopTime, hopper, breakDownText)

}

//function yang dijalankan ketika ada inputan dari form CrushLog
function stoppedHopperNew(hopperName,problem){
	console.log("jalan");

	var k =  mapLineBundle[hopperName]
	if (typeof k != 'undefined'){
		k.stop();
		k.attr({stroke:"#000", "stroke-width":1});
	}

	var hopper = getHopper(hopperName);
	var breakDownText = getBreakDownText(hopperName);
	var anima = Raphael.animation({opacity:1},1000);

	breakDownText.stop();
	breakDownText.attr({opacity:0});

	hopper.attr({fill: "#ff0000"});
	breakDownText.attr({text:problem});
	breakDownText.animate(anima.repeat(Infinity));

}


//untuk menjalan function yang mengecek setiap satu menit
//agar bisa menstopkan status runnning conveyor
function checkEveryMinutesBreakDown(stopTime,hopper,breakDownText){
	//var iInterval= setInterval(function(){stopTheBreakDown(iInterval,stopTime,hopper,breakDownText)} ,60000);
	if(stopTime.indexOf(":") != -1){
		iInterval= setInterval(function(){stopTheBreakDown(iInterval,stopTime,hopper,breakDownText)} ,60000);
	}else{
		var iInterval = setInterval(function(){
			console.log("sebelum waktu");
			stopTime--;
			if(stopTime===0){
				breakDownText.stop();
				breakDownText.attr({opacity:0});

				hopper.attr({fill:"none"});
				clearInterval(iInterval);//clear interval jadi dia tidak mencek lg, jika kondisi jam sudah sama
			}

		}, 60000);
	}
}

function stopTheBreakDown(iInterval, stopTime, hopper, text){
	var date = new Date();
	var min = date.getMinutes().toString();

	//tambahkan 0 didepan jika digit minute adalah 1
	if(min.length === 1){
		min = "0" + date.getMinutes();
	};

	var hourMin = date.getHours() + ":"+ min;

	console.log(stopTime);
	console.log(hourMin);

	if(stopTime == hourMin){
		text.stop();
		text.attr({opacity:0});

		hopper.attr({fill:"none"});
		clearInterval(iInterval);//clear interval jadi dia tidak mencek lg, jika kondisi jam sudah sama
	}

}

function stopTheBreakDownNew(hopperName){
	
	var hopper = getHopper(hopperName);
	var breakDownText = getBreakDownText(hopperName);
	
	breakDownText.stop();
	breakDownText.attr({opacity:0});

	hopper.attr({fill:"none"});
}

function createBarge(p, cWidth, cHeight){

	Raphael.el.bargeText = function(){
		this.attr({'font-size': 11,'font-weight':'bold'
			,fill:"#ffffff","text-anchor":"start"
				,'font-weight':'bold'});
		return this;
	}


	//set object K1
	var bargeK1Set = p.set();
	var bargeK1 = p.rect(cWidth-1320,cHeight-335,100,200,10);//kotak bargeK1
	var bargeName = p.text(cWidth-1310, cHeight-320,"Barge Name :").bargeText();//text bargeName
	var capacity = p.text(cWidth-1310, cHeight-280,"Capacity :").bargeText();//text capacity
	var product = p.text(cWidth-1310, cHeight-240,"Product :").bargeText();//text product
	var tonnage = p.text(cWidth-1310, cHeight-200,"Tonnage In :").bargeText();//text ETC
	var bargeNameVal = p.text(cWidth-1310,cHeight-305,"")//text bargeName
	var capacityVal = p.text(cWidth-1310,cHeight-265,"")//text capacity
	var productVal = p.text(cWidth-1310, cHeight-225,"")//text product
	var tonnageVal = p.text(cWidth-1310, cHeight-185,"")//text value ETC
	mapBargeTextValue["K1NAME"] = bargeNameVal;
	mapBargeTextValue["K1CAP"] = capacityVal;
	mapBargeTextValue["K1PROD"] = productVal;
	mapBargeTextValue["K1TON"]  = tonnageVal;

	bargeK1Set.push(bargeK1);//push
	bargeK1Set.push(bargeName);//push
	bargeK1Set.push(capacity);
	bargeK1Set.push(product);
	bargeK1Set.push(tonnage);
	bargeK1Set.push(bargeNameVal);
	bargeK1Set.push(capacityVal);
	bargeK1Set.push(productVal);
	bargeK1Set.push(tonnageVal);

	bargeK1Set.translate(-40,0);


	//set objectK3
	var bargeK3Set = p.set();
	var bargeK3 = p.rect(cWidth-1360,cHeight-735,100,200,10);//kotak bargeK3
	var bargeNameK3 = p.text(cWidth-1350, cHeight-720,"Barge Name :").bargeText();//text bargeName
	var capacityK3 = p.text(cWidth-1350, cHeight-680,"Capacity :").bargeText();//text capacity
	var productK3 = p.text(cWidth-1350, cHeight-640,"Product :").bargeText();//text product
	var tonnageK3 = p.text(cWidth-1350, cHeight-600,"Tonnage In :").bargeText();//text ETC
	var bargeNameK3Val = p.text(cWidth-1350, cHeight-705,"");//text bargeName
	var capacityK3Val = p.text(cWidth-1350, cHeight-665,"");//text capacity
	var productK3Val = p.text(cWidth-1350, cHeight-625,"");//text product
	var tonnageK3Val = p.text(cWidth-1350,cHeight-585,"");//text ETC
	mapBargeTextValue["K3NAME"] = bargeNameK3Val;
	mapBargeTextValue["K3CAP"] = capacityK3Val;
	mapBargeTextValue["K3PROD"] = productK3Val;
	mapBargeTextValue["K3TON"]  = tonnageK3Val;

	bargeK3Set.push(bargeK3);//push
	bargeK3Set.push(bargeNameK3);//push

}

//mengisi nama barge, cap, ton, dll
function fillBarge(jetProdObj){

	Raphael.el.bargeText = function(textToFill){
		this.attr({'font-size': 11,'font-weight':'bold'
			,fill:"#3BFFFF","text-anchor":"start",text:textToFill});
		return this;
	}

	Raphael.el.bargeTextK3 = function(textToFill){
		this.attr({'font-size': 11,'font-weight':'bold'
			,fill:"#99ff99","text-anchor":"start",text:textToFill});
		return this;
	}

	var obj = JSON.parse(jetProdObj);
	var jetty = obj.jetty;
	var bargeName = obj.bargeName;
	var bargeCap = obj.bargeCap.toLocaleString();
	var bargeProduct = obj.product;
	var bargeTonnage = obj.tonnage.toLocaleString();

	if(jetty === 'K1'){
		getBargeTextValObject("K1NAME").bargeText(bargeName);
		getBargeTextValObject("K1CAP").bargeText(bargeCap);
		getBargeTextValObject("K1PROD").bargeText(bargeProduct);
		getBargeTextValObject("K1TON").bargeText(bargeTonnage);
	}else{
		getBargeTextValObject("K3NAME").bargeTextK3(bargeName);
		getBargeTextValObject("K3CAP").bargeTextK3(bargeCap);
		getBargeTextValObject("K3PROD").bargeTextK3(bargeProduct);
		getBargeTextValObject("K3TON").bargeTextK3(bargeTonnage);
	}

}

//function yang mengisi nilai tonase barge
function fillBargeTon(jetProd){
	var obj = JSON.parse(jetProd);
	var tonnage = obj.tonnage.toLocaleString();
	if(obj.jetty === 'K1'){
		getBargeTextValObject("K1TON").bargeText(tonnage);
	}else{
		getBargeTextValObject("K3TON").bargeTextK3(tonnage);
	}

}

//function yang mengisikan data data barge
function completedBarge(jetProd){
	var obj = JSON.parse(jetProd);
	var tonnage = obj.tonnage.toLocaleString();
	if(obj.jetty === 'K1'){
		getBargeTextValObject("K1NAME").bargeText("");
		getBargeTextValObject("K1CAP").bargeText("");
		getBargeTextValObject("K1PROD").bargeText("");
		getBargeTextValObject("K1TON").bargeText("");
	}else{
		getBargeTextValObject("K3NAME").bargeTextK3("");
		getBargeTextValObject("K3CAP").bargeTextK3("");
		getBargeTextValObject("K3PROD").bargeTextK3("");
		getBargeTextValObject("K3TON").bargeTextK3("");
	}
}

//function yang membuat kotak lastEntryTrailer beserta dengan isinya
function createLastEntryTrailerBox(p, cWidth, cHeight){

	Raphael.el.trailerHopperText = function(){
		this.attr({'font-size': 11,'font-weight':'bold',"text-anchor":"start", fill:"#ffffff"});
		return this;
	}

	Raphael.el.trailerHopperTextValue = function(){
		this.attr({'font-size': 11,'font-weight':'bold',"text-anchor":"start", fill:"#ffb3ff"});
		return this;
	}

	var title = p.text(cWidth-500,cHeight-790,"Last Dumping Trailer");
	title.attr({"text-decoration":"underline", "text-anchor":"start", "font-size":14
		, 'font-weight':'bold', fill:"#0D0D0D"});

	var lastEntrySet = p.set();
	var lastEntryBox = p.rect(cWidth-500,cHeight-780,370,60,10);
	var h1Text = p.text(cWidth-490, cHeight-760,"H1 :").trailerHopperText();
	var h2Text = p.text(cWidth-490, cHeight-740,"H2 :").trailerHopperText();
	var h3Text = p.text(cWidth-400, cHeight-760,"H3 :").trailerHopperText();
	var h4Text = p.text(cWidth-400, cHeight-740,"H4 :").trailerHopperText();
	var h5Text = p.text(cWidth-310, cHeight-760,"H5 :").trailerHopperText();
	var h6Text = p.text(cWidth-310, cHeight-740,"H6 :").trailerHopperText();
	var h7Text = p.text(cWidth-220, cHeight-760,"H7 :").trailerHopperText();

	var h1TextValue = p.text(cWidth-465, cHeight-760,"").trailerHopperTextValue();
	var h2TextValue = p.text(cWidth-465, cHeight-740,"").trailerHopperTextValue();
	var h3TextValue = p.text(cWidth-375, cHeight-760,"").trailerHopperTextValue();
	var h4TextValue = p.text(cWidth-375, cHeight-740,"").trailerHopperTextValue();
	var h5TextValue = p.text(cWidth-285, cHeight-760,"").trailerHopperTextValue();
	var h6TextValue = p.text(cWidth-285, cHeight-740,"").trailerHopperTextValue();
	var h7TextValue = p.text(cWidth-195, cHeight-760,"").trailerHopperTextValue();

	lastEntrySet.push(title);
	lastEntrySet.push(lastEntryBox);
	lastEntrySet.push(h1Text);
	lastEntrySet.push(h2Text);
	lastEntrySet.push(h3Text);
	lastEntrySet.push(h4Text);
	lastEntrySet.push(h5Text);
	lastEntrySet.push(h6Text);
	lastEntrySet.push(h7Text);
	lastEntrySet.push(h1TextValue);
	lastEntrySet.push(h2TextValue);
	lastEntrySet.push(h3TextValue);
	lastEntrySet.push(h4TextValue);
	lastEntrySet.push(h5TextValue);
	lastEntrySet.push(h6TextValue);
	lastEntrySet.push(h7TextValue);

	lastEntrySet.translate(-120,25);

	mapLastTrailerText["H1"] = h1TextValue;
	mapLastTrailerText["H2"] = h2TextValue;
	mapLastTrailerText["H3"] = h3TextValue;
	mapLastTrailerText["H4"] = h4TextValue;
	mapLastTrailerText["H5"] = h5TextValue;
	mapLastTrailerText["H6"] = h6TextValue;
	mapLastTrailerText["H7"] = h7TextValue;
}

//mengisi object di lastEntryTrailer dengan data yang sudah ditentukan
function fillLastEntryTrailerBox(listJson){
	var data = JSON.parse(listJson);
	console.log(listJson);

	data.forEach(function(item){
		var textToFill = getLastEntryTrailer(item.hopperCode);
		textToFill.attr({text:item.truckNo})
	});

}

//function yang membuat hopper1
function createHopper1(p, cWidth, cHeight, anim){

	//create kotak hopper1
	var hopper1 = p.rect(cWidth-300,cHeight-80,20,45);

	//memasukkan object hopper ke dalam map
	mapHopper["H1"] = hopper1;


	//mendapatkan posisi hopper1
	var hopper1LineWidth = cWidth-300-20;
	var hopper1LineHeight = cHeight-80+20;

	//lingkaran spl 23
	var splS23Circle = p.circle(hopper1LineWidth-340,hopper1LineHeight-105,30 );

	//var combine diolah berdasarkan posisi hopper1
	var combine = 'M'+hopper1LineWidth+','+hopper1LineHeight;

	//create line s3 yang bersumber dari hopper1
	var lineS3 = p.path(combine + ' l-55,-80 l-610,0');

	//line ke stockpile
	var lineL1 = p.path(combine + ' l-78,-113 l-170,-40 l0,50');

	//line ke stockpile
	var lineL2 = p.path(combine + ' l-78,-113 l-170,-40 l-214,0 l0,50');

	//line s23
	var lineS23 =  p.path(combine + ' l-78,-113 l-170,-40 l-200,0 l106,50');

	//line s22
	var lineS22 =  p.path(combine + ' l-78,-113 l-170,-40 l-200,0 l0,-26');

	//line ke stockpile
	var lineL3 = p.path(combine + ' l-78,-113 l-170,-40 l-414,0 l0,50');

	//line ke barge
	var lineL4 = p.path(combine + ' l-78,-113 l-170,-40 l-650,0');

	var h1LineBundle = p.set();
	map["H1L12"] = lineL4;//H1L12 karena di mcc input pilihan ke jetty adalah L12
	map["H1S3"] = lineS3;

	//beberapa object line dimasukkan untuk jadi satu object
	h1LineBundle.push(lineL4);
	h1LineBundle.push(lineS3);
	mapLineBundle["H1"] = h1LineBundle;

	//buat text hopper1
	var txtHopper1 = p.text(hopper1LineWidth+75,hopper1LineHeight-10,"Hopper 1")
	.attr({'font-size': 14,'font-weight':'bold'});

	//buat text line S1
	var txtLineS1 =  p.text(hopper1LineWidth-27,hopper1LineHeight-60,"S1").lineTextAttr();

	//buat text line S3
	var txtLineS3 = p.text(hopper1LineWidth-300,hopper1LineHeight-60,"S3").lineTextAttr();

	//buat text line L12
	var txtLineL12 = p.text(hopper1LineWidth-165,hopper1LineHeight-120,"L12").lineTextAttr();

	//buat text line L4
	var txtLineL4 = p.text(hopper1LineWidth-780,hopper1LineHeight-142,"L4").lineTextAttr();

	//buat text line L1
	var txtLineL1 = p.text(hopper1LineWidth-260,hopper1LineHeight-110,"L1").lineTextAttr();
	txtLineL1.attr({transform:'r-90'});

	//buat text line L2
	var txtLineL2 = p.text(hopper1LineWidth-475,hopper1LineHeight-110,"L2").lineTextAttr();
	txtLineL2.attr({transform:'r-90'});

	//buat text line L3
	var txtLineL3 = p.text(hopper1LineWidth-675,hopper1LineHeight-110,"L3").lineTextAttr();
	txtLineL3.attr({transform:'r-90'});

	//buat text line S22
	var txtLineS22 = p.text(hopper1LineWidth-435,hopper1LineHeight-165,"S22").lineTextAttr();

	//buat text line S23
	var txtLineS23 = p.text(hopper1LineWidth-400,hopper1LineHeight-120,"S23").lineTextAttr();
	txtLineS23.attr({transform:'r20'});


}

//function yang membuat hopper2
function createHopper2(p, cWidth, cHeight, anim){

	//create kotak hopper 2
	var hopper2 = p.rect(cWidth-390,cHeight-245,20,45);	

	//memasukkan object hopper ke dalam map
	mapHopper["H2"] = hopper2;

	//mendapatkan posisi hopper2
	var hopper2LineWidth = cWidth-390-10;
	var hopper2LineHeight = cHeight-245+45;

	var combine = 'M'+hopper2LineWidth+','+hopper2LineHeight;
	var lineL26 = p.path(combine + 'l-167,-40 l-651,0');
	var lineS3 =  p.path(combine + 'l-167,-40 l-201,0 l0,27 l106,50');

	var h2LineBundle = p.set();
	map["H2S4"] = lineL26;
	map["H2S23"] = lineS3;

	//beberapa object line dijadikan dalam satu object
	h2LineBundle.push(lineL26);
	h2LineBundle.push(lineS3);
	mapLineBundle["H2"] = h2LineBundle;

	//create text hopper2 
	var text = p.text(hopper2LineWidth+75,hopper2LineHeight,"Hopper 2")
	.attr({'font-size': 14,'font-weight':'bold'});

	//buat text line S4
	var txtLineS4 =  p.text(hopper2LineWidth-65,hopper2LineHeight-30,"S4").lineTextAttr();

	//buat text line S21
	var txtLineS21 =  p.text(hopper2LineWidth-270,hopper2LineHeight-50,"S21").lineTextAttr();

	//buat text line S21
	var txtLineS21 =  p.text(hopper2LineWidth-270,hopper2LineHeight-50,"S21").lineTextAttr();

	//buat text line L25
	var txtLineL25 =  p.text(hopper2LineWidth-490,hopper2LineHeight-50,"L25").lineTextAttr();

	//buat text line L26
	var txtLineL26 =  p.text(hopper2LineWidth-700,hopper2LineHeight-50,"L26").lineTextAttr();

}

//function yang membuat hopper7
function createHopper7(p, cWidth, cHeight, anim){

	//create kotak hopper7
	var hopper7 = p.rect(cWidth-300,cHeight-270,20,45);
	hopper7.attr({transform: 'r67'});

	//memasukkan object hopper kedalam map
	mapHopper["H7"] = hopper7;


	//mendapatkan posisi hopper7
	var hopper7LineWidth = cWidth-316;
	var hopper7LineHeight = cHeight-250;

	var combine = 'M'+ hopper7LineWidth+','+hopper7LineHeight;
	var splCombineL16 = 'M'+ (hopper7LineWidth-290)+','+(hopper7LineHeight-185);
	var splCombineL14 = 'M'+ (hopper7LineWidth-530)+','+(hopper7LineHeight-185);

	var lineL16 = p.path(splCombineL16 + ' l0,50 l-440,0');//line L16 belom disambung sampai jetty K3
	var lineL14 = p.path(splCombineL14 + ' l0,50 l-200,0');//line l14 belom disambung sampai jetty K3

	var lineS20 = p.path(combine + ' l-30,-70 l-100,-32 l-300,0 l0,-135');
	var lineL26 = p.path(combine + ' l-30,-70 l-100,-32 l-600,0 l0,112 l-172,0' );
	var lineL29 = p.path(combine + ' l-30,-70 l-100,-32 l-600,0 l0,-152 l-70,-112 l-105,0' );

	//tambahkan object ke dalam MAP
	var h7LineBundle = p.set();
	map["H7L23"] = lineL29;//TO JETTY
	map["H7S20"] = lineS20;//TO STOCKPILE


	//beberapa object line dijadikan dalam satu object
	h7LineBundle.push(lineL29);
	h7LineBundle.push(lineS20);
	mapLineBundle["H7"] = h7LineBundle;

	//create text hopper7
	var txtHopper7 = p.text(hopper7LineWidth+80,hopper7LineHeight+20,"Hopper 7")
	.attr({'font-size': 14,'font-weight':'bold'});

	//create textS17
	var txtS17 = p.text(hopper7LineWidth-20,hopper7LineHeight-25,"S17").lineTextAttr();
	txtS17.attr({transform:'r70'});

	//create textS18
	var txtS18 = p.text(hopper7LineWidth-75,hopper7LineHeight-70,"S18").lineTextAttr();
	txtS18.attr({transform:'r20'});

	//create textS19
	var txtS19 = p.text(hopper7LineWidth-250,hopper7LineHeight-85,"S19").lineTextAttr();

	//create textS20
	var txtS20 = p.text(hopper7LineWidth-420,hopper7LineHeight-155,"S20").lineTextAttr();
	txtS20.attr({transform:'r-90'});

	//create textL23
	var txtL23 = p.text(hopper7LineWidth-550,hopper7LineHeight-85,"L23").lineTextAttr();

	//create textL24
	var txtL24 = p.text(hopper7LineWidth-740,hopper7LineHeight-70,"L24").lineTextAttr();
	txtL24.attr({transform:'r-90'});

	//create textL27
	var txtL27 = p.text(hopper7LineWidth-740,hopper7LineHeight-180,"L27").lineTextAttr();
	txtL27.attr({transform:'r-90'});

	//create textL28
	var txtL28 = p.text(hopper7LineWidth-770,hopper7LineHeight-300,"L28").lineTextAttr();
	txtL28.attr({transform:'r55'});

	//create textL29
	var txtL28 = p.text(hopper7LineWidth-850,hopper7LineHeight-350,"L29").lineTextAttr();

	// create txt L16
	var txtL16 = p.text(hopper7LineWidth-300,hopper7LineHeight-155,"L16").lineTextAttr();
	txtL16.attr({transform:'r-90'});

	// create txt L21
	var txtL21 = p.text(hopper7LineWidth-540,hopper7LineHeight-155,"L21").lineTextAttr();
	txtL21.attr({transform:'r-90'});

	// create txt L22
	var txtL22 = p.text(hopper7LineWidth-650,hopper7LineHeight-145,"L22").lineTextAttr();

	var spl20Circle = p.circle(hopper7LineWidth-430,hopper7LineHeight-235,50);

}

function createHopper5(p, cWidth, cHeight, anim){

	//create kotak hopper5
	var hopper5 = p.rect(cWidth-200,cHeight-310,20,45);
	hopper5.attr({transform: 'r110'})	

	//memasukkan object hopper kedalam map
	mapHopper["H5"] = hopper5;

	var hopper5LineWidth = cWidth-212;
	var hopper5LineHeight = cHeight-295;
	var combine = 'M'+hopper5LineWidth+','+hopper5LineHeight;
	var combineL8 = 'M'+(cWidth-1030)+','+(cHeight-600);

	var spl13Circle = p.circle(cWidth-950,cHeight-530,35);

	var lineL6 = p.path(combine+' l-233,-80 l-585,0 l0,150 l-188,0' );//to jetty 
	var lineL9 = p.path(combine+' l-233,-80 l-585,0 l0,-130 l-80,-130 l-110,0' );//to jetty 
	var lineS13 = p.path(combine+' l-233,-80 l-585,0 l0,-130 l80,-25' );//to stockpile

	var lineL8 = p.path(combineL8 + ' l-41,30'); //dorongan dari stockpile, line belom tersambung sampai ke jetty

	var h5LineBundle = p.set();
	map["H5L6"] = lineL6;
	map["H5L8"] = lineL9;
	map["H5S13"] = lineS13;

	//beberapa object line dijadikan dalam satu object bundle
	h5LineBundle.push(lineL6);
	h5LineBundle.push(lineL9);
	h5LineBundle.push(lineS13);
	mapLineBundle["H5"] = h5LineBundle;

	//create text hopper5
	var txtHopper5 = p.text(hopper5LineWidth+80,hopper5LineHeight+20,"Hopper 5")
	.attr({'font-size': 14,'font-weight':'bold'});

	//create textS11
	var txtS11 = p.text(hopper5LineWidth-50,hopper5LineHeight-25,"S11").lineTextAttr();
	txtS11.attr({transform:'r20'});

	//create textS12
	var txtS12 = p.text(hopper5LineWidth-210,hopper5LineHeight-80,"S12").lineTextAttr();
	txtS12.attr({transform:'r20'});

	//create textL13
	var txtL13 = p.text(hopper5LineWidth-280,hopper5LineHeight-90,"L13").lineTextAttr();

	//create textL5
	var txtL5 = p.text(hopper5LineWidth-810,hopper5LineHeight,"L5").lineTextAttr();
	txtL5.attr({transform:'r-90'});

	//create textL7
	var txtL7 = p.text(hopper5LineWidth-810,hopper5LineHeight-150,"L7").lineTextAttr();
	txtL7.attr({transform:'r-90'});

	//create textS13
	var txtS13 = p.text(hopper5LineWidth-800,hopper5LineHeight-225,"S13").lineTextAttr();
	txtS13.attr({transform:'r-10'});

	//create textL8
	var txtL8 = p.text(hopper5LineWidth-845,hopper5LineHeight-275,"L8").lineTextAttr();
	txtL8.attr({transform:'r60'});

	//create textL9
	var txtL9 = p.text(hopper5LineWidth-955,hopper5LineHeight-346,"L9").lineTextAttr();

	//create textL6
	var txtL6 = p.text(hopper5LineWidth-890,hopper5LineHeight+63,"L6").lineTextAttr();

}

function createHopper6(p, cWidth, cHeight, anim){

	//create kotak hopper6
	var hopper6 = p.rect(cWidth-375,cHeight-425,20,45);
	hopper6.attr({transform:'r20'});

	//memasukkan object hopper kedalam map
	mapHopper["H6"] = hopper6;

	var hopper6LineWidth = cWidth-374;
	var hopper6LineHeight = cHeight-381;

	var combine = 'M'+hopper6LineWidth+','+hopper6LineHeight;

	var lineS16 = p.path(combine +' l-15,36 l-55,-20 l-100,0 ll0,-120'); //to stockpile
	var lineL6 = p.path(combine +' l-15,36 l-55,-20 l-586,0 l0,140 l-188,0');//to jetty

	//masukkan objectLine keDalam Map
	var h6LineBundle = p.set();
	map["H6L15"] = lineL6;//to jetty, di program input pilihan line nya = L15
	map["H6S16"] = lineS16;//to stockpile

	h6LineBundle.push(lineL6);
	h6LineBundle.push(lineS16);
	mapLineBundle["H6"] = h6LineBundle;

	//create text hopper6
	var txtHopper6 = p.text(hopper6LineWidth+55,hopper6LineHeight-15,"Hopper 6")
	.attr({'font-size': 14,'font-weight':'bold'});

	//create text S14
	var txtLineS14 = p.text(hopper6LineWidth-12,hopper6LineHeight+10,"S14").lineTextAttr();
	txtLineS14.attr({transform:'r-65'});

	//create text S16
	var txtLineS16 = p.text(hopper6LineWidth-178,hopper6LineHeight-30,"S16").lineTextAttr();
	txtLineS16.attr({transform:'r-90'});

	var spl16Circle = p.circle(hopper6LineWidth-170,hopper6LineHeight-105,50);

}

function createHopper4(p, cWidth, cHeight, anim){

	//create kotak hopper4
	var hopper4 = p.rect(cWidth-320,cHeight-530,20,45);
	hopper4.attr({transform:'r50'});

	//memasukkan object hopper kedalam map
	mapHopper["H4"] = hopper4;

	var hopper4LineWidth = cWidth-308;
	var hopper4LineHeight = cHeight-533;

	var combine = 'M'+hopper4LineWidth+','+hopper4LineHeight;

	var lineS7 = p.path(combine+' l-55,-60 l-625,0');
	var lineL20 = p.path(combine+' l-30,-33 l-100,0 l-70,-87 l-713,0');
	var lineL17 = p.path(combine+' l-30,-33 l-100,0 l-70,-87 l-20,0 l0,50');
	var lineL18 = p.path(combine+' l-30,-33 l-100,0 l-70,-87 l-200,0 l0,50');
	var lineL19 = p.path(combine+' l-30,-33 l-100,0 l-70,-87 l-400,0 l0,50');

	var h4LineBundle = p.set();
	map["H4S7"] = lineS7;
	map["H4S10"] = lineL20;//H4S10 karena di mcc input pilihannya S10

	h4LineBundle.push(lineS7);
	h4LineBundle.push(lineL20);
	mapLineBundle["H4"] = h4LineBundle;


	//create text hopper4
	var txtHopper4 = p.text(hopper4LineWidth+50,hopper4LineHeight+35,"Hopper 4")
	.attr({'font-size': 14,'font-weight':'bold'});

	//create text S8
	var txtLineS8 = p.text(hopper4LineWidth-5,hopper4LineHeight-20,"S8").lineTextAttr();
	txtLineS8.attr({transform:'r50'});

	//create text S9
	var txtLineS9 = p.text(hopper4LineWidth-75,hopper4LineHeight-23,"S9").lineTextAttr();

	//create text S10
	var txtLineS10 = p.text(hopper4LineWidth-170,hopper4LineHeight-100,"S10").lineTextAttr();
	txtLineS10.attr({transform:'r50'});

	//create text L17
	var txtLineL17 = p.text(hopper4LineWidth-230,hopper4LineHeight-85,"L17").lineTextAttr();
	txtLineL17.attr({transform:'r-90'});	

	//create text L18
	var txtLineL18 = p.text(hopper4LineWidth-410,hopper4LineHeight-85,"L18").lineTextAttr();
	txtLineL18.attr({transform:'r-90'});	

	//create text L19
	var txtLineL19 = p.text(hopper4LineWidth-610,hopper4LineHeight-85,"L19").lineTextAttr();
	txtLineL19.attr({transform:'r-90'});	


	//create textL20
	var txtLineL20 = p.text(hopper4LineWidth-858,hopper4LineHeight-130,"L20").lineTextAttr();

}

function createHopper3(p, cWidth, cHeight, anim){

	//create kotak hopper3
	var hopper3 = p.rect(cWidth-200,cHeight-600,20,45);
	hopper3.attr({transform:'r70'});

	//memasukkan object hopper kedalam map
	mapHopper["H3"] = hopper3;

	hopper3LineWidth = cWidth-195;
	hopper3LineHeight = cHeight-590;

	var combine = 'M'+hopper3LineWidth+','+hopper3LineHeight;
	var lineS7 = p.path(combine+' l-30,-80 l-113,104 l-25,-27 l-625,0');//to stockpile
	var lineL20 = p.path(combine+' l-30,-80 l-113,104 l-100,0 l-70,-87 l-713,0');//to jetty

	var h3LineBundle = p.set();
	map["H3S7"] = lineS7; 
	map["H3S10"] = lineL20; //H3S10 karena di mcc input pilihannya S10

	h3LineBundle.push(lineS7);
	h3LineBundle.push(lineL20);
	mapLineBundle["H3"] = h3LineBundle;

	//create text hopper3
	var txtHopper3 = p.text(hopper3LineWidth+50,hopper3LineHeight+35,"Hopper 3")
	.attr({'font-size': 14,'font-weight':'bold'});

	//create text S5
	var txtLineS5 = p.text(hopper3LineWidth-25,hopper3LineHeight-30,"S5").lineTextAttr();

	//create text S6
	var txtLineS6 = p.text(hopper3LineWidth-100,hopper3LineHeight-30,"S6").lineTextAttr();
	txtLineS6.attr({transform:'r-45'});

	//create text S7
	var txtLineS7 = p.text(hopper3LineWidth-550,hopper3LineHeight+10,"S7").lineTextAttr();

}


function boatK1(p, cWidth, cHeight, anim){

	var boatK1 = p.rect(cWidth-1250,cHeight-270,50,100,10);
	var boatK3 = p.rect(cWidth-1250,cHeight-680,50,100,10);
	
	var textK1 = p.text(cWidth-1225,cHeight-155,"K1").attr({'font-size': 14,'font-weight':'bold', fill:"#ffffff"});
	var textK1 = p.text(cWidth-1225,cHeight-565,"K3").attr({'font-size': 14,'font-weight':'bold', fill:"#ffffff"});

}

//membuat object text quantity stock di stockpile
function createTextStokStpl(p, cWidth, cHeight,lJson){

	Raphael.el.textSpl = function(){
		this.attr({'font-size': 13,'font-weight':'bold'
			,fill:"#FFFF00","text-anchor":"start"});
		return this;
	}
	var flashy = Raphael.animation({opacity :.3},1000);

	//create textValue L1
	var stockL1 = p.text(cWidth - 560,cHeight - 170,0).textSpl();
//	var elem = stockL1.animate(flashy.repeat("Infinity"));

	//create textValue L2
	var stockL2 = p.text(cWidth - 775,cHeight - 170,0).textSpl();
//	stockL2.animateWith(elem, flashy, flashy.repeat("Infinity"));

	//create textValue L3
	var stockL3 = p.text(cWidth - 975,cHeight - 170,0).textSpl();
//	stockL3.animateWith(elem, flashy, flashy.repeat("Infinity"));

	//create textValue L16
	var stockL16 = p.text(cWidth - 610,cHeight - 425,0).textSpl();
	stockL16.attr({"text-anchor":"end"})
//	stockL16.animateWith(elem, flashy, flashy.repeat("Infinity"));

	//create textValue L21
	var stockL21 = p.text(cWidth - 855,cHeight - 425,0).textSpl();
	stockL21.attr({"text-anchor":"end"})
//	stockL21.animateWith(elem, flashy, flashy.repeat("Infinity"));

	//create textValue L17
	var stockL17 = p.text(cWidth - 520,cHeight - 610,0).textSpl();
//	stockL17.animateWith(elem, flashy, flashy.repeat("Infinity"));

	//create textValue L18
	var stockL18 = p.text(cWidth - 700,cHeight - 610,0).textSpl();
//	stockL18.animateWith(elem, flashy, flashy.repeat("Infinity"));

	//create textValue L19
	var stockL19 = p.text(cWidth - 900,cHeight - 610,0).textSpl();
//	stockL19.animateWith(elem, flashy, flashy.repeat("Infinity"));

	//create textValue L8
	var stockL8 = p.text(cWidth - 1025,cHeight - 610,0).textSpl();
//	stockL8.animateWith(elem, flashy, flashy.repeat("Infinity"));
//	stockL8.animate(flashy.repeat("Infinity"));

	mapStockSpl["L1"] = stockL1;
	mapStockSpl["L2"] = stockL2;
	mapStockSpl["L3"] = stockL3;
	mapStockSpl["L16"] = stockL16;
	mapStockSpl["L21"] = stockL21;
	mapStockSpl["L17"] = stockL17;
	mapStockSpl["L18"] = stockL18;
	mapStockSpl["L19"] = stockL19;
	mapStockSpl["L8"] = stockL8;

	createTextStplCv(p, cWidth, cHeight, lJson);

	var obj = JSON.parse(lJson);
	for(var i = 0 ; i < obj.length; i++){

		var stockSpl = getStockSpl(obj[i].pk.reclaim);
		//stockSpl.attr({text: obj[i].currentStock.toLocaleString()});
	}

}

function createTextStplCv(p, cWidth, cHeight,lJson){

	Raphael.el.cvText = function(){
		this.attr({'font-size': 11,'font-weight':'bold',"text-anchor":"start", fill:"#ffffff"});
		return this;
	}

	Raphael.el.cvTextVal = function(){
		this.attr({'font-size': 11,'font-weight':'bold',"text-anchor":"start", fill:"#ffb3ff"});
		return this;
	}


	var title = p.text(cWidth-1100,cHeight-790,"Stockpile CV");
	title.attr({"text-decoration":"underline", "text-anchor":"start", "font-size":14
		, 'font-weight':'bold',fill:"#0D0D0D"});

	var cvSet = p.set();
	var cvBox = p.rect(cWidth-1100,cHeight-780,450,60,10);
	var l1Text = p.text(cWidth-1090, cHeight-760,"L1 :").cvText();
	var l2Text = p.text(cWidth-1090, cHeight-740,"L2 :").cvText();
	var l3Text = p.text(cWidth-1000, cHeight-760,"L3 :").cvText();
	var l8Text = p.text(cWidth-1000, cHeight-740,"L8 :").cvText();
	var l16Text = p.text(cWidth-910, cHeight-760,"L16 :").cvText();
	var l17Text = p.text(cWidth-910, cHeight-740,"L17 :").cvText();
	var l18Text = p.text(cWidth-820, cHeight-760,"L18 :").cvText();
	var l19Text = p.text(cWidth-820, cHeight-740,"L19 :").cvText();
	var l21Text = p.text(cWidth-730, cHeight-760,"L21 :").cvText();


	var l1TextVal = p.text(cWidth-1065, cHeight-760,"").cvTextVal();
	var l2TextVal = p.text(cWidth-1065, cHeight-740,"").cvTextVal();
	var l3TextVal = p.text(cWidth-975, cHeight-760,"").cvTextVal();
	var l8TextVal = p.text(cWidth-975, cHeight-740,"").cvTextVal();
	var l16TextVal = p.text(cWidth-880, cHeight-760,"").cvTextVal();
	var l17TextVal = p.text(cWidth-880, cHeight-740,"").cvTextVal();
	var l18TextVal = p.text(cWidth-790, cHeight-760,"").cvTextVal();
	var l19TextVal = p.text(cWidth-790, cHeight-740,"").cvTextVal();
	var l21TextVal = p.text(cWidth-700, cHeight-760,"").cvTextVal();

	cvSet.push(cvBox);
	cvSet.push(title);
	cvSet.push(l1Text);
	cvSet.push(l2Text);
	cvSet.push(l3Text);
	cvSet.push(l8Text);
	cvSet.push(l16Text);
	cvSet.push(l17Text);
	cvSet.push(l18Text);
	cvSet.push(l19Text);
	cvSet.push(l21Text);
	cvSet.push(l1TextVal);
	cvSet.push(l2TextVal);
	cvSet.push(l3TextVal);
	cvSet.push(l8TextVal);
	cvSet.push(l16TextVal);
	cvSet.push(l17TextVal);
	cvSet.push(l18TextVal);
	cvSet.push(l19TextVal);
	cvSet.push(l21TextVal);

	cvSet.translate(-25,25);

	mapStockSpl["CVL1"] = l1TextVal;
	mapStockSpl["CVL2"] = l2TextVal;
	mapStockSpl["CVL3"] = l3TextVal;
	mapStockSpl["CVL16"] = l16TextVal;
	mapStockSpl["CVL21"] = l21TextVal;
	mapStockSpl["CVL17"] = l17TextVal;
	mapStockSpl["CVL18"] = l18TextVal;
	mapStockSpl["CVL19"] = l19TextVal;
	mapStockSpl["CVL8"] = l8TextVal;

	var obj = JSON.parse(lJson);
	for(var i = 0 ; i < obj.length; i++){

		var stockSpl = getStockSpl("CV"+obj[i].pk.reclaim);
		stockSpl.attr({text: obj[i].currentCv.toLocaleString()});
	}
}


function createTextBreakDown(p, cWidth, cHeight){

	Raphael.el.breakDownText = function(){
		this.attr({'font-size': 13,'font-weight':'bold'
			,fill:"#FF0000","text-anchor":"start"
				,opacity:0,'font-weight':'bold'});
		return this;
	}

	var breakHopper1 = p.text(cWidth-275, cHeight-50,"").breakDownText();
	var breakHopper2 = p.text(cWidth-355, cHeight-180,"").breakDownText();
	var breakHopper7 = p.text(cWidth-267, cHeight-210,"").breakDownText();
	var breakHopper5 = p.text(cWidth-162, cHeight-255,"").breakDownText();
	var breakHopper6 = p.text(cWidth-350, cHeight-375,"").breakDownText();
	var breakHopper4 = p.text(cWidth-290, cHeight-480,"").breakDownText();
	var breakHopper3 = p.text(cWidth-175, cHeight-535,"").breakDownText();

	mapBreakDownText["H1"] = breakHopper1;
	mapBreakDownText["H2"] = breakHopper2;
	mapBreakDownText["H3"] = breakHopper3;
	mapBreakDownText["H4"] = breakHopper4;
	mapBreakDownText["H5"] = breakHopper5;
	mapBreakDownText["H6"] = breakHopper6;
	mapBreakDownText["H7"] = breakHopper7;
}

function createTextTotalProduksi(p, cWidth, cHeight){

	var totalProdHopper1 = p.text(cWidth-210, cHeight-70,0).totalProdTextAttr();
	var totalProdHopper2 = p.text(cWidth-355, cHeight-215,0).totalProdTextAttr();
	var totalProdHopper7 = p.text(cWidth-200, cHeight-230,0).totalProdTextAttr();
	var totalProdHopper5 = p.text(cWidth-95, cHeight-275,0).totalProdTextAttr();
	var totalProdHopper6 = p.text(cWidth-280, cHeight-395,0).totalProdTextAttr();
	var totalProdHopper4 = p.text(cWidth-220, cHeight-497,0).totalProdTextAttr();
	var totalProdHopper3 = p.text(cWidth-110, cHeight-555,0).totalProdTextAttr();


	mapTotalProdText["H1"] = totalProdHopper1;
	mapTotalProdText["H2"] = totalProdHopper2;
	mapTotalProdText["H3"] = totalProdHopper3;
	mapTotalProdText["H4"] = totalProdHopper4;
	mapTotalProdText["H5"] = totalProdHopper5;
	mapTotalProdText["H6"] = totalProdHopper6;
	mapTotalProdText["H7"] = totalProdHopper7;

}

Raphael.el.lineTextAttr = function(){
	this.attr({'font-size': 11,'font-weight':'bold', fill:"#ffffff"});
	return this;
};

Raphael.el.totalProdTextAttr = function(){
	this.attr({'font-size': 12,'font-weight':'bold', fill:"#00ffff","text-anchor":"start"});
	return this;
};

function getLine(line){
	return map[line];
}

function getHopper(hopper){
	return mapHopper[hopper];
}

function getStockSpl(reclaim){
	return mapStockSpl[reclaim];
}

function getBreakDownText(hopper){
	return mapBreakDownText[hopper];
}

function getLastEntryTrailer(hopper){
	return mapLastTrailerText[hopper];
}

function getBargeTextValObject(objectToFind){
	return mapBargeTextValue[objectToFind];
}

function getTotalTextProduksi(textTotalProduct){
	return mapTotalProdText[textTotalProduct];
}

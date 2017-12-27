//paper.install(window);
function onLoadPlantStatus(hoppers) {
	paper.install(window);
	paper.setup('myCanvas');

	var groupHopper1 = new Group();
	var groupHopper2 = new Group();
	var groupHopper3 = new Group();
	var groupHopper4 = new Group();
	var groupHopper5 = new Group();
	var groupHopper6 = new Group();
	var groupHopper7 = new Group();

	var obj = JSON.parse(hoppers);
	console.log(obj);

	// mapping ke object dari json
	var h1DariJson = obj[0];
	var h2DariJson = obj[1];
	var h3DariJson = obj[2];
	var h4DariJson = obj[3];
	var h5DariJson = obj[4];
	var h6DariJson = obj[5];
	var h7DariJson = obj[6];

	var hopper1 = new h1Proto(h1DariJson);
	hopper1.graphic();

	var hopper2 = new h2Proto(h2DariJson);
	hopper2.graphic();
	
	var hopper3 = new h3Proto(h3DariJson);
	hopper3.graphic();
	
	var hopper4 = new h4Proto(h4DariJson);
	hopper4.graphic();
	
	var hopper5 = new h5Proto(h5DariJson);
	hopper5.graphic();
	
	var hopper6 = new h6Proto(h6DariJson);
	hopper6.graphic();
	
	var hopper7 = new h7Proto(h7DariJson);
	hopper7.graphic();

	// method untuk menjalankan animasi
	// method ini di jalankan 60x dalam satu detik
	view.onFrame = function(event) {
		hopper1.animation();
		hopper2.animation();
		hopper3.animation();
		hopper4.animation();
		hopper5.animation();
		hopper6.animation();
		hopper7.animation();
	}

	function h1Proto(objDariJson) {
		this.hopper = objDariJson;
		this.lightness = 1;
		this.max = "false";

		this.graphic = function() {

			var hopperBox = new Path.Rectangle(10, 10, 75, 50);
			var hopperLine = new Path.Line({
				from : [ 85, 35 ],
				to : [ 320, 35 ]
			});
			var hopperBoxStatus = new Path.Rectangle(320, 10, 75, 50);
			var hopperTextStatus = new PointText(new Point(170, 30));
			var hopperName = new PointText(new Point(38, 40));

			hopperName.content = this.hopper.hopperCode;
			hopperName.fillColor = "black";

			hopperTextStatus.fillColor = "black";

			if (this.hopper.hasOwnProperty("problemName")) {
				hopperTextStatus.content = this.hopper.problemName;

				hopperBox.fillColor = {
					hue : 0,
					saturation : 1,
					lightness : 0.5
				};
			} else {
				hopperTextStatus.content = this.hopper.lineCode;

				hopperBox.fillColor = {
					hue : 120,
					saturation : 0.6,
					lightness : 0.5
				};
			}

			this.lightness = hopperBox.fillColor.lightness;

			groupHopper1.addChild(hopperBox);
			groupHopper1.addChild(hopperLine);
			groupHopper1.addChild(hopperBoxStatus);
			groupHopper1.addChild(hopperName);

			groupHopper1.strokeColor = 'black'
		};

		this.animation = function() {

			if (this.hopper.hasOwnProperty("problemName")) {
				groupHopper1.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			} else {
				groupHopper1.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			}
		};
	}

	function h2Proto(objDariJson) {
		this.hopper = objDariJson;
		this.lightness = 1;
		this.max = "false";

		this.graphic = function() {

			var hopperBox = new Path.Rectangle(10, 70, 75, 50);
			var hopperLine = new Path.Line({
				from : [ 85, 95 ],
				to : [ 320, 95 ]
			});
			var hopperBoxStatus = new Path.Rectangle(320, 70, 75, 50);
			var hopperTextStatus = new PointText(new Point(170, 90));
			var hopperName = new PointText(new Point(38, 100));

			hopperName.content = this.hopper.hopperCode;
			hopperName.fillColor = "black";

			hopperTextStatus.fillColor = "black";

			if (this.hopper.hasOwnProperty("problemName")) {
				hopperTextStatus.content = this.hopper.problemName;

				hopperBox.fillColor = {
					hue : 0,
					saturation : 1,
					lightness : 0.5
				};
			} else {
				hopperTextStatus.content = this.hopper.lineCode;

				hopperBox.fillColor = {
					hue : 120,
					saturation : 0.6,
					lightness : 0.5
				};
			}

			this.lightness = hopperBox.fillColor.lightness;

			groupHopper2.addChild(hopperBox);
			groupHopper2.addChild(hopperLine);
			groupHopper2.addChild(hopperBoxStatus);
			groupHopper2.addChild(hopperName);

			groupHopper2.strokeColor = 'black'
		};

		this.animation = function() {

			if (this.hopper.hasOwnProperty("problemName")) {
				groupHopper2.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			} else {
				groupHopper2.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			}
		};
	}

	function h3Proto(objDariJson) {
		this.hopper = objDariJson;
		this.lightness = 1;
		this.max = "false";

		this.graphic = function() {

			var hopperBox = new Path.Rectangle(10, 130, 75, 50);
			var hopperLine = new Path.Line({
				from : [ 85, 155 ],
				to : [ 320, 155 ]
			});
			var hopperBoxStatus = new Path.Rectangle(320, 130, 75, 50);
			var hopperTextStatus = new PointText(new Point(170, 150));
			var hopperName = new PointText(new Point(38, 160));

			hopperName.content = this.hopper.hopperCode;
			hopperName.fillColor = "black";

			hopperTextStatus.fillColor = "black";

			if (this.hopper.hasOwnProperty("problemName")) {
				hopperTextStatus.content = this.hopper.problemName;

				hopperBox.fillColor = {
					hue : 0,
					saturation : 1,
					lightness : 0.5
				};
			} else {
				hopperTextStatus.content = this.hopper.lineCode;

				hopperBox.fillColor = {
					hue : 120,
					saturation : 0.6,
					lightness : 0.5
				};
			}

			this.lightness = hopperBox.fillColor.lightness;

			groupHopper3.addChild(hopperBox);
			groupHopper3.addChild(hopperLine);
			groupHopper3.addChild(hopperBoxStatus);
			groupHopper3.addChild(hopperName);

			groupHopper3.strokeColor = 'black'
		};

		this.animation = function() {

			if (this.hopper.hasOwnProperty("problemName")) {
				groupHopper3.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			} else {
				groupHopper3.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			}
		};
	}
	
	function h4Proto(objDariJson) {
		this.hopper = objDariJson;
		this.lightness = 1;
		this.max = "false";

		this.graphic = function() {

			var hopperBox = new Path.Rectangle(10, 190, 75, 50);
			var hopperLine = new Path.Line({
				from : [ 85, 215 ],
				to : [ 320, 215 ]
			});
			var hopperBoxStatus = new Path.Rectangle(320, 190, 75, 50);
			var hopperTextStatus = new PointText(new Point(170, 210));
			var hopperName = new PointText(new Point(38, 220));

			hopperName.content = this.hopper.hopperCode;
			hopperName.fillColor = "black";

			hopperTextStatus.fillColor = "black";

			if (this.hopper.hasOwnProperty("problemName")) {
				hopperTextStatus.content = this.hopper.problemName;

				hopperBox.fillColor = {
					hue : 0,
					saturation : 1,
					lightness : 0.5
				};
			} else {
				hopperTextStatus.content = this.hopper.lineCode;

				hopperBox.fillColor = {
					hue : 120,
					saturation : 0.6,
					lightness : 0.5
				};
			}

			this.lightness = hopperBox.fillColor.lightness;

			groupHopper4.addChild(hopperBox);
			groupHopper4.addChild(hopperLine);
			groupHopper4.addChild(hopperBoxStatus);
			groupHopper4.addChild(hopperName);

			groupHopper4.strokeColor = 'black'
		};

		this.animation = function() {

			if (this.hopper.hasOwnProperty("problemName")) {
				groupHopper4.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			} else {
				groupHopper4.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			}
		};
	}

	function h5Proto(objDariJson) {
		this.hopper = objDariJson;
		this.lightness = 1;
		this.max = "false";

		this.graphic = function() {

			var hopperBox = new Path.Rectangle(410, 10, 75, 50);
			var hopperLine = new Path.Line({
				from : [ 485, 35 ],
				to : [ 720, 35 ]
			});
			var hopperBoxStatus = new Path.Rectangle(720, 10, 75, 50);
			var hopperTextStatus = new PointText(new Point(570, 30));
			var hopperName = new PointText(new Point(438, 40));

			hopperName.content = this.hopper.hopperCode;
			hopperName.fillColor = "black";

			hopperTextStatus.fillColor = "black";

			if (this.hopper.hasOwnProperty("problemName")) {
				hopperTextStatus.content = this.hopper.problemName;

				hopperBox.fillColor = {
					hue : 0,
					saturation : 1,
					lightness : 0.5
				};
			} else {
				hopperTextStatus.content = this.hopper.lineCode;

				hopperBox.fillColor = {
					hue : 120,
					saturation : 0.6,
					lightness : 0.5
				};
			}

			this.lightness = hopperBox.fillColor.lightness;

			groupHopper5.addChild(hopperBox);
			groupHopper5.addChild(hopperLine);
			groupHopper5.addChild(hopperBoxStatus);
			groupHopper5.addChild(hopperName);

			groupHopper5.strokeColor = 'black'
		};

		this.animation = function() {

			if (this.hopper.hasOwnProperty("problemName")) {
				groupHopper5.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			} else {
				groupHopper5.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			}
		};
	}
	
	function h6Proto(objDariJson) {
		this.hopper = objDariJson;
		this.lightness = 1;
		this.max = "false";

		this.graphic = function() {

			var hopperBox = new Path.Rectangle(410, 70, 75, 50);
			var hopperLine = new Path.Line({
				from : [ 485, 95 ],
				to : [ 720, 95 ]
			});
			var hopperBoxStatus = new Path.Rectangle(720, 70, 75, 50);
			var hopperTextStatus = new PointText(new Point(570, 90));
			var hopperName = new PointText(new Point(438, 100));

			hopperName.content = this.hopper.hopperCode;
			hopperName.fillColor = "black";

			hopperTextStatus.fillColor = "black";

			if (this.hopper.hasOwnProperty("problemName")) {
				hopperTextStatus.content = this.hopper.problemName;

				hopperBox.fillColor = {
					hue : 0,
					saturation : 1,
					lightness : 0.5
				};
			} else {
				hopperTextStatus.content = this.hopper.lineCode;

				hopperBox.fillColor = {
					hue : 120,
					saturation : 0.6,
					lightness : 0.5
				};
			}

			this.lightness = hopperBox.fillColor.lightness;

			groupHopper6.addChild(hopperBox);
			groupHopper6.addChild(hopperLine);
			groupHopper6.addChild(hopperBoxStatus);
			groupHopper6.addChild(hopperName);

			groupHopper6.strokeColor = 'black'
		};

		this.animation = function() {

			if (this.hopper.hasOwnProperty("problemName")) {
				groupHopper6.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			} else {
				groupHopper6.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			}
		};
	}
	
	function h7Proto(objDariJson) {
		this.hopper = objDariJson;
		this.lightness = 1;
		this.max = "false";

		this.graphic = function() {

			var hopperBox = new Path.Rectangle(410, 130, 75, 50);
			var hopperLine = new Path.Line({
				from : [ 485, 155 ],
				to : [ 720, 155 ]
			});
			var hopperBoxStatus = new Path.Rectangle(720, 130, 75, 50);
			var hopperTextStatus = new PointText(new Point(570, 150));
			var hopperName = new PointText(new Point(438, 160));

			hopperName.content = this.hopper.hopperCode;
			hopperName.fillColor = "black";

			hopperTextStatus.fillColor = "black";

			if (this.hopper.hasOwnProperty("problemName")) {
				hopperTextStatus.content = this.hopper.problemName;

				hopperBox.fillColor = {
					hue : 0,
					saturation : 1,
					lightness : 0.5
				};
			} else {
				hopperTextStatus.content = this.hopper.lineCode;

				hopperBox.fillColor = {
					hue : 120,
					saturation : 0.6,
					lightness : 0.5
				};
			}

			this.lightness = hopperBox.fillColor.lightness;

			groupHopper7.addChild(hopperBox);
			groupHopper7.addChild(hopperLine);
			groupHopper7.addChild(hopperBoxStatus);
			groupHopper7.addChild(hopperName);

			groupHopper7.strokeColor = 'black'
		};

		this.animation = function() {

			if (this.hopper.hasOwnProperty("problemName")) {
				groupHopper7.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			} else {
				groupHopper7.children[0].fillColor.lightness = this.lightness;
				if (this.max == "false") {
					this.lightness += 0.005;
				}

				if (this.max == "true") {
					this.lightness -= 0.005;
				}

				if (this.lightness.toFixed(1) == 1) {
					this.max = "true";
				}

				if (this.lightness.toFixed(1) == 0.5) {
					this.max = "false";
				}
			}
		};
	}
}

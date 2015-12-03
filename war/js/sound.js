jQuery(document).ready( function() {
    //音源をロード
	sound1 = new Audio('/sound/scan.mp3');
	sound2 = new Audio('/sound/load.mp3');
	sound3 = new Audio('/sound/warning.mp3');
	sound4 = new Audio('/sound/callback.mp3');
	sound5 = new Audio('/sound/warning2.mp3');

	sound6 = new Audio('/sound/mario/1Up.mp3');
	sound7 = new Audio('/sound/mario/dokan.mp3');
	sound8 = new Audio('/sound/mario/gameOver.mp3');

	sound9 = new Audio('/sound/scan2.mp3');
	sound10 = new Audio('/sound/scan3.mp3');
	sound11 = new Audio('/sound/warning3.mp3');


	sound1.load();
	sound2.load();
	sound3.load();
	sound4.load();
	sound5.load();

	sound9.load();
	sound10.load();
	sound11.load();
});

function soundScan2(){
	sound10.play();
}

function soundScan(){
	sound1.play();
}
function soundSuccess(){
	sound2.play();
}
function soundWarning(){
	sound3.play();
}
function soundCallback(){
	sound4.play();
}
function soundWarning2(){
	sound5.play();
}

function sound1Up(){
	sound6.play();
}

function soundDokan(){
	sound7.play();
}

function soundGameOver(){
	sound8.play();
}


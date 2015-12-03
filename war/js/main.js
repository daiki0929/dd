
//ダブルサブミット用フラグ
var submitFlg = false;

// ===============================
// 桁区切り
// ===============================
function addFigure(str) {
	var num = new String(str).replace(/,/g, "");
	while(num != (num = num.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
	return num;
}

// ===============================
// 遷移
// ===============================
function nextAction(thatForm, url){
	thatForm.action = url;
	if(!submitFlg) {
		submitFlg = true;
		thatForm.submit();
	 }else{
		 submitFlg = false;
	 }
}

// ===============================
// 遷移 ウィンドウ指定
// ===============================
function nextAction(thatForm, url, target){
	d = new Date();
	if(target == '_blank'){
	    targetName = 'A' + d.getHours() + d.getMinutes() + d.getSeconds() + d.getMilliseconds();
		window.open("about:blank",targetName);
		thatForm.action = url;
		thatForm.target = targetName;
		thatForm.method = "post";
		thatForm.submit();
	}else if(target != undefined){
		window.open("about:blank",target);
		thatForm.action = url;
		thatForm.target = target;
		thatForm.method = "post";
		thatForm.submit();

	}else if(target == undefined){
		thatForm.action = url;
		thatForm.target = '_self';
		if(!submitFlg) {
			submitFlg = true;
			thatForm.submit();
		 }else{
			 submitFlg = false;
		 }
	}
}

//===============================
// BCCへ遷移
//===============================
function bccPage(id, redirect){
	if(redirect != ''){
		window.open("/bcc/login/done?id=" + id + "&redirect=" + redirect, '_comm');
	}else{
		window.open("/bcc/login/done?id=" + id, '_comm');
	}
}
//===============================
//めがぽんへ遷移
//===============================
function megaponPage(id, redirect){
	if(redirect != ''){
		window.open("/megapon/login/done?id=" + id + "&redirect=" + redirect, '_comm');
	}else{
		window.open("/megapon/login/done?id=" + id, '_comm');
	}
}

// ===============================
// ダウンロード
// ===============================
function download(thatForm, url){
	thatForm.action = url;
	thatForm.submit();
}

// ===============================
// メッセージ表示
// ===============================
function showMsg(id, str, msgLevel){

	if(str != ""){
		$(id).removeClass('message-area-info');
		$(id).removeClass('message-area-error');
		$(id).addClass(msgLevel);
		$(id).html('<button class="close" data-dismiss="' + msgLevel + '">&times;</button><strong>！</strong> ' + str);
		$(id).fadeIn(700);
		if(msgLevel == 'alert message-area-error'){
			$(id).delay(5000).fadeOut(1000);
		}else{
			$(id).delay(2000).fadeOut(2000);
		}
	}
}
function showSuccessMsg(id, str){

	if(str != ""){
		$(id).removeClass('message-area-error');
		$(id).addClass('message-area-info');
		$(id).html('<button class="close">&times;</button><strong><i class="icon-ok-sign"></i></strong> ' + str.replace(/\r?\n/g, '<br>'));
		$(id).fadeIn(700);
		$(id).delay(1500).fadeOut(1000);
	}
}
function showErrorMsg(id, str){
	if(str != ""){
		$(id).removeClass('message-area-info');
		$(id).addClass('message-area-error');
		$(id).html('<button class="close">&times;</button><strong><i class="icon-exclamation-sign"></i></strong> ' + str.replace(/\r?\n/g, '<br>'));
		$(id).fadeIn(700);
		$(id).delay(5000).fadeOut(1000);
	}
}


// ===============================
// メタエンコード
// ===============================
function fh(str,flg) {
	str = str.replace(/&/g,"&amp;");
	str = str.replace(/"/g,"&quot;");
	str = str.replace(/'/g,"&#039;");
	str = str.replace(/</g,"&lt;");
	str = str.replace(/>/g,"&gt;");
	if(flg == true)
	{
		str = str.replace(/\n/g,"<br>");
	}
	return str;
}


// ===============================
// 3桁区切り
// ===============================
function numberSeparator(str) {
	var num = new String(str).replace(/,/g, "");
	while(num != (num = num.replace(/^(-?\d+)(\d{3})/, "$1,$2")));
	return num;
}

// ===============================
// 推奨ブラウザ判定
// ===============================
function isDeprecatedBrowser() {
	var msie=navigator.appVersion.toLowerCase();
	msie=(msie.indexOf('msie')>-1)?parseInt(msie.replace(/.*msie[ ]/,'').match(/^[0-9]+/)):0;

	if ((msie < 10) && (msie != 0)) {
		return true;
	}else{
		return false;
	}
}

//===============================
//PC判定 画面サイズではなく、OSで判定
//===============================
function isPc() {
	if (
		navigator.userAgent.indexOf('iPhone') > 0
		||navigator.userAgent.indexOf('iPad') > 0
		|| navigator.userAgent.indexOf('iPod') > 0
		|| navigator.userAgent.indexOf('Android') > 0) {
		return false;
	}else{
		return true;
	}
}

function round(val, errorChara){
	if(Math.round(val)){
		return Math.round(val * 10) /10;
	}else{
		return errorChara;
	}

}


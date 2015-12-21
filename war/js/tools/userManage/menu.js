
/**
 * メニューを新規作成します。
 */
var createUrl = "/tools/userManage/reserve/doneCreateMenu";
function createMenu(){
	console.log("スタート");
	var title = $("[name=title]").val();
	var time = $("[name=time]").val();
	var price = $("[name=price]").val();
	var content = $("[name=content]").val();
	var img = $("[name=img]").val();
	var menuPageKey = $("[name=menuPageKey]").val();
	$.post(createUrl, {
		'title' : title,
		'time' : time,
		'price' : price,
		'content' : content,
		'img' : img,
		'menuPageKey' : menuPageKey
	}, function(data){
		switch(data.obj){
		case null:
			alert("読み込みに失敗しました");
			break;

		default:
			console.log("OK" + data.obj);
		location.href = "/tools/userManage/reserve/menuList?id=" + menuPageKey;
		break;
		}
	}, 'json');
}

/**
 * メニューを非公開にします。
 */
var closeUrl = "/tools/userManage/reserve/closeMenu";
function closeMenu(menuKey){
	console.log("非公開にします。");
	console.log(menuKey);
	$.post(closeUrl, {
		'menuKey' : menuKey,
	}, function(data){
		switch(data.obj){
		case null:
			alert("読み込みに失敗しました");
			break;

		default:
			$('#' + menuKey).append("<p style='color:red;'>非公開にしました</p>");
		$('#' + menuKey).fadeOut("slow");
		break;
		}
	}, 'json');
}
/**
 * メニューを公開にします。
 */
var doPublicUrl = "/tools/userManage/reserve/doPublicMenu";
function doPublicMenu(menuKey){
	console.log("公開にします。");
	console.log(menuKey);
	$.post(doPublicUrl, {
		'menuKey' : menuKey,
	}, function(data){
		switch(data.obj){
		case null:
			alert("読み込みに失敗しました");
			break;

		default:
			$('#' + menuKey).append("<p style='color:red;'>公開にしました</p>");
		$('#' + menuKey).fadeOut("slow");
		break;
		}
	}, 'json');
}
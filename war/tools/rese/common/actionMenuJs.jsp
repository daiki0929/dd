<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
//menuList.jsp
/**
 * メニューを新規作成します。
 */
var createUrl = "/tools/rese/reserve/doneCreateMenu";
function createMenu(){
	console.log("スタート");
	var title = $("[name=title]").val();
	var time = $("[name=time]").val();
	var price = $("[name=price]").val();
	var content = $("[name=content]").val();
	var imgPath = $("[name=imgPath]").val();
	var menuPageKey = $("[name=menuPageKey]").val();
	$.post(createUrl, {
		'title' : title,
		'time' : time,
		'price' : price,
		'content' : content,
		'imgPath' : imgPath,
		'menuPageKey' : menuPageKey
	}, function(data){
		switch(data.obj){
		case null:
			alert("読み込みに失敗しました");
			break;

		default:
			console.log("OK" + data.obj);
		location.href = "/tools/rese/reserve/menuList?id=" + menuPageKey;
		break;
		}
	}, 'json');
}

/**
 * メニューを非公開にします。
 */
var closeUrl = "/tools/rese/reserve/closeMenu";
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
var doPublicUrl = "/tools/rese/reserve/doPublicMenu";
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
</script>
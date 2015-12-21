<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/userManage/common/meta.jsp"%>
<title>メニューページ一覧</title>
<!-- css -->
<%@ include file="/tools/userManage/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/userManage/common/importJs.jsp"%>
<script type="text/javascript">
/**
 * 予約を完了します。
 */
var postUrl = "/tools/userManage/reserve/doneReserve";
function CompleteReserve(daysOfTheWeek){
	var name = $(".name").val();
	var mailadress = $(".mailadress").val();
	var phone = $(".phone").val();

	$.post(postUrl, {
		'menuKey' : '${f:h(menu.key)}',
		'reserveTime' : '${reserveTime}',
		'menuEndTime' : '${menuEndTime}',
		'name' : name,
		'mailadress' : mailadress,
		'phone' : phone
	}, function(data){
		console.log(data.obj);
		switch(data.obj){
		case null:
			alert("読み込みに失敗しました。");
			break;

		default:
			location.href = "/tools/userManage/reserve/finishReserve";
		break;
		}
	}, 'json');
}
</script>

</head>
<body>
	<div class="container">
		<div class="span12">
			<h2>予約内容</h2>
			<h3>メニュー情報</h3>
			<p>メニュー：${menu.title}</p>
			<p>予約日：${reserveTime} ~ ${menuEndTime}</p>
			<p>料金：${menu.price}円</p>

			<h3>お客様情報</h3>
			<p class="name">お名前：${customerName}様</p>
			<p class="mailadress">メールアドレス：${customerMailadress}</p>
			<p class="phone">携帯番号：${customerPhone}</p>

			<p class="btn btn-info" onclick="CompleteReserve();">予約を完了する</p>
		</div>
	</div>
</body>
</html>

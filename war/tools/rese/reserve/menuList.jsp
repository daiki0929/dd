<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>メニューページ一覧</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<%@ include file="/tools/rese/common/actionMenuJs.jsp"%>
<style type="text/css">
.textForm{
	width: 80%;
	padding: 10px 10px 10px 10px;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	//メニューの時間を分にして、切り捨てをする。
	$(".menuTime").each(function(i, elem) {
		$(".menuTime")[i].innerText = Math.floor($(elem).text());
	});
	//非公開メニューのURLをセット
	$(".closedMenuURL").attr("href" , location.pathname + '?id=${f:h(menuPageKey)}' + "&s=closed");
});
</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container mainContent">
		<div class="span12">
			<h3>メニューの新規作成</h3>
			<div class="span8">
				<form name="form" method="post" id="formId" enctype="multipart/form-data">
					<h5>メニューの名前</h5>
					<input type="text" name="title" value="${menu.title}" style="width: 80%; padding: 20px 20px 20px 5px;">
					<h5>時間</h5>
					<select name="time">
						<option value="1800">30 分</option>
						<option value="2400">40 分</option>
						<option value="2700">45 分</option>
						<option value="3600">60 分</option>
						<option value="4500">75 分</option>
						<option value="5400">90 分</option>
						<option value="7200">120 分</option>
						<option value="9000">150 分</option>
						<option value="10800">180 分</option>
						<option value="14400">240 分</option>
						<option value="18000">300 分</option>
						<option value="21600">360 分</option>
						<option value="25200">420 分</option>
					</select>
					<h5>料金</h5>
					<p>カンマ無し・半角数字でご記入ください。</p>
					<input type="text" name="price" placeholder="2000" style="width: 30%; padding: 20px 20px 20px 5px;">円
					<h5>メニューの内容(600字以内)</h5>
					<textarea rows="3" name="content" class="textForm">${menuPage.description}</textarea>
					<h5>画像</h5>
					<p>※有料会員限定</p>
					<input type="file" name="imgPath" disabled="disabled">
					<input type="hidden" name="menuPageKey" value="${f:h(menuPageKey)}">
					<br />
					<p class="button" style="margin-top: 20px;" onclick="createMenu();">追加</p>
				</form>
				<hr />
				<h3>メニューの一覧</h3>
				<c:if test="${publicMenuList != null}">
					<h4>公開のメニュー</h4>
					<p><a class="closedMenuURL">非公開メニューを見る</a></p>
					<div class="menuList">
						<c:forEach var="menu" items="${publicMenuList}">
							<div id="${f:h(menu.key)}" style="width: 100%; border: 1px solid #dedede; margin-bottom: 20px;">
								<p style="background-color: #f3f3f3; padding: 8px;">${menu.title}</p>
								<div style="padding: 0 10px 10px 10px;">
									<p>時間：<span class="menuTime">${menu.time/60}</span>分</p>
									<p>料金：${menu.price}円</p>
									<a href="/tools/rese/reserve/EditMenu?id=${f:h(menu.key)}" class="buttonMin-gray" style="text-decoration: none;">編集する</a>
									<br />
									<p onclick="closeMenu('${f:h(menu.key)}');" class="buttonMin-blue">非公開にする</p>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:if>	
					
				<c:if test="${closedMenuList != null}">
					<h4>非公開のメニューを見る</h4>
					<p><a href="/tools/rese/reserve/menuList?id=${f:h(menuPageKey)}">公開メニュー</a></p>
					<c:forEach var="menu" items="${closedMenuList}">
						<div id="${f:h(menu.key)}" style="width: 100%; border: 1px solid #dedede; margin-bottom: 20px;">
							<p style="background-color: #f3f3f3; padding: 8px;">メニュー名：${menu.title}</p>
							<div style="padding: 0 10px 10px 10px;">
								<p>時間：<span class="menuTime">${menu.time/60}</span>分</p>
								<p>料金：${menu.price}円</p>
								<a href="/tools/rese/reserve/EditMenu?id=${f:h(menu.key)}" class="buttonMin-gray">編集する</a>
								<br />
								<p onclick="doPublicMenu('${f:h(menu.key)}');" class="buttonMin-red">公開にする</p>
							</div>
						</div>
					</c:forEach>
				</c:if>
			</div>
			<div class="span3" style="background-color: #000; height: 180px; margin-bottom: 20px;"></div>
		</div>
	</div>
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>

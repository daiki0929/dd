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
<%@ include file="/tools/userManage/common/actionMenuJs.jsp"%>
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
	<%@ include file="/tools/userManage/common/topBar.jsp"%>
	<div class="container">
		<div class="span12">
			<a href="/tools/userManage/reserve/menuPageList"><p class="btn btn-warning">戻る</p></a>
			<h3>メニューの新規作成</h3>
			<form name="form" method="post" id="formId" enctype="multipart/form-data">
				<p>メニューの名前</p>
				<input type="text" name="title" value="${menu.title}">
				<p>時間</p>
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
				<p>料金</p>
				<input type="text" name="price">円
				<p>メニューの内容(600字以内)</p>
				<input type="text" name="content">
				<p>画像</p>
				<input type="file" name="img">
				<input type="hidden" name="menuPageKey" value="${f:h(menuPageKey)}">
				<br />
				<p class="btn btn-info" onclick="createMenu();">追加</p>
			</form>
			<hr />
			<h3>メニューの一覧</h3>
			<c:if test="${publicMenuList != null}">
				<h4>公開のメニュー</h4>
				<p><a class="closedMenuURL">非公開メニューを見る</a></p>
				<div class="span12 menuList">
					<c:forEach var="menu" items="${publicMenuList}">
						<div id="${f:h(menu.key)}" class="span8" style="border: 1px solid #dedede; margin-bottom: 20px;">
							<p style="background-color: #f3f3f3;">メニュー名：${menu.title}</p>
							<div class="span8">
								<p>時間：<span class="menuTime">${menu.time/60}</span>分</p>
								<p>料金：${menu.price}円</p>
								<a href="/tools/userManage/reserve/EditMenu?id=${f:h(menu.key)}">編集する</a>
								<br />
								<p onclick="closeMenu('${f:h(menu.key)}');" class="btn btn-info">非公開にする</p>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>	
				
			<c:if test="${closedMenuList != null}">
				<div class="span12 closedMenuList">
					<h4>非公開のメニューを見る</h4>
					<p><a href="/tools/userManage/reserve/menuList?id=${f:h(menuPageKey)}">公開メニュー</a></p>
					<c:forEach var="menu" items="${closedMenuList}">
					<div id="${f:h(menu.key)}" class="span8" style="border: 1px solid #dedede; margin-bottom: 20px;">
							<p style="background-color: #f3f3f3;">メニュー名：${menu.title}</p>
							<div class="span8">
								<p>時間：<span class="menuTime">${menu.time/60}</span>分</p>
								<p>料金：${menu.price}円</p>
								<a href="/tools/userManage/reserve/EditMenu?id=${f:h(menu.key)}">編集する</a>
								<br />
								<p onclick="doPublicMenu('${f:h(menu.key)}');" class="btn btn-warning">公開にする</p>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>

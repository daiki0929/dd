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
	 * メニューの時間を分にして、切り捨てをする。
	 */
	$(document).ready(function() {
		$(".menuTime").each(function(i, elem) {
			$(".menuTime")[i].innerText = Math.floor($(elem).text());
		});
	});
</script>
</head>
<body>
	<div class="container">
		<div class="span12">
			<h2>予約ページ</h2>
			<p>メニューページタイトル：${menuPage.pageTitle}</p>
			<img src="${menuPage.topImg}" />
			<p>メニューページコンテンツ：${menuPage.description}</p>
			<hr />
			<h3>メニュー</h3>
			<form action="/tools/userManage/reserve/timeschedule">
				<c:forEach var="menu" items="${menuList}">
					<div class="span3" style="background-color: #f2f2f2;">
						<p>${menu.title}</p>
						<p>時間：<span class="menuTime">${menu.time/60}</span>分</p>
						<p>料金：${menu.price}円</p>
						<input type="hidden" name="userId" value="${f:h(msUserKey)}">
						<input type="hidden" name="menuId" value="${f:h(menu.key)}">
						<input type="submit" class="btn btn-warning" value="予約する">
					</div>
				</c:forEach>
			</form>
		</div>
	</div>
</body>
</html>

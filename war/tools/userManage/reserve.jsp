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
</head>
<body>
	<%@ include file="/tools/userManage/common/topBar.jsp"%>
	<div class="container">
		<div class="span12">
			<h2>予約ページ</h2>
			<p>${menuPage.pageTitle}</p>
			<img src="${menuPage.topImg}" />
			<p>${menuPage.time}分</p>
			<p>${menuPage.price}円</p>
			<p>${menuPage.content}</p>
			<hr />
			<h3>メニュー</h3>
			<c:forEach var="menu" items="${menuList}">
				<a href="/tools/userManage/reserve/Timeschedule?id=${f:h(menu.key)}">
					<div class="span3" style="background-color: #f2f2f2;">
						<p>タイトル：${menu.menuTitle}</p>
						<p>時間：${menu.time}</p>
						<p>料金：${menu.price}</p>
					</div>
				</a>
			</c:forEach>
		</div>
	</div>
</body>
</html>

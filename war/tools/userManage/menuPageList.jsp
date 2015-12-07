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
			<h3>メニューページの一覧</h3>
			<a href="/tools/userManage/reserve/createMenuPage"><button type="button" class="btn btn-warning navbar-btn">予約ページ作成</button></a>
			<c:forEach var="menuPage" items="${menuPageList}">
				<p style="margin-top: 20px; background-color: #f2f2f2;">${menuPage.pageTitle}</p>
				<a href="/tools/userManage/reserve/editMenuPage?id=${f:h(menuPage.key)}"><p class="btn">メニューページ編集</p></a>
				<a href="/tools/userManage/reserve/menuList?id=${f:h(menuPage.key)}"><p class="btn">メニュー管理</p></a>
				<a href="/tools/userManage/reserve/reserve?id=${f:h(menuPage.key)}"><p class="btn btn-info">メニューページ確認</p></a>
			</c:forEach>
		</div>
	</div>
</body>
</html>

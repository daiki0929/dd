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
			<a href="/tools/userManage/reserve/menuPageList"><p class="btn btn-warning">戻る</p></a>
			<h3>メニューの新規作成</h3>
			<form action="/tools/userManage/reserve/doneCreateMenu" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
				<p>メニューの名前</p>
				<input type="text" name="menuTitle" value="${menu.menuTitle}">
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
				<input type="file" name="phone"> <input type="hidden" name="menuPageKey" value="${f:h(menuPageKey)}">
				<br />
				<input type="submit" value="追加">
			</form>
			<hr />
			<h3>メニューの一覧</h3>
			<div class="span3">
				<c:forEach var="menu" items="${menuList}">
					<p>タイトル：${menu.menuTitle}</p>
					<p>時間：${menu.time}</p>
					<p>料金：${menu.price}</p>
					<a href="/tools/userManage/reserve/EditMenu?id=${f:h(menu.key)}">編集する</a>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>

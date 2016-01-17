<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>メニュー作成</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<%@ include file="/tools/rese/common/actionMenuJs.jsp"%>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container">
		<!-- エラーメッセージ -->
		<%@ include file="/tools/rese/common/error.jsp"%>
		<div class="span12">
			<h3>メニューの新規作成</h3>
			<form>
				<p>メニューの名前</p>
				<input type="text" name="title">
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
				<p>※有料会員限定</p>
				<input type="file" name="imgPath"> <input type="hidden" name="menuPageKey" value="${f:h(menuPageKey)}" disabled="disabled">
				<br />
				<p class="btn btn-info" onclick="createMenu();">完了</p>
			</form>
			<a href="/tools/rese/reserve/selectMenu?id=${f:h(menuPageKey)}">完成したメニューページを見る</a>
			
			<div class="span12 menuList">
				<c:forEach var="menu" items="${menuList}">
					<div id="${f:h(menu.key)}" class="span8" style="border: 1px solid #dedede; margin-bottom: 20px;">
						<p style="background-color: #f3f3f3;">メニュー名：${menu.title}</p>
						<div class="span8">
							<p>時間：<span class="menuTime">${menu.time/60}</span>分</p>
							<p>料金：${menu.price}円</p>
							<a href="/tools/rese/reserve/EditMenu?id=${f:h(menu.key)}">編集する</a>
							<br />
							<p onclick="deleteMenu(${f:h(menu.key)})" class="btn btn-info">非公開にする</p>
						</div>
					</div>
				</c:forEach>
			</div>
			
		</div>
	</div>
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>

<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>予約確認</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
</head>
<body style="background-image: url('/img/back1.jpg'); background-repeat: no-repeat; background-size:cover;">
<div class="container" style="background-color: #fff; border-radius:5px; margin-bottom: 50px;">
	<div style="padding: 3%;">
			<h2 class="reservePageTitle">予約内容</h2>
			<hr />
			<h4>メニュー情報</h4>
			<p>メニュー：${menu.title}</p>
			<p>予約日：${reserveTime} ~ ${menuEndTime}</p>
			<p>料金：${menu.price}円</p>
			<br/>
			<h4>お客様情報</h4>
			<p>お名前：${customerName}様</p>
			<p>メールアドレス：${customerMailaddress}</p>
			<p>携帯番号：${customerPhone}</p>
			
			<form action="/doneConfirm" method="post">
				<input type="hidden" name="menuKey" value="${f:h(menu.key)}">
				<input type="hidden" name="reserveTime" value="${reserveTime}">
				<input type="hidden" name="menuEndTime" value="${menuEndTime}">
				<input type="hidden" name="customerName" value="${customerName}">
				<input type="hidden" name="customerMailaddress" value="${customerMailaddress}">
				<input type="hidden" name="customerPhone" value="${customerPhone}">
				<input type="submit" class="reserveBtn" value="予約を確定する">
			</form>
		</div>
	</div>
</body>
</html>

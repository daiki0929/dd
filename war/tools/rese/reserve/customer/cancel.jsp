<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>予約キャンセル</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
</head>
<body style="background-image: url('/img/back1.jpg'); background-repeat: no-repeat; background-size:cover;">
<div class="container" style="background-color: #fff; border-radius:5px; margin-bottom: 50px;">
	<div style="padding: 3%;">
		<h3>キャンセルページ</h3>
			<c:if test="${reserve != null}">
				<a href=""><p class="btn btn-warning">キャンセルする</p></a>
				<h3>お客様情報</h3>
				<p>お名前：${customer.name}様</p>
				<h4>予約内容</h4>
				<h4>メニュー情報</h4>
				<p>メニュー：${reserve.menuTitle}</p>
				<p>予約日：${reserve.startTime} ~ ${reserve.endTime}</p>
				<p>料金：${reserve.price}円</p>
			</c:if>
			<c:if test="${reserve == null}">
				<p>キャンセル期間を過ぎています。直接店舗までご連絡ください。</p>
				<p>${msUser.phone}</p>
				<p>${msUser.mailaddress}</p>
				<h3>お客様情報</h3>
				<p>お名前：${customer.name}様</p>
				<h2>予約内容</h2>
				<h3>メニュー情報</h3>
				<p>メニュー：${reserve.menuTitle}</p>
				<p>予約日：${reserve.startTime} ~ ${reserve.endTime}</p>
				<p>料金：${reserve.price}円</p>
			</c:if>
		</div>
	</div>
</body>
</html>

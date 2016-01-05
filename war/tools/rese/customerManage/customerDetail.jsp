<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>顧客情報</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container">
		<div id="content">
			<div class="span12">
				<h3>${customer.name} 様</h3>
				<div class="span2" style="background-color: #f2f2f2;">
					<p>年齢</p>
					<p>${customer.age}</p>
					<p>性別</p>
					<p>${customer.sex}</p>
					<p>住所</p>
					<p>${customer.address}</p>
					<p>電話番号</p>
					<p>${customer.phone}</p>
					<p>メールアドレス</p>
					<p>${customer.mailaddress}</p>
				</div>
				<div class="span10" style="background-color: #f2f2f2;">
				<c:forEach var="reserve" items="${reserveList}">
					<p>${reserve.menuTitle}</p>
					<p>${reserve.startTime}</p>
				</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

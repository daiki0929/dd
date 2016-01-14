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
<style type="text/css">
.menuCard{
	background-color: #f2f2f2;
	border: 1px solid #dedede;
	padding: 10px;
	margin-bottom: 20px;
}
.reserveBtn{
	border-style: none;
	color: #fff;
	width: 50%;
	height: auto;
	padding: 10px;
	margin: 0 auto;
	background-color: #3498db;
}

</style>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<script type="text/javascript">
	var length;
	$(document).ready(function() {
		/**
		 * メニューの時間を分にして、切り捨てをする。
		 */
		$(".menuTime").each(function(i, elem) {
			$(".menuTime")[i].innerText = Math.floor($(elem).text());
		});
	});
	
</script>
</head>
<body style="background-image: url('/img/rese/reserveBack.jpg'); no-repeat">
	<div class="container" style="background-color: #fff;">
		<div style="padding: 3%;">
			<h2>${menuPage.pageTitle}</h2>
			<%-- <img src="${menuPage.topImgPath}" /> --%>
			<div>
				<p class="dscription">${menuPage.description}</p>
			</div>
			<hr />
			<h3>メニュー</h3>
			<p>メニューをお選び下さい。</p>
			<div>
				<c:forEach var="menu" items="${menuList}">
					<form action="/tools/rese/reserve/customer/timeschedule" method="post">
						<div class="span3 menuCard" style="background-color: #f2f2f2; border: 1px solid #dedede; padding: 10px;">
							<p style="font-size: 1.1em">${menu.title}</p>
							<p>時間：<span class="menuTime">${menu.time/60}</span>分</p>
							<p>料金：${menu.price}円</p>
							<input type="hidden" name="userId" value="${f:h(msUserKey)}">
							<input type="hidden" name="menuId" value="${f:h(menu.key)}">
							<input type="submit" class="reserveBtn" value="予約する">
						</div>
					</form>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>

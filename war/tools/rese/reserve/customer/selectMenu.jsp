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
.dscription{
	color:#2C3E50;
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
		
		//制限の確認
		if(${limitOver}){
			$('.reserveBtn').css("background-color", "#b2b2b2");	
			$('form').attr("action", "");
			$('.reserveBtn').css("cursor", "not-allowed");
		}
	});
	
</script>
</head>
<body style="background-image: url('/img/back1.jpg'); background-repeat: no-repeat; background-size:cover;">
	<div class="container" style="background-color: #fff; border-radius:5px;">
		<div style="padding: 3%;">
			<c:if test="${limitOver}">
          		<p class="well"><span style="color: red;">※</span>この予約ページは予約可能制限数に達しているため、利用出来ません。オーナー様に直接ご連絡下さい。</p>
          	</c:if>
			<h2 class="reservePageTitle">${menuPage.pageTitle}</h2>
			<%-- <img src="${menuPage.topImgPath}" /> --%>
			<div>
				<p class="dscription">${menuPage.description}</p>
			</div>
			<hr />
			<p style="color: #2C3E50;">お好きなメニューを1つお選び下さい。</p>
			<div>
				<c:forEach var="menu" items="${menuList}">
					<form action="/r/reserve/${msUser.userPath}/${menuPage.pagePath}" method="post">
						<div class="span3 menuCard" style="background-color: #f8f8f8; border: 1px solid #dedede; padding: 10px;">
							<p style="font-size: 1.1em">${menu.title}</p>
							<p>時間：<span class="menuTime">${menu.time/60}</span>分</p>
							<p>料金：${menu.price}円</p>
							<input type="hidden" name="userId" value="${f:h(msUser.key)}">
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

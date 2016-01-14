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

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$('#myTab a').click(function (e) {
		  e.preventDefault();
		  $(this).tab('show');
		});
});

</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container">
		<div class="span12" style="margin-bottom: 20px;">
			<h3>メニューページの一覧</h3>
			<a href="/tools/rese/reserve/createMenuPage"><button type="button" class="btn btn-warning navbar-btn">予約ページ作成</button></a>
		</div>
		<div class="span8" style="padding: 10px 10px 20px 10px;">
			
			<ul class="nav nav-tabs" id="myTab">
			  <li class="active"><a href="#public">公開</a></li>
			  <li><a href="#closed">非公開</a></li>
			</ul>
			
			<div class="tab-content">
			  <div class="tab-pane active" id="public">
			  	<c:forEach var="menuPage" items="${openMenuPageList}">
					<p style="margin-top: 20px; background-color: #f2f2f2;">${menuPage.pageTitle}</p>
					<a href="/tools/rese/reserve/editMenuPage?id=${f:h(menuPage.key)}"><p class="btn">メニューページ編集</p></a>
					<a href="/tools/rese/reserve/menuList?id=${f:h(menuPage.key)}"><p class="btn">メニュー管理</p></a>
					<a href="/tools/rese/reserve/customer/selectMenu?userPath=${msUser.userPath}&pagePath=${menuPage.pagePath}" target="_blank"><p class="btn btn-info">メニューページ確認</p></a>
				</c:forEach>
			  </div>
			  <div class="tab-pane" id="closed">
			  <p>※非公開のメニューページは毎週日曜日に自動で削除されます。</p>
			  	<c:forEach var="menuPage" items="${closedMenuPageList}">
					<p style="margin-top: 20px; background-color: #f2f2f2;">${menuPage.pageTitle}</p>
					<a href="/tools/rese/reserve/editMenuPage?id=${f:h(menuPage.key)}"><p class="btn">メニューページ編集</p></a>
					<a href="/tools/rese/reserve/menuList?id=${f:h(menuPage.key)}"><p class="btn">メニュー管理</p></a>
					<a href="/tools/rese/reserve/customer/selectMenu?userPath=${msUser.userPath}&pagePath=${f:h(menuPage.key)}" target="_blank"><p class="btn btn-info">メニューページ確認</p></a>
				</c:forEach>
			  </div>
			</div>
		</div>
	</div>
</body>
</html>

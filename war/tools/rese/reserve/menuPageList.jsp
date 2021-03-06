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
<style type="text/css">
.link{
	width: 35%;
	min-width: 210px;;
	display: inline-block;
	padding: 5px 15px;
	margin-right: 30px;
	margin-left: 10px;
	background-color: #f8f8f8;
	float: left;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$('#myTab a').click(function (e) {
		  e.preventDefault();
		  $(this).tab('show');
		});
});

/**
 * メニューページにアクセスします。
 */
function accessMenuPage(menuPageURL){
	window.open(menuPageURL, '_blank');
}

</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container mainContent" style="min-height: 500px;">
		<div class="span12">
			<div class="span12" style="margin-bottom: 20px;">
				<h3>メニューページの一覧</h3>
				<p>メニューページの一覧です。</p>
				<div class="button"><a href="/tools/rese/reserve/createMenuPage">新規作成する</a></div>
			</div>
			<div class="span8" style="padding: 10px 10px 20px 10px;">
				
				<ul class="nav nav-tabs" id="myTab">
				  <li class="active"><a href="#public">公開</a></li>
				  <li><a href="#closed">非公開</a></li>
				</ul>
				
				<div class="tab-content">
				  <div class="tab-pane active" id="public">
				  	<c:forEach var="menuPage" items="${openMenuPageList}">
					  	<div style="width: 99%; border:1px solid #dedede; margin: 0 0 20px 0;">
							<p style="background-color: #f2f2f2; padding: 10px 0 10px 10px">${menuPage.pageTitle}</p>
							<!-- <img src="/img/link.png" style="float:left; padding: 0 5px 0 5px;" width="5%;"> -->
							<div class="link">
								<img alt="" src="/img/link2.png" width="28px;" style="cursor: pointer; margin-top: -2px;" onclick="accessMenuPage('http://rese.space/r/${msUser.userPath}/${menuPage.pagePath}');">
								<input type="text" value="http://rese.space/r/${msUser.userPath}/${menuPage.pagePath}" readonly="readonly" onclick="this.select()" style="padding: 15px 10px 15px 10px; margin-top:5px; cursor: default;"/>
							</div>
							<div style="margin-left: 10px;">
								<p class="buttonMin-gray" style="display: inline-block; margin-right: 10px;">
									<a href="/tools/rese/reserve/editMenuPage?id=${f:h(menuPage.key)}">メニューページ編集</a>
								</p>
								<p class="buttonMin-gray" style="display: inline-block; margin-right: 10px;">
									<a href="/tools/rese/reserve/menuList?id=${f:h(menuPage.key)}">メニュー管理</a>
								</p>
							</div>
						</div>
					</c:forEach>
				  </div>
				  <div class="tab-pane" id="closed">
				  <p>※非公開のメニューページは毎週日曜日に自動で削除されます。</p>
				  	<c:forEach var="menuPage" items="${closedMenuPageList}">
						<p style="margin-top: 20px; background-color: #f2f2f2;">${menuPage.pageTitle}</p>
						<a href="/tools/rese/reserve/editMenuPage?id=${f:h(menuPage.key)}"><p class="btn">メニューページ編集</p></a>
						<a href="/tools/rese/reserve/menuList?id=${f:h(menuPage.key)}"><p class="btn">メニュー管理</p></a>
						<a href="/r/${msUser.userPath}/${menuPage.pagePath}" target="_blank"><p class="btn btn-info">メニューページ確認</p></a>
					</c:forEach>
				  </div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>

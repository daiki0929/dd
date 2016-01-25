<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>管理者ページ</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<script type="text/javascript">
	
</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container mainContent">
		<div class="span12">
			<h2>管理者ページ</h2>
			<p>
				<a href="/tools/rese/editOperationHours">・営業日時の設定</a>
			</p>
			<p>
				<a href="/tools/rese/editAcount">・アカウントの設定</a>
			</p>
			<div style="margin-top: 40px;">
				<a href="/tools/rese/comeAndGo/logout">
					<p class="buttonMin-blue">ログアウト</p>
				</a>
			</div>
		</div>
	</div>
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>

<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/userManage/common/meta.jsp"%>
<title>会員登録ページ</title>
<!-- css -->
<%@ include file="/tools/userManage/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/userManage/common/importJs.jsp"%>
<script type="text/javascript">
	</script>
</head>
<body>
	<div id="container">
		<div id="content">
			<!-- エラーメッセージ -->
			<%@ include file="/tools/userManage/common/error.jsp"%>
			<div class="span12">
				<a href="/tools/userManage/entry"><button class="btn btn-warning">会員登録</button></a>
				<h3>ログイン</h3>
				<form action="/tools/userManage/doneLogin" method="post">
					<p>メールアドレス</p>
					<input type="text" name="mailaddress" value=${mailaddress}>
					<p>パスワード</p>
					<input type="text" name="password" value=${password}> <br />
					<input type="submit" value="ログイン">
				</form>
			</div>
		</div>
	</div>
</body>
</html>

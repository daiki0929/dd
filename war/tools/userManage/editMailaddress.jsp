<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/userManage/common/meta.jsp"%>
<title>アカウントページ</title>
<!-- css -->
<%@ include file="/tools/userManage/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/userManage/common/importJs.jsp"%>
<script type="text/javascript">
	
</script>
</head>
<body>
	<%@ include file="/tools/userManage/common/topBar.jsp"%>
	<div class="container">
		<div class="span12">
			<form action="/tools/userManage/doneEditAcount">
				<p>メールアドレス</p>
				<p>変更後のメールアドレスに確認メールが届きます。</p>
				<p>メールに記載されている認証リンクをクリックし、変更を完了してください。</p>
				<input type="text" name="name" value="${msUser.mailaddress}">
				<input type="submit" class="btn btn-info" value="変更する">
			</form>
		</div>
	</div>
</body>
</html>

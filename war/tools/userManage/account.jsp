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
				<h2>アカウント情報</h2>
				<p>名前</p>
				<input type="text" name="name" value="${msUser.name}">
				<p>ふりがな</p>
				<input type="text" name="kanaName" value="${msUser.kanaName}">
				<p>メールアドレス</p>
				<p>${msUser.mailaddress}</p>
				<a href="/tools/userManage/editMailaddress">変更する</a>
				<p>電話番号</p>
				<input type="text" name="phone" value="${msUser.phone}">
				<p>住所</p>
				<input type="text" name="address" value="${msUser.address}"><br/>
				<input type="submit" class="btn btn-info" value="変更する">
			</form>
		</div>
	</div>
</body>
</html>

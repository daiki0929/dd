<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>アカウントページ</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<script type="text/javascript">
	
</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container">
		<div class="span12">
			<form action="/tools/rese/doneEditAcount">
				<h2>アカウント情報</h2>
				<p>名前</p>
				<input type="text" name="name" value="${msUser.name}">
				<p>メールアドレス</p>
				<input type="text" name="mailaddress" value="${msUser.mailaddress}">
				<!-- <a href="/tools/rese/editMailaddress">変更する</a> -->
				<p>電話番号</p>
				<input type="text" name="phone" value="${msUser.phone}">
				<p>住所</p>
				<input type="text" name="address" value="${msUser.address}"><br/>
			</form>
			
			<c:if test="${msUser.gmailAddress != null}">
				<p>Googleアカウントと連携済みです。</p>
				<p>登録されているGmailアドレス：${msUser.gmailAddress}</p>
			</c:if>
			<c:if test="${msUser.gmailAddress == null}">
				<p>Googleアカウントを連携することで、お客様にメールを送信することが出来ます。</p>
				<p>連携すると、アカウント情報のメールアドレスがGmailアドレスに変更されます。</p>
				<a href="/tools/rese/comeAndGo/google/signIn"><p class="btn btn-warning">Googleアカウントと連携する</p></a>
			</c:if>
			
		</div>
	</div>
</body>
</html>

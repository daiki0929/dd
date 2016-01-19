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
	<div class="container mainContent">
		<div class="span12">
			<form action="/tools/rese/doneEditAcount">
				<h2>アカウント情報</h2>
				<h5>名前</h5>
				<input type="text" name="name" value="${msUser.name}">
				<h5>メールアドレス</h5>
				<input type="text" name="mailaddress" value="${msUser.mailaddress}">
				<!-- <a href="/tools/rese/editMailaddress">変更する</a> -->
				<h5>電話番号</h5>
				<p>店舗の電話番号として表示されます。</p>
				<input type="text" name="phone" value="${msUser.phone}">
				<h5>住所</h5>
				<p>店舗の住所として表示されます。</p>
				<input type="text" name="address" value="${msUser.address}"><br/>
				<input type="submit" value="更新" style="width: 200px; background-color: #f39c12; border: none; padding: 10px; color:#fff; border-radius: 5px;">
			</form>
			
			<%-- <c:if test="${msUser.gmailAddress != null}">
				<p>Googleアカウントと連携済みです。</p>
				<p>登録されているGmailアドレス：${msUser.gmailAddress}</p>
			</c:if>
			<c:if test="${msUser.gmailAddress == null}">
				<p>Googleアカウントを連携することで、お客様にメールを送信することが出来ます。</p>
				<p>連携すると、アカウント情報のメールアドレスがGmailアドレスに変更されます。</p>
				<a href="/tools/rese/comeAndGo/google/signIn"><p class="btn btn-warning">Googleアカウントと連携する</p></a>
			</c:if> --%>
			
		</div>
	</div>
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>

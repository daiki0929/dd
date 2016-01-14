<%-- <%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
META情報
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>会員登録ページ</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

JSインポート
<%@ include file="/tools/rese/common/importJs.jsp"%>
<script type="text/javascript">
	</script>
</head>
<body>
	<div id="container">
		<div id="content">
			<!-- エラーメッセージ -->
			<%@ include file="/tools/rese/common/error.jsp"%>
			<div class="span12">
				<h3>会員登録</h3>
				<form action="/tools/rese/comeAndGo/twitter/doneEntry" method="post">
					<p>名前</p>
					<p>予約フォームに表示する名前を記入してください。(店舗名/個人名など)</p>
					<input type="text" name="name">
					<p>メールアドレス</p>
					<p>予約の通知を送信するメールアドレスを記入してください。</p>
					<input type="text" name="mailaddress" placeholder="sample@example.com">
					<br />
					<input type="submit" value="登録">
				</form>
			</div>
		</div>
	</div>
</body>
</html>
 --%>
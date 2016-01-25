<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>予約完了</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<script type="text/javascript">
</script>
</head>
<body style="background-image: url('/img/back1.jpg'); background-repeat: no-repeat; background-size:cover;">
<div class="container" style="background-color: #fff; border-radius:5px; margin-bottom: 50px;">
	<div style="padding: 3%;">		
		<h2 class="reservePageTitle">予約完了</h2>
		<hr>
		<p>予約完了しました。<br>予約先とお客様に通知のメールを送信しました。</p>
		</div>
	</div>
</body>
</html>

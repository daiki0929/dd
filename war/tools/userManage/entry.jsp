<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
	<%-- META情報 --%>
	<%@ include file="/tools/userManage/common/meta.jsp" %>
	<title>会員登録ページ</title>
	<!-- css -->
	<%@ include file="/tools/userManage/common/importCss.jsp" %>

	<%-- JSインポート --%>
	<%@ include file="/tools/userManage/common/importJs.jsp" %>
	<script type="text/javascript">
	</script>
</head>
<body>
<div id="container">
    <div id="content">
	    <!-- エラーメッセージ -->
	    <%@ include file="/tools/userManage/common/error.jsp" %>
	    <div class="span12">
	   		<h3>会員登録</h3>
	    	<form action="/tools/userManage/entry/DoneEntry" method="post">
		    	<p>名前</p>
		    	<input type="text" name="name" value="${name}">
		    	<p>ふりがな</p>
		    	<input type="text" name="kanaName" value="${kanaName}">
		    	<p>メールアドレス</p>
		    	<input type="text" name="mailaddress" value="${mailaddress}">
		    	<p>パスワード</p>
		    	<input type="password" name="password" value="${password}">
		    	<p>パスワード(確認)</p>
		    	<input type="password" name="passwordConfirm">
		    	<br/>
		    	<input type="submit" value="登録">
	    	</form>
	    </div>
	</div>
</div>
</body>
</html>

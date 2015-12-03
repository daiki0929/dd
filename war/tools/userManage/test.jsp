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
<h3>テストページです。</h3>
<a href="${loginUrl}">ログイン</a>
<a href="${logoutUrl}">ログアウト</a>
</body>
</html>

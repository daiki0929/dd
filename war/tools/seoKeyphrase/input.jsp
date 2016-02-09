<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>タイトル入力</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<%@ include file="/tools/rese/common/actionMenuJs.jsp"%>
</head>
<body>
	<div class="container mainContent">
		<div class="span12">
			<h3>タイトル入力画面</h3>
			<form action="/tools/seoKeyphrase/doneInput" method="post">
				<textarea rows="10" cols="10" name="sentence"></textarea>
				<input type="submit" value="OK">
			</form>
		</div>
	</div>
</body>
</html>

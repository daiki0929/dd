<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>エラー</title>
<!-- css -->
<%@ include file="/tools/userManage/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/userManage/common/importJs.jsp"%>
</head>
<body>
	<%@ include file="/tools/userManage/common/topBar.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<p style="color: red; margin-top: 20px;">エラーが発生しました。</p>
			</div>
		</div>
	</div>
</body>
</html>

<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>画像アップロードのテスト</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<script type="text/javascript" src="/js/tools/rese/uploadImg.js"></script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div style="margin-top: 200px;">
		<form id="entryForm">
			<input name="formFile" type="file" enctype="multipart/form-data">
			<input id="uploadUrl" type="hidden" />
			<button id="upload">Upload</button>
		</form>
	</div>
	<ul id="list">
	</ul>
</body>
</html>
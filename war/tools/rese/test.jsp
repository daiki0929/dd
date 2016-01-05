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
<script>
	$(document).ready(function() {
		$.ajax({
			type : "GET",
			url : "/tools/rese/FileUpload",
		}).success(function(data) {
			$("#uploadUrl").val(data.url);
			console.log(data.url);
		}).error(function() {
			alert("error.");
		});
		
		$.ajax({
			type : "GET",
			url : "/tools/rese/FileUpload",
		}).success(function(data) {
			var blobContents = JSON.parse(data);
			for (var i = 0; i < blobContents.length; i++) {
				$("#list").append("<li><a href=/uploadFile?key=" + blobContents[i].key.name + ">" + blobContents[i].filename + "</a></li>");
			}
		}).error(function() {
			alert("error.");
		});
		$("#upload").on("click", function() {
			var $form = $("#entryForm");
			var formData = new FormData($form[0]);
			var promise = $.ajax({
				type : "POST",
				url : $("#uploadUrl").val(),
				processData : false,
				contentType : false,
				data : formData
			});
			promise.success(function(data) {
				alert("作成しました");
			}).error(function() {
				alert("作成に失敗しました");
			});
			return false;
		});
	});
</script>
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
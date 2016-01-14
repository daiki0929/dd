//GCSに画像をアップロードします。セレクタ、POST先のURLは固定で利用して下さい。
$(document).ready(function() {
	$.ajax({
		type : "GET",
		url : "/fileUpload",
	}).success(function(data) {
		$("#uploadUrl").val(data.url);
		console.log(data.url);
	}).error(function() {
		alert("error.");
	});
	
	$.ajax({
		type : "GET",
		url : "/fileUpload",
	}).success(function(data) {
		var blobContents = JSON.parse(data);
		for (var i = 0; i < blobContents.length; i++) {
			$("#list").append("<li><a href=/uploadFile?key=" + blobContents[i].key.name + ">" + blobContents[i].filename + "</a></li>");
		}
		console.log(data);
	}).error(function() {
		alert("error.");
	});

//	$("#upload").on("click", function() {
//		var $form = $("#entryForm");
//		var formData = new FormData($form[0]);
//		var promise = $.ajax({
//			type : "POST",
//			url : $("#uploadUrl").val(),
//			processData : false,
//			contentType : false,
//			data : formData
//		});
//		promise.success(function(data) {
//			alert("作成しました");
//		}).error(function() {
//			alert("作成に失敗しました");
//		});
//		return false;
//	});
});

function startUpload() {
	console.log("画像アップロード開始");
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
//		alert("作成しました");
	}).error(function() {
		alert("画像アップロードに失敗しました。");
	});
	return false;
};
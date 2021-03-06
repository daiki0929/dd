<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>メニュー編集</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {
		var length = parseInt(document.getElementsByTagName("option").length);
		/* 時間 */
		for (var count = 0; count < length; count++){
			if($('select[name=time] option')[count].value == "${menu.time}"){
				var selectedItem = $('option')[count];
				selectedItem.setAttribute("selected", "true");
				break;
			　　　}
		}
	});
</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container mainContent">
		<!-- エラーメッセージ -->
		<%@ include file="/tools/rese/common/error.jsp"%>
		<div class="span12">
			<h3>メニューの編集</h3>
			<form action="/tools/rese/reserve/doneEditMenu" method="post"
				accept-charset="UTF-8" enctype="multipart/form-data">
				<p>メニューの名前</p>
				<input type="text" name="menuTitle" value="${menu.title}">
				<p>時間</p>
				<select name="time">
					<option value="1800">30 分</option>
					<option value="2400">40 分</option>
					<option value="2700">45 分</option>
					<option value="3600">60 分</option>
					<option value="4500">75 分</option>
					<option value="5400">90 分</option>
					<option value="7200">120 分</option>
					<option value="9000">150 分</option>
					<option value="10800">180 分</option>
					<option value="14400">240 分</option>
					<option value="18000">300 分</option>
					<option value="21600">360 分</option>
					<option value="25200">420 分</option>
				</select>
				<p>料金</p>
				<input type="text" name="price" value="${menu.price}">円
				<p>メニューの内容(600字以内)</p>
				<input type="text" name="content" value="${menu.content}">
				<p>画像</p>
				<p>※有料会員限定</p>
				<input type="file" name="imgPath" disabled="disabled">
				<input type="hidden" name="menuKey" value="${f:h(menu.key)}">
				<br />
				<input type="submit" value="更新" style="width: 200px; background-color: #f39c12; border: none; padding: 10px; color:#fff; border-radius: 5px; margin-top: 20px;">
			</form>
		</div>
	</div>
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>

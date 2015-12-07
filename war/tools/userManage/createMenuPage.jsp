<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>メニューページ作成</title>
<!-- css -->
<%@ include file="/tools/userManage/common/importCss.jsp"%>
<%-- JSインポート --%>
<%@ include file="/tools/userManage/common/importJs.jsp"%>

</head>
<body>
	<%@ include file="/tools/userManage/common/topBar.jsp"%>
	<!-- エラーメッセージ -->
	<%@ include file="/tools/userManage/common/error.jsp"%>
	<div class="span12">
		<h3>メニューページ作成</h3>
		<form action="/tools/userManage/reserve/doneCreateMenuPage" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
			<p>メニューページのタイトル</p>
			<input type="text" name="pageTitle">
			<p>説明文</p>
			<textarea rows="3" name="description"></textarea>
			<p>トップ画像</p>
			<input type="file" name="topImg">
			<p>予約形態</p>
			<label class="radio"> <input type="radio" name="reserveSystem" value="firstArrival" checked> 先着順
			</label> <label class="radio"> <input type="radio" name="reserveSystem" value="recognition"> 承認制
			</label>
			<p>公開設定</p>
			<label class="radio"> <input type="radio" name="status" value="open" checked> 公開
			</label> <label class="radio"> <input type="radio" name="status" value="not_open"> 非公開
			</label>
			<p>予約受け付け期間</p>
			<select name="reserveStartDay">
				<option value="">設定しない</option>
				<option value="604800">7日前から</option>
				<option value="1209600">14日前から</option>
				<option value="1814400">21日前から</option>
				<option value="2592000">30日前から</option>
				<option value="5184000">60日前から</option>
				<option value="7776000">90日前から</option>
			</select>
			<p>キャンセル期間</p>
			<select name="reserveStopDay">
				<option value="">設定しない</option>
				<option value="3600">1時間前まで</option>
				<option value="7200">2時間前まで</option>
				<option value="10800">3時間前まで</option>
				<option value="14400">4時間前まで</option>
				<option value="18000">5時間前まで</option>
				<option value="21600">6時間前まで</option>
				<option value="25200">7時間前まで</option>
				<option value="28800">8時間前まで</option>
				<option value="32400">9時間前まで</option>
				<option value="36000">10時間前まで</option>
				<option value="39600">11時間前まで</option>
				<option value="43200">12時間前まで</option>
				<option value="86400">1日前まで</option>
				<option value="172800">2日前まで</option>
				<option value="259200">3日前まで</option>
				<option value="345600">4日前まで</option>
				<option value="432000">5日前まで</option>
				<option value="518400">6日前まで</option>
				<option value="604800">7日前まで</option>
			</select>
			<p>休み設定</p>
			<small>定休日以外に、メニューページごとで休みを設定出来ます。</small>
			<input type="text" id="datepicker" name="noReserveDate">
			<br />
			<input type="submit" value="完了" class="btn btn-warning">
		</form>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datepicker").datepicker({
				format : "yyyy/mm/dd",
				multidate : true
			});
		});
	</script>
</body>
</html>

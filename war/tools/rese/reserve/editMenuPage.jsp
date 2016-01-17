<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>メニューページ編集</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>
<style type="text/css">
.selected a {
  background: #dedede !important;
}
.textForm{
	width: 80%;
	padding: 10px 10px 10px 10px;
}
</style>
<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<%@ include file="/tools/rese/common/actionMenuPageJs.jsp"%>
<script type="text/javascript" src="/js/tools/rese/noReserveDate.js"></script>
<!-- <script type="text/javascript" src="/js/tools/rese/uploadImg.js"></script> -->
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container">
		<div class="span12">
			<!-- エラーメッセージ -->
			<%-- <%@ include file="/tools/rese/common/error.jsp"%> --%>
			<div class="span8">
				<h3>メニューページ編集</h3>
				<p style="margin-bottom: 20px;">メニューページの設定を編集します。</p>
				<form action="/tools/rese/reserve/doneEditMenuPage" id="entryForm" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
					<h5>メニューページのタイトル</h5>
					<input type="text" name="pageTitle" value="${menuPage.pageTitle}" style="width: 80%; padding: 20px 20px 20px 5px;">
					<h5>説明文</h5>
					<textarea rows="3" name="description" class="textForm">${menuPage.description}</textarea>
					<%-- <p>トップ画像</p>
					<p>※有料会員限定です。</p>
					<input name="topImgPath" id="uploadImg" type="file" enctype="multipart/form-data" value="${menuPage.topImgPath}" onchange="startUpload();" disabled="disabled">
					<input id="uploadUrl" type="hidden" /> --%>
					<h5>予約形態</h5>
					<c:if test="${menuPage.reserveSystem == 'firstArrival'}">
						<label class="radio">
							<input type="radio" name="reserveSystem" value="firstArrival" checked> 先着順
						</label>
						<label class="radio">
							<input type="radio" name="reserveSystem" value="recognition" disabled="disabled"> 承認制(※有料会員限定)
						</label>
					</c:if>
					<c:if test="${menuPage.reserveSystem == 'recognition'}">
						<label class="radio"> <input type="radio" name="reserveSystem" value="firstArrival"> 先着順
						</label>
						<label class="radio"> <input type="radio" name="reserveSystem" value="recognition" disabled="disabled" checked> 承認制(※有料会員限定)
						</label>
					</c:if>

					<h5>公開設定</h5>
					<p>※非公開のメニューページは毎週日曜日に自動で削除されます。</p>
					<c:if test="${menuPage.status == 'public'}">
						<label class="radio"> <input type="radio" name="status" value="public" checked> 公開
						</label>
						<label class="radio"> <input type="radio" name="status" value="closed"> 非公開
						</label>
					</c:if>
					<c:if test="${menuPage.status == 'closed'}">
						<label class="radio"> <input type="radio" name="status" value="public"> 公開
						</label>
						<label class="radio"> <input type="radio" name="status" value="closed" checked> 非公開
						</label>
					</c:if>

					<h5>何分ごとに予約を受け付けますか？</h5>
					<small>例えば、10時から営業開始で、15分毎の場合、お客様が予約できる時間は10:00、10:15、10:30、10:45 ... となります。30分毎の場合は、10:00、10:30、11:00、11:30 ... となります。</small>
					<br/>
					<select name="reserveInterval">
						<option value="900">15分ごと</option>
						<option value="1800">30分ごと</option>
						<option value="3600">60分ごと</option>
						<option value="7200">120分ごと</option>
					</select>
					<h5>受付開始</h5>
					<div class="dayForm" style="display: none;">
						<select name="reserveStartTime">
							<!-- <option value="0">設定しない</option>
							<option value="7">7日前から</option>
							<option value="14">14日前から</option>
							<option value="21">21日前から</option>
							<option value="30">30日前から</option>
							<option value="60">60日前から</option>
							<option value="90">90日前から</option> -->
							<option value="0">設定しない</option>
							<option value="604800">7日前から</option>
							<option value="1209600">14日前から</option>
							<option value="1814400">21日前から</option>
							<option value="2592000">30日前から</option>
							<option value="5184000">60日前から</option>
							<option value="7776000">90日前から</option>
						</select>
						<h5>受付終了</h5>
						<select name="reserveEndTime">
							<option value="0">設定しない</option>
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
						<h5>キャンセル期間</h5>
						<p>キャンセル期間を過ぎた場合、メール・お電話でのキャンセルとなります。</p>
						<select name="cancelTime">
							<option value="0">設定しない</option>
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
					</div>

					<h5>休み設定</h5>
					<small>定休日以外に、メニューページごとで休みを設定出来ます。</small>
					<p>※有料会員限定です。</p>
					<input type="hidden" name="noReserveDate" value="">
					<%-- <br />
					<input type="text" id="datepicker" name="noReserveDate" value="${menuPage.noReserveDate}">
					<br />
					<a id="button" href="javascript:;">カレンダーから選択する。</a>
  					<div id="pochipochi"></div>
					<br /> --%>
					
					<input type="hidden" name="id" value="${f:h(menuPage.key)}">
					<input type="submit" value="更新" style="width: 200px; background-color: #f39c12; border: none; padding: 10px; color:#fff; border-radius: 5px;">
				</form>
			</div>
			<div class="span3" style="background-color: #000; height: 180px; margin-bottom: 20px;"></div>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#datepicker").datepicker({
					format : "yyyy/mm/dd",
					multidate : true
				});
			});
		</script>
	</div>
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>

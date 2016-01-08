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

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<%@ include file="/tools/rese/common/actionMenuPageJs.jsp"%>
<script type="text/javascript" src="/js/tools/rese/noReserveDate.js"></script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container">
		<div class="span12">
			<!-- エラーメッセージ -->
			<%@ include file="/tools/rese/common/error.jsp"%>
			<div class="span12">
				<a href="/tools/rese/reserve/menuPageList"><button type="button" class="btn btn-warning navbar-btn">戻る</button></a>
				<h3>メニューページ編集</h3>
				<form action="/tools/rese/reserve/doneEditMenuPage" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
					<p>メニューページのタイトル</p>
					<input type="text" name="pageTitle" value="${menuPage.pageTitle}">
					<p>説明文</p>
					<textarea rows="3" name="description">${menuPage.description}</textarea>
					<p>トップ画像</p>
					<input type="file" name="topImg" value="${menuPage.topImg}">
					<p>予約形態</p>
					<c:if test="${menuPage.reserveSystem == 'firstArrival'}">
						<label class="radio">
							<input type="radio" name="reserveSystem" value="firstArrival" checked> 先着順
						</label>
						<label class="radio">
							<input type="radio" name="reserveSystem" value="recognition"> 承認制
						</label>
					</c:if>
					<c:if test="${menuPage.reserveSystem == 'recognition'}">
						<label class="radio"> <input type="radio" name="reserveSystem" value="firstArrival" checked> 先着順
						</label>
						<label class="radio"> <input type="radio" name="reserveSystem" value="recognition" checked> 承認制
						</label>
					</c:if>

					<p>公開設定</p>
					<c:if test="${menuPage.status == 'open'}">
						<label class="radio"> <input type="radio" name="status" value="open" checked> 公開
						</label>
						<label class="radio"> <input type="radio" name="status" value="not_open"> 非公開
						</label>
					</c:if>
					<c:if test="${menuPage.status == 'not_open'}">
						<label class="radio"> <input type="radio" name="status" value="open"> 公開
						</label>
						<label class="radio"> <input type="radio" name="status" value="not_open" checked> 非公開
						</label>
					</c:if>

					<p>何分ごとに予約を受け付けますか？</p>
					<small>例えば、10時から営業開始で、15分毎の場合、お客様が予約できる時間は10:00、10:15、10:30、10:45 ... となります。30分毎の場合は、10:00、10:30、11:00、11:30 ... となります。</small>
					<br/>
					<select name="reserveInterval">
						<option value="900">15分ごと</option>
						<option value="1800">30分ごと</option>
						<option value="3600">60分ごと</option>
						<option value="7200">120分ごと</option>
					</select>
					<p>予約受け付け</p>
					<div class="dayForm" style="display: none;">
						<p>受付開始</p>
						<select name="reserveStartTime">
							<option value="0">設定しない</option>
							<option value="7">7日前から</option>
							<option value="14">14日前から</option>
							<option value="21">21日前から</option>
							<option value="30">30日前から</option>
							<option value="60">60日前から</option>
							<option value="90">90日前から</option>
						</select>
						<p>受付終了</p>
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
						<p>キャンセル期間</p>
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

					<p>休み設定</p>
					<small>定休日以外に、メニューページごとで休みを設定出来ます。</small>
					
					<!-- 表示を直す -->
					<input type="text" id="datepicker" name="noReserveDate" value="${menuPage.noReserveDate}">
					<input type="hidden" name="noReserveDate" value="">
					<a id="button" href="javascript:;">カレンダー</a>
  					<div id="pochipochi"></div>
					<input type="hidden" name="id" value="${f:h(menuPage.key)}">
					<br />
					<input type="submit" value="更新" class="btn btn-warning">
				</form>
			</div>
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
</body>
</html>

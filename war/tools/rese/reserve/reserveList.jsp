<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>予約一覧</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>
<link href="/css/fullCalendar/fullcalendar.min.css" rel="stylesheet">
<link href="/css/tools/rese/common.css" rel="stylesheet">


<%-- JSインポート --%>
<script src="/js/fullCalendar/moment.min.js" type="text/javascript"></script>
<script src="/js/moment/moment-timezone-with-data.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>

<script src="/js/fullCalendar/jquery-ui.custom.min.js" type="text/javascript"></script>
<script src="/js/fullCalendar/fullcalendar.min.js" type="text/javascript"></script>
<script src="/js/fullCalendar/ja.js" type="text/javascript"></script>
<script src="/js/fullCalendar/gcal.js" type="text/javascript"></script>

<style type="text/css">
.fc-sun { color: #e74c3c; }  /* 日曜日 */
.fc-sat { color: #2980b9; } /* 土曜日 */

.fc-button{
	border: 1px solid #dedede;
	color: #000;
}
.fc-next-button{
	margin-left: 5px;
	padding-left: 5px;
}
</style>
<!-- 予約リストに関するjs -->
<%@ include file="/tools/rese/common/reserveListJs.jsp"%>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container mainContent">
		<div class="span12">
			<div class="span8" style="height: 150px; margin-bottom: 20px;">
				<h3>予約管理</h3>
				<p>予約情報が自動で追加されます。カレンダーの予約情報をクリックすると、詳細を表示することが出来ます。</p>
				<div class="button"><a href="/tools/rese/reserve/createReserve">予約を記入する</a></div>
				<!-- <div class="button"><a href="#createReserve" data-toggle="modal">予約を記入する</a></div> -->
			</div>
			<div class="span3" style="background-color: #000; height: 180px; margin-bottom: 20px; margin-top: 20px;"></div>
			<div id="calendar" class="span8" style="margin-bottom: 20px; height: auto;"></div>
			<div class="span3" style="border: 1px solid #dedede;">
				<p class="minTitle" style="padding: 10px 0 10px 10px; background-color: #ecf0f1;"><img alt="" src="/img/letter.png" class="minTitleIcon">新規予約情報</p>
				<div style="padding:0 10px 10px 10px;">
					<c:forEach var="reserve" items="${reserveList}" begin="0" end="4">
						<p style="margin-bottom: 15px;"><a href="/tools/rese/customerManage/customerDetail?id=${f:h(reserve.customerRef.key)}">${reserve.customerName}</a> 様から予約が入りました。<br/><span class="newReserve">${reserve.noticeDate}</span></p>
					</c:forEach>
				</div>
			</div>
		</div>
		
		<c:forEach var="reserve" items="${reserveList}">
			<div id='${f:h(reserve.key)}' class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			  <form action="">
				  <div class="modal-header">
				    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				    <a href="/tools/rese/customerManage/customerDetail?id=${f:h(reserve.customerRef.key)}"><h3 id="myModalLabel">${reserve.customerName} 様</h3></a>
				  </div>
				  <div class="modal-body">
				    <h5>予約日</h5>
				    <input type="text" name="startTime" value="${reserve.startTime}" class="reserveTime">
				    <input type="text" name="endTime" value="${reserve.endTime}" class="reserveTime">
				    <h5>予約メニュー</h5>
				    <p>${reserve.menuTitle}</p>
				    <h5>メールアドレス</h5>
			    	<p>${reserve.customerMailaddress}</p>
				    <h5>携帯番号</h5>
			    	<p>${reserve.customerPhone}</p>
				  </div>
				  <div class="modal-footer">
				    <a><p data-dismiss="modal" aria-hidden="true" class="closeButton" style="float: left; margin-top: 10px; cursor: pointer;">☓ 閉じる</p></a>
				    <p class="btn btn-primary" onclick="editReserve('${f:h(reserve.key)}');">更新する</p>
				  </div>
			  </form>
			</div>
		</c:forEach>
		
		<div id='createReserve' class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			  <form action="/tools/rese/reserve/createReserve" method="post">
				  <div class="modal-header">
				    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				  	<h4>予約の新規作成</h4>
				  </div>
				  <div class="modal-body">
				    <h5>メニューページ</h5>
				    <p>メニューページを選択してください。</p>
			    	<select name="menuPage" onchange="getMenu();">
						<option value="no">選択してください。
						<c:forEach var="menuPage" items="${menuPageList}">
							<option value="${f:h(menuPage.key)}">${menuPage.pageTitle}
					    </c:forEach>
					</select>
					
					<h5>メニュー</h5>
				    <p>メニューを選択してください。</p>
			    	<select name="menu" id="menuSelect">
					</select>
					
					<h5>予約日時</h5>
					<p>日程を選択してください。</p>
					<input style="cursor:pointer; background-color: #fff;" id="calendar" type="text" placeholder="クリックしてください" onchange="calculate();" name="reserveDate">
					<p>時間を選択してください。</p>
					<select id="reserveMoments" name="reserveMoments" class="validate[required]">
					</select>
					
					
				  </div>
				  <div class="modal-footer">
				    <a><p data-dismiss="modal" aria-hidden="true" class="closeButton" style="float: left; margin-top: 10px; cursor: pointer;">☓ 閉じる</p></a>
				  	<input type="submit" value="次へ">
				  </div>
			  </form>
		</div>
	</div>
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>
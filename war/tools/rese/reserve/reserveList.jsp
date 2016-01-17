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
</style>
<script type="text/javascript">
	var eventList = [];
	var calendar;
	//今月の予約情報を取得します。
	$(document).ready(function() {
		/* console.log("eventList" + JSON.stringify(eventList)); */
		calendar = $('#calendar').fullCalendar({
			header : {
				left : 'prev,next today',
				center : 'title',
				right : 'month agendaWeek agendaDay'
			},
			eventClick : function(view) {
				/* 予約詳細ページに飛ばす */
				/* alert('ビュー表示イベント ' + view.title); */
			},
			timeFormat : 'H:mm',
			eventSources : [eventList]
		});
		$('.fc-button-group .fc-prev-button').attr("onClick","getMonthReserve();");
		$('.fc-button-group .fc-next-button').attr("onClick","getMonthReserve();");
		/* console.log($(".fc-content-skeleton .fc-sun:first").attr('data-date')); */
		/* console.log($(".fc-content-skeleton .fc-sat:last").attr('data-date')); */
	 	$.post("/tools/rese/reserve/getMonthReserveList", {
	 		'startDate' : $(".fc-content-skeleton .fc-sun:first").attr('data-date'),
	 		'endDate' : $(".fc-content-skeleton .fc-sat:last").attr('data-date')
	 	}, function(data){
	 		switch(data.obj){
	 		case null:
	 			alert("読み込みに失敗しました");
	 			break;

	 		default:
	 			/* console.log(data.obj); */
	 			var eventsThisMonth = {};
	 			$.each(data.obj, function(index, val){
		 			var event = {
						title : data.obj[index].customerName,
						start : data.obj[index].start,
						end : data.obj[index].end,
						url : data.obj[index].key
					}
	 				/* console.log(data.obj[index].key); */
		 			
		 			eventsThisMonth["event"] = event;
	 				eventList.push(eventsThisMonth.event);
	 			});
	 			$('#calendar').fullCalendar('addEventSource', eventList);
 				$('#calendar').fullCalendar('refetchEvents');
 				$('.fc-day-grid-event').attr("data-toggle", "modal");
 				$('.fc-day-grid-event').attr("role", "button");
	 		break;
	 		}
	 	}, 'json');
	 	
	 	//予約日時のフォーマットを変更します。 
	 	$(".reserveTime").each(function(index, element){
		 	var m = moment($(element).val(), "ddd MMM DD HH:mm:ss zzz yyyy", 'en');
		 	var output = m.format('MM月DD日 HH:mm');
		 	$(element).text(output);
		 	$(element).attr("value", output);
		 	/* $(element).append("<input type='text' name='startTime' value='${reserve.startTime}' class='reserveTime'>"); */
	 	});
	 	
	 	//新規予約日時のフォーマットを変更します。 
	 	$(".newReserve").each(function(index, element){
		 	var m = moment($(element).text(), "ddd MMM DD HH:mm:ss zzz yyyy", 'en');
		 	var output = m.format('MM月DD日 HH:mm');
		 	$(element).text(output);
	 	});
	});
	
	
	//1月の予約情報を取得します。
	function getMonthReserve(){
		/* console.log($(".fc-content-skeleton .fc-sun:first").attr('data-date'));
		console.log($(".fc-content-skeleton .fc-sat:last").attr('data-date')); */
		var monthEventList = [];
		/* var eventArray = {}; */
		eventList.splice(0, eventList.length);
		$.post("/tools/rese/reserve/getMonthReserveList", {
	 		'startDate' : $(".fc-content-skeleton .fc-sun:first").attr('data-date'),
	 		'endDate' : $(".fc-content-skeleton .fc-sat:last").attr('data-date')
	 	}, function(data){
	 		switch(data.obj){
	 		case null:
	 			alert("読み込みに失敗しました");
	 			break;

	 		default:
	 			console.log(data.obj);
 				var eventArray = {};
	 			$('#calendar').fullCalendar('removeEvents');
 				$.each(data.obj, function(index, val){
	 			var event = {
					title : data.obj[index].customerName,
					start : data.obj[index].start,
					end : data.obj[index].end
				}
	 			eventArray["event"] = event;
	 			monthEventList.push(eventArray.event);
 				});
 				$('#calendar').fullCalendar('addEventSource', monthEventList);
 				$('#calendar').fullCalendar('refetchEvents');
	 		break;
	 		}
	 	}, 'json');
	};
	
	
	function editReserve(reservKey){
		var startTime = $('#' + reservKey +' form input[name=startTime]').val();
		var endTime = $('#' + reservKey +' form input[name=endTime]').val();
		
		$.post("/tools/rese/reserve/doneEditReserve", {
	 		'startDate' : startDate,
	 		'endDate' : endDate,
	 	}, function(data){
	 		switch(data.obj){
	 		case null:
	 			alert("読み込みに失敗しました");
	 			break;

	 		default:
	 			console.log(data.obj);
	 			$('#' + data.obj +' form .closeButton').prepend("<p style='color:red;' class='saveMsg'>保存しました</p>");
	 		break;
	 		}
	 	}, 'json');
	};
	
	
</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container">
		<div class="span12">
			<div class="span8" style="height: 150px; margin-bottom: 20px;">
				<h3>予約管理</h3>
				<p>予約情報が自動で追加されます。カレンダーの予約情報をクリックすると、詳細を表示することが出来ます。</p>
				<div class="button"><a href="">予約を記入する</a></div>
			</div>
			<div class="span3" style="background-color: #000; height: 180px; margin-bottom: 20px;"></div>
			<div id="calendar" class="span8" style="margin-bottom: 20px;"></div>
			<div class="span3">
				<div style="width: 100%; border-bottom: 1px solid #dedede; background-color: #dedede;">
					<h4 style="padding: 10px 0 0 10px;">新規予約情報</h4>
				</div>
				<div style="padding:10px; background-color: #F8F8F8;">
					<c:forEach var="reserve" items="${reserveList}" begin="1" end="5">
						<p style="margin-bottom: 15px;"><a href="/tools/rese/customerManage/customerDetail?id=${f:h(reserve.customerRef.key)}">${reserve.customerName}</a> 様から予約が入りました。<br/><span class="newReserve">${reserve.startTime}</span></p>
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
					</select>
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
	</div>
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>
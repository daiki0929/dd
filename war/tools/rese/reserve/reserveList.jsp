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

<%-- <%@ include file="/tools/rese/common/importJs.jsp"%> --%>
<!-- No file found for: /js/fullCalendar/jquery.min.mapと出るので、リンクから貼ってます。 -->
<!-- <script src="/js/fullCalendar/jquery.min.js" type="text/javascript"></script> -->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

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
	//今月の予約情報を取得します。
	$(document).ready(function() {
	 	$.post("/tools/rese/reserve/displayReserveCal", {
	 		'today' : '${today}'
	 	}, function(data){
	 		switch(data.obj){
	 		case null:
	 			alert("読み込みに失敗しました");
	 			break;

	 		default:
	 			console.log(data.obj);
	 			var eventsThisMonth = {};
	 			$.each(data.obj, function(index, val){
		 			var event = {
						title : data.obj[index].title,
						start : data.obj[index].start,
						end : data.obj[index].end
					}
		 			eventsThisMonth["event"] = event;
	 				eventList.push(eventsThisMonth.event);
	 			});
	 		break;
	 		}
	 	}, 'json');

		
	});
	var calendar;
	$(document).ajaxComplete(function(){
		console.log("eventList" + JSON.stringify(eventList));
		calendar = $('#calendar').fullCalendar({
			header : {
				left : 'prev,next today',
				center : 'title',
				right : 'month agendaWeek agendaDay'
			},
			eventClick : function(view) {
				/* 予約詳細ページに飛ばす */
				alert('ビュー表示イベント ' + view.title);
			},
			timeFormat : 'H(:mm)',
			eventSources : [eventList]
		});
		$('.fc-button-group .fc-prev-button').attr("onClick","getPreCalStartDay();");
		$('.fc-button-group .fc-next-button').attr("onClick","getNxtCalStartDay();");
	});

	//先月の予約情報を取得します。
	function getPreCalStartDay(){
		var eventsPreMonth = {};
		eventList.splice(0, eventList.length);
		$.post("/tools/rese/reserve/getPreCalStartDay", {
	 		'today' : '${today}'
	 	}, function(data){
	 		switch(data.obj){
	 		case null:
	 			alert("読み込みに失敗しました");
	 			break;

	 		default:
 				var eventsPreMonth = {};
 				$.each(data.obj, function(index, val){
	 			var event = {
					title : data.obj[index].title,
					start : data.obj[index].start,
					end : data.obj[index].end
				}
	 			eventsPreMonth["event"] = event;
 				eventList.push(eventsPreMonth.event);
 				});
	 		break;
	 		}
	 	}, 'json');
	};
	
	//翌月の予約情報を取得します。
	function getNxtCalStartDay(){
		var eventsNxtMonth = {};
		eventList.splice(0, eventList.length);
		$.post("/tools/rese/reserve/getNxtCalStartDay", {
	 		'today' : '${today}'
	 	}, function(data){
	 		switch(data.obj){
	 		case null:
	 			alert("読み込みに失敗しました");
	 			break;

	 		default:
	 			console.log(data.obj);
 				var eventsNxtMonth = {};
 				$.each(data.obj, function(index, val){
	 			var event = {
					title : data.obj[index].title,
					start : data.obj[index].start,
					end : data.obj[index].end
				}
	 			eventsNxtMonth["event"] = event;
 				eventList.push(eventsNxtMonth.event);
 				});
	 		break;
	 		}
	 	}, 'json');
	};
	

	
</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container">
		<div class="span12" style="margin-top: 50px;">
			<div class="span8" style="height: 150px;"><p>新規予約</p></div>
			<div class="span3" style="background-color: #f1c40f; height: 150px;">広告</div>
		</div>
		<div class="span12">
		<div id="calendar" class="span8"></div>
		<div class="span3" style="background-color: #f2f2f2; height: 800px;"></div>
		</div>
	</div>
</body>
</html>
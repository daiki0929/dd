<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	var eventList = [];
	var calendar;
	//今月の予約情報を取得します。
	$(document).ready(function() {
		/* console.log("eventList" + JSON.stringify(eventList)); */
		calendar = $('#calendar').fullCalendar({
			header : {
				left : 'prev,next',
				center : 'title',
				right : '',
				height: 700
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
 				setModalTarget();
	 		break;
	 		}
	 	}, 'json');
	 	
	 	//予約日時のフォーマットを変更します。 
	 	$(".reserveTime").each(function(index, element){
		 	var m = moment($(element).text(), "ddd MMM DD HH:mm:ss zzz yyyy", 'en');
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
		var monthEventList = [];
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
					end : data.obj[index].end,
					url : data.obj[index].key
				}
	 			eventArray["event"] = event;
	 			monthEventList.push(eventArray.event);
 				});
 				$('#calendar').fullCalendar('addEventSource', monthEventList);
 				$('#calendar').fullCalendar('refetchEvents');
 				setModalTarget();
	 		break;
	 		}
	 	}, 'json');
	};
	
	//モーダルするのに必要な情報をセットします。
	function setModalTarget(){
		$('.fc-day-grid-event').attr("data-toggle", "modal");
		$('.fc-day-grid-event').each(function(i, elem){
			var key = $(elem).attr("href");
			console.log(key);
			$(elem).attr("data-target", key);
		});
	}
	
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
	
	/**
	 * メニューを取得します。
	 */
	 var postUrl = "/tools/rese/reserve/getMenu";
	 function getMenu(){
		 deleteMenu();
	 	var menuPageKey = $("select[name=menuPage]").val();
	 	$.post(postUrl, {
	 		'menuPageKey' : menuPageKey
	 	}, function(data){
	 		console.log(data);
	 		switch(data.obj){
	 		case null:
	 			break;

	 		default:
	 			if(data.obj == null){
					$('#menuSelect option').append("<option value='' disabled>選択できるメニューはありません。</option>");
	 			}
	 			$.each(data.obj,function(index,val){
	 				console.log("ログ：" + data.obj.key);
					$('#menuSelect').append("<option value=" + data.obj[index].key + ">" + data.obj[index].title);
	 			});
	 			break;
	 		}
	 	}, 'json');
	 }
	
	//最上部までスクロール
	$(document).ready(function() {
		$(function() {
			// scroll body to 0px on click
			$('#back-top a').click(function() {
				$('body,html').animate({
					scrollTop : 0
				}, 600);
				return false;
			});
		});
	});
	
	/**
	  * 予約時間を削除します。
	  */
	 function deleteMenu(){
		 $('#menuSelect option').remove();
	 }
	
	
</script>
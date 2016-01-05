<%@ page pageEncoding="utf-8" %>
$(document).ready(function() {
	console.log('${statusByDays}');
	var checkLength = parseInt(document.getElementsByTagName("label").length);

	var daysOfTheWeek = ["sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"];
	var number = -1;
	/* {statusByDays.+ val +.shopStatus} が出来ないので、配列で渡しています。*/
	var daysOfTheWeekStatus = ["${statusByDays.sunday.shopStatus}", "${statusByDays.monday.shopStatus}", "${statusByDays.tuesday.shopStatus}", "${statusByDays.wednesday.shopStatus}", "${statusByDays.thursday.shopStatus}", "${statusByDays.friday.shopStatus}", "${statusByDays.saturday.shopStatus}"];
	var daysOfTheWeekStartTime = ["${statusByDays.sunday.startTime}", "${statusByDays.monday.startTime}", "${statusByDays.tuesday.startTime}", "${statusByDays.wednesday.startTime}", "${statusByDays.thursday.startTime}", "${statusByDays.friday.startTime}", "${statusByDays.saturday.startTime}"];
	var daysOfTheWeekEndTime = ["${statusByDays.sunday.endTime}", "${statusByDays.monday.endTime}", "${statusByDays.tuesday.endTime}", "${statusByDays.wednesday.endTime}", "${statusByDays.thursday.endTime}", "${statusByDays.friday.endTime}", "${statusByDays.saturday.endTime}"];

	$.each(daysOfTheWeek,function(index,val){
		console.log(val);
		number++
		/* 営業の有無を表示します */
		for (var count = 0; count < 2; count++){
			if($('form[name='+ val +'] input[name='+ val +'ShopStatus]')[count].value == daysOfTheWeekStatus[number]){
				var selectedItem = $('form[name='+ val +'] input[name='+ val +'ShopStatus]')[count];
				console.log($('form[name='+ val +'] input[name='+ val +'ShopStatus]')[count]);
				console.log(daysOfTheWeekStatus[number]);
				selectedItem.setAttribute("checked", "true");
				break;
			}
		}

		var length = parseInt(document.getElementsByTagName("option").length);
		/* 開店時間を表示します */
		for (var count = 0; count < length; count++){
			if($('form[name='+ val +'] select[name=startTime] option')[count].value == daysOfTheWeekStartTime[number]){
				var selectedItem = $('form[name='+ val +'] select[name=startTime] option')[count];
				selectedItem.setAttribute("selected", "true");
				break;
			}
		}
		/* 閉店時間を表示します */
		for (var count = 0; count < length; count++){
			if($('form[name='+ val +'] select[name=endTime] option')[count].value == daysOfTheWeekEndTime[number]){
				var selectedItem = $('form[name='+ val +'] select[name=endTime] option')[count];
				selectedItem.setAttribute("selected", "true");
				break;
			}
		}
	});
});

var postUrl = "/tools/rese/doneEditOperationHours";
/* 営業日時を保存します */
function postOperationHours(daysOfTheWeek){
	$('.saveMsg').remove();
	var shopStatus = $('form[name='+ daysOfTheWeek +'] input[name='+ daysOfTheWeek +'ShopStatus]').val();
	var startTime = $("[name="+ daysOfTheWeek +"] [name=startTime]").val();
	var endTime = $("[name="+ daysOfTheWeek +"] [name=endTime]").val();
	$.post(postUrl, {
		'daysOfTheWeek' : daysOfTheWeek,
		'shopStatus' : shopStatus,
		'startTime' : startTime,
		'endTime' : endTime
	}, function(data){
		console.log(data.obj);
		switch(data.obj){
		case null:
			alert("保存に失敗しました");
			break;

		default:
			$('form[name=' + data.obj + ']').append("<p style='color:red;' class='saveMsg'>保存しました</p>");
			break;
			
		case 'error':
//			$('form[name=' + data.obj + ']').append("<p style='color:red;' class='saveMsg'>" + data.msg + "</p>");
			alert(data.msg);
			break;
			
			
		}
	}, 'json');
}

/* 営業の有無を設定します */
function postShopStatus(daysOfTheWeek, shopStatus){
	$('.saveMsg').remove();
	var startTime = $("[name="+ daysOfTheWeek +"] [name=startTime]").val();
	var endTime = $("[name="+ daysOfTheWeek +"] [name=endTime]").val();
	$.post(postUrl, {
		'daysOfTheWeek' : daysOfTheWeek,
		'shopStatus' : shopStatus,
		'startTime' : startTime,
		'endTime' : endTime
	}, function(data){
		console.log(data.obj);
		switch(data.obj){
		case null:
			alert("保存に失敗しました");
			break;

		default:
			$('form[name=' + data.obj + ']').append("<p style='color:red;' class='saveMsg'>保存しました</p>");
		break;
		}
	}, 'json');

}
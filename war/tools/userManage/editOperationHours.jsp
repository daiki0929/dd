<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/userManage/common/meta.jsp"%>
<title>メニュー編集</title>
<!-- css -->
<%@ include file="/tools/userManage/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/userManage/common/importJs.jsp"%>

<script type="text/javascript">
	$(document).ready(function() {
		var checkLength = parseInt(document.getElementsByTagName("label").length);
		/* 営業日 or 非営業日
		var daysOfTheWeek = [ "sunday", "monday", "tuesday", "wednesday", "tursday", "friday", "saturday" ]
			$(daysOfTheWeek).each(function(index, elem) {
				console.log(elem);
				if($("input[name=" + elem + "ShopStatus]")[count].value == '${statusByDays.' + elem + '.shopStatus}'){
					var selectedItem = $("input[name=" + elem + "ShopStatus]")[count];
					selectedItem.setAttribute("checked", "true");
					break;
			　　　}
			}); */
		
		for (var count = 0; count < checkLength; count++){
			if($('[name=sunday] [name=sundayShopStatus]')[count].val(); == "${statusByDays.sunday.startTime}"){
				var selectedItem = $('[name=sunday] [name=sundayShopStatus]')[count];
				selectedItem.setAttribute("selected", "true");
				break;
			　　　}
		}
		
		var length = parseInt(document.getElementsByTagName("option").length);
		/* 開店時間 */
		for (var count = 0; count < length; count++){
			if($('select[name=startTime] option')[count].value == "${statusByDays.sunday.startTime}"){
				var selectedItem = $('select[name=startTime] option')[count];
				selectedItem.setAttribute("selected", "true");
				break;
			　　　}
		}
		/* 閉店時間 */
		for (var count = 0; count < length; count++){
			if($('select[name=endTime] option')[count].value == "${statusByDays.sunday.endTime}"){
				var selectedItem = $('select[name=endTime] option')[count];
				selectedItem.setAttribute("selected", "true");
				break;
			　　　}
		}
	});
	
	/* Jsonで営業日時を保存します。 */
	function postShopStatus(daysOfTheWeek){
		var postUrl = "/tools/userManage/doneEditOperationHours";
		
		var shopStatus = $('[name='+ daysOfTheWeek +'] [name=shopStatus]').val();
		var startTime = $("[name="+ daysOfTheWeek +"] [name=startTime]").val();
		var endTime = $("[name="+ daysOfTheWeek +"] [name=endTime]").val();
		
		console.log(daysOfTheWeek);
		console.log(shopStatus);
		console.log(startTime);
		console.log(endTime);
		
		
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
				$('form[name=' + data.obj + ']').append("<p style='color:red;'>保存しました</p>");
				break;
			}
		}, 'json');
	}
</script>
</head>
<body>
	<%@ include file="/tools/userManage/common/topBar.jsp"%>
	<div class="container">
		<!-- エラーメッセージ -->
		<%@ include file="/tools/userManage/common/error.jsp"%>
		<div class="span12">
			<h3>営業日時</h3>
			<!-- 日曜日 -->
			<form name="sunday">
				<p>日曜日</p>
				<label class="radio">
					<input type="radio" name="sundayShopStatus" value="open" onclick="postShopStatus('sunday')"> 営業日
				</label>
				<label class="radio">
					<input type="radio" name="sundayShopStatus" value="notOpen" onclick="postShopStatus('sunday')"> 非営業日
				</label> <br />
				<p>営業開始時間</p>
				<select name="startTime" onchange="postShopStatus('sunday')">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
				<p>営業終了時間</p>
				<select name="endTime" onchange="postShopStatus('sunday')">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
			</form>

			<!-- 月曜日 -->
			<form name="monday">
				<p>月曜日</p>
				<label class="radio">
					<input type="radio" name="mondayShopStatus" value="open"> 営業日
				</label>
				<label class="radio">
					<input type="radio" name="mondayShopStatus" value="notOpen"> 非営業日
				</label> <br />
				<p>営業開始時間</p>
				<select name="startTime">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
				<p>営業終了時間</p>
				<select name="endTime">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
			</form>

			<!-- 火曜日 -->
			<form name="tuesday">
				<p>火曜日</p>
				<label class="radio">
					<input type="radio" name="tuesdayShopStatus" value="open"> 営業日
				</label>
				<label class="radio">
					<input type="radio" name="tuesdayShopStatus" value="notOpen"> 非営業日
				</label> <br />
				<p>営業開始時間</p>
				<select name="startTime">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
				<p>営業終了時間</p>
				<select name="endTime">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
			</form>

			<!-- 水曜日 -->
			<form name="wednesday">
				<p>水曜日</p>
				<label class="radio">
					<input type="radio" name="wednesdayShopStatus" value="open"> 営業日
				</label>
				<label class="radio">
					<input type="radio" name="wednesdayShopStatus" value="notOpen"> 非営業日
				</label> <br />
				<p>営業開始時間</p>
				<select name="startTime">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
				<p>営業終了時間</p>
				<select name="endTime">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
			</form>

			<!-- 木曜日 -->
			<form name="thursday">
				<p>木曜日</p>
				<label class="radio">
					<input type="radio" name="thursdayShopStatus" value="open"> 営業日
				</label>
				<label class="radio">
					<input type="radio" name="thursdayShopStatus" value="notOpen"> 非営業日
				</label> <br />
				<p>営業開始時間</p>
				<select name="startTime">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
				<p>営業終了時間</p>
				<select name="endTime">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
			</form>

			<!-- 金曜日 -->
			<form name="friday">
				<p>金曜日</p>
				<label class="radio">
					<input type="radio" name="fridayShopStatus" value="open"> 営業日
				</label>
				<label class="radio">
					<input type="radio" name="fridayShopStatus" value="notOpen"> 非営業日
				</label> <br />
				<p>営業開始時間</p>
				<select name="startTime">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
				<p>営業終了時間</p>
				<select name="endTime">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
			</form>

			<!-- 土曜日 -->
			<form name="saturday">
				<p>土曜日</p>
				<label class="radio">
					<input type="radio" name="saturdayShopStatus" value="open"> 営業日
				</label>
				<label class="radio">
					<input type="radio" name="saturdayShopStatus" value="notOpen"> 非営業日
				</label> <br />
				<p>営業開始時間</p>
				<select name="startTime">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
				<p>営業終了時間</p>
				<select name="endTime">
					<option value='0:00'>0:00</option>
					<option value='0:30'>0:30</option>
					<option value='1:00'>1:00</option>
					<option value='1:30'>1:30</option>
					<option value='2:00'>2:00</option>
					<option value='2:30'>2:30</option>
					<option value='3:00'>3:00</option>
					<option value='3:30'>3:30</option>
					<option value='4:00'>4:00</option>
					<option value='4:30'>4:30</option>
					<option value='5:00'>5:00</option>
					<option value='5:30'>5:30</option>
					<option value='6:00'>6:00</option>
					<option value='6:30'>6:30</option>
					<option value='7:00'>7:00</option>
					<option value='7:30'>7:30</option>
					<option value='8:00'>8:00</option>
					<option value='8:30'>8:30</option>
					<option value='9:00'>9:00</option>
					<option value='9:30'>9:30</option>
					<option value='10:00'>10:00</option>
					<option value='10:30'>10:30</option>
					<option value='11:00'>11:00</option>
					<option value='11:30'>11:30</option>
					<option value='12:00'>12:00</option>
					<option value='12:30'>12:30</option>
					<option value='13:00'>13:00</option>
					<option value='13:30'>13:30</option>
					<option value='14:00'>14:00</option>
					<option value='14:30'>14:30</option>
					<option value='15:00'>15:00</option>
					<option value='15:30'>15:30</option>
					<option value='16:00'>16:00</option>
					<option value='16:30'>16:30</option>
					<option value='17:00'>17:00</option>
					<option value='17:30'>17:30</option>
					<option value='18:00'>18:00</option>
					<option value='18:30'>18:30</option>
					<option value='19:00'>19:00</option>
					<option value='19:30'>19:30</option>
					<option value='20:00'>20:00</option>
					<option value='20:30'>20:30</option>
					<option value='21:00'>21:00</option>
					<option value='21:30'>21:30</option>
					<option value='22:00'>22:00</option>
					<option value='22:30'>22:30</option>
					<option value='23:00'>23:00</option>
					<option value='23:30'>23:30</option>
					<option value='23:59'>23:59</option>
				</select>
			</form>
		</div>
	</div>
</body>
</html>

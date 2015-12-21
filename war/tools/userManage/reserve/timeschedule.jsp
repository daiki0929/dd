<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/userManage/common/meta.jsp"%>
<title>メニューページ一覧</title>
<!-- css -->
<%@ include file="/tools/userManage/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/userManage/common/importJs.jsp"%>
<script type="text/javascript" charset="UTF-8">
$(document).ready(function() {
	var length = parseInt(document.getElementsByTagName("input").length);
	/* 選択したメニューにチェックを入れる */
	for (var count = 0; count < length; count++){
		if($('form[name=selectMenu] .menuRadio')[count].value == "${f:h(selectedMeuKey)}"){
			var selectedItem = $('form[name=selectMenu] .menuRadio')[count];
			selectedItem.setAttribute("checked", "true");
			break;
		　　　}
	}
});


var mondayShopStatus = "${statusByDays.monday.shopStatus}";
var sundayShopStatus = "${statusByDays.sunday.shopStatus}";
var tuesdayShopStatus = "${statusByDays.tuesday.shopStatus}";
var wednesdayShopStatus = "${statusByDays.wednesday.shopStatus}";
var thursdayShopStatus = "${statusByDays.thursday.shopStatus}";
var fridayShopStatus = "${statusByDays.friday.shopStatus}";
var saturdayShopStatus = "${statusByDays.saturday.shopStatus}";

/**
 * 予約可能な日を表示します。
 */
$(function() {
	$("#datepicker").datepicker({
		dateFormat : 'yy/mm/dd', //表示フォーマット
		minDate : '0d',
		maxDate : '+${reserveStartTime}d',
		beforeShowDay : function(day) {
			var result;
			switch (day.getDay()) {
			case 0: // 日曜日を選択できないようにする
				if(sundayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			case 1: // 月曜日を選択できないようにする
				if(mondayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			case 2: // 火曜日を選択できないようにする
				if(tuesdayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			case 3: // 水曜日を選択できないようにする
				if(wednesdayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			case 4: // 木曜日を選択できないようにする
				if(thursdayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			case 5: // 金曜日を選択できないようにする
				if(fridayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			case 6: // 土曜日を選択できないようにする
				if(saturdayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			default:
				result = [ true ]; // それ以外は選択できる
			break;
			}
			return result;
		}
	});
});

/**
 * 予約可能な時間を計算します。
 */
 var postUrl = "/tools/userManage/reserve/calculateTime";
 function calculate(meutime){
	deleteCalculate()
 	var reserveDate = $("input[name=reserveDate]").val();
 	var menuKey = $('form[name=selectMenu] .menuRadio[checked=true]').val();
	console.log(menuKey);
 	$.post(postUrl, {
 		'reserveDate' : reserveDate,
 		'menuKey' : menuKey
 	}, function(data){
 		console.log(data);
 		console.log(data.obj);
 		switch(data.obj){
 		case null:
 			alert("読み込みに失敗しました");
 			break;

 		default:
			$('#reserveMoments').append("<option class='timeRadio'>選択して下さい</option>");
 			if(data.obj == null){
				$('#reserveMoments').append("<option class='timeRadio' disabled>予約できる時間はありません。</option>");
 			}
 			$.each(data.obj,function(index,val){
 				$('#reserveMoments').append("<option value=" + data.obj[index] + " class='timeRadio'>" + data.obj[index] + "</option>");
 			});
 			/* if($('#reserveMoments option')[0] != null){
	 			var selectTime = $('#reserveMoments option')[0];
				selectTime.setAttribute("selected", "true");
 			} */
 		break;
 		}
 	}, 'json');
 }
 
 /**
  * 予約時間を削除します。
  */
 function deleteCalculate(){
	 $('#reserveMoments option').remove();
 }
/**
 * メニューの時間を分にして、切り捨てをする。
 */
$(document).ready(function() {
	$(".menuTime").each(function(i, elem) {
		$(".menuTime")[i].innerText = Math.floor($(elem).text());
	});
});
</script>
</head>
<body>
	<div class="container">
		<div class="span12">
			<h2>予約をすすめる</h2>
			<form action="/tools/userManage/reserve/confirmReserve" name="selectMenu">
				<div class="span7">
					<h3>メニュー・予約日時を選択して下さい</h3>
					<c:forEach var="menu" items="${menuList}">
						<label class="radio"> <input type="radio" name="orderMenu" value="${f:h(menu.key)}" class="menuRadio">
							${menu.title}(<span class="menuTime">${menu.time/60}</span>分)${menu.price}円
						</label>
					</c:forEach>
					<h4>日程を決める</h4>
					<input type="text" name="reserveDate" onchange="calculate();" id="datepicker">
					<h4>時間を決める</h4>
					<select id="reserveMoments" name="reserveMoments">
					</select>
					<h3>連絡先を記入して下さい</h3>
					<h4>お名前</h4>
					<p>(例)田中太郎</p>
					<input type="text" name="customerName">
					<h4>メールアドレス</h4>
					<p>(例)sample@mail.com</p>
					<input type="text" name="customerMailadress">
					<h4>電話番号</h4>
					<p>(例)000-0000-0000 「-」の記入を忘れないようにして下さい。</p>
					<input type="text" name="customerPhone">
					<br />
					<input type="submit" value="次へ">
				</div>
			</form>
		</div>
	</div>
</body>
</html>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>メニューページ一覧</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>


<link rel="stylesheet" href="/css/pickadate/default.css">
<link rel="stylesheet" href="/css/pickadate/default.date.css">
 
 
<script src="/js/pickadate/picker.js"></script>
<script src="/js/pickadate/picker.date.js"></script>
<script src="/js/pickadate/legacy.js"></script>
<script src="/js/pickadate/ja_JP.js"></script>


<script src="/js/jq/jquery.validationEngine.js"></script>
<script src="/js/jq/jquery.validationEngine-ja.js"></script>
<link rel="stylesheet" href="/css/jq/validationEngine.jquery.css">

<script type="text/javascript" charset="UTF-8">
var mondayShopStatus = "${statusByDays.monday.shopStatus}";
var sundayShopStatus = "${statusByDays.sunday.shopStatus}";
var tuesdayShopStatus = "${statusByDays.tuesday.shopStatus}";
var wednesdayShopStatus = "${statusByDays.wednesday.shopStatus}";
var thursdayShopStatus = "${statusByDays.thursday.shopStatus}";
var fridayShopStatus = "${statusByDays.friday.shopStatus}";
var saturdayShopStatus = "${statusByDays.saturday.shopStatus}";


$(function(){
	  jQuery("#form_1").validationEngine();
});
	
$(function() {
    $('#calendar').pickadate({
    	format: 'yyyy/mm/dd',
     	min: [${reserveFrom}],
   	  	max: [${reserveTo}],
    	disable: ${offDaysOfTheWeekNum}
    });
});
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




/**
 * 予約可能な時間を計算します。
 */
 var postUrl = "/tools/rese/reserve/customer/calculateTime";
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
			/* $('#reserveMoments').append("<option class='timeRadio'>選択して下さい</option>"); */
 			if(data.obj == null){
				$('#reserveMoments').append("<option class='timeRadio' disabled>予約できる時間はありません。</option>");
 			}
 			$.each(data.obj,function(index,val){
 				$('#reserveMoments').append("<option value=" + data.obj[index] + " class='timeRadio'>" + data.obj[index] + "</option>");
 			});
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
<%-- 		<form action="/tools/rese/reserve/customer/confirmReserve" name="selectMenu" id="form_1"> --%>
		<form action="/confirm" name="selectMenu" id="form_1" method="post">
			<div class="span7">
				<h3>メニュー・予約日時を選択して下さい</h3>
				<c:forEach var="menu" items="${menuList}">
					<label class="radio"> <input type="radio" name="orderMenu" value="${f:h(menu.key)}" class="menuRadio">
						${menu.title}(<span class="menuTime">${menu.time/60}</span>分)${menu.price}円
					</label>
				</c:forEach>
				<h4>日程を決める</h4>
			 	<input style="cursor:pointer; background-color: #fff;" id="calendar" type="text" placeholder="クリックしてください" onchange="calculate();" name="reserveDate">
				<h4>時間を決める</h4>
				<p>日程を選択すると、予約可能な時間が表示されます。</p>
				<select id="reserveMoments" name="reserveMoments" class="validate[required]">
				</select>
				<h3>連絡先を記入して下さい</h3>
				<h4>お名前</h4>
				<p>(例)田中太郎</p>
				<input type="text" name="customerName" class="validate[required]">
				<h4>メールアドレス</h4>
				<p>(例)sample@mail.com</p>
				<input type="text" name="customerMailaddress" class="validate[required[custom[email]]]">
				<h4>電話番号</h4>
				<p>(例)000-0000-0000 「-」の記入を忘れないようにして下さい。</p>
				<input type="text" name="customerPhone" class="validate[custom[phoneHyphen]]">
				<!-- <h4>パスワード</h4>
				<p>パスワードを登録すると、次回から記入が簡単になります。(4文字以上8文字以内)</p>
				<input class="validate[required, minSize[4], maxSize[8]] text-input" type="password" id="passwd" name="customerPassword"/>
				<br/>
				<p>確認</p>
				<input class="validate[required,equals[passwd]] text-input" type="password" id="re_passwd" name="customerPasswordConfirm"/> -->
				<br />
				<input type="submit" value="次へ">
			</div>
		</form>
	</div>
</div>
	
</body>
</html>
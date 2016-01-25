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
	
	/* メニューの時間を分にして、切り捨てをする。 */
	$(".menuTime").each(function(i, elem) {
		$(".menuTime")[i].innerText = Math.floor($(elem).text());
	});
	
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
  * 日程と時間をリセットします。
  */
 function clearValue(){
 	$("[name=reserveDate]").val('');
 	$("[name=reserveMoments] option").remove();
 }


</script>
</head>
<body style="background-image: url('/img/back1.jpg'); background-repeat: no-repeat; background-size:cover;">
<div class="container" style="background-color: #fff; border-radius:5px; margin-bottom: 50px;">
	<div style="padding: 3%;">
		<h2 class="reservePageTitle">メニュー・予約日時を選択して下さい</h2>
		<hr />
		<c:if test="${error != null}">
			<p style="color:red;">ご予約が他のお客様と重複しました。再度、日時を選択して下さい。</p>
		</c:if>
		<form action="/confirm" name="selectMenu" id="form_1" method="post">
			<div class="span7">
				<c:forEach var="menu" items="${menuList}">
					<label class="radio"> <input type="radio" name="orderMenu" value="${f:h(menu.key)}" class="menuRadio" onclick="clearValue();">
						${menu.title} (<span class="menuTime">${menu.time/60}</span>分)${menu.price}円
					</label>
				</c:forEach>
				<h4>日程を決める</h4>
			 	<input style="cursor:pointer; background-color: #fff;" id="calendar" type="text" placeholder="クリックしてください" onchange="calculate();" name="reserveDate">
				<h4>時間を決める</h4>
				<p>日程を選択すると、予約可能な時間が表示されます。</p>
				<select id="reserveMoments" name="reserveMoments" class="validate[required]">
				</select>
				<h3>ご連絡先をご記入ください。</h3>
				<h4>お名前</h4>
				<p>(例)田中太郎</p>
				<input type="text" name="customerName" class="validate[required]" value="${customerName}">
				<h4>メールアドレス</h4>
				<p>(例)sample@mail.com</p>
				<input type="text" name="customerMailaddress" class="validate[required[custom[email]]]" value="${customerMailaddress}">
				<h4>電話番号</h4>
				<p>(例)000-0000-0000 「-」の記入を忘れないようにして下さい。</p>
				<input type="text" name="customerPhone" class="validate[custom[phoneHyphen]]" value="${customerPhone}">
				<br />
				<input type="submit" class="reserveBtn" style="width:200px; border: none; margin: 20px 0 40px 0;" value="次へ">
			</div>
		</form>
	</div>
	</div>
</div>
	
</body>
</html>
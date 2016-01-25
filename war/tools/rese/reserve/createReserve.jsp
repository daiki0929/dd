<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>予約作成</title>
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
  	format: 'yyyy/mm/dd'
  });
});


/**
 * 予約可能な時間を計算します。
 */
 var caluculateUrl = "/tools/rese/reserve/customer/calculateTime";
 function calculate(meutime){
	deleteCalculate()
 	var reserveDate = $("input[name=reserveDate]").val();
 	var menuKey = $('select[name=menuKey]').val();
	console.log(reserveDate);
	console.log("メニューkey：" + menuKey);
 	$.post(caluculateUrl, {
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
 				$('#reserveMoments').prop("disabled", "");
 				$('select[name=customerKey]').prop("disabled", "");
 				$('#reserveMoments').append("<option value=" + data.obj[index] + " class='timeRadio'>" + data.obj[index] + "</option>");
 			});
 		break;
 		}
 	}, 'json');
 }


/**
 * メニューを取得します。
 */
 var getMenuUrl = "/tools/rese/reserve/getMenu";
 $(document).ready(function(){
	 $("select[name=menuPage]").change(function (){
		 deleteMenu();
	 	var menuPageKey = $("select[name=menuPage]").val();
	 	$.post(getMenuUrl, {
	 		'menuPageKey' : menuPageKey
	 	}, function(data){
	 		console.log(data);
	 		switch(data.obj){
	 		case null:
	 			$('input:not(#submit)').attr("disabled", "true");
	 			$('select:gt(0)').attr("disabled", "true");
	 			$('#calendar').css("background-color", "#dedede");
	 			break;
	
	 		default:
	 			if(data.obj == null){
					$('#menuSelect option').append("<option value='' disabled>選択できるメニューはありません。</option>");
					break;
	 			}
 				$('#menuSelect').prop("disabled", "");
 				
				$('#menuSelect').append("<option value=''>選択してください。</option>");
	 			$.each(data.obj,function(index,val){
	 				console.log("ログ：" + data.obj.key);
					$('#menuSelect').append("<option value=" + data.obj[index].key + ">" + data.obj[index].title + "<span class='menuTime'> (" + data.obj[index].time + "分)</span>" + data.obj[index].price + "円");
					parseTime();
	 			});
	 			break;
	 		}
	 	}, 'json');
	 }).change();
});


 
 /**
  * 予約時間を削除します。
  */
 function deleteCalculate(){
	 $('#reserveMoments option').remove();
 }
/**
 * メニューの時間を分にして、切り捨てをする。
 */
 function parseTime(){
	$(".menuTime").each(function(i, elem) {
		$(".menuTime")[i].innerText = Math.floor($(elem).text());
	});
 }
/**
  * 予約時間を削除します。
  */
 function deleteMenu(){
	 $('#menuSelect option').remove();
 }
 /**
  * 顧客選択を不可にする。
  * 新規顧客を可能にする。
  */
 function disableSelectCustomer(){
	 $('select[name=customerKey]').prop("disabled", "true");
	 $('select[name=customerKey]').attr("class", "");
	 $('#customerBtn').attr("onclick", "ableSelectCustomer();");
	 
	 $('input[name=customerName]').prop("disabled", "");
	 $('input[name=customerMailaddress]').prop("disabled", "");
	 $('input[name=customerPhone]').prop("disabled", "");
	 
 }
 /**
  * 顧客選択を可能にする。
  * 新規顧客を不可にする。
  */
 function ableSelectCustomer(){
	 $('select[name=customerKey]').prop("disabled", "");
	 $('select[name=customerKey]').attr("class", "validate[required]");
	 $('#customerBtn').attr("onclick", "disableSelectCustomer();");
	 
	 $('input[name=customerName]').prop("disabled", "true");
	 $('input[name=customerMailaddress]').prop("disabled", "true");
	 $('input[name=customerPhone]').prop("disabled", "true");
 }
 
 /**
  * 日程の選択を可能にする。
  */
 function ableSelectDate(){
	 $('#calendar').prop("disabled", "");
	 $('#calendar').css("background-color", "#fff");
	 console.log($('select[name=menuKey]').val());
	 if($('select[name=menuKey]').val() == ""){
		 $('#calendar').prop("disabled", "true");
		 $('#calendar').css("background-color", "#dedede");
	 }
 }
</script>
</head>
<body>
<%@ include file="/tools/rese/common/topBar.jsp"%>
<div class="container mainContent">
	<div class="span12">
		<h3>予約の新規作成</h3>
		<div class="span7">
			<form action="/tools/rese/reserve/doneCreateReserve" name="selectMenu" id="form_1" method="post">
				<h3>メニュー・予約日時を選択して下さい</h3>
				<select name="menuPage">
					<option value="">選択してください。
					<c:forEach var="menuPage" items="${menuPageList}">
						<option value="${f:h(menuPage.key)}">${menuPage.pageTitle}
				    </c:forEach>
				</select>
				
				<h5>メニュー</h5>
			    <p>メニューを選択してください。</p>
		    	<select name="menuKey" id="menuSelect" onchange="ableSelectDate();" class="validate[required]">
		    		
				</select>
					
				<h4>日程を決める</h4>
			 	<input style="cursor:pointer; background-color: #dedede;" id="calendar" type="text" placeholder="クリックしてください" onchange="calculate();" name="reserveDate" disabled>
				<h4>時間を決める</h4>
				<p>日程を選択すると、予約可能な時間が表示されます。</p>
				<select id="reserveMoments" name="reserveMoments" class="validate[required]">
				</select>
				
				<h4>顧客の選択</h4>
				<select name="customerKey" class="validate[required]">
					<c:if test="${customerList != null}">
						<option>選択してください。
						<c:forEach var="customer" items="${customerList}">
							<option value="${f:h(customer.key)}">${customer.name}
					    </c:forEach>
				    </c:if>
				</select>
				 
				<button type="button" id="customerBtn" class="btn btn-danger" data-toggle="collapse" data-target="#demo" onclick="disableSelectCustomer();">
				  新規顧客として登録する
				</button>
				<div id="demo" class="collapse">
					<h4>お名前</h4>
					<p>(例)田中太郎</p>
					<input type="text" name="customerName" class="validate[required]" disabled>
					<h4>メールアドレス</h4>
					<p>(例)sample@mail.com</p>
					<p>メールアドレスを登録しない場合、予約フォームからの予約においてリピーターとして保存することが出来ません。</p>
					<input type="text" name="customerMailaddress" disabled>
					<h4>電話番号</h4>
					<p>(例)000-0000-0000 「-」の記入を忘れないようにして下さい。</p>
					<input type="text" name="customerPhone" disabled>
				</div>
				<br />
				<input type="submit" id="submit" value="完了">
			</form>
		</div>
	</div>
</div>
<%@ include file="/tools/rese/common/footer.jsp"%>
	
</body>
</html>
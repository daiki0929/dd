<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<!DOCTYPE html>
<html lang="jp">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Dashboard">
    <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>Rese - 予約自動管理ツール</title>

    <!-- Bootstrap core CSS -->
    <link href="/tools/rese/dashboard/assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/tools/rese/dashboard/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link href="/tools/rese/dashboard/assets/js/fullcalendar/bootstrap-fullcalendar.css" rel="stylesheet" />
    <link href="/css/fullCalendar/fullcalendar.min.css" rel="stylesheet">
        
    <!-- Custom styles for this template -->
    <link href="/tools/rese/dashboard/assets/css/style.css" rel="stylesheet">
    <link href="/tools/rese/dashboard/assets/css/style-responsive.css" rel="stylesheet">
    <%@ include file="/tools/rese/dashboard/common/importCss.jsp"%>
    <link rel="stylesheet" href="/css/pickadate/default.css">
	<link rel="stylesheet" href="/css/pickadate/default.date.css">
 
	
	<script src="/tools/rese/dashboard/assets/js/jquery.js"></script>
    <%@ include file="/tools/rese/dashboard/common/reserveListJs.jsp"%>
    <script src="/js/jq/jquery.min.js"></script>
    
    <!-- カレンダー入力 -->
    <script src="/js/fullCalendar/moment.min.js" type="text/javascript"></script>

	<script src="/js/pickadate/picker.js"></script>
	<script src="/js/pickadate/picker.date.js"></script>
	<script src="/js/pickadate/legacy.js"></script>
	<script src="/js/pickadate/ja_JP.js"></script>
	
	
	<script src="/js/jq/jquery.validationEngine.js"></script>
	<script src="/js/jq/jquery.validationEngine-ja.js"></script>
	<link rel="stylesheet" href="/css/jq/validationEngine.jquery.css">
	
    <style type="text/css">
    .fc-toolbar{
    	height: 30px;
    }
    .fc-center h2{
    	color:#000;
    	font-size: 1.5em;
    }
	.formMinTitle {
		padding: .5em .75em;
		background-color: #f6f6f6;
		border-left: 6px solid #ccc;
		margin-top: 20px;
	}
    </style>
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
	 
	 //顧客情報をセットします。
	 function setCustomerInfo(){
		 console.log($('[name=customerKey]').val());
		 $.post("/tools/rese/reserve/GetCustomerInfo", {
		 		'customerKey' : $('[name=customerKey]').val()
		 	}, function(data){
		 		console.log(data);
		 		switch(data.obj){
		 		case null:
					
					$('[name=customerName]').attr("value", "");
					$('[name=customerName]').prop("disabled", "");
					$('[name=customerName]').css("background-color", "#fff");
					
					$('[name=customerMailaddress]').attr("value", "");
					$('[name=customerMailaddress]').prop("disabled", "");
					$('[name=customerMailaddress]').css("background-color", "#fff");
					
					$('[name=customerPhone]').attr("value", "");
					$('[name=customerPhone]').prop("disabled", "");
					$('[name=customerPhone]').css("background-color", "#fff");
		 			break;
		
		 		default:
					$('[name=customerName]').attr("value", data.obj.name);
					$('[name=customerName]').attr("disabled", "disabled");
					$('[name=customerName]').css("background-color", "#dedede");
					$('[name=customerName]').css("background-color", "#dedede");
					
					$('[name=customerMailaddress]').attr("value", data.obj.mailaddress);
					$('[name=customerMailaddress]').attr("disabled", "disabled");
					$('[name=customerMailaddress]').css("background-color", "#dedede");
					
					$('[name=customerPhone]').attr("value", data.obj.phone);
					$('[name=customerPhone]').attr("disabled", "disabled");
					$('[name=customerPhone]').css("background-color", "#dedede");
		 			
		 			break;
		 		}
		 	}, 'json');
	 }
	</script>
  </head>

  <body>

  <section id="container" >
     <%@ include file="/tools/rese/dashboard/common/topBar.jsp"%>
     <!-- header end -->
      
     <%@ include file="/tools/rese/dashboard/common/sideMenu.jsp"%>

      <!--main content start-->
      <section id="main-content">
          <section class="wrapper">
          	<h3><i class="fa fa-angle-right"></i> 予約の記入</h3>
          	<p>上から順番に記入することで、空き時間を自動で算出します。</p>
			<hr>
              <!-- page start-->
              <div class="row mt">
                  <aside class="col-lg-9 mt">
                      <section class="panel">
                          <div class="panel-body" style="">
		                      <form action="/tools/rese/reserve/doneCreateReserve" name="selectMenu" id="form_1" method="post">
								<h5 class="formMinTitle">予約ページ</h5>
								<select name="menuPage">
									<option value="">選択してください。
									<c:forEach var="menuPage" items="${menuPageList}">
										<option value="${f:h(menuPage.key)}">${menuPage.pageTitle}
								    </c:forEach>
								</select>
								
								<h5 class="formMinTitle">メニュー</h5>
							    <p>メニューを選択してください。</p>
						    	<select name="menuKey" id="menuSelect" onchange="ableSelectDate();" class="validate[required]">
						    		
								</select>
									
								<h5 class="formMinTitle">日程を決める</h5>
							 	<input style="color:#000; cursor:pointer;width:200px; background-color: #dedede; padding: 10px;" id="calendar" type="text" placeholder="クリックしてください" onchange="calculate();" name="reserveDate" value="" disabled>
								<h5 class="formMinTitle">時間を決める</h5>
								<p>日程を選択すると、予約可能な時間が表示されます。</p>
								<select id="reserveMoments" name="reserveMoments" class="validate[required]">
								</select>
								
								<h5 class="formMinTitle">顧客情報</h5>
								<p>新規顧客を選択すると、お客様情報を記入することが出来ます。</p>
								<select name="customerKey" style="margin-bottom: 20px;" onchange="setCustomerInfo();">
									<c:if test="${customerList != null}">
										<option>選択してください。
										<option value="">新規顧客
										<c:forEach var="customer" items="${customerList}">
											<option value="${f:h(customer.key)}">${customer.name}
									    </c:forEach>
								    </c:if>
								</select>
								<br>
																
								<div>
								  <div class="well">
								    <h5>お名前</h5>
									<p>(例)田中太郎</p>
									<input type="text" name="customerName" class="validate[required]">
									<h5>メールアドレス</h5>
									<p>(例)sample@mail.com</p>
									<p>メールアドレスを登録しない場合、予約フォームからの予約においてリピーターとして保存することが出来ません。</p>
									<input type="text" name="customerMailaddress">
									<h5>電話番号</h5>
									<p>(例)000-0000-0000 「-」の記入を忘れないようにして下さい。</p>
									<input type="text" name="customerPhone">
								  </div>
								</div>
								<br />
								<input type="submit" id="submit" class="btn btn-warning" value="完了">
								
							</form>
						</div>
					</section>
                  </aside>
                  
                  <div class="col-lg-3 ds" style="margin-top: 25px;">
                  	<div style="background-color: #2C3E50;">
	                  	<p style="padding: 15px; color:#fff; text-align: center;">PROプラン(2016年3月頃スタート)</p>
                  	</div>
                  	<a href="http://px.a8.net/svt/ejp?a8mat=2C2h5W+85IQSA+2VOI+64RJ5" target="_blank">
					<img border="0" width="240" height="auto" alt="" src="http://www22.a8.net/svt/bgt?aid=141203264493&wid=002&eno=01&mid=s00000013437001030000&mc=1"></a>
					<img border="0" width="1" height="1" src="http://www16.a8.net/0.gif?a8mat=2C2h5W+85IQSA+2VOI+64RJ5" alt="">
                  </div><!-- /col-lg-3 -->
              </div>
              <!-- page end-->
		</section><! --/wrapper -->
      </section><!-- /MAIN CONTENT -->

      <!--main content end-->
      
      <!--footer start-->
      <%@ include file="/tools/rese/dashboard/common/footer.jsp"%>
      <!--footer end-->
  </section>

    <script src="/js/fullCalendar/moment.min.js" type="text/javascript"></script>
	<script src="/js/fullCalendar/fullcalendar.min.js" type="text/javascript"></script>
	<script src="/js/fullCalendar/ja.js" type="text/javascript"></script>
	<script src="/js/fullCalendar/gcal.js" type="text/javascript"></script>
	
    <script src="/tools/rese/dashboard/assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="/tools/rese/dashboard/assets/js/jquery.dcjqaccordion.2.7.js"></script>
	

    <!--common script for all pages-->
    <script src="/tools/rese/dashboard/assets/js/common-scripts.js"></script>


  </body>
</html>

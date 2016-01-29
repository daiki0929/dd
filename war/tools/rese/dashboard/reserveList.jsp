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
	
	<script src="/tools/rese/dashboard/assets/js/jquery.js"></script>
    <%@ include file="/tools/rese/dashboard/common/reserveListJs.jsp"%>
    
    <!-- カレンダー入力 -->
    <script src="/js/fullCalendar/moment.min.js" type="text/javascript"></script>
	<script src="/js/kalendae/kalendae.js"></script>
	<link href="/css/kalendae/kalendae.css" rel="stylesheet">

    <style type="text/css">
    .fc-toolbar{
    	height: 30px;
    }
    .fc-center h2{
    	color:#000;
    	font-size: 1.5em;
    }
    
    </style>
    <script type="text/javascript">
    	//予約を削除します。
    	function deleteReserve(reserveKey){
    		c = confirm("予約を削除してもよろしいですか？");
    	     if ( c == true ){
    	    	 var str = "/tools/rese/reserve/deleteReserve?id=" + reserveKey;
    	    	 location.href = str;
    	     }else{
    	     }  		
    	}
    	
    	$(document).ready(function (){
    		if(${limitOver}){
    			$('#createBtnURL h3').css("background-color", "#b2b2b2");	
    			$('#createBtnURL h3').css("border", "1px solid #b2b2b2");	
    			$('#createBtnURL').attr("href", "");	
    			$('#createBtnURL h3').css("cursor", "not-allowed");
    		}
    	})
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
          	<h3><i class="fa fa-angle-right"></i> 予約一覧</h3>
          	<c:if test="${role == 'FREE'}">
          		<p class="alertMsg">最大50件/月まで予約管理可能です。「今月の予約管理数」が50に到達すると、翌月になるまで予約ページは利用出来ません。</p>
          	</c:if>
          	<c:if test="${role == 'PRO'}">
          		<p class="alertMsg">最大200件/月まで予約管理可能です。「今月の予約管理数」が20に到達すると、翌月になるまで予約ページは利用出来ません。</p>
          	</c:if>
          	<c:if test="${limitOver}">
          		<p class="well"><span style="color: red;">※</span>予約管理管理数が最大に達しています。予約ページが利用出来ない状態です。PROプランにアップグレードするには<a href="">こちら</a>から。</p>
          	</c:if>
          	<p>今月の予約管理数：${reserveListSize}</p>
			<a href="/tools/rese/reserve/createReserve" id="createBtnURL"><h3 class="btn btn-warning" style="width: 200px;">予約を記入する</h3></a>
			<div style="margin-top: 20px;">
				<p><a href="/tools/rese/reserve/showHowTo">使い方ガイド</a>はこちら</p>
			</div>
			<hr>
              <!-- page start-->
              <div class="row mt">
                  <aside class="col-lg-9 mt">
                      <section class="panel">
                          <div class="panel-body" style="">
                              <div id="calendar" class="has-toolbar"></div>
                          </div>
                      </section>
                  </aside>
                  
                  <div class="col-lg-3 ds" style="margin-top: 25px;">
                  	<!-- <div style="background-color: #2C3E50;">
	                  	<p style="padding: 15px; color:#fff; text-align: center;">PROプラン(2016年3月頃スタート)</p>
                  	</div> -->
                  	<a href="http://px.a8.net/svt/ejp?a8mat=2C2H4W+85IQSA+2VOI+64RJ5" target="_blank">
					<img border="0" width="240" height="auto" alt="" src="http://www22.a8.net/svt/bgt?aid=141203264493&wid=002&eno=01&mid=s00000013437001030000&mc=1"></a>
					<img border="0" width="1" height="1" src="http://www16.a8.net/0.gif?a8mat=2C2H4W+85IQSA+2VOI+64RJ5" alt="">
                    <!--COMPLETED ACTIONS DONUTS CHART-->
					<h3>新着予約</h3>
					<c:forEach var="reserve" items="${reserveList}" begin="0" end="4">
						<p style="margin-bottom: 15px;"></p>
						<div class="desc">
							<div class="thumb">
								<span class="badge bg-theme"><i class="fa fa-clock-o"></i></span>
							</div>
							<div class="details">
								<p><muted><span class="newReserve">${reserve.noticeDate}</span></muted><br/>
								   <a href="/tools/rese/customerManage/customerDetail?id=${f:h(reserve.customerRef.key)}">${reserve.customerName}</a> 様から予約が入りました。
								</p>
							</div>
						</div>
					</c:forEach>
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


	<c:forEach var="reserve" items="${reserveList}">
		<!-- モーダル -->
		<div id='${f:h(reserve.key)}' class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header" style="background-color: #F2F2F2;">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			    <a href="/tools/rese/customerManage/customerDetail?id=${f:h(reserve.customerRef.key)}" class="modal-title" id="myModalLabel"><h4 style="color: #3A87AD;">${reserve.customerName} 様</h4></a>
		    	<p><img src="/img/mail.png" class="minTitleIcon"/>${reserve.customerMailaddress}</p>
		    	<p><img src="/img/phone.png" class="minTitleIcon"/>${reserve.customerPhone}</p>
		      </div>
		      <div class="modal-body">
		       <h5><img src="/img/menu.png" class="minTitleIcon"/>予約メニュー</h5>
			    <p>${reserve.menuTitle}</p>
			    <h5><img src="/img/calendar.png" class="minTitleIcon"/>予約日</h5>
			    <p><span class="reserveTime">${reserve.startTime}</span> 〜 <span class="reserveTime">${reserve.endTime}</span></p>
			    <!-- 編集フォーム -->
				<div class="accordion-group">
			    <div class="accordion-heading" style="border: 1px solid #dedede;">
			      <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href='#collapse${f:h(reserve.key)}' style="color:#3A87AD; padding-left: 5px; background-color: #f2f2f2;">
			        予約日時を変更する
			      </a>
			    </div>
			    <div id='collapse${f:h(reserve.key)}' class="accordion-body collapse" style="border: 1px solid #dedede;padding: 5px;">
			    <form action="/tools/rese/reserve/doneEditReserve">
			      <div class="accordion-inner">
			        <h5 style="margin: 10px 0;">予約日 ※必須</h5>
			        <input type="text" class="auto-kal" data-kal="format: 'YYYY/MM/DD'" name="reserveDate">
					<h5 style="margin-top: 10px;">開始時間 ※必須</h5>
					<select id="reserveMoments" name="reserveMoments">
						<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
					</select>
					<br/>
					<input type=hidden name="reserveKey" value='${f:h(reserve.key)}'>
			      	<input type="submit" value="完了" class="btn btn-warning" style="margin: 20px 0;">
			      </div>
			    </form>
			    </div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-danger" data-dismiss="modal" style="float: left;" onclick="deleteReserve('${f:h(reserve.key)}');">削除</button>
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		      </div>
		    </div>
		  </div>
		</div>
		</div>
	</c:forEach>
	
	
	
    <!-- js placed at the end of the document so the pages load faster -->
    <script src="/js/fullCalendar/moment.min.js" type="text/javascript"></script>
    <!-- <script src="/tools/rese/dashboard/assets/js/jquery.js"></script> -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- <script src="/tools/rese/dashboard/assets/js/jquery-ui-1.9.2.custom.min.js"></script> -->
    <script src="/js/fullCalendar/jquery-ui.custom.min.js" type="text/javascript"></script>
	<!-- <script src="/tools/rese/dashboard/assets/js/fullcalendar/fullcalendar.min.js"></script> -->
	<script src="/js/fullCalendar/fullcalendar.min.js" type="text/javascript"></script>
	<script src="/js/fullCalendar/ja.js" type="text/javascript"></script>
	<script src="/js/fullCalendar/gcal.js" type="text/javascript"></script>
	
    <script src="/tools/rese/dashboard/assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="/tools/rese/dashboard/assets/js/jquery.dcjqaccordion.2.7.js"></script>
	

    <!--common script for all pages-->
    <script src="/tools/rese/dashboard/assets/js/common-scripts.js"></script>


  </body>
</html>

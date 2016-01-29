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

    <title>営業時間の設定</title>

    <!-- Bootstrap core CSS -->
    <link href="/tools/rese/dashboard/assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/tools/rese/dashboard/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link href="/tools/rese/dashboard/assets/js/fancybox/jquery.fancybox.css" rel="stylesheet" />
    <!-- Custom styles for this template -->
    <link href="/tools/rese/dashboard/assets/css/style.css" rel="stylesheet">
    <link href="/tools/rese/dashboard/assets/css/style-responsive.css" rel="stylesheet">
    
    <%@ include file="/tools/rese/dashboard/common/importCss.jsp"%>

    <script src="/tools/rese/dashboard/assets/js/jquery.js"></script>
    
    <style type="text/css">
    .radio{
    	margin-left: 20px;
    }
    .daysOfWeek{
    	 color: #fff;
    	 width: 200px;
    	 padding: 10px;
    	 background-color: #424A5D;
    }
    .startHour{
    	font-weight: bold;
    }
    .endHour{
    	margin-top: 10px;
    	font-weight: bold;
    }
    </style>
    <script type="text/javascript" charset="utf-8">
		<%@ include file="/js/tools/rese/shopStatus.js"%>
	</script>
  </head>

  <body>

  <section id="container" >
      <%@ include file="/tools/rese/dashboard/common/topBar.jsp"%>
  
      <!--header end-->
      
      <%@ include file="/tools/rese/dashboard/common/sideMenu.jsp"%>
      
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper site-min-height">
          	<h3><i class="fa fa-angle-right"></i> 営業日時の設定</h3>
          	<p>予約フォームの空き時間自動算出に利用されます。</p>
          	<hr>
			<!-- SORTABLE TO DO LIST -->
              <div class="row mt mb">
                  <div class="col-md-12">
                  	<div class="col-md-12">
						<!-- 日曜日 -->
						<form name="sunday">
							<h5 class="daysOfWeek">日曜日</h5>
							<label class="radio"> <input type="radio"
								name="sundayShopStatus" value="open"
								onchange="postShopStatus('sunday','open')"> 営業日
							</label> <label class="radio"> <input type="radio"
								name="sundayShopStatus" value="notOpen"
								onchange="postShopStatus('sunday','notOpen')"> 非営業日
							</label> <br />
							<p class="startHour">営業開始時刻</p>
							<select name="startTime" onchange="postOperationHours('sunday')">
								<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
							</select>
							<p class="endHour">営業終了時刻</p>
							<select name="endTime" onchange="postOperationHours('sunday')">
								<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
							</select>
						</form>
			
						<!-- 月曜日 -->
						<form name="monday">
							<h5 class="daysOfWeek">月曜日</h5>
							<label class="radio"> <input type="radio"
								name="mondayShopStatus" value="open"
								onchange="postShopStatus('monday','open')"> 営業日
							</label> <label class="radio"> <input type="radio"
								name="mondayShopStatus" value="notOpen"
								onchange="postShopStatus('monday','notOpen')"> 非営業日
							</label> <br />
							<p class="startHour">営業開始時刻</p>
							<select name="startTime" onchange="postOperationHours('monday')">
								<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
							</select>
							<p class="endHour">営業終了時刻</p>
							<select name="endTime" onchange="postOperationHours('monday')">
								<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
							</select>
						</form>
			
						<!-- 火曜日 -->
						<form name="tuesday">
							<h5 class="daysOfWeek">火曜日</h5>
							<label class="radio"> <input type="radio"
								name="tuesdayShopStatus" value="open"
								onchange="postShopStatus('tuesday','open')"> 営業日
							</label> <label class="radio"> <input type="radio"
								name="tuesdayShopStatus" value="notOpen"
								onchange="postShopStatus('tuesday','notOpen')"> 非営業日
							</label> <br />
							<p class="startHour">営業開始時刻</p>
							<select name="startTime" onchange="postOperationHours('tuesday')">
								<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
							</select>
							<p class="endHour">営業終了時刻</p>
							<select name="endTime" onchange="postOperationHours('tuesday')">
								<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
							</select>
						</form>
			
						<!-- 水曜日 -->
						<form name="wednesday">
							<h5 class="daysOfWeek">水曜日</h5>
							<label class="radio"> <input type="radio"
								name="wednesdayShopStatus" value="open"
								onchange="postShopStatus('wednesday','open')"> 営業日
							</label> <label class="radio"> <input type="radio"
								name="wednesdayShopStatus" value="notOpen"
								onchange="postShopStatus('wednesday','notOpen')"> 非営業日
							</label> <br />
							<p class="startHour">営業開始時刻</p>
							<select name="startTime" onchange="postOperationHours('wednesday')">
								<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
							</select>
							<p class="endHour">営業終了時刻</p>
							<select name="endTime" onchange="postOperationHours('wednesday')">
								<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
							</select>
						</form>
			
						<!-- 木曜日 -->
						<form name="thursday">
							<h5 class="daysOfWeek">木曜日</h5>
							<label class="radio"> <input type="radio"
								name="thursdayShopStatus" value="open"
								onchange="postShopStatus('thursday','open')"> 営業日
							</label> <label class="radio"> <input type="radio"
								name="thursdayShopStatus" value="notOpen"
								onchange="postShopStatus('thursday','notOpen')"> 非営業日
							</label> <br />
							<p class="startHour">営業開始時刻</p>
							<select name="startTime" onchange="postOperationHours('thursday')">
								<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
							</select>
							<p class="endHour">営業終了時刻</p>
							<select name="endTime" onchange="postOperationHours('thursday')">
								<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
							</select>
						</form>
			
						<!-- 金曜日 -->
						<form name="friday">
							<h5 class="daysOfWeek">金曜日</h5>
							<label class="radio"> <input type="radio"
								name="fridayShopStatus" value="open"
								onchange="postShopStatus('friday','open')"> 営業日
							</label> <label class="radio"> <input type="radio"
								name="fridayShopStatus" value="notOpen"
								onchange="postShopStatus('friday','notOpen')"> 非営業日
							</label> <br />
							<p class="startHour">営業開始時刻</p>
							<select name="startTime" onchange="postOperationHours('friday')">
								<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
							</select>
							<p class="endHour">営業終了時刻</p>
							<select name="endTime" onchange="postOperationHours('friday')">
								<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
							</select>
						</form>
			
						<!-- 土曜日 -->
						<form name="saturday">
							<h5 class="daysOfWeek">土曜日</h5>
							<label class="radio"> <input type="radio"
								name="saturdayShopStatus" value="open"
								onchange="postShopStatus('saturday','open')"> 営業日
							</label> <label class="radio"> <input type="radio"
								name="saturdayShopStatus" value="notOpen"
								onchange="postShopStatus('saturday','notOpen')"> 非営業日
							</label> <br />
							<p class="startHour">営業開始時刻</p>
							<select name="startTime" onchange="postOperationHours('saturday')">
								<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
							</select>
							<p class="endHour">営業終了時刻</p>
							<select name="endTime" onchange="postOperationHours('saturday')">
								<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
							</select>
						</form>
					</div>
                      
                  </div><!--/col-md-12 -->
              </div><!-- /row -->
      <!--main content end-->
      
 </section>
      <%@ include file="/tools/rese/dashboard/common/footer.jsp"%>

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="/js/fullCalendar/moment.min.js" type="text/javascript"></script>
	<script src="/tools/rese/dashboard/assets/js/fancybox/jquery.fancybox.js"></script>    
    <script src="/tools/rese/dashboard/assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="/tools/rese/dashboard/assets/js/jquery.dcjqaccordion.2.7.js"></script>
    <script src="/tools/rese/dashboard/assets/js/jquery.scrollTo.min.js"></script>
    <script src="/tools/rese/dashboard/assets/js/jquery.nicescroll.js" type="text/javascript"></script>


    <!--common script for all pages-->
    <script src="/tools/rese/dashboard/assets/js/common-scripts.js"></script>

    <!--script for this page-->
  
  <script type="text/javascript">
      $(function() {
        //    fancybox
          jQuery(".fancybox").fancybox();
      });

  </script>
  
  <script>
      //custom select box

      $(function(){
          $("select.styled").customSelect();
      });

  </script>

  </body>
</html>

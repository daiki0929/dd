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

    <title>予約ページ作成</title>

    <!-- Bootstrap core CSS -->
    <link href="/tools/rese/dashboard/assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/tools/rese/dashboard/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link href="/tools/rese/dashboard/assets/js/fullcalendar/bootstrap-fullcalendar.css" rel="stylesheet" />
    <link href="/css/fullCalendar/fullcalendar.min.css" rel="stylesheet">
        
    <!-- Custom styles for this template -->
    <link href="/tools/rese/dashboard/assets/css/style.css" rel="stylesheet">
    <link href="/tools/rese/dashboard/assets/css/style-responsive.css" rel="stylesheet">
	
	<script src="/tools/rese/dashboard/assets/js/jquery.js"></script>
	<style type="text/css">
	.link{
		width: 35%;
		min-width: 210px;;
		display: inline-block;
		padding: 5px 15px;
		margin-right: 30px;
		margin-left: 10px;
		margin-bottom: 10px;
		background-color: #f8f8f8;
	}
	.radio{
		padding-left: 20px;
	}
	.formMinTitle {
		padding: .5em .75em;
		background-color: #f6f6f6;
		border-left: 6px solid #ccc;
		margin-top: 20px;
	}
		
	</style>
	<script type="text/javascript">
	$(document).ready(function(){
		$('#myTab a').click(function (e) {
			  e.preventDefault();
			  $(this).tab('show');
			});
	});
	
	/**
	 * メニューページにアクセスします。
	 */
	function accessMenuPage(menuPageURL){
		
		window.open(menuPageURL, '_blank');
	}
	
	</script>
  </head>

  <body>

  <section id="container" >
     <%@ include file="/tools/rese/dashboard/common/topBar.jsp"%>
     <!-- header end -->
      
     <%@ include file="/tools/rese/dashboard/common/sideMenu.jsp"%>

      <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper">
          	<h3><i class="fa fa-angle-right"></i> 予約ページの作成</h3>
          	<hr>
              <!-- page start-->
              <div class="row mt">
                  <aside class="col-lg-9 mt">
                      <section class="panel">
                          <div class="panel-body" style="">
                          	<form action="/tools/rese/reserve/doneCreateMenuPage" id="entryForm" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
								<h5 class="formMinTitle">メニューページのタイトル</h5>
								<input type="text" name="pageTitle" style="width: 80%; padding: 10px 10px 10px 5px;">
								<h5 class="formMinTitle">説明文</h5>
								<textarea rows="3" name="description" class="textForm" style="width: 80%;"></textarea>
								<%-- <p>トップ画像</p>
								<p>※有料会員限定です。</p>
								<!-- <input type="file" name="topImgPath"> -->
								<input name="topImgPath" type="file" enctype="multipart/form-data" value="${menuPage.topImgPath}" onchange="startUpload();" disabled="disabled">
								<input id="uploadUrl" type="hidden" /> --%>
								<h5 class="formMinTitle">予約形態</h5>
								<p>先着順：予約ページから予約した時点で、予約確定となります。</p>
								<label class="radio"> <input type="radio" name="reserveSystem" value="firstArrival" checked> 先着順
								</label> <label class="radio"> <input type="radio" name="reserveSystem" value="recognition" disabled="disabled"> 承認制(※有料会員限定)
								</label>
								<h5 class="formMinTitle">公開設定</h5>
								<p>※非公開のメニューページは毎週日曜日に自動で削除されます。</p>
								<label class="radio"> <input type="radio" name="status" value="public" checked> 公開
								</label> <label class="radio"> <input type="radio" name="status" value="closed"> 非公開
								</label>
								<h5 class="formMinTitle">何分ごとに予約を受け付けますか？</h5>
								<small>例えば、10時から営業開始で、15分毎の場合、お客様が予約できる時間は10:00、10:15、10:30、10:45 ... となります。30分毎の場合は、10:00、10:30、11:00、11:30 ... となります。</small>
								<br/>
								<select name="reserveInterval">
									<option value="900">15分ごと</option>
									<option value="1800">30分ごと</option>
									<option value="3600">60分ごと</option>
									<option value="7200">120分ごと</option>
								</select>
								<h5 class="formMinTitle">受付開始</h5>
								<p>受付を開始する期間を設定します。</p>
								<select name="reserveStartTime">
									<option value="0">設定しない</option>
									<option value="604800">7日前から</option>
									<option value="1209600">14日前から</option>
									<option value="1814400">21日前から</option>
									<option value="2592000">30日前から</option>
									<option value="5184000">60日前から</option>
									<option value="7776000">90日前から</option>
								</select>
								<h5 class="formMinTitle">受付終了</h5>
								<p>受付を終了する締め切り時間を設定します。</p>
								<select name="reserveEndTime">
									<option value="0">設定しない</option>
									<option value="3600">1時間前まで</option>
									<option value="7200">2時間前まで</option>
									<option value="10800">3時間前まで</option>
									<option value="14400">4時間前まで</option>
									<option value="18000">5時間前まで</option>
									<option value="21600">6時間前まで</option>
									<option value="25200">7時間前まで</option>
									<option value="28800">8時間前まで</option>
									<option value="32400">9時間前まで</option>
									<option value="36000">10時間前まで</option>
									<option value="39600">11時間前まで</option>
									<option value="43200">12時間前まで</option>
									<option value="86400">1日前まで</option>
									<option value="172800">2日前まで</option>
									<option value="259200">3日前まで</option>
									<option value="345600">4日前まで</option>
									<option value="432000">5日前まで</option>
									<option value="518400">6日前まで</option>
									<option value="604800">7日前まで</option>
								</select>
								<h5 class="formMinTitle">キャンセル期間</h5>
								<p>キャンセル可能な締め切り時間を設定します。キャンセル期間を過ぎると、お電話かメールでキャンセルするようお客様へお伝えします。</p>
								<select name="cancelTime">
									<option value="0">設定しない</option>
									<option value="3600">1時間前まで</option>
									<option value="7200">2時間前まで</option>
									<option value="10800">3時間前まで</option>
									<option value="14400">4時間前まで</option>
									<option value="18000">5時間前まで</option>
									<option value="21600">6時間前まで</option>
									<option value="25200">7時間前まで</option>
									<option value="28800">8時間前まで</option>
									<option value="32400">9時間前まで</option>
									<option value="36000">10時間前まで</option>
									<option value="39600">11時間前まで</option>
									<option value="43200">12時間前まで</option>
									<option value="86400">1日前まで</option>
									<option value="172800">2日前まで</option>
									<option value="259200">3日前まで</option>
									<option value="345600">4日前まで</option>
									<option value="432000">5日前まで</option>
									<option value="518400">6日前まで</option>
									<option value="604800">7日前まで</option>
								</select>
								<h5 class="formMinTitle">休み設定</h5>
								<p>定休日以外に、メニューページごとで休みを設定出来ます。(※有料会員限定)</p>
								<input type="hidden" name="noReserveDate" value="">
								<%-- <br />
								<input type="text" id="datepicker" name="noReserveDate" value="${menuPage.noReserveDate}">
								<br />
								<a id="button" href="javascript:;">カレンダーから選択する。</a>
				 					<div id="pochipochi"></div>
								<br /> --%>
								<input type="hidden" name="id" value="${f:h(menuPage.key)}">
								<input type="submit" value="完了" class="btn btn-warning" style="width: 200px;">
							</form>
                          </div>
                      </section>
                  </aside>
                  <%@ include file="/tools/rese/dashboard/common/rightSide.jsp"%>
              </div>
              <!-- page end-->
		</section><!-- /wrapper -->
      </section><!-- /MAIN CONTENT -->

      <!--main content end-->
	  <!--footer start-->
	  <%@ include file="/tools/rese/dashboard/common/footer.jsp"%>
	  <!--footer end-->
      
  </section>

    <script src="/tools/rese/dashboard/assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="/tools/rese/dashboard/assets/js/jquery.dcjqaccordion.2.7.js"></script>


    <!--common script for all pages-->
    <script src="/tools/rese/dashboard/assets/js/common-scripts.js"></script>

  </body>
</html>

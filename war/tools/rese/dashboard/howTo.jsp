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

    <title>Rese使い方ガイド</title>

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
    h5{
    	font-weight: bold;
    }
    .shadow {
	    margin-top: 5px; /* 画像の上の余白*/
	    display: inline-block; 
	    box-shadow: 0px 5px 15px -5px rgba(0, 0, 0, 0.8); /*横方向　下方向　ぼかし　広がり　色 */
	    width: 100%;
	    height: auto;
	}
	.minTitle {
		padding: .5em .75em;
		background-color: #f6f6f6;
		border-left: 6px solid #ccc;
		margin-top: 20px;
	}
    </style>
  </head>

  <body>

  <section id="container" >
      <%@ include file="/tools/rese/dashboard/common/topBar.jsp"%>
  
      <!--header end-->
      
      <%@ include file="/tools/rese/dashboard/common/sideMenu.jsp"%>
      
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper site-min-height">
          	<h3><i class="fa fa-angle-right"></i> アカウント情報</h3>
          	<hr>
              <div class="row mt mb">
                  <div class="col-md-12">
                  <section class="panel">
                  <div class="panel-body" style="">
						<h4>Rese使い方ガイド</h4>
						<h5 class="minTitle">１．営業時間を設定しよう</h5>
						<p>初期設定では00:00~23:59となっています。曜日ごとに営業時間を設定しましょう。(メニューの営業時間から編集)</p>
						<img alt="営業時間編集" src="/img/rese/editOperationHour.png" class="shadow">
						<h5 class="minTitle">２．予約ページを作成しよう</h5>
						<p>現在の空き時間を自動算出してくれる予約ページを作成しましょう。(メニューの予約ページから作成)</p>
						<img alt="予約ページ作成" src="/img/rese/createMenuPage.png" class="shadow">
						<h5 class="minTitle">３．メニューを作成しよう</h5>
						<p>予約ページを作成した後に、メニュー管理ボタンからメニューを作成しましょう。(メニューの予約ページから作成)</p>
						<img alt="メニュー作成" src="/img/rese/createMenu.png" class="shadow">
						<h5 class="minTitle">４．予約ページをシェアしよう</h5>
						<p>予約ページのリンクをSNSでシェア！サイトに埋め込むことも出来ます。(メニューの予約ページから作成)</p>
						<p>リンクアイコンをクリックすると、別タブで予約ページを開きます。</p>
						<img alt="メニュー作成" src="/img/rese/sharePage.png" class="shadow">
				</div>
				</section>
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
    <!-- <script src="/tools/rese/dashboard/assets/js/jquery.scrollTo.min.js"></script>
    <script src="/tools/rese/dashboard/assets/js/jquery.nicescroll.js" type="text/javascript"></script> -->


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

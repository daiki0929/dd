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
    h5{
    	font-weight: bold;
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
                  	<form action="/tools/rese/doneEditAcount">
						<h5>名前</h5>
						<input type="text" name="name" value="${msUser.name}">
						<h5>メールアドレス</h5>
						<input type="text" name="mailaddress" value="${msUser.mailaddress}">
						<!-- <a href="/tools/rese/editMailaddress">変更する</a> -->
						<h5>電話番号</h5>
						<p>店舗の電話番号として表示されます。</p>
						<input type="text" name="phone" value="${msUser.phone}">
						<h5>住所</h5>
						<p>店舗の住所として表示されます。</p>
						<input type="text" name="address" value="${msUser.address}"><br/><br/>
						<input type="submit" value="更新" class="btn btn-primary">
					</form>
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

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

    <title>エラーページ</title>

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
          <p style="color: red; margin-top: 20px;">エラーが発生しました。</p>
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

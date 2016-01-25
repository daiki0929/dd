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

    <title>メニュー編集</title>

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
	<script type="text/javascript">
		$(document).ready(function() {
			var length = parseInt(document.getElementsByTagName("option").length);
			/* 時間 */
			for (var count = 0; count < length; count++){
				if($('select[name=time] option')[count].value == "${menu.time}"){
					var selectedItem = $('option')[count];
					selectedItem.setAttribute("selected", "true");
					break;
				　　　}
			}
		});
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
          	<h3><i class="fa fa-angle-right"></i> メニュー編集</h3>
          	<hr>
              <!-- page start-->
              <div class="row mt">
                  <aside class="col-lg-9 mt">
                      <section class="panel">
                          <div class="panel-body" style="">
                          	<form action="/tools/rese/reserve/doneEditMenu" method="post"
								accept-charset="UTF-8" enctype="multipart/form-data">
								<p style="margin-top: 10px;">メニューの名前</p>
								<input type="text" name="menuTitle" value="${menu.title}" style="width: 80%; padding: 10px 10px 10px 5px;">
								<p style="margin-top: 10px;">時間</p>
								<select name="time">
									<option value="1800">30 分</option>
									<option value="2400">40 分</option>
									<option value="2700">45 分</option>
									<option value="3600">60 分</option>
									<option value="4500">75 分</option>
									<option value="5400">90 分</option>
									<option value="7200">120 分</option>
									<option value="9000">150 分</option>
									<option value="10800">180 分</option>
									<option value="14400">240 分</option>
									<option value="18000">300 分</option>
									<option value="21600">360 分</option>
									<option value="25200">420 分</option>
								</select>
								<p style="margin-top: 10px;">料金</p>
								<p>カンマ無し・半角数字でご記入ください。</p>
								<input type="text" name="price" value="${menu.price}">円
								<p style="margin-top: 10px;">メニューの内容(600字以内)</p>
								<%-- <input type="text" name="content" value="${menu.content}"> --%>
								<textarea rows="3" name="content" class="textForm" style="width: 80%;">${menu.content}</textarea>
								<!-- <p>画像</p>
								<p>※有料会員限定</p>
								<input type="file" name="imgPath" disabled="disabled"> -->
								<input type="hidden" name="menuKey" value="${f:h(menu.key)}">
								<br />
								<input type="submit" value="更新" class="btn btn-warning">
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

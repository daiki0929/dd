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

    <title>予約ページ</title>

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
	.menuPageTitle{
		 color:#63A7E4;
		 font-weight: bold;
	}
	.menuPageTitle:hover{
		color:#508bbf; 
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
          	<h3><i class="fa fa-angle-right"></i> 予約ページ</h3>
          	<p>予約ページの一覧です。</p>
          	<a href="/tools/rese/reserve/CreateMenuPage"><p class="btn btn-warning">新規作成</p></a>
          	<hr>
              <!-- page start-->
              <div class="row mt">
                  <aside class="col-lg-9 mt">
                      <section class="panel">
                          <div class="panel-body" style="">
                          	<ul class="nav nav-tabs" id="myTab">
							  <li class="active"><a href="#public">公開</a></li>
							  <li><a href="#closed">非公開</a></li>
							</ul>
							
							<div class="tab-content">
							  <div class="tab-pane active" id="public">
							  	<c:forEach var="menuPage" items="${openMenuPageList}">
								  	<div style="width: 99%; border:1px solid #dedede; margin: 20px 0 20px 0;">
										<p style="background-color: #f2f2f2; padding: 10px 0 10px 10px;">
											<a href="http://localhost:8888/r/${msUser.userPath}/${menuPage.pagePath}" class="menuPageTitle" target="_blank">${menuPage.pageTitle}</a>
										</p>
										<!-- <img src="/img/link.png" style="float:left; padding: 0 5px 0 5px;" width="5%;"> -->
										<div class="link">
											<img alt="" src="/img/link2.png" width="28px;" style="cursor: pointer; margin-top: -2px;" onclick="accessMenuPage('http://localhost:8888/r/${msUser.userPath}/${menuPage.pagePath}');">
											<input type="text" value="http://localhost:8888/r/${msUser.userPath}/${menuPage.pagePath}" readonly="readonly" onclick="this.select()" style="padding: 15px 10px 15px 10px; margin-top:5px; cursor: default;"/>
										</div>
										<div style="margin-left: 10px; display: inline-block;">
											<p class="buttonMin-gray" style="display: inline-block; margin-right: 10px;">
												<a href="/tools/rese/reserve/editMenuPage?id=${f:h(menuPage.key)}" class="btn btn-default">メニューページ編集</a>
											</p>
											<p class="buttonMin-gray" style="display: inline-block; margin-right: 10px;">
												<a href="/tools/rese/reserve/menuList?id=${f:h(menuPage.key)}" class="btn btn-default">メニュー管理</a>
											</p>
										</div>
									</div>
								</c:forEach>
							  </div>
							  <div class="tab-pane" id="closed">
							  <br/>
							  <p>※非公開のメニューページは毎週日曜日に自動で削除されます。</p>
							  	<c:forEach var="menuPage" items="${closedMenuPageList}">
									<div style="width: 99%; border:1px solid #dedede; margin: 20px 0 20px 0;">
										<p style="background-color: #f2f2f2; padding: 10px 0 10px 10px;">
											<a href="http://localhost:8888/r/${msUser.userPath}/${menuPage.pagePath}" class="menuPageTitle" target="_blank">${menuPage.pageTitle}</a>
										</p>
										<!-- <img src="/img/link.png" style="float:left; padding: 0 5px 0 5px;" width="5%;"> -->
										<div class="link">
											<img alt="" src="/img/link2.png" width="28px;" style="cursor: pointer; margin-top: -2px;" onclick="accessMenuPage('http://localhost:8888/r/${msUser.userPath}/${menuPage.pagePath}');">
											<input type="text" value="http://localhost:8888/r/${msUser.userPath}/${menuPage.pagePath}" readonly="readonly" onclick="this.select()" style="padding: 15px 10px 15px 10px; margin-top:5px; cursor: default;"/>
										</div>
										<div style="margin-left: 10px; display: inline-block;">
											<p class="buttonMin-gray" style="display: inline-block; margin-right: 10px;">
												<a href="/tools/rese/reserve/editMenuPage?id=${f:h(menuPage.key)}" class="btn btn-default">メニューページ編集</a>
											</p>
											<p class="buttonMin-gray" style="display: inline-block; margin-right: 10px;">
												<a href="/tools/rese/reserve/menuList?id=${f:h(menuPage.key)}" class="btn btn-default">メニュー管理</a>
											</p>
										</div>
									</div>
								</c:forEach>
							  </div>
							</div>
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

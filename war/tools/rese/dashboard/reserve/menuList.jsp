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

    <title>メニュー</title>

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
	
	<%@ include file="/tools/rese/common/actionMenuJs.jsp"%>
	
	<style type="text/css">
	.textForm{
		width: 80%;
		padding: 10px 10px 10px 10px;
	}
	</style>
	<script type="text/javascript">
	$(document).ready(function() {
		//メニューの時間を分にして、切り捨てをする。
		$(".menuTime").each(function(i, elem) {
			$(".menuTime")[i].innerText = Math.floor($(elem).text());
		});
		//非公開メニューのURLをセット
		$(".closedMenuURL").attr("href" , location.pathname + '?id=${f:h(menuPageKey)}' + "&s=closed");
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
          	<h3><i class="fa fa-angle-right"></i> メニュー</h3>
          	<p>メニューの一覧です。</p>
          	<hr>
              <!-- page start-->
              <div class="row mt">
                  <aside class="col-lg-9 mt">
                      <section class="panel">
                          <div class="panel-body" style="">
							<h3>新規作成</h3>
							<form name="form" method="post" id="formId" enctype="multipart/form-data">
							<h5>メニューの名前</h5>
							<input type="text" name="title" value="${menu.title}" style="width: 80%; padding: 10px 10px 10px 5px;">
							<h5>時間</h5>
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
							<h5>料金</h5>
							<p>カンマ無し・半角数字でご記入ください。</p>
							<input type="text" name="price" placeholder="2000" style="width: 30%; padding: 10px 10px 10px 5px;">円
							<h5>メニューの内容(600字以内)</h5>
							<textarea rows="3" name="content" class="textForm">${menuPage.description}</textarea>
							<!-- <h5>画像</h5>
							<p>※有料会員限定</p>
							<input type="file" name="imgPath" disabled="disabled"> -->
							<input type="hidden" name="menuPageKey" value="${f:h(menuPageKey)}">
							<br />
							<p class="btn btn-warning" style="margin-top: 20px;" onclick="createMenu();">追加</p>
							</form>
							<hr>
							<c:if test="${publicMenuList != null}">
								<h4>公開のメニュー</h4>
								<p><a class="closedMenuURL">非公開メニューを見る</a></p>
								<div class="menuList">
									<c:forEach var="menu" items="${publicMenuList}">
										<div id="${f:h(menu.key)}" style="width: 100%; border: 1px solid #dedede; margin-bottom: 20px;">
											<p style="background-color: #f3f3f3; padding: 8px;">${menu.title}</p>
											<div style="padding: 0 10px 10px 10px;">
												<p>時間：<span class="menuTime">${menu.time/60}</span>分</p>
												<p>料金：${menu.price}円</p>
												<a href="/tools/rese/reserve/EditMenu?id=${f:h(menu.key)}" class="btn btn-primary" style="text-decoration: none;">編集する</a>
												<p onclick="closeMenu('${f:h(menu.key)}');" class="btn btn-danger">非公開にする</p>
											</div>
										</div>
									</c:forEach>
								</div>
							</c:if>	
							
							<c:if test="${closedMenuList != null}">
							<h4>非公開のメニューを見る</h4>
							<p><a href="/tools/rese/reserve/menuList?id=${f:h(menuPageKey)}">公開メニュー</a></p>
							<c:forEach var="menu" items="${closedMenuList}">
							<div id="${f:h(menu.key)}" style="width: 100%; border: 1px solid #dedede; margin-bottom: 20px;">
								<p style="background-color: #f3f3f3; padding: 8px;">メニュー名：${menu.title}</p>
								<div style="padding: 0 10px 10px 10px;">
									<p>時間：<span class="menuTime">${menu.time/60}</span>分</p>
									<p>料金：${menu.price}円</p>
									<a href="/tools/rese/reserve/EditMenu?id=${f:h(menu.key)}" class="btn btn-primary">編集する</a>
									<p onclick="doPublicMenu('${f:h(menu.key)}');" class="btn btn-info">公開にする</p>
								</div>
							</div>
							</c:forEach>
							</c:if>
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

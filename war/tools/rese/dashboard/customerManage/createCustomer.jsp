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

    <title>顧客作成</title>

    <!-- Bootstrap core CSS -->
    <link href="/tools/rese/dashboard/assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/tools/rese/dashboard/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link href="/tools/rese/dashboard/assets/js/fancybox/jquery.fancybox.css" rel="stylesheet" />
    <!-- Custom styles for this template -->
    <link href="/tools/rese/dashboard/assets/css/style.css" rel="stylesheet">
    <link href="/tools/rese/dashboard/assets/css/style-responsive.css" rel="stylesheet">

    <script src="/tools/rese/dashboard/assets/js/jquery.js"></script>
	    
	<script src="/js/jq/jquery.validationEngine.js"></script>
	<script src="/js/jq/jquery.validationEngine-ja.js"></script>
	<link rel="stylesheet" href="/css/jq/validationEngine.jquery.css">
    <style type="text/css">
    .customerName{
    	color: #83c3fc;
    }
    .customerName:hover{
    	opacity:0.8;
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
  </head>

  <body>

  <section id="container" >
      <%@ include file="/tools/rese/dashboard/common/topBar.jsp"%>
      <!--header end-->
      <%@ include file="/tools/rese/dashboard/common/sideMenu.jsp"%>
      
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper site-min-height">
          	<h3><i class="fa fa-angle-right"></i> 顧客管理</h3>
          	<p>顧客一覧です。予約ページから予約したお客様の情報は自動で追加されます。</p>
          	<button type="button" class="btn btn-round btn-primary" data-toggle="modal" data-target="#myModal">追加する</button>
          	<hr>
          	<!-- page start-->
              <div class="row mt">
                  <aside class="col-lg-9 mt">
                      <section class="panel">
                          <div class="panel-body" style="">
                          	<!-- エラーメッセージ -->
							<%@ include file="/tools/rese/common/error.jsp"%>
                          	<form action="/tools/rese/customerManage/doneRegistCustomer" method="post">
						      	<h5 class="formMinTitle">名前 <span style="color: red; font-size: 0.6em;">※必須</span></h5>
							    <p>(例)田中太郎</p>
								<input type="text" name="customerName">
								<!-- <h5 class="formMinTitle">年齢</h5>
								<p>(例)20</p>
								<input type="text" name="customerAge">歳 -->
								<h5 class="formMinTitle">住所</h5>
								<p>(例)大阪府大阪市中央区◯町目◯-◯</p>
								<input type="text" name="customerAddress">
							    <h5 class="formMinTitle">メールアドレス</h5>
								<input type="text" name="customerMailaddress">
							    <h5 class="formMinTitle">携帯番号</h5>
								<input type="text" name="customerPhone">
								<br/>
								<input type="submit" value="追加" class="btn btn-warning" style="width: 200px; margin: 20px 0;">
							</form>
                          </div>
                      </section>
                  </aside>
                  <%@ include file="/tools/rese/dashboard/common/rightSide.jsp"%>
              </div>
              <!-- page end-->
		</section><!-- wrapper -->
      </section><!-- /MAIN CONTENT -->
			  
      <!--main content end-->
      <%@ include file="/tools/rese/dashboard/common/footer.jsp"%>
      
  </section>

	<script src="/tools/rese/dashboard/assets/js/fancybox/jquery.fancybox.js"></script>    
    <script src="/tools/rese/dashboard/assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="/tools/rese/dashboard/assets/js/jquery.dcjqaccordion.2.7.js"></script>
 

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

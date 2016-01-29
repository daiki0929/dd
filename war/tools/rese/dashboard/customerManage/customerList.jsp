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

    <title>顧客管理</title>

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
    </style>
    <script type="text/javascript">
    $(document).ready(function (){
		if(${limitOver}){
			$('#createBtnURL').css("background-color", "#b2b2b2");	
			$('#createBtnURL').css("border", "1px solid #b2b2b2");	
			$('#createBtnURL').attr("href", "");	
			$('#createBtnURL').css("cursor", "not-allowed");
		}
	})
    </script>
  </head>

  <body>

  <section id="container" >
      <%@ include file="/tools/rese/dashboard/common/topBar.jsp"%>
  
      <!--header end-->
      
      <%@ include file="/tools/rese/dashboard/common/sideMenu.jsp"%>
      
      <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper site-min-height">
          	<h3><i class="fa fa-angle-right"></i> 顧客管理</h3>
          	<p>顧客一覧です。予約ページから予約したお客様の情報は自動で追加されます。</p>
          	<c:if test="${role == 'FREE'}">
         		<p class="alertMsg">最大100人まで管理可能です。最大に達すると自動・手動で追加出来ないのでご注意ください。</p>
          	</c:if>
          	<c:if test="${role == 'PRO'}">
          		<p class="alertMsg">最大500人まで予約管理可能です。最大に達すると自動・手動で追加出来ないのでご注意ください。</p>
          	</c:if>
			<c:if test="${limitOver}">
       			<p class="well"><span style="color: red;">※</span>顧客管理の制限数に達しているため追加出来ない状態です。PROプランにアップグレードするには<a href="">こちら</a>から。</p>
       		</c:if>
          	<a href="/tools/rese/customerManage/createCustomer" class="btn btn-round btn-primary" id="createBtnURL">追加する</a>
          	<hr>
				<div class="row mt">
					<c:forEach var="customer" items="${customerList}">
						<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 desc" style="margin-bottom: 20px;">
							<div class="project-wrapper">
			                    <div class="project">
									<div style="background-color: #fff;">
		                            	<p style="background-color: #2C3E50; padding: 10px;">
		                            		<a href="/tools/rese/customerManage/customerDetail?id=${f:h(customer.key)}" class="customerName">${customer.name}</a>
	                            		</p>
										<p style="padding: 0 0 5px 10px;">${customer.phone}</p>
										<p style="padding: 0 0 10px 10px;">${customer.mailaddress}</p>
									</div>
			                    </div>
			                </div>
						</div><!-- col-lg-4 -->
					</c:forEach>
				</div><!-- /row -->

		</section><! --/wrapper -->
      </section><!-- /MAIN CONTENT -->
      <!--main content end-->
      <%@ include file="/tools/rese/dashboard/common/footer.jsp"%>
      
  </section>

    <!-- js placed at the end of the document so the pages load faster -->
	<script src="/tools/rese/dashboard/assets/js/fancybox/jquery.fancybox.js"></script>    
    <script src="/tools/rese/dashboard/assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="/tools/rese/dashboard/assets/js/jquery.dcjqaccordion.2.7.js"></script>
    <!-- <script src="/tools/rese/dashboard/assets/js/jquery.scrollTo.min.js"></script>
    <script src="/tools/rese/dashboard/assets/js/jquery.nicescroll.js" type="text/javascript"></script>
 -->
 

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

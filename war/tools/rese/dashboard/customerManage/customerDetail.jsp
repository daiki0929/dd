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

    <title>顧客の詳細情報</title>

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
	table.company {
	    border-collapse: separate;
	    border-spacing: 0px 15px;
	}
	 
	table.company th,
	table.company td {
	    padding: 10px;
	}
	 
	table.company th {
	    background: #424A5D;
	    vertical-align: middle;
	    text-align: left;
	    width: 200px;
	    overflow: visible;
	    position: relative;
	    color: #fff;
	    font-weight: normal;
	    font-size: 15px;
	}
	 
	table.company td {
	    background: #fff;
	    width: 360px;
	    padding-left: 20px;
	}
	.customerForm{
		border: none;
		background-color: #f8f8f8;
	}
	</style>
    <script type="text/javascript">
    	//顧客を削除します。
    	function deleteCustomer(customerKey){
    		c = confirm("予約を削除してもよろしいですか？");
    	     if ( c == true ){
    	    	 var str = "/tools/rese/reserve/deleteCustomer?id=" + customerKey;
    	    	 location.href = str;
    	     }else{
    	     }  		
    	}
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
          	<h3><i class="fa fa-angle-right"></i> ${customer.name}様</h3>
          	<p>お客様の連絡情報、来店履歴を確認することが出来ます。</p>
			<a href="#myModal" role="button" data-toggle="modal"><img src="/img/pen.png" class="minTitleIcon"/>お客様情報を編集する</a>
          	<hr>
			<!-- SORTABLE TO DO LIST -->
              <div class="row mt mb">
                  <div class="col-md-12">
                      <table class="company">
					    <tbody>
					        <tr>
					            <th class="arrow_box">名前</th>
					            <td>${customer.name}</td>
					        </tr>
					        <tr>
					            <th class="arrow_box">メールアドレス</th>
					            <td>${customer.mailaddress}</td>
					        </tr>
					        <tr>
					            <th class="arrow_box">電話番号</th>
					            <td>${customer.phone}</td>
					        </tr>
					    </tbody>
					</table>
					<div style=" padding: 8px;">
    		   			<h4>来店履歴</h4>
    		   		</div>
					<div style="background-color: #fff; padding: 10px;">
						<c:forEach var="reserve" items="${reserveList}">
							<p style="border-bottom: 1px solid #dedede; padding-bottom: 10px;"><span class="reserveTime" style="margin-right: 20px;">${reserve.startTime}</span>${reserve.menuTitle}</p>
						</c:forEach>
					</div>
                  </div><!--/col-md-12 -->
              </div><!-- /row -->
			  
			  <form action="/tools/rese/customerManage/doneEditCustomer" method="post">
			  <div id='myModal' class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header" style="background-color: #424A5D;">
				        <h4 class="modal-title" id="myModalLabel">顧客情報の編集</h4>
				      </div>
				      <div class="modal-body"">
				      	<form action="/tools/rese/customerManage/doneEditCustomer">
					    	<p>名前</p>
						    <input type="text" name="name" value="${customer.name}">
					    	<p style="margin-top: 10px;">メールアドレス</p>
						    <input type="text" name="mailaddress" value="${customer.mailaddress}">
					    	<p style="margin-top: 10px;">電話番号</p>
						    <input type="text" name="phone" value="${customer.phone}">
						    <br/>
						    <input type="hidden" name="customerKey" value="${f:h(customer.key)}">
						    <input type="submit" value="完了" class="btn btn-warning" style="margin: 20px 0;">
					    </form>
				      <div class="modal-footer" style="margin-top: 20px;">
				      	<button type="button" class="btn btn-danger" data-dismiss="modal" style="float: left;" onclick="deleteCustomer('${f:h(customer.key)}');">削除</button>
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				      </div>
				    </div>
				  </div>
				</div>
			</div>
			</form>

      <!--main content end-->
      
 </section>
      <%@ include file="/tools/rese/dashboard/common/footer.jsp"%>

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="/js/fullCalendar/moment.min.js" type="text/javascript"></script>
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

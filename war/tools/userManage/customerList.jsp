<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
	<%-- META情報 --%>
	<%@ include file="/tools/userManage/common/meta.jsp" %>
	<title>Index</title>
	<!-- css -->
	<%@ include file="/tools/userManage/common/importCss.jsp" %>

	<%-- JSインポート --%>
	<%@ include file="/tools/userManage/common/importJs.jsp" %>
	<script type="text/javascript">
	   $(document).ready(function() {
		   
	      $("#my-menu").mmenu({
	    	  extensions: ["effect-menu-slide", "effect-listitems-slide"]
	      });
	   
	   $(".displayCheck").click(function () {
		   $(".userCheck").removeClass("hidden");
		 });	
	   
	   });
	</script>
</head>
<body>
<div id="container">
<%--        		<c:forEach var="customer" items="${userList}">
	       		<label class="checkbox inline">
				  <input type="checkbox" id="" value="${customer.key}"> ${customer.name}${customer.address}${customer.phone}${customer.mailaddress}
				</label>
			</c:forEach> --%>

    <header class="span12" id="header">
    	<a href="#my-menu" class="btn-flat">MENU</a>
    </header>
    <div id="content">
    	<div class="span12">
    		<a href="/tools/userManage/Logout"><button class="btn btn-warning" style="margin-top: 20px;">ログアウト</button></a>
    		<a href="/tools/userManage/reserve/MenuList"><button class="btn btn-info" style="margin-top: 20px;">全てのメニューページ</button></a>
    		<a href="/tools/userManage/reserve/CreateMenuPage"><button class="btn btn-info" style="margin-top: 20px;">メニューページ作成</button></a>
			<h3>お客様一覧</h3>
			<h4>グループ作成</h4>
			<button class="btn displayCheck">サンプルボタン</button>
			<form action="" method="post">
				<p>グループ名を記入して下さい。</p>
				<input type="text" name="groupName">
				<input type="submit">
			</form>
			<c:if test="${!empty groupList}">
					<c:forEach var="group" items="${groupList}">
								<p><a href="">${group.groupName}</a></p>
								<c:forEach var="customer" items="${userList}">
									<p><a href="">${customer.name}${customer.address}${customer.phone}${customer.mailaddress}</a></p>
								</c:forEach>
					</c:forEach>
			</c:if>
			<c:forEach var="customer" items="${customerList}">
				<a href="/tools/userManage/userDetail">
					<div class="span2" style="background-color: #f2f2f2;">
						<p>${customer.name}</p>
						<p>${customer.age}</p>
						<p>${customer.address}</p>
						<p>${customer.phone}</p>
						<p>${customer.mailaddress}</p>
					</div>
				</a>
				<label class="checkbox inline hidden userCheck" style="float: left;">
				  <input type="checkbox" id="" value="${customer.key}" name="useId"> 追加
				</label>
			</c:forEach>
		</div>
    </div>
    
	<c:if test="${!empty groupList}">
	    <nav id="my-menu">
	    	<!-- グループがある場合 -->
		    <c:forEach var="group" items="${groupList}">
			    <ul>
				    <li><a href="">${group.groupName}</a>
				    	<ul>
				    		<!-- ループで回す -->
				 	    	<li><a href="">お客様一覧</a>
				            	<ul>
				            		<!-- 客をループで回す -->
									<c:forEach var="customer" items="${userList}">
										<li><a href="">${customer.name}${customer.address}${customer.phone}${customer.mailaddress}</a></li>
									</c:forEach>
								</ul>
				            </li>
				    	</ul>
				   	</li>
		   		 </ul>
	   		 </c:forEach>
	    </nav>
	</c:if>
    
	<c:if test="${empty groupList}">
	    <nav id="my-menu">
	  		 <!-- グループが無い場合 -->
           	<ul>
				<div style="border-bottom: 1px solid gray; width: 100%;">
					<form action="" style="display: inline;"><input type="text" style="height:40px;" placeholder="グループA"></form>
					<p class="btn-flat" style="float: right; display: inline;">グループ作成</p>
				</div>
           		<!-- 客をループで回す -->
				<c:forEach var="customer" items="${userList}">
					<li><a href="">${customer.name}${customer.address}${customer.phone}${customer.mailaddress}</a></li>
				</c:forEach>
			</ul>
		 </nav>
	</c:if>
    
    <!-- エラーメッセージ -->
    <%@ include file="/tools/userManage/common/error.jsp" %>
    <div class="span12">
    <h3>新規追加</h3>
    	<form action="/tools/userManage/RegistDoneCustomer" method="post">
    	<p>名前</p>
    	<input type="text" name="name">
    	<p>年齢</p>
    	<input type="text" name="age">
    	<p>住所</p>
    	<input type="text" name="address">
    	<p>電話番号</p>
    	<input type="text" name="phone">
    	<p>メールアドレス</p>
    	<input type="text" name="mailaddress">
    	<br/>
    	<input type="submit" value="追加">
    	</form>
    </div>
</div>
</body>
</html>

<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/userManage/common/meta.jsp"%>
<title>Index</title>
<!-- css -->
<%@ include file="/tools/userManage/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/userManage/common/importJs.jsp"%>
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
	<%@ include file="/tools/userManage/common/topBar.jsp"%>
	<div id="container">
		<div id="content">
			<div class="span12">
				<h3>お客様一覧</h3>
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
		<!-- エラーメッセージ -->
		<%@ include file="/tools/userManage/common/error.jsp"%>
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
				<br />
				<input type="submit" value="追加">
			</form>
		</div>
	</div>
</body>
</html>

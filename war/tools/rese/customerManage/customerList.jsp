<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>Index</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<script type="text/javascript">
	   $(document).ready(function() {
		   
	      $("#my-menu").mmenu({
	    	  extensions: ["effect-menu-slide", "effect-listitems-slide"]
	      });
	   
		   $(".displayCheck").click(function () {
			   $(".userCheck").removeClass("hidden");
			 });	
		   
		   $('select[name=rebuildSelect] option').each(function(index, elem){
			   if($(elem).val() == '${f:h(menuPageKey)}'){
				   $(elem).attr("selected", "true");
				   return false;
			   }
		   })
	   });
	   
	   function filterCustomer(){
		   var menuPageKey = $('select[name=rebuildSelect]').val();
		   window.location.href = "/tools/rese/customerManage/customerList?id=" + menuPageKey;
	   }
	</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container">
		<div id="content" class="span12">
			<div class="span3" style="height: 500px; background-color: #f2f2f2;">
				<div style="margin-left: 10px;">
					<h5>顧客を検索する</h5>
					<form action="">
						<input type="text" placeholder="名前/メールアドレス/電話番号">
					</form>
					<h5>予約ページで絞り込む</h5>
					<select name="rebuildSelect" onchange="filterCustomer();">
						<option value="">指定なし
						<c:forEach var="menuPage" items="${menuPageList}">
							<option value="${f:h(menuPage.key)}">${menuPage.pageTitle}
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="span8">
				<a href="#myModal" role="button" class="btn" data-toggle="modal">追加する</a>
				<h3>お客様一覧</h3>
				<c:forEach var="customer" items="${customerList}">
					<a href="/tools/rese/customerManage/customerDetail?id=${f:h(customer.key)}">
						<div class="span2" style="background-color: #f2f2f2;">
							<p>${customer.name}</p>
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
		
		<div class="span12">
			<form action="/tools/rese/doneRegistCustomer" method="post">
			<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-header">
				    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				    <h3 id="myModalLabel">新規追加</h3>
				  </div>
				  <div class="modal-body">
				    <h5>名前</h5>
				    <p>(例)田中太郎</p>
					<input type="text" name="customerName">
					<h5>ふりがな</h5>
					<p>(例)たなかたろう</p>
					<input type="text" name="customerKanaName">
					<h5>年齢</h5>
					<p>(例)20</p>
					<input type="text" name="customerAge">歳
					<h5>性別</h5>
					<label class="radio">
					  <input type="radio" name="sex" id="" value="male">男性
					</label>
					<label class="radio">
					  <input type="radio" name="sex" id="" value="female">女性
					</label>
					<h5>住所</h5>
					<p>(例)大阪府大阪市中央区◯町目◯-◯</p>
					<input type="text" name="customerAddress">
				    <h5>メールアドレス</h5>
					<input type="text" name="customerMailaddress">
				    <h5>携帯番号</h5>
					<input type="text" name="customerPhone">
				  </div>
				  <div class="modal-footer">
				    <a><p data-dismiss="modal" aria-hidden="true" class="closeButton" style="float: left; margin-top: 10px; cursor: pointer;">☓ 閉じる</p></a>
				    <p class="btn btn-primary" onclick="registCustomer();">保存</p>
			 	 </div>
			  </div>
			</form>
		</div>
	</div>
</body>
</html>

<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>お客様一覧</title>
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
		   
			 //制限の確認
			if(${limitOver}){
				$('.reserveBtn').css("background-color", "#b2b2b2");	
				$('form').attr("action", "");
				$('.reserveBtn').css("cursor", "not-allowed");
			}
	   });
	   
	   function filterCustomer(){
		   var menuPageKey = $('select[name=rebuildSelect]').val();
		   window.location.href = "/tools/rese/customerManage/customerList?p=" + menuPageKey;
	   }
	</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container mainContent">
		<div id="content" class="span12">
			<div class="span8" style="border-right: 1px solid #dedede; margin-top: 20px;">
				<h3>顧客リスト</h3>
				<p>顧客のリストです。名前をクリックすると、詳細ページを開くことが出来ます。</p>
				<c:if test="${role == 'free'}">
          		<p class="alertMsg">最大100人まで管理可能です。最大に達すると自動・手動で追加出来ないのでご注意ください。</p>
	          	</c:if>
	          	<c:if test="${role == 'pro'}">
	          		<p class="alertMsg">最大500人まで予約管理可能です。最大に達すると自動・手動で追加出来ないのでご注意ください。</p>
	          	</c:if>
				<c:if test="${limitOver}">
          			<p class="well"><span style="color: red;">※</span>この予約ページは予約可能制限数に達しているため、利用出来ません。オーナー様に直接ご連絡下さい。</p>
          		</c:if>
				<div class="button" style="margin-bottom: 20px;"><a href="#myModal" data-toggle="modal">追加する</a></div>
				<c:forEach var="customer" items="${customerList}">
					<div class="span3" style="border:2px solid #dedede; padding: 10px; margin-bottom:20px; position: relative;">
						<p><img alt="" src="/img/login-human.png" class="minTitleIcon"><a href="/tools/rese/customerManage/customerDetail?id=${f:h(customer.key)}">${customer.name}</a></p>
						<div style="width:100%; height: 1px; background-color: #dedede;"></div>
						<div style="margin-top: 10px; background-color: #f8f8f8; padding: 10px;">
							<p>${customer.phone}</p>
							<p>${customer.mailaddress}</p>
						</div>
					</div>
					<%-- <label class="checkbox inline hidden userCheck" style="float: left;">
						<input type="checkbox" id="" value="${customer.key}" name="useId"> 追加
					</label> --%>
				</c:forEach>
				
			</div>
			<div class="span3" style="height: 230px; margin-top: 20px;">
				<p class="minTitle" style="background-color: #ecf0f1; padding: 5px 0"><img alt="" src="/img/search.png" class="minTitleIcon" style="margin-left: 10px;">顧客を検索する</p>
				<p style="font-size: 0.9em;">携帯番号はハイフンを付けてご記入ください。</p>
				<form action="/tools/rese/customerManage/customerList">
					<input type="text" placeholder="名前/メールアドレス/電話番号" name="s" style="width: 80%; padding: 20px 0 20px 5px; float: left;">
					<button type="submit" value="Search" style="background-color: #f8f8f8; border: 1px solid #dedede; width: 15%; height: 40px; margin-left: 5px;"><img src="/img/search.png" width="60%" style="padding: 5px 0 5px 0;"></button>
				</form>
				
				<p class="minTitle" style="background-color: #ecf0f1; padding: 5px 0"><img alt="" src="/img/note.png" class="minTitleIcon" style="margin-left: 10px;">予約ページで絞り込む</p>
				<select name="rebuildSelect" onchange="filterCustomer();">
					<option value="">指定なし
					<c:forEach var="menuPage" items="${menuPageList}">
						<option value="${f:h(menuPage.key)}">${menuPage.pageTitle}
					</c:forEach>
				</select>
			</div>
			<%@ include file="/tools/rese/common/ad.jsp"%>
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
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>

<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>顧客情報</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
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
    background: #295890;
    vertical-align: middle;
    text-align: left;
    width: 100px;
    overflow: visible;
    position: relative;
    color: #fff;
    font-weight: normal;
    font-size: 15px;
}
 
table.company td {
    background: #f8f8f8;
    width: 360px;
    padding-left: 20px;
}
.customerForm{
	border: none;
	background-color: #f8f8f8;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	//予約日時のフォーマットを変更します。 
	$(".reserveTime").each(function(index, element){
		var m = moment($(element).text(), "ddd MMM DD HH:mm:ss zzz yyyy", 'en');
		var output = m.format('MM月DD日 HH:mm');
		$(element).text(output);
	});
});
</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container mainContent">
		<div class="span12">
			<div class="span8">
			<h3>お客様情報</h3>
			<p>お客様の連絡情報、来店履歴を確認することが出来ます。</p>
			<a href="#myModal" role="button" data-toggle="modal"><img src="/img/pen.png" class="minTitleIcon"/>お客様情報を編集する</a>
			</div>
			<table class="company span8">
			    <tbody>
			        <tr>
			            <th class="arrow_box">名前</th>
			            <td>${customer.name}</td>
			        </tr>
			        <tr>
			            <th class="arrow_box">Mail</th>
			            <td>${customer.mailaddress}</td>
			        </tr>
			        <tr>
			            <th class="arrow_box">Phone</th>
			            <td>${customer.phone}</td>
			        </tr>
			    </tbody>
			</table>
			<div class="span8" style="background-color: #f8f8f8; margin-bottom: 30px;">
    		   <div style="width: 100%; background-color: #295890; margin-bottom: 10px;">
    		   		<div style=" padding: 8px;">
    		   			<h5 style="color:#fff; font-weight: lighter;">予約情報</h5>
    		   		</div>
    		   </div>
				<div style="padding: 10px;">
			        <c:forEach var="reserve" items="${reserveList}">
						<p style="border-bottom: 1px solid #dedede; padding-bottom: 10px;"><span class="reserveTime" style="margin-right: 20px;">${reserve.startTime}</span>${reserve.menuTitle}</p>
					</c:forEach>
				</div>
			</div>
			
			
 
			<!-- 顧客情報の編集フォーム -->
			<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			  <div class="modal-header">
			    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			    <h3 id="myModalLabel">Modal header</h3>
			  </div>
			  <div class="modal-body">
			    <form action="/tools/rese/customerManage/doneEditCustomer">
			    	<p>名前</p>
				    <input type="text" name="name" value="${customer.name}">
			    	<p>メールアドレス</p>
				    <input type="text" name="mailaddress" value="${customer.mailaddress}">
			    	<p>電話番号</p>
				    <input type="text" name="phone" value="${customer.phone}">
				    <input type="hidden" name="customerKey" value="${f:h(customer.key)}">
				    <br/>
				    <input type="submit" value="完了" style="width: 200px; background-color: #f39c12; border: none; padding: 10px; color:#fff; border-radius: 5px;">
			    </form>
			  </div>
			  <div class="modal-footer">
			    <a><p data-dismiss="modal" aria-hidden="true" class="closeButton" style="float: left; margin-top: 10px; cursor: pointer;">☓ 閉じる</p></a>
			  </div>
			</div>
			<!-- //顧客情報の編集フォーム -->
						
		</div>
	</div>
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>

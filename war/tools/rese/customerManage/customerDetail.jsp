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
	<div class="container">
		<div class="span12">
			<div class="span8">
			<h3>お客様情報</h3>
			<p>お客様の連絡情報、来店履歴を確認することが出来ます。</p>
			</div>
			<table class="company span8">
			    <tbody>
			        <tr>
			            <th class="arrow_box">名前</th>
			            <td><input type="text" name="name" value="${customer.name}" style="padding: 10px;"></td>
			        </tr>
			        <tr>
			            <th class="arrow_box">Mail</th>
			            <td><input type="text" name="mailaddress" value="${customer.mailaddress}" style="padding: 10px;"></td>
			        </tr>
			        <tr>
			            <th class="arrow_box">Phone</th>
			            <td><input type="text" name="phone" value="${customer.phone}" style="padding: 10px;"></td>
			        </tr>
			    </tbody>
			</table>
			<div class="span3" style="background-color: #000; height: 200px;"></div>
			<div class="span8" style="background-color: #f8f8f8;">
    		   <div style="width: 100%; background-color: #295890; margin-bottom: 10px;">
    		   		<div style=" padding: 8px;">
    		   			<h5 style="color:#fff; font-weight: lighter;">来店履歴</h5>
    		   		</div>
    		   </div>
				<div style="padding: 10px;">
			        <c:forEach var="reserve" items="${reserveList}">
						<p style="border-bottom: 1px solid #dedede; padding-bottom: 10px;"><span class="reserveTime" style="margin-right: 20px;">${reserve.startTime}</span>${reserve.menuTitle}</p>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>

<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>宅配便の一括検索サービス</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<script>
function postNo(){
	$('.goodsDetail').remove();
	var inquiryNo = $('[name=inquiryNo]').val();
	
	var request = [
	       	    { url: "/tools/shippingSearch/SearchSagawa" },
	       	    { url: "/tools/shippingSearch/SearchYamato" },
	       	    /* { url: "/tools/shippingSearch/SearchSeino" }, */
	];
	
	for (var i = 0; i < request.length; i++) {
	 	$.post(request[i].url, {
			'inquiryNo' : inquiryNo
		}, function(data){
			console.log(data.obj);
			switch(data.obj){
			case null:
				break;
	
			default:
		    	if(data.obj != null){
		    		$('#goodsInfo tbody').append("<tr class='goodsDetail'>");
		    		$('.goodsDetail').append("<td class='company'>" + data.obj.company + "</td>");
		    		$('.goodsDetail').append("<td class='inquiryNo'>" + data.obj.inquiryNo + "</td>");
		    		$('.goodsDetail').append("<td class='status'>" + data.obj.status + "</td>");
		    		$('.goodsDetail').append("<td class='shippingDate'>" + data.obj.shippingDate + "</td>");
		    		$('.goodsDetail').append("<td class='arriveDate'>" + data.obj.arriveDate + "</td>");
		    	}
				break;
				
			case 'error':
				alert(data.msg);
				break;
				
				
			}
		}, 'json');
	}}
	
/* 
	var jqXHRList = [];
	// Ajaxのリクエスト処理を作成します
	for (var i = 0; i < request.length; i++) {
	    jqXHRList.push($.post(request[i].url, {
			'inquiryNo' : inquiryNo
		}, 'json'));
	}
	// 非同期処理を実行します
	$.when.apply($, jqXHRList).done(function () {
		var companyArray = [];
	    var sagawa = arguments[0][0];
	    var yamato = arguments[1][0];
	    var seino = arguments[2][0];
	    companyArray.push(sagawa);
	    companyArray.push(yamato);
	    companyArray.push(seino);
	    
	    $.each(companyArray, function(index, val) {
	    	if(val != null){
	    		$('.company').append(val.company);
	    		$('.inquiryNo').append(val.inquiryNo);
	    		$('.status').append(val.status);
	    		$('.shippingDate').append(val.shippingDate);
	    		$('.arriveDate').append(val.arriveDate);
	    	}
    	});
	}).fail(function (ex) {
	    // サーバー側での処理に失敗した際に、呼び出されます
	    alert("通信に失敗しました");
	});
} */
</script>
</head>
<body>
	<div class="container span12">
		<div class="span12">
			<form id="entryForm">
				<input name="inquiryNo" type="text">
				<p class="btn btn-info" onclick="postNo();">検索</p>
			</form>

			<table class="table table-bordered table-striped" id="goodsInfo">
				<thead>
					<tr>
						<th>会社名</th>
						<th>お問い合わせ番号</th>
						<th>お届け状況</th>
						<th>出荷日</th>
						<th>到着日</th>
					</tr>
				</thead>
				<tbody>
					<!-- <tr class="goodsDetail">
						<td class="company"></td>
						<td class="inquiryNo"></td>
						<td class="status"></td>
						<td class="shippingDate"></td>
						<td class="arriveDate"></td>
					</tr> -->
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
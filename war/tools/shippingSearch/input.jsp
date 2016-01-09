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
<script type="text/javascript" src="/js/countable/Countable.js"></script>

<script>
$(document).ready(function(){
var area = document.getElementById('countableArea');
Countable.live(area, function(counter){
  document.getElementById('paragraphs').innerHTML = counter['paragraphs'];
  
  if(counter['paragraphs'] > 10 ){
	  $('#paragraphs').attr("style", "color:red;");
  }
  if(counter['paragraphs'] <= 10 ){
	  $('#paragraphs').attr("style", "color:#000;");
  }
})
	
});

function postNo(){
	showLoadingImage();
	
	$('#goodsInfo tbody tr td').remove();
	var inquiryNo = $('[name=inquiryNo]').val();
	
	var request = [
	       	    { url: "/tools/shippingSearch/SearchSagawa" },
	       	    { url: "/tools/shippingSearch/SearchYamato" },
	       	    { url: "/tools/shippingSearch/SearchYusei" },
	       	    /* { url: "/tools/shippingSearch/SearchSeino" }, */
	];
	
	for (var i = 0; i < request.length; i++) {
	 	$.post(request[i].url, {
			'inquiryNo' : inquiryNo
		}, function(data){
			switch(data.obj){
			case null:
				break;
	
			default:
		    	if(data.obj != null){
		    		 $.each(data.obj, function(index, val) {
		    			 console.log(data.obj[index]);
						if(data.obj[index].shippingDate){
				    		var company = "<td class='company'>" + data.obj[index].company + "</td>";
				    		var inquiryNo = "<td class='inquiryNo'>" + data.obj[index].inquiryNo + "</td>";
				    		var status = "<td class='status'>" + data.obj[index].status + "</td>";
				    		var shippingDate = "<td class='shippingDate'>" + data.obj[index].shippingDate + "</td>";
				    		var arriveDate = "<td class='arriveDate'>" + data.obj[index].arriveDate + "</td>";
 			    		
				    		$('tbody').append("<tr>" + company + inquiryNo + status + shippingDate + arriveDate + "</tr>");
						}
	    	    	});
		    	}
				break;
				
			case 'error':
				alert(data.msg);
				break;
				
				
			}
		}, 'json');
	}
}



// クルクル画像表示
function showLoadingImage() {
	$(".imgBox").append('<img src="/img/load.gif" class="loader" width="30%;">');
}
// クルクル画像消去
function hideLoadingImage() {
	$(".loader").fadeOut(0);
}

$(document).ajaxComplete(function(){
	hideLoadingImage();
});

	
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
				<p>最大10個まで一度に検索できます。</p>
				<p>追跡番号は段落で区切ります。最大20秒ほどかかります。</p>
				<p>対応会社：佐川急便、クロネコヤマト、日本郵政</p>
				<p>以下は例です。</p>
				<blockquote>0000000000<br/>1111111111<br/>2222222222</blockquote>
				<div class="warningArea"></div>
				<div class="commentArea"></div>
			  	<p>追跡番号:<span id="paragraphs">0</span>個</p>
				<textarea rows="3" name="inquiryNo" id="countableArea"></textarea>
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
				</tbody>
			</table>
			<div class="imgBox"></div>
		</div>
	</div>
</body>
</html>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>レポート</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>
<%@ include file="/tools/rese/common/actionMenuJs.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript">
$(document).ready( function(){
	
	
	var chartURL = "/tools/rese/report/chart";
	for (var i = 0; i < 4; i++){
		$.post(chartURL, {
			'chartNumber' : i
		}, function(data) {
			/* console.log(data); */
			
			switch (data.msg){
			  case "revenu":
				  revenuChart(data.obj);
			    break;
			  case "visit":
				  visitChart(data.obj);
			    break;
			  case "pay":
				  payChart(data.obj);
			    break; 
			}
		}, 'json');
	}
});
	
	
	
	/* 月別の売上チャート */
	function revenuChart(revenuMap){
		console.log(revenuMap);
 		var thisYear = {
				data : []
			}
	 	$.each(revenuMap,function(index,val){
			thisYear.data.push(val);
		});
 		
 		
		$(function () {
		    $('#revenuChart').highcharts({
		        chart: {
		            type: 'line',
		            backgroundColor: '#f8f8f8'
		        },
		        title: {
		            text: '今年の売上推移'
		        },
		        subtitle: {
		            text: ''
		        },
		        xAxis: {
		            categories: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
		            labels : {
						rotation : -45,
						align : 'right',
					}
		        },
		        yAxis: {
		            title: {
		                text: '金額(円) , k=1000'
		            }
		        },
		        plotOptions: {
		            line: {
		                dataLabels: {
		                    enabled: false
		                },
		                enableMouseTracking: true
		            }
		        },
		        tooltip: {
		            valueSuffix: '円'
		          },
		        series: [{
		            name: '今年',
		            data: thisYear.data,
			        color : '#f39c12'
		        }]
		    });
		});
	}
	
	/* 来店回数ランキング */
	function visitChart(visitData){
		console.log(visitData);
 		var customerName = {
				data : []
		}
		var visitRanking = {
				data : []
		}
		
	 	var visitCustomerList = [];
		var visitNumberList = [];
		//名前・来店回数を取り出して、それぞれのリストに追加していく。
		for (var i = 0; i < visitData.length; i++){
 			customerName.data.push(visitData[i].name);
 			visitRanking.data.push(visitData[i].visitNumber);
		}
		
		$(function () {
		    $('#visitRankingChart').highcharts({
		        chart: {
		            type: 'bar',
		            backgroundColor: '#f8f8f8'
		        },
		        title: {
		            text: '来店回数ランキング'
		        },
		        xAxis: {
		            categories: customerName.data
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: ''
		            }
		        },
		        legend: {
		            reversed: true
		        },
		        plotOptions: {
		            series: {
		                stacking: 'normal'
		            }
		        },
		        series: [{
		            name: '来店回数',
		            data: visitRanking.data,
			        color : '#3498db'
		        }]
		    });
		});
	}
	
	//合計金額ランキング
	function payChart (payData) {
		console.log(payData);
 		var customerName = {
				data : []
		}
		var payRanking = {
				data : []
		}
		
		//名前・来店回数を取り出して、それぞれのリストに追加していく。
		for (var i = 0; i < payData.length; i++){
 			customerName.data.push(payData[i].name);
 			payRanking.data.push(payData[i].totalPayment);
		}
 		
		$(function () {
		    $('#payRankingChart').highcharts({
		        chart: {
		            type: 'bar',
		            backgroundColor: '#f8f8f8'
		        },
		        title: {
		            text: '合計金額ランキング'
		        },
		        xAxis: {
		            categories: customerName.data
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: ''
		            }
		        },
		        legend: {
		            reversed: true
		        },
		        plotOptions: {
		            series: {
		                stacking: 'normal'
		            }
		        },
		        series: [{
		            name: '支払い合計金額',
		            data: payRanking.data,
			        color : '#1abc9c'
		        }]
		    });
		});
	};
	

</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container mainContent">
		<div class="span12">
			<h3>レポート</h3>
			<p>お店の改善に役立つレポートです。３時間ごとに自動更新されます。</p>
			<p>グラフをクリック・フォーカスすると詳細が表示されます。</p>
			<div class="span11" id="revenuChart" style="margin: 0; height: 400px; padding: 10px;">
			</div>
			
			<div class="span5" id="visitRankingChart" style="margin: 20px 0; padding: 10px;">
			</div>
			
			<div class="span5" id="payRankingChart" style="margin: 20px 0; padding: 10px;">
			</div>
		</div>
	</div>
	<%@ include file="/tools/rese/common/footer.jsp"%>
</body>
</html>

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

    <title>メニュー</title>

    <!-- Bootstrap core CSS -->
    <link href="/tools/rese/dashboard/assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/tools/rese/dashboard/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link href="/tools/rese/dashboard/assets/js/fullcalendar/bootstrap-fullcalendar.css" rel="stylesheet" />
    <link href="/css/fullCalendar/fullcalendar.min.css" rel="stylesheet">
        
    <!-- Custom styles for this template -->
    <link href="/tools/rese/dashboard/assets/css/style.css" rel="stylesheet">
    <link href="/tools/rese/dashboard/assets/css/style-responsive.css" rel="stylesheet">
	
	<script src="/tools/rese/dashboard/assets/js/jquery.js"></script>
	<!-- グラフ -->
	<script src="/js/highcharts/highcharts.js"></script>
	<script src="/js/highcharts/exporting.js"></script>
	<script src="/js/highcharts/highcharts-more.js"></script>
	<script src="/js/iphone-style-checkboxes.js"></script>
	<script src="/js/jq/jquery.autosize.min.js"></script>
	
	
	<style type="text/css">
	.textForm{
		width: 80%;
		padding: 10px 10px 10px 10px;
	}
		#loader-bg {
	  display: none;
	  position: fixed;
	  width: 100%;
	  height: 100%;
	  top: 0px;
	  left: 0px;
	  background: #000;
	  z-index: 1;
	}
	#loader {
	  display: none;
	  position: fixed;
	  top: 50%;
	  left: 50%;
	  width: 200px;
	  height: 200px;
	  margin-top: -100px;
	  margin-left: -100px;
	  text-align: center;
	  color: #fff;
	  z-index: 2;
	}
	</style>
	<script type="text/javascript" src="/js/jq/load.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
	
			var chartURL = "/tools/rese/report/chart";
			for (var i = 0; i < 4; i++) {
				$.post(chartURL, {
					'chartNumber' : i
				}, function(data) {
					/* console.log(data); */
	
					switch (data.msg) {
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
		function revenuChart(revenuMap) {
			console.log(revenuMap);
			var thisYear = {
				data : []
			}
			$.each(revenuMap, function(index, val) {
				thisYear.data.push(val);
			});
	
			$(function() {
				$('#revenuChart').highcharts(
						{
							chart : {
								type : 'line'
							},
							title : {
								text : ''
							},
							subtitle : {
								text : ''
							},
							xAxis : {
								categories : [ '1月', '2月', '3月', '4月', '5月', '6月',
										'7月', '8月', '9月', '10月', '11月', '12月' ],
								labels : {
									rotation : -45,
									align : 'right',
								}
							},
							yAxis : {
								title : {
									text : '金額(円) , k=1000'
								}
							},
							plotOptions : {
								line : {
									dataLabels : {
										enabled : false
									},
									enableMouseTracking : true
								}
							},
							tooltip : {
								valueSuffix : '円'
							},
							series : [ {
								name : '今年',
								data : thisYear.data,
								color : '#f39c12'
							} ]
						});
			});
		}
	
		/* 来店回数ランキング */
		function visitChart(visitData) {
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
			for (var i = 0; i < visitData.length; i++) {
				customerName.data.push(visitData[i].name);
				visitRanking.data.push(visitData[i].visitNumber);
			}
	
			$(function() {
				$('#visitRankingChart').highcharts({
					chart : {
						type : 'bar'
					},
					title : {
						text : ''
					},
					xAxis : {
						categories : customerName.data
					},
					yAxis : {
						min : 0,
						title : {
							text : ''
						}
					},
					legend : {
						reversed : true
					},
					plotOptions : {
						series : {
							stacking : 'normal'
						}
					},
					series : [ {
						name : '来店回数',
						data : visitRanking.data,
						color : '#3498db'
					} ]
				});
			});
		}
	
		//合計金額ランキング
		function payChart(payData) {
			console.log(payData);
			var customerName = {
				data : []
			}
			var payRanking = {
				data : []
			}
	
			//名前・来店回数を取り出して、それぞれのリストに追加していく。
			for (var i = 0; i < payData.length; i++) {
				customerName.data.push(payData[i].name);
				payRanking.data.push(payData[i].totalPayment);
			}
	
			$(function() {
				$('#payRankingChart').highcharts({
					chart : {
						type : 'bar'
					},
					title : {
						text : ''
					},
					xAxis : {
						categories : customerName.data
					},
					yAxis : {
						min : 0,
						title : {
							text : ''
						}
					},
					legend : {
						reversed : true
					},
					plotOptions : {
						series : {
							stacking : 'normal'
						}
					},
					series : [ {
						name : '支払い合計金額',
						data : payRanking.data,
						color : '#1abc9c'
					} ]
				});
			});
		};
	</script>
</head>
  <body>
	<div id="loader-bg">
		<div id="loader">
			<img src="/img/load_bar.svg" width="100" height="100" alt="Now Loading..." />
			<p>Now Loading...</p>
		</div>
	</div>	
  <section id="container" >
     <%@ include file="/tools/rese/dashboard/common/topBar.jsp"%>
     <!-- header end -->
      
     <%@ include file="/tools/rese/dashboard/common/sideMenu.jsp"%>

		<div id="wrap">
	      <!--main content start-->
	      <section id="main-content">
	          <section class="wrapper">
	          	<h3><i class="fa fa-angle-right"></i> メニュー</h3>
	          	<p>クリック or フォーカスすると詳細を見ることが出来ます。</p>
	          	<hr>
	              <!-- page start-->
	              <div class="row mt">
	                  <div class="col-lg-12" style="margin-bottom: 20px; ">
	                      <div class="content-panel">
	                          <h4><i class="fa fa-angle-right"></i> 今年の売上推移</h4>
	                          <div class="panel-body">
	                              <div class="span11" id="revenuChart" style="margin: 0; height: 400px; padding: 10px;"></div>
	                          </div>
	                      </div>
	                  </div>
	                  <div class="col-lg-6">
	                      <div class="content-panel">
	                          <h4><i class="fa fa-angle-right"></i> 来店回数ランキング</h4>
	                          <div class="panel-body">
	                            <div class="span5" id="visitRankingChart" style="margin: 20px 0; padding: 10px;"></div>
	                          </div>
	                      </div>
	                  </div>
	                  <div class="col-lg-6">
	                      <div class="content-panel">
	                          <h4><i class="fa fa-angle-right"></i> 合計金額ランキング</h4>
	                          <div class="panel-body">
	                            <div class="span5" id="payRankingChart" style="margin: 20px 0; padding: 10px;"></div>
	                          </div>
	                      </div>
	                  </div>
	              </div>
	              <!-- page end-->
			</section><!-- /wrapper -->
	      </section><!-- /MAIN CONTENT -->
	 	</div>

      <!--main content end-->
	  <!--footer start-->
	  <%@ include file="/tools/rese/dashboard/common/footer.jsp"%>
	  <!--footer end-->
      
  </section>

    <script src="/tools/rese/dashboard/assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="/tools/rese/dashboard/assets/js/jquery.dcjqaccordion.2.7.js"></script>


    <!--common script for all pages-->
    <script src="/tools/rese/dashboard/assets/js/common-scripts.js"></script>


  </body>
</html>

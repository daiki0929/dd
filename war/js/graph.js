
var GraphClass = (function () {
	//コンストラクタ
    function GraphClass() {
    	this.type;
    }

	GraphClass.prototype.init = function( _target , _type , _unit ) {
		var chart;
		if( _type == 'line' ) {
			chart = getLineChart(_target , _type , _unit );
		} else if(_type == 'column') {
			chart = getBarChart(_target , _type , _unit );
		} else if(_type == 'pie') {
			chart = getPieChart(_target , _type , _unit );
		}
		return chart;
	}


	//グラフ作成
	GraphClass.prototype.createGraph = function(url , chart , cacheClear) {
		var type = chart.options.chart.type;
		jQuery.each(chart.series , function(i, series) {
			chart.series[0].remove(true);
		});
		chart.showLoading('Loading...pleace wait');
		$.post( url , {'cacheClear': cacheClear},
			function(json) {
				if(json.status == 'success'){
					//グラフのプロット
					if( type == 'line' ) {
						addSeriesForLineGraph(chart, json);
					} else if( type == 'column' ) {
						addSeriesForBarGraph(chart, json, '#025BFF');
					} else if( type == 'pie' ) {
						addSeriesForPieGraph(chart, json);
					}
					chart.hideLoading('');
				} else if(json.status == 'nodata') {
					//console.log("json.sutatus == nodata");
					chart.hideLoading('対象クールでデータがまだ存在しません');
					var renderTo = $("#" + chart.options.chart.renderTo );
					chart.destroy();

					renderTo.empty();
					renderTo.append('<p style="padding-top:80px; text-align:center; font-weight:bold;">対象のクール(' + json.summaryRange + ')でデータがまだ存在しません</p>');

				} else if(json.status == 'summary') {
					chart.hideLoading('集計中です、しばらくお待ちください。');
					var renderTo = $("#" + chart.options.chart.renderTo );
					chart.destroy();
					renderTo.empty();
					renderTo.append('<p style="padding-top:80px; text-align:center; font-weight:bold;">集計中です、しばらくお待ちください。</p>');

				} else {
					chart.showLoading('グラフ描画に失敗しました。しばらくたってからご利用ください。');
				}
			},
			'json'
		);
	}

	//グラフ作成
	GraphClass.prototype.createGraphNoAjax = function(chart , cacheClear, json) {
		var type = chart.options.chart.type;
		jQuery.each(chart.series , function(i, series) {
			chart.series[0].remove(true);
		});
		//グラフのプロット
		if( type == 'line' ) {
			addSeriesForLineGraph(chart, json);
		} else if( type == 'column' ) {
			addSeriesForBarGraph(chart, json, '#025BFF');
		} else if( type == 'pie' ) {
			addSeriesForPieGraph(chart, json);
		}
		chart.hideLoading('');
		console.log(chart);
	}

//===================================================================================================================

    //折れ線グラフのプロット
    function addSeriesForLineGraph(chart, json) {

    	var colors = ['#025BFF', '#02DEFF', '#843AFF', '#24CBE5', '#64E572', '#FF9655', '#FF3838', '#50EAB1'];
		var xval = new Date();
	    var series = {
	            name: '',
	            color:[],
	        	data: []
	    }

		jQuery.each(json.graphDtoList , function(i, parent) {

	        series.name = parent.name;

	    	jQuery.each(parent.data , function(j, child) {
	    		xval = child[0];
	    		var yval = child[1];

	            var x = [xval, yval];
				//series.data.push( xval , parseInt(yval) );
	            var time = moment(xval , 'YYYY/MM/DD').toDate().getTime();

	            if(yval != "null"){
	            	series.data.push([ time , parseInt(yval)]);
	            }else{
	            	series.data.push([ time , null]);
	            }
				series.color = (colors[i]);
	        });

	    	chart.addSeries(series);
	    	chart.setTitle({text: json.title});
	    	series.data = [];
	    });

    }

	//棒グラフのプロット
    function addSeriesForBarGraph( chart, json, color ) {
    	var xtitle = [];

    	var colors = [color];
		var xval = new Date();

        var series = {
	            name: '',
	            color:[],
            	data: []
        }

    	jQuery.each(json.graphDtoList , function(i, parent) {
	        xtitle.push(parent.name + "さん");

        	jQuery.each(parent.data , function(j, child) {
        		var yval = child[1];
                xval = child[0];
                var x = [xval, yval];
				series.name = xval;
				series.color = (colors[0]);
				series.data.push([parseInt(yval)]);
            });

        });
		chart.addSeries(series);
		chart.setTitle({text: json.title});
		chart.xAxis[0].setCategories(xtitle);

	}

	//円グラフのプロット
	function addSeriesForPieGraph(chart, json) {
		var colors = ['#025BFF', '#02DEFF', '#843AFF', '#24CBE5', '#64E572', '#FF9655', '#FF3838', '#50EAB1'];
		var xval = new Date();
		var series = {
		        name: '出品カテゴリー',
		        color:[],
		    	data: []
		}
		jQuery.each(json.graphDtoList , function(i, parent) {
			//console.log("json[a].name=" + parent.name);
			//console.log("json[a].data=" + parent.data);
			series.name = parent.name;

			jQuery.each(parent.data , function(j, child) {
			    var yval = child[1];
			    xval = child[0];
			    var x = [xval, yval];
				//console.log("y=" + yval + " x=" + xval);
			    //console.log("name=" + xval );
				series.data.push([xval , parseInt(yval)]);
				series.color = (colors[i]);
			});
			//console.log("series=" + series);
					chart.addSeries(series);
					chart.setTitle({text: json.title});
					series.data = [];
		});
	}
//========================================================================================================================
	function getLineChart( _target , _type , _unit ) {
		return new Highcharts.Chart(
			{
				chart: {
					renderTo: _target,
					type: _type,
					marginTop: 24,
					marginLeft: 80
				},
				title: {
			    	x: 0,
			    	y: 0,
			    	margin: 0,
			        text: '',
			        align: 'left',
			        style: {
			        	color: '#3E576F',
			        	fontSize: '12px'
			        }
			    },
			    subtitle: {
			        text: '',
			        x: -20
			    },
			    xAxis: {
					gridLineWidth: 1 ,
					lineColor: '#000',
			    	tickColor: '#000',
		    		type: 'datetime',
		    		dateTimeLabelFormats: { // don't display the dummy year
		    			day : '%m/%d',
		    			week : '%m/%d',
		    			month: '%m/%d',
		    			year : '%m/%d'
		    		}
				},
			    yAxis: {
			    	minorTickInterval: 'auto',
			    	lineColor: '#000',
			    	lineWidth: 1,
			    	tickWidth: 1,
			    	tickColor: '#000',
			    	labels: {
			    		format: '{value:,.f}' + _unit
			    	},
			    	title: {
			    		text: ''
			    	},
			    	min:0,
			    	plotLines: [{
			    		value: 0,
			    		width: 1,
			    		color: '#808080'
			    	}]
			    },
			    tooltip: {
					formatter: function() {
						return '<b>'+ this.series.name +'</b><br/>'+
						Highcharts.dateFormat('%Y/%m/%d' , this.x) +': '+ addFigure( this.y ) + _unit;
			        },
					dateTimeLabelFormats: { // don't display the dummy year
						day : '%m/%d',
						week : '%m/%d',
						month: '%m/%d',
						year : '%m/%d'
					}
				} ,
			    legend: {
			    	layout: 'vertical',
			    	align: 'bottom',
			    	verticalAlign: 'center',
			    	x: 200,
			    	y: 0,
			    	borderWidth: 1,
			    	enabled: false
			    },
				loading: {
					labelStyle: {
						background: 'url(/img/ajax-loader2.gif) no-repeat center',
						display: 'block',
						height:'100px'
					}
				},
			    credits: {
		            enabled: false
		        },
		        exporting: {
		        	buttons: {
		        		exportButton: {
		        			enabled:false
		        		},
		        		printButton: {
		        			enabled:false
		        		},
		        		contextButton: {
		        			enabled:false
		        		}
		        	}
		        }
			}
		);
	}

	function getBarChart( _target , _type , _unit ) {
		return new Highcharts.Chart(
			{
				chart: {
					renderTo: _target,
					type: _type,
					marginTop: 24
				},
				title: {
			    	x: 0,
			    	y: 0,
			    	margin: 0,
			        text: '',
			        align: 'left',
			        style: {
			        	color: '#3E576F',
			        	fontSize: '12px'
			        }
			    },
			    subtitle: {
			        text: '',
			        x: -20
			    },
			    xAxis: {
					gridLineWidth: 1 ,
					lineColor: '#000',
					tickColor: '#000',
					labels: {
						style: {
							color: '#555',
							fontSize: '9px',
							fontFamily: 'Verdana, sans-serif'
						}
					}
				},
			    yAxis: {
			    	minorTickInterval: 'auto',
			    	lineColor: '#000',
			    	lineWidth: 1,
			    	tickWidth: 1,
			    	tickColor: '#000',
			    	labels: {
			    		format: '{value:,.f}' + _unit
			    	},
			    	title: {
			    		text: ''
			    	},
			    	min:0,
			    	plotLines: [{
			    		value: 0,
			    		width: 1,
			    		color: '#808080'
			    	}]
			    },
			    tooltip: {
					formatter: function() {
						return '<b>'+ this.x +'</b><br/>' + addFigure(this.y) + _unit + '<br/>';
					} ,
					headerFormat:''
				} ,
			    legend: {
			    	layout: 'vertical',
			    	align: 'bottom',
			    	verticalAlign: 'center',
			    	x: 200,
			    	y: 0,
			    	borderWidth: 1,
			    	enabled: false
			    },
				loading: {
					labelStyle: {
						background: 'url(/img/ajax-loader2.gif) no-repeat center',
						display: 'block',
						height:'100px'
					}
				},
			    credits: {
		            enabled: false
		        },
		        exporting: {
		        	buttons: {
		        		exportButton: {
		        			enabled:false
		        		},
		        		printButton: {
		        			enabled:false
		        		},
		        		contextButton: {
		        			enabled:false
		        		}
		        	}
		        }
			}
		);
	}

	function getPieChart( _target , _type , _unit ) {
		return new Highcharts.Chart(
			{
				chart: {
					renderTo: _target,
					type: _type,
					marginTop: 24
				},
				title: {
			    	x: 0,
			    	y: 0,
			    	margin: 0,
			        text: '',
			        align: 'left',
			        style: {
			        	color: '#3E576F',
			        	fontSize: '12px'
			        }
			    },
			    subtitle: {
			        text: '',
			        x: -20
			    },
			    xAxis: {
					gridLineWidth: 1 ,
					lineColor: '#000',
					tickColor: '#000',
					labels: {
						style: {
							color: '#555',
							fontSize: '9px',
							fontFamily: 'Verdana, sans-serif'
						}
					}
				},
			    yAxis: {
			    	minorTickInterval: 'auto',
			    	lineColor: '#000',
			    	lineWidth: 1,
			    	tickWidth: 1,
			    	tickColor: '#000',
			    	labels: {
			    		format: '{value:,.f}' + _unit
			    	},
			    	title: {
			    		text: ''
			    	},
			    	min:0,
			    	plotLines: [{
			    		value: 0,
			    		width: 1,
			    		color: '#808080'
			    	}]
			    },
			    tooltip: {
					headerFormat:'',
					pointFormat: '<b>{series.name}</b><br/>{point.y:,.f}' + _unit ,
					dateTimeLabelFormats: { // don't display the dummy year
						day : '%m/%d',
						week : '%m/%d',
						month: '%m/%d',
						year : '%m/%d'
					}
				} ,
			    legend: {
			    	layout: 'vertical',
			    	align: 'bottom',
			    	verticalAlign: 'center',
			    	x: 200,
			    	y: 0,
			    	borderWidth: 1,
			    	enabled: false
			    },
				loading: {
					labelStyle: {
						background: 'url(/img/ajax-loader2.gif) no-repeat center',
						display: 'block',
						height:'100px'
					}
				},
			    credits: {
		            enabled: false
		        },
		        exporting: {
		        	buttons: {
		        		exportButton: {
		        			enabled:false
		        		},
		        		printButton: {
		        			enabled:false
		        		},
		        		contextButton: {
		        			enabled:false
		        		}
		        	}
		        },
		        plotOptions: {
		        	pie: {
		        		allowPointSelect: true,
		        		cursor: 'pointer',
		        		dataLabels: {
		        			enabled: true,
	                        color: '#000000',
	                        connectorColor: '#000000',
	                        formatter: function() {
	                            return '<b>'+ this.point.name +'</b>: '+ (Math.round(this.percentage * 10) / 10) +' %';
	                        }
	                    }
	                }
	            }
			}
		);
	}

    return GraphClass;
})();

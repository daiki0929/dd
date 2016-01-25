<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>画像アップロードのテスト</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>
<link rel="stylesheet" href="/css/pickadate/default.css">
<link rel="stylesheet" href="/css/pickadate/default.date.css">

<%-- JSインポート --%>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.29/angular.min.js"></script>

<style type="text/css">
.datepickerdemoBasicUsage {
	/** Demo styles for mdCalendar. */
	
}

.datepickerdemoBasicUsage md-content {
	padding-bottom: 200px;
}

.datepickerdemoBasicUsage .validation-messages {
	font-size: 11px;
	color: darkred;
	margin: 10px 0 0 25px;
}
</style>
<script type="text/javascript">
angular.module('datepickerBasicUsage',
	    ['ngMaterial', 'ngMessages']).controller('AppCtrl', function($scope) {
	  $scope.myDate = new Date();
	  $scope.minDate = new Date(
	      $scope.myDate.getFullYear(),
	      $scope.myDate.getMonth() - 2,
	      $scope.myDate.getDate());
	  $scope.maxDate = new Date(
	      $scope.myDate.getFullYear(),
	      $scope.myDate.getMonth() + 2,
	      $scope.myDate.getDate());
	  $scope.onlyWeekendsPredicate = function(date) {
	    var day = date.getDay();
	    return day === 0 || day === 6;
	  }
	});
</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	
	
	<div ng-controller="AppCtrl" style='padding: 40px;' ng-cloak>
		<md-content>
		<h4>Standard date-picker</h4>
		<md-datepicker ng-model="myDate" md-placeholder="Enter date"></md-datepicker>
		<h4>Disabled date-picker</h4>
		<md-datepicker ng-model="myDate" md-placeholder="Enter date" disabled></md-datepicker>
		<h4>Date-picker with min date and max date</h4>
		<md-datepicker ng-model="myDate" md-placeholder="Enter date"
			md-min-date="minDate" md-max-date="maxDate"></md-datepicker>
		<h4>Only weekends are selectable</h4>
		<md-datepicker ng-model="myDate" md-placeholder="Enter date"
			md-date-filter="onlyWeekendsPredicate"></md-datepicker>
		<h4>Only weekends within given range are selectable</h4>
		<md-datepicker ng-model="myDate" md-placeholder="Enter date"
			md-min-date="minDate" md-max-date="maxDate"
			md-date-filter="onlyWeekendsPredicate"></md-datepicker>
		<h4>With ngMessages</h4>
		<form name="myForm">
			<md-datepicker name="dateField" ng-model="myDate"
				md-placeholder="Enter date" required md-min-date="minDate"
				md-max-date="maxDate" md-date-filter="onlyWeekendsPredicate"></md-datepicker>
			<div class="validation-messages"
				ng-messages="myForm.dateField.$error">
				<div ng-message="valid">The entered value is not a date!</div>
				<div ng-message="required">This date is required!</div>
				<div ng-message="mindate">Date is too early!</div>
				<div ng-message="maxdate">Date is too late!</div>
				<div ng-message="filtered">Only weekends are allowed!</div>
			</div>
		</form>
		</md-content>
	</div>
</body>
</html>
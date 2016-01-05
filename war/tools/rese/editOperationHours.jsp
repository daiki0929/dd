<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>メニュー編集</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>


<script type="text/javascript" charset="utf-8">
<%@ include file="/js/tools/rese/shopStatus.js"%>
</script>

</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
	<div class="container">
		<!-- エラーメッセージ -->
		<%@ include file="/tools/rese/common/error.jsp"%>
		<div class="span12">
			<h3>営業日時</h3>
			<!-- 日曜日 -->
			<form name="sunday">
				<p>日曜日</p>
				<label class="radio"> <input type="radio"
					name="sundayShopStatus" value="open"
					onchange="postShopStatus('sunday','open')"> 営業日
				</label> <label class="radio"> <input type="radio"
					name="sundayShopStatus" value="notOpen"
					onchange="postShopStatus('sunday','notOpen')"> 非営業日
				</label> <br />
				<p>営業開始時刻</p>
				<select name="startTime" onchange="postOperationHours('sunday')">
					<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
				</select>
				<p>営業終了時刻</p>
				<select name="endTime" onchange="postOperationHours('sunday')">
					<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
				</select>
			</form>

			<!-- 月曜日 -->
			<form name="monday">
				<p>月曜日</p>
				<label class="radio"> <input type="radio"
					name="mondayShopStatus" value="open"
					onchange="postShopStatus('monday','open')"> 営業日
				</label> <label class="radio"> <input type="radio"
					name="mondayShopStatus" value="notOpen"
					onchange="postShopStatus('monday','notOpen')"> 非営業日
				</label> <br />
				<p>営業開始時刻</p>
				<select name="startTime" onchange="postOperationHours('monday')">
					<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
				</select>
				<p>営業終了時刻</p>
				<select name="endTime" onchange="postOperationHours('monday')">
					<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
				</select>
			</form>

			<!-- 火曜日 -->
			<form name="tuesday">
				<p>火曜日</p>
				<label class="radio"> <input type="radio"
					name="tuesdayShopStatus" value="open"
					onchange="postShopStatus('tuesday','open')"> 営業日
				</label> <label class="radio"> <input type="radio"
					name="tuesdayShopStatus" value="notOpen"
					onchange="postShopStatus('tuesday','notOpen')"> 非営業日
				</label> <br />
				<p>営業開始時刻</p>
				<select name="startTime" onchange="postOperationHours('tuesday')">
					<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
				</select>
				<p>営業終了時刻</p>
				<select name="endTime" onchange="postOperationHours('tuesday')">
					<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
				</select>
			</form>

			<!-- 水曜日 -->
			<form name="wednesday">
				<p>水曜日</p>
				<label class="radio"> <input type="radio"
					name="wednesdayShopStatus" value="open"
					onchange="postShopStatus('wednesday','open')"> 営業日
				</label> <label class="radio"> <input type="radio"
					name="wednesdayShopStatus" value="notOpen"
					onchange="postShopStatus('wednesday','notOpen')"> 非営業日
				</label> <br />
				<p>営業開始時刻</p>
				<select name="startTime" onchange="postOperationHours('wednesday')">
					<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
				</select>
				<p>営業終了時刻</p>
				<select name="endTime" onchange="postOperationHours('wednesday')">
					<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
				</select>
			</form>

			<!-- 木曜日 -->
			<form name="thursday">
				<p>木曜日</p>
				<label class="radio"> <input type="radio"
					name="thursdayShopStatus" value="open"
					onchange="postShopStatus('thursday','open')"> 営業日
				</label> <label class="radio"> <input type="radio"
					name="thursdayShopStatus" value="notOpen"
					onchange="postShopStatus('thursday','notOpen')"> 非営業日
				</label> <br />
				<p>営業開始時刻</p>
				<select name="startTime" onchange="postOperationHours('thursday')">
					<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
				</select>
				<p>営業終了時刻</p>
				<select name="endTime" onchange="postOperationHours('thursday')">
					<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
				</select>
			</form>

			<!-- 金曜日 -->
			<form name="friday">
				<p>金曜日</p>
				<label class="radio"> <input type="radio"
					name="fridayShopStatus" value="open"
					onchange="postShopStatus('friday','open')"> 営業日
				</label> <label class="radio"> <input type="radio"
					name="fridayShopStatus" value="notOpen"
					onchange="postShopStatus('friday','notOpen')"> 非営業日
				</label> <br />
				<p>営業開始時刻</p>
				<select name="startTime" onchange="postOperationHours('friday')">
					<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
				</select>
				<p>営業終了時刻</p>
				<select name="endTime" onchange="postOperationHours('friday')">
					<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
				</select>
			</form>

			<!-- 土曜日 -->
			<form name="saturday">
				<p>土曜日</p>
				<label class="radio"> <input type="radio"
					name="saturdayShopStatus" value="open"
					onchange="postShopStatus('saturday','open')"> 営業日
				</label> <label class="radio"> <input type="radio"
					name="saturdayShopStatus" value="notOpen"
					onchange="postShopStatus('saturday','notOpen')"> 非営業日
				</label> <br />
				<p>営業開始時刻</p>
				<select name="startTime" onchange="postOperationHours('saturday')">
					<%@ include file="/tools/rese/common/operationStartTime.jsp"%>
				</select>
				<p>営業終了時刻</p>
				<select name="endTime" onchange="postOperationHours('saturday')">
					<%@ include file="/tools/rese/common/operationEndTime.jsp"%>
				</select>
			</form>
		</div>
	</div>
</body>
</html>

<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
$(document).ready(function() {
	var length = parseInt(document.getElementsByTagName("option").length);
	/* 予約受け付け間隔 */
	for (var count = 0; count < length; count++){
		if($('select[name=reserveInterval] option')[count].value == "${menuPage.interval}"){
			var selectedItem = $('select[name=reserveInterval] option')[count];
			selectedItem.setAttribute("selected", "true");
			break;
		}
	}
	/* 予約受け付け開始 */
	for (var count = 0; count < length; count++){
		if($('select[name=reserveStartTime] option')[count].value == "${menuPage.reserveStartTime}"){
			var selectedItem = $('select[name=reserveStartTime] option')[count];
			selectedItem.setAttribute("selected", "true");
			break;
		}
	}
	/* 予約受け付け終了 */
	for (var count = 0; count < length; count++){
		if($('select[name=reserveEndTime] option')[count].value == "${menuPage.reserveEndTime}"){
			var selectedItem = $('select[name=reserveEndTime] option')[count];
			selectedItem.setAttribute("selected", "true");
			break;
		}
	}
	/* キャンセル期間 */
	for (var count = 0; count < length; count++){
		if($('select[name=cancelTime] option')[count].value == "${menuPage.cancelTime}"){
			var selectedItem = $('select[name=cancelTime] option')[count];
			selectedItem.setAttribute("selected", "true");
			$(".dayForm").css("display", "");
			break;
		}
	}
});

</script>
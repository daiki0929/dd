/**
 * 予約可能な日を表示します。
 */
$(function() {
	var mondayShopStatus = "${statusByDays.monday.shopStatus}";
	var sundayShopStatus = "${statusByDays.sunday.shopStatus}";
	var tuesdayShopStatus = "${statusByDays.tuesday.shopStatus}";
	var wednesdayShopStatus = "${statusByDays.wednesday.shopStatus}";
	var thursdayShopStatus = "${statusByDays.thursday.shopStatus}";
	var fridayShopStatus = "${statusByDays.friday.shopStatus}";
	var saturdayShopStatus = "${statusByDays.saturday.shopStatus}";

	$("#datepicker").datepicker({
		dateFormat : 'yy年mm月dd日', //表示フォーマット
		minDate : '-${reserveStartTime}s', //受付可能(現在から何日・何時間前までか)
		maxDate : '+${reserveEndTime}s', //受付可能(現在から何日・何時間後までか)
		beforeShowDay : function(day) {
			var result;
			switch (day.getDay()) {
			case 0: // 日曜日を選択できないようにする
				if(sundayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			case 1: // 月曜日を選択できないようにする
				if(mondayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			case 2: // 火曜日を選択できないようにする
				if(tuesdayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			case 3: // 水曜日を選択できないようにする
				if(wednesdayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			case 4: // 木曜日を選択できないようにする
				if(thursdayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			case 5: // 金曜日を選択できないようにする
				if(fridayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			case 6: // 土曜日を選択できないようにする
				if(saturdayShopStatus == "notOpen"){
					result = [ false ];
				}else{
					result = [ true ];
				}
				break;
			default:
				result = [ true ]; // それ以外は選択できる
			break;
			}
			return result;
		}
	});
});

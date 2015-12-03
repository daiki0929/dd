$(function() {
	
	$(".purchaseNew").click(function() {
		if ($("input[name=webpay-token]").val() != "") {
			if (confirm("入力されたカード情報で決済します。よろしいですか？")) {
				$("#purchaseNewRmUserId").val($(this).attr('data-id'));
				$("#form1").submit();
			}
		} else {
			alert("カード情報を入力してください。");
		}
	});

	$(".purchaseRegisterd").click(function() {
		if (confirm("登録済みのカードで決済します。よろしいですか？")) {
			$("#purchaseNewRmUserId").val($(this).attr('data-id'));
			$("#form2").submit();
		}
	});
});
	

	var doWebpayCreateToken = function() {
		
		return confirm("カード情報がセットされました。");
	};

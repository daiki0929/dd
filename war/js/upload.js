/* 読み込んだら自動でclassにuploadが付いているタグを
 * 非同期サブミットに変えます
 */
$.extend({
	/**
	 * @param selector <input type="file"/>を示すセレクタ
	 * @param channelToken レスポンスを返すチャネルのトークン
	 * @param params {userのID, use関連付けるID, before, success, error}
	 */
	upload : function(selector, channelToken, params) {
		var data = {};

		if(params != null){
			console.log(params);

			if(params.before != null){
				params.before.call();
			}
			if(params.user != null){
				data.user = params.user;
			}
			if(params.useId != null){
				data.use = params.use;
			}
		}

		$.post("/files/createUploadUrl", data, function( res ){
			if(res.status == 'success') {
				var uploadUrl = res.msg

				console.log($(selector).attr("name"));
				//チャネルトークンのコピー
				var channelToken = $('<input/>').attr('name','channelToken').val(channelToken);

				$form = $("<form/>")
						.append($(selector).clone())
						.append(channelToken)
						;
				//requestData = new FormData($form[0]);
				requestData = $("#formId");

				console.log(requestData);


				console.log(uploadUrl);

				$.ajax(uploadUrl, {
						type: 'post',
						processData: false,
						contentType: false,
						data: requestData,
						dataType: 'json',
						success: function(){
							if(params.success!=null){
								params.success.call($(selector),channelToken);
							}
						}
				});
			}
		}, "json");




	}
});

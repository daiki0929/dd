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
<%@ include file="/tools/rese/common/importJs.jsp"%>

<script src="/js/pickadate/picker.js"></script>
<script src="/js/pickadate/picker.date.js"></script>
<script src="/js/pickadate/legacy.js"></script>
<script src="/js/pickadate/ja_JP.js"></script>

<script src="/js/jq/jquery.validationEngine.js"></script>
<script src="/js/jq/jquery.validationEngine-ja.js"></script>
<link rel="stylesheet" href="/css/jq/validationEngine.jquery.css">

<script>
$(function(){
  jQuery("#form_1").validationEngine();
});
$(function() {
    $('#calendar').pickadate({
    	format: 'yyyy/mm/dd',
    	/* disable: [
    		1, 7
    	] */
    });
});
</script>
</head>
<body>
	<%@ include file="/tools/rese/common/topBar.jsp"%>
		<div class="container">
		<div class="span12">
			<h2>予約をすすめる</h2>
			<form action="/tools/rese/reserve/customer/confirmReserve" name="selectMenu" id="form_1">
				<div class="span7">
					<h3>メニュー・予約日時を選択して下さい</h3>
					<c:forEach var="menu" items="${menuList}">
						<label class="radio"> <input type="radio" name="orderMenu" value="${f:h(menu.key)}" class="menuRadio">
							${menu.title}(<span class="menuTime">${menu.time/60}</span>分)${menu.price}円
						</label>
					</c:forEach>
					<h4>日程を決める</h4>
					<fieldset class="fieldset">
					  <input id="calendar"  type="text" placeholder="クリックしてください" onchange="calculate();" name="reserveDate" class="validate[required]">
					</fieldset>
					
					<h4>時間を決める</h4>
					<select id="reserveMoments" name="reserveMoments" class="validate[required]">
					</select>
					<h3>連絡先を記入して下さい</h3>
					<h4>お名前</h4>
					<p>(例)田中太郎</p>
					<input type="text" name="customerName" class="validate[required]">
					<h4>メールアドレス</h4>
					<p>(例)sample@mail.com</p>
					<input type="text" name="customerMailadress" class="validate[required[custom[email]]]">
					<h4>電話番号</h4>
					<p>(例)000-0000-0000 「-」の記入を忘れないようにして下さい。</p>
					<input type="text" name="customerPhone" class="validate[custom[phoneHyphen]]">
					<br />
					<input type="submit" value="次へ">
				</div>
			</form>
		</div>
	</div>
</body>
</html>
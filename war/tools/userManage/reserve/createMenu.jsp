<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Index</title>
</head>
<body>
   <!-- エラーメッセージ -->
   <%@ include file="/tools/userManage/common/error.jsp" %>
   <div class="span12">
   <h3>メニュー作成</h3>
   	<form action="/tools/userManage/reserve/DoneCreateMenu" method="post">
   	<p>メニューの名前</p>
   	<input type="text" name="menuTitle">
   	<p>時間</p>
   	<input type="text" name="time">
   	<p>料金</p>
   	<input type="text" name="price">
   	<p>メニューの内容</p>
   	<input type="text" name="content">
   	<p>画像</p>
   	<input type="file" name="phone">
   	<input type="hidden" name="id" value="${f:h(id)}">
   	<br/>
   	<input type="submit" value="完了">
   	</form>
   </div>
</body>
</html>

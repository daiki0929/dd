<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<div id="data.obj.key" class="span8 singleMenu" style="border: 1px solid #dedede; margin-bottom: 20px;">
	<p style="background-color: #f3f3f3;">メニュー名：data.obj.title</p>
	<div class="span8">
		<p>
			時間：<span class="menuTime">data.obj.time</span>分
		</p>
		<p>料金：${menu.price}円</p>
		<a href="/tools/userManage/reserve/EditMenu?id=data.obj.key">編集する</a>
		<br />
		<p onclick="deleteMenu(data.obj.key)" class="btn btn-info">非公開にする</p>
	</div>
</div>
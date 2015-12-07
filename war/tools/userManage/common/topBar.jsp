<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>


<nav class="navbar navbar-fixed-top" style="background-color:#f2f2f2;">
	<div class="navbar-inner" style="padding: 10px 0;">
        <ul class="nav navbar-nav">
            <li><a href="#">タイトル</a></li>
            <li class="active"><a href="/tools/userManage/customerList">顧客</a></li>
            <li><a href="#">予約管理</a></li>
            <li><a href="/tools/userManage/reserve/menuPageList">予約ページ</a></li>
            <li><a href="#">メッセージ</a></li>
            <li><a href="/tools/userManage/setting">管理者ページ</a></li>
        </ul>
        <button type="button" class="btn btn-warning navbar-btn" style="float: right; margin-right: 5%;">予約ページ作成</button>
  </div>
</nav>

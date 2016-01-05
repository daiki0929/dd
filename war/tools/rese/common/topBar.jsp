<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>


<nav class="navbar navbar-fixed-top"">
	<div class="navbar-inner" style="padding: 10px 0; background-color: #16a085; background-image: none;">
        <ul class="nav navbar-nav">
            <li><a href="#" style="color:#fff;">タイトル</a></li>
            <li><a href="/tools/rese/customerManage/customerList" style="color:#fff;">顧客</a></li>
            <li><a href="/tools/rese/reserve/reserveList" style="color:#fff;">予約管理</a></li>
            <li><a href="/tools/rese/reserve/menuPageList" style="color:#fff;">予約ページ</a></li>
            <li><a href="#" style="color:#fff;">レポート</a></li>
            <li><a href="/tools/rese/setting" style="color:#fff;">管理者ページ</a></li>
        </ul>
        <button type="button" class="btn btn-warning navbar-btn" style="float: right; margin-right: 5%;">予約ページ作成</button>
  </div>
</nav>

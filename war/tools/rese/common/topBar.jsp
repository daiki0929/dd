<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>


<!-- <nav class="navbar navbar-fixed-top">
	<div class="navbar-inner" style="padding: 10px 0; background-color: #16a085; background-image: none;">
        <ul class="nav navbar-nav" style="background-color: #000;">
            <li><a href="#" style="color:#fff;">Rese</a></li>
            <li><a href="/tools/rese/reserve/reserveList" style="color:#fff;">予約管理</a></li>
            <li><a href="/tools/rese/customerManage/customerList" style="color:#fff;">顧客</a></li>
            <li><a href="/tools/rese/reserve/menuPageList" style="color:#fff;">予約ページ</a></li>
            <li><a href="#" style="color:#fff;">レポート</a></li>
            <li><a href="/tools/rese/setting" style="color:#fff;">管理者ページ</a></li>
        </ul>
        <button type="button" class="btn btn-warning navbar-btn" style="float: right; margin-right: 5%;">予約ページ作成</button>
  </div>
</nav>
 -->
 <div class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <a href="#" class="brand">Rese</a>
      <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
 
      <div class="nav-collapse collapse">
        <ul class="nav">
			<li><a href="/tools/rese/reserve/reserveList" style="color:#fff;">予約管理</a></li>
			<li><a href="/tools/rese/customerManage/customerList" style="color:#fff;">顧客</a></li>
			<li><a href="/tools/rese/reserve/menuPageList" style="color:#fff;">予約ページ</a></li>
			<li><a href="#" style="color:#fff;">レポート</a></li>
			<li><a href="/tools/rese/setting" style="color:#fff;">管理者ページ</a></li>
        </ul>
        <ul class="nav pull-right">
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              ユーザー名<span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <li><a href="/tools/rese/comeAndGo/logout">ログアウト</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </div>
</div>
 
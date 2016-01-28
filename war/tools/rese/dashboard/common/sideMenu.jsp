<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- **********************************************************************************************************************************************************
	 MAIN SIDEBAR MENU
*********************************************************************************************************************************************************** -->
 <!--sidebar start-->
 <aside>
     <div id="sidebar"  class="nav-collapse " style="background-color: #2c3e50;">
         <!-- sidebar menu start-->
         <ul class="sidebar-menu" id="nav-accordion">
         
         	  <p class="centered"><a href="profile.html"><img src="${userImgPath}" class="img-circle" width="60"></a></p>
         	  <h5 class="centered">${userName}</h5>
         	  	
             <li class="mt">
                 <a href="/tools/rese/reserve/reserveList">
                     <i class="fa fa-dashboard"></i>
                     <span>予約管理</span>
                 </a>
             </li>

             <li class="sub-menu">
                 <a href="/tools/rese/reserve/menuPageList">
                     <i class="fa fa-book"></i>
                     <span>予約ページ</span>
                 </a>
             </li>
             
             <li class="sub-menu">
                 <a href="/tools/rese/customerManage/CustomerList" >
                     <i class="fa fa-th"></i>
                     <span>顧客管理</span>
                 </a>
             </li>
             
             <li class="sub-menu">
                 <a href="/tools/rese/report/chart" >
                     <i class=" fa fa-bar-chart-o"></i>
                     <span>レポート</span>
                 </a>
             </li>
             
             <li class="sub-menu">
                 <a href="/tools/rese/editOperationHours">
                     <i class="fa fa-cogs"></i>
                     <span>営業時間</span>
                 </a>
             </li>

             <li class="sub-menu">
                 <a href="/tools/rese/editAcount">
                     <i class="fa fa-cogs"></i>
                     <span>アカウント情報</span>
                 </a>
             </li>
             
             <li class="sub-menu">
                 <a href="/tools/rese/reserve/showHowTo">
                     <i class="fa fa-cogs"></i>
                     <span>使い方ガイド</span>
                 </a>
             </li>
         </ul>
         <!-- sidebar menu end-->
     </div>
 </aside>
 <!--sidebar end-->
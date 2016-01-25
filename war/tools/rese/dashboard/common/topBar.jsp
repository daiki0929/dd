<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- **********************************************************************************************************************************************************
     TOP BAR CONTENT & NOTIFICATIONS
     *********************************************************************************************************************************************************** -->
 <!--header start-->
 <header class="header black-bg" style="background-color: #63A7E4; border-bottom: 1px solid #4c8fc9;">
         <div class="sidebar-toggle-box">
             <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
         </div>
       <!--logo start-->
       <a href="index.html" class="logo"><b>Rese</b></a>
       <!--logo end-->
       <div class="top-menu">
       	<ul class="nav pull-right top-menu">
               <li><a class="logout" href="/tools/rese/comeAndGo/logout" style="background-color: #2C3E50; border: none;">Logout</a></li>
       	</ul>
       </div>
   </header>
 <!--header end-->
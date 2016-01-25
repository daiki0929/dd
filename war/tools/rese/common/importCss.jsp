<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<link href="/css/bootstrap/bootstrap.css" rel="stylesheet">
	<link href="/css/jq/sort_style.css" rel="stylesheet">
	<link href="/css/jq/jquery.mmenu.css" type="text/css" rel="stylesheet" />
	<link href="/css/jq/jquery.mmenu.effects.css" type="text/css" rel="stylesheet" />
	<link href="/css/jq/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" />
	<link href="/css/animate.css" rel="stylesheet" />
	<link href="/css/bcc_random_banner.css" rel="stylesheet" />
	<link href="/css/global.css" rel="stylesheet" />
	<link href='https://fonts.googleapis.com/css?family=Merriweather+Sans' rel='stylesheet' type='text/css'>


	<style type="text/css">
	@media only screen and (max-width : 780px) {
		body {
		}
	}
	@media only screen and (min-width : 781px) {
		body {
			margin-top: 60px;
		}
	}
	body{
		 background-color: #f8f8f8;
	}
	.mainContent{
		min-height: 400px;
		background-color: #fff;
	}
	.sideBar{
		margin-top: 30px;
	}
	.minTitle{
		font-weight: bold;
	}
	.minTitleIcon{
		width: 18px;
		margin-right: 3px;
		margin-top: -2px;
	}
	/*
	Back to top button 
	*/
	#back-top {
	    position: fixed;
	    bottom: 3%;
	    right: 3%;
    }
	#back-top span {
	    width: 66px;
	    height: 60px;
	    display: block;
    }
	    
	    
	#footer{
		width: 100%;
		height: 100px;
		background-color: #7f8c8d;
		margin-top: 60px;
	}
	.button a {
	  width: 280px;
	  background-color: #f39c12;
	  border-radius: 5px;
	  color: #ffffff;
	  display: block;
	  font-size: 18px;
	  text-align: center;
	  text-decoration: none;
	  padding: 10px 0;
	}
	.button {
	  width: 280px;
	  background-color: #f39c12;
	  border-radius: 5px;
	  color: #ffffff;
	  display: block;
	  font-size: 18px;
	  text-align: center;
	  text-decoration: none;
	  padding: 5px 0;
	  cursor: pointer;
	}
	.button a:hover {
	  opacity:0.6;
	}
	.button:hover {
	  opacity:0.6;
	}
	.buttonMin-gray a {
	  width: 200px;
	  background-color: #bdc3c7;
	  border-radius: 5px;
	  color: #ffffff;
	  display: block;
	  font-size: 16px;
	  text-align: center;
	  text-decoration: none;
	  padding: 10px 0;
	}
	.buttonMin-gray {
	  width: 200px;
	  background-color: #bdc3c7;
	  border-radius: 5px;
	  color: #ffffff;
	  display: block;
	  font-size: 18px;
	  text-align: center;
	  text-decoration: none;
	  padding: 5px 0;
	  cursor: pointer;
	}
	.buttonMin-gray:hover {
	  opacity:0.6;
	}
	.buttonMin-gray a:hover {
	  opacity:0.6;
	}
	.buttonMin-blue {
	  width: 200px;
	  background-color: #295890;
	  border-radius: 5px;
	  color: #ffffff;
	  display: block;
	  font-size: 18px;
	  text-align: center;
	  text-decoration: none;
	  padding: 10px 0;
	  cursor: pointer;
	}
	.buttonMin-blue a:hover {
	  opacity:0.6;
	}
	.buttonMin-red {
	  width: 200px;
	  background-color: #e74c3c;
	  border-radius: 5px;
	  color: #ffffff;
	  display: block;
	  font-size: 18px;
	  text-align: center;
	  text-decoration: none;
	  padding: 10px 0;
	  cursor: pointer;
	}
	.buttonMin-red a:hover {
	  opacity:0.6;
	}
	
	.sideBar{
		margin-top: 20px;
	}
	.reservePageTitle{
		 color:#63A7E4;
	}
	 .reserveBtn{
		border-style: none;
		color: #fff;
		width: 50%;
		height: auto;
		padding: 10px;
		margin: 0 auto;
		background-color: #3498db;
	}
	.reserveBtn::before,
	.reserveBtn::after {
		position: absolute;
		z-index: -1;
		display: block;
		content: '';
	}
	.reserveBtn,
	.reserveBtn::before,
	.reserveBtn::after {
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		-webkit-transition: all .3s;
		transition: all .3s;
	}
	
	.reserveBtn:hover{
		opacity:0.5;
	}
	</style>

	<style type="text/css">
	</style>
	<link rel="stylesheet" href="/css/font/font-awesome.css" />
	<link rel="stylesheet" href="/css/font/font-awesome-ie7.min.css" />
	<link rel="stylesheet" href="/css/media_query.css" />
	<link rel="stylesheet" href="/css/iphone-style-checkboxes.css" />
	<link rel="stylesheet" href="/css/color.css" />
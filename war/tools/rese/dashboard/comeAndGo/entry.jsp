<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<!DOCTYPE html>
<html lang="jp">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Dashboard">
    <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>Rese - 会員登録</title>

    <!-- Bootstrap core CSS -->
    <link href="/tools/rese/dashboard/assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="/tools/rese/dashboard/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
        
    <!-- Custom styles for this template -->
    <link href="/tools/rese/dashboard/assets/css/style.css" rel="stylesheet">
    <link href="/tools/rese/dashboard/assets/css/style-responsive.css" rel="stylesheet">
	
	<style type="text/css">
		.btn-google:hover{
			background-color: #c4392f;
		}
	</style>
	<script type="text/javascript">
		function change(){
			$('[name=password]').attr("type", "text");
			$('#confirmText').attr("onclick", "reChange();");
		}
		function reChange(){
			$('[name=password]').attr("type", "password");
			$('#confirmText').attr("onclick", "change();");
		}
	</script>
	<!-- Facebook Conversion Code for 会員登録 -->
<script>(function() {
  var _fbq = window._fbq || (window._fbq = []);
  if (!_fbq.loaded) {
    var fbds = document.createElement('script');
    fbds.async = true;
    fbds.src = '//connect.facebook.net/en_US/fbds.js';
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(fbds, s);
    _fbq.loaded = true;
  }
})();
window._fbq = window._fbq || [];
window._fbq.push(['track', '6037421117372', {'value':'0.00','currency':'JPY'}]);
</script>
<noscript><img height="1" width="1" alt="" style="display:none" src="https://www.facebook.com/tr?ev=6037421117372&amp;cd[value]=0.00&amp;cd[currency]=JPY&amp;noscript=1" /></noscript>
  </head>

  <body>

      <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->

	  <div id="login-page">
	  	<div class="container">
	  	
		      <div class="form-login">
		        <h2 class="form-login-heading" style="background-color: #63A7E4;">sign up</h2>
		        <div class="login-wrap">
		        	<!-- エラーメッセージ -->
					<%@ include file="/tools/rese/common/error.jsp"%>
		      		<form class="form-login" action="/tools/rese/comeAndGo/doneEntry" method="post" style="margin-top:0;">
			            <p>名前</p>
						<input type="text" name="name" class="form-control" value="${name}">
						<p style="margin-top: 10px;">メールアドレス</p>
						<input type="text" name="mailaddress" class="form-control" value="${mailaddress}">
						<p style="margin-top: 10px;">パスワード<a onclick="change();" id="confirmText" style="cursor: pointer;"> (確認する)</a></p>
						<input type="password" name="password" class="form-control" value="${password}">
						<!-- <p style="margin-top: 10px;">パスワード(確認)</p>
						<input type="password" class="form-control" name="passwordConfirm"> -->
						<br />
			            <button class="btn btn-theme btn-block" type="submit" style="border: none; background-color: #63A7E4;"> Sign Up</button>
		            </form>
		            <hr/>
	             	<div class="login-social-link centered">
		            <p>SNSで会員登録する</p>
		                <!-- <a href="/tools/rese/comeAndGo/facebook/signIn?s=Rese"><p class="btn btn-facebook" style="border: none; width: 180px; margin: 5px 0;">Facebook</p></a>
		                <a href="/tools/rese/comeAndGo/twitter/signIn?s=Rese"><p class="btn btn-twitter" style="border: none; width: 180px; margin: 5px 0;">Twitter</p></a> -->
		                <a href="/tools/rese/comeAndGo/google/signIn"><p class="btn btn-twitter" style="background-color: #DB4437; border: none; width: 180px; margin: 5px 0;">Google</p></a>
		            </div>
		        </div>
		      </div>	  	
	  	
	  	</div>
	  </div>

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="/tools/rese/dashboard/assets/js/jquery.js"></script>
    <script src="/tools/rese/dashboard/assets/js/bootstrap.min.js"></script>

    <!--BACKSTRETCH-->
    <!-- You can use an image of whatever size. This script will stretch to fit in any screen size.-->
    <script type="text/javascript" src="/tools/rese/dashboard/assets/js/jquery.backstretch.min.js"></script>
    <script>
        $.backstretch("/img/rese/login_backImg2.png", {speed: 500});
    </script>


  </body>
</html>

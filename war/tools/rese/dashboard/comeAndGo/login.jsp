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

    <title>Rese - ログイン</title>

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
	//パスワードを送信します。
	function sendPassword(){
		var mailaddress = $('.modal-body input[name=email]').val();
		$.post("/tools/rese/sendPassword", {
			'mailaddress' : mailaddress
		}, function(data){
			switch(data.obj){
			case null:
				alert("エラーが発生しました。");
				break;

			default:
				$('#errorMsg p').remove();
				$('#errorMsg').append("<p style='color:red;'>" + data.msg + "</p>");
			break;
			}
		}, 'json');
	}
	</script>
  </head>

  <body>

      <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->

	  <div id="login-page">
	  	<div class="container">
	  	
		      <div class="form-login">
		        <h2 class="form-login-heading" style="background-color: #63A7E4;">sign in now</h2>
		        <div class="login-wrap">
		        	<!-- エラーメッセージ -->
					<%@ include file="/tools/rese/common/error.jsp"%>
		      		<form class="form-login" action="/tools/rese/comeAndGo/doneLogin" method="post" style="margin-top:0;">
			            <input type="text" name="mailaddress" class="form-control" placeholder="メールアドレス" autofocus value='${mailaddress}'>
			            <br>
			            <input type="password" name="password" class="form-control" placeholder="パスワード" value='${password}'>
			            <label class="checkbox">
			                <span class="pull-right">
			                    <a data-toggle="modal" href="login.html#myModal"> パスワードを忘れましたか？</a>
			
			                </span>
			            </label>
			            <button class="btn btn-theme btn-block" type="submit" style="border: none; background-color: #63A7E4;"><i class="fa fa-lock"></i> LOGIN</button>
		            </form>
		            <hr>
		            
		            <div class="login-social-link centered">
		            <p>SNSでログインする</p>
		                <!-- <a href="/tools/rese/comeAndGo/facebook/signIn?s=Rese"><p class="btn btn-facebook" style="border: none; width: 180px; margin: 5px 0;">Facebook</p></a>
		                <a href="/tools/rese/comeAndGo/twitter/signIn?s=Rese"><p class="btn btn-twitter" style="border: none; width: 180px; margin: 5px 0;">Twitter</p></a> -->
		                <a href="/tools/rese/comeAndGo/google/signIn"><p class="btn btn-twitter" style="background-color: #DB4437; border: none; width: 180px; margin: 5px 0;">Google</p></a>
		            </div>
		            <div class="registration">
		                <a class="" href="/tools/rese/comeAndGo/entry">
		                    会員登録をする
		                </a>
		            </div>
		
		        </div>
		
		          <!-- Modal -->
		          <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
		              <div class="modal-dialog">
		                  <div class="modal-content">
		                      <div class="modal-header">
		                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                          <h4 class="modal-title">パスワードを忘れましたか？</h4>
		                      </div>
		                      <div class="modal-body">
		                          <p>メールアドレスをご記入ください。パスワードを送信します。</p>
		                          <div id="errorMsg"></div>
		                          <input type="text" name="email" placeholder="Email" autocomplete="off" class="form-control placeholder-no-fix">
		                      </div>
		                      <div class="modal-footer">
		                          <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
		                          <p class="btn btn-theme" onclick="sendPassword();">Submit</p>
		                      </div>
		                  </div>
		              </div>
		          </div>
		          <!-- modal -->
		
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

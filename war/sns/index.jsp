<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>TwitterAPI</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<!-- メイン -->
			<div class="span12">
				<tag:notloggedin>
					<a href="/api/twitter/SignIn"><img src="http://akahoshitakuya.com/wp-content/uploads/2012/03/redirect-1.jpeg" /></a>
				</tag:notloggedin>
				<tag:loggedin>
					<h1>Welcome ${twitter.screenName} (${twitter.id})</h1>

					<form action="/api/tweet" method="post">
						<textarea cols="80" rows="2" name="text"></textarea>
						<input type="submit" name="post" value="update" />
					</form>
					<a href="/api/twitter/logOut">logout</a>
				</tag:loggedin>
			</div>
		</div>
	</div>
</body>
</html>

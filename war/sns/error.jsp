<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>TwitterAPIエラー</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">

			<!-- メイン -->
			<div class="span12">
				<c:if test="${not empty errors}">
					<p class="errorTitle">エラーが発生しました。</p>
					<div class="error">
						<div class="errorTitle">
							<div class="errorHonbun">
								${exception.message}
							</div>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>

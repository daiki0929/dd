<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<%-- META情報 --%>
<%@ include file="/tools/rese/common/meta.jsp"%>
<title>SEOチェッカー</title>
<!-- css -->
<%@ include file="/tools/rese/common/importCss.jsp"%>
<style type="text/css">
.zoom {
    transform:scale(0.55);
    -o-transform:scale(0.55);
    -webkit-transform:scale(0.55);
    -moz-transform:scale(0.55);
    -ms-transform:scale(0.55);
    transform-origin:0 0;
    -o-transform-origin:0 0;
    -webkit-transform-origin:0 0;
    -moz-transform-origin:0 0;
    -ms-transform-origin:0 0;
}

</style>

<%-- JSインポート --%>
<%@ include file="/tools/rese/common/importJs.jsp"%>

<script type="text/javascript">
$('a[data-toggle="tab"]').on('shown', function (e) {
	 // クリックされたタブ名はこんな感じで取得する。
	 var tabName = e.target.href;
	 var items = tabName.split("#"); // activated tab

	 // 処理内容を書く

	 //e.relatedTarget // 直前のタブはこんなんで取得可能らしい
	});


function searchSeo(){
    var siteUrl = $("[name=siteUrl]").val();
    var seoChekiUrl = "http://seocheki.net/site-check.php?u=" + siteUrl;
    var mozUrl = "https://moz.com/researchtools/ose/links?site=" + siteUrl + "&filter=&source=external&target=page&group=0&page=1&sort=page_authority&anchor_id=&anchor_type=&anchor_text=&from_site=";
    var similarWebUrl = "http://www.similarweb.com/website/" + siteUrl;
    var seoKicksSiteUrl = siteUrl.replace(/(http:\/\/|https:\/\/)/g,"");
    console.log(seoKicksSiteUrl);
    var seoKicksUrl = "https://en.seokicks.de/backlinks/" + seoKicksSiteUrl;
    var webArchiveUrl = "http://web.archive.org/web/*/" + siteUrl;
    $('iframe[name=seoCheki]').attr('src',seoChekiUrl).load(function(){ 
        var html_body = $(this).contents().find('body').html(); 
        alert(html_body); 
    }); 
    $('iframe[name=moz]').attr('src',mozUrl).load(function(){ 
        var html_body = $(this).contents().find('body').html(); 
        alert(html_body); 
    }); 
    $('iframe[name=similarWeb]').attr('src',similarWebUrl).load(function(){ 
        var html_body = $(this).contents().find('body').html(); 
        alert(html_body); 
    }); 
    $('iframe[name=seoKicks]').attr('src',seoKicksUrl).load(function(){ 
        var html_body = $(this).contents().find('body').html(); 
        alert(html_body); 
    }); 
    $('iframe[name=webArchive]').attr('src',webArchiveUrl).load(function(){ 
        var html_body = $(this).contents().find('body').html(); 
        alert(html_body); 
    }); 
}
</script>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-46662474-31', 'auto');
  ga('send', 'pageview');

</script>
</head>
<body>
<%@ include file="/tools/seoChecker/common/topBar.jsp"%>
	<div class="container span12" style="margin-bottom: 40px;">
		<div class="span12">
		<div class="ninja_onebutton">
			<script type="text/javascript">
			//<![CDATA[
			(function(d){
			if(typeof(window.NINJA_CO_JP_ONETAG_BUTTON_3e1b3115456f3336867a0d3e26a1c40b)=='undefined'){
			    document.write("<sc"+"ript type='text\/javascript' src='\/\/omt.shinobi.jp\/b\/3e1b3115456f3336867a0d3e26a1c40b'><\/sc"+"ript>");
			}else{
			    window.NINJA_CO_JP_ONETAG_BUTTON_3e1b3115456f3336867a0d3e26a1c40b.ONETAGButton_Load();}
			})(document);
			//]]>
			</script>
			<span class="ninja_onebutton_hidden" style="display:none;"></span>
			<span style="display:none;" class="ninja_onebutton_hidden"></span>
		</div>
       	<input type="text" name="siteUrl" onchange="searchSeo();" style="width: 400px; height:30px; padding: 10px 10px 10px 5px; margin-top: 40px;">
        <p onclick="searchSeo();" class="btn btn-info" style="margin-top: 20px;">チェック！</p>
        <div class="tabbable" style="margin-top: 20px;">
		 <ul class="nav nav-tabs">
		  <li class="active"><a href="#home" data-toggle="tab">SEOチェキ</a></li>
		  <li><a href="#tab1" data-toggle="tab">MOZ</a></li>
		  <li><a href="#tab2" data-toggle="tab">SimilarWeb</a></li>
		  <li><a href="#tab3" data-toggle="tab">SEOkicks</a></li>
		  <li><a href="#tab4" data-toggle="tab">WebArchive</a></li>
		 </ul>
		 <div id="my-tab-content" class="tab-content">
		  <div class="tab-pane active" id="home">
		    <div style="width:700px;height:600px;overflow-x:hidden;border:1px solid #999">
	            <div style="width:700px;height:3081px;overflow:hidden;">
	                <iframe name="seoCheki" class="zoom" frameborder="0" scrolling="no" width="1290px" height="5603px" ></iframe>
	            </div>
        	</div>
		  </div>
		  <div class="tab-pane" id="tab1">
		    <div style="width:700px;height:600px;overflow-x:hidden;border:1px solid #999">
	            <div style="width:700px;height:3081px;overflow:hidden;">
	                <iframe name="moz" class="zoom" frameborder="0" scrolling="no" width="1290px" height="5603px" ></iframe>
	            </div>
        	</div>
		  </div>
		  <div class="tab-pane" id="tab2">
		    <div style="width:700px;height:600px;overflow-x:hidden;border:1px solid #999">
	            <div style="width:700px;height:3081px;overflow:hidden;">
	                <iframe name="similarWeb" class="zoom" frameborder="0" scrolling="no" width="1290px" height="5603px" ></iframe>
	            </div>
        	</div>
		  </div>
		  <div class="tab-pane" id="tab3">
		    <div style="width:700px;height:600px;overflow-x:hidden;border:1px solid #999">
	            <div style="width:700px;height:3081px;overflow:hidden;">
	                <iframe name="seoKicks" class="zoom" frameborder="0" scrolling="no" width="1290px" height="5603px" ></iframe>
	            </div>
        	</div>
		  </div>
		  <div class="tab-pane" id="tab4">
		    <div style="width:700px;height:600px;overflow-x:hidden;border:1px solid #999">
	            <div style="width:700px;height:3081px;overflow:hidden;">
	                <iframe name="webArchive" class="zoom" frameborder="0" scrolling="no" width="1290px" height="5603px" ></iframe>
	            </div>
        	</div>
		  </div>
		 </div>
		</div>
	</div>
	</div>
</body>
</html>
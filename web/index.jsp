<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=emulateIE7" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>SDUView Searcher</title>
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<link href="css/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="container">
		<div id="bd">
	        <div id="main">
	        	<h1 class="title">
	            	<div class="logo large"></div>
	            </h1>
	            <form action = "loginAction.do">
		            <div class="inputArea">
		            	<input type="text" class="searchInput" name="inputMessage"/>
		                <input type="submit" class="searchButton" text = ""/>
		            </div>
	            </form>
	        </div><!-- End of main -->
	    </div><!--End of bd-->
	    
	    <div class="foot">
	    	<div class="wrap">
	            <div class="copyright"></div>
	        </div>
	    </div>
	</div>
</body>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/global.js"></script>

</html>
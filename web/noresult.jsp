<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=emulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SDUView Searcher</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/result.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="container">
	<div id="hd" class="ue-clear">
    	<div class="logo"></div>
    	<form action = "loginAction.do">
        <div class="inputArea">
        	<input type="text" class="searchInput" name="inputMessage" />
            <input type="submit" class="searchButton" />
        </div>
        </form>
    </div>

	<div id="bd" class="ue-clear">
        <div id="main">
            <div class="resultArea">
            	<p class="resultTotal">
                	<span class="info">没有找到相关内容</span>
                </p>												
 					
            </div>
        </div><!-- End of main -->
    </div><!--End of bd-->
</div>

<div id="foot"></div>
</body>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/pagination.js"></script>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=emulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
                	<span class="info">找到约&nbsp;<span class="totalResult"><%=request.getAttribute("news total") %></span>&nbsp;条结果(用时<span class="time"><%=request.getAttribute("search time") %></span>毫秒)</span>
                </p>
				
				
				
                <div class="resultList">
                    
                    <%
                    for(int i = 0;i<Integer.parseInt(request.getAttribute("news total").toString());i++){
                    	
                    	out.println("<div class=\"resultItem\">");
                    	
                    	out.println("<div class=\"itemHead\">");
                    	out.println("<a href=\""+request.getAttribute("news url"+i)+"\""+"  target=\"_blank\" class=\"title\">");
                    	//out.println("<span class=\"keyWord\">");
                    	out.println(request.getAttribute("news title"+i));
                    	out.println("<br>");
                    	//out.println("</span>");
                    	out.println("</a>");
                    	out.println("</div>");
                    	
                    	out.println("<div class=\"itemBody\">");
                    	String newsbody = request.getAttribute("news content"+i).toString();
                    	if(newsbody.length()>81)
                    		newsbody = newsbody.substring(0, 80);
                    	out.println(newsbody);
                    	out.println("<br>");
                    	out.println("</div>");
                    	
                    	out.println("<div class=\"itemFoot\">");
                    	//out.println("<span class=\"info\">");
                    	out.println("<label>发布时间：</label>");
                    	//out.println("<span class=\"value\">");
                    	out.println(request.getAttribute("news date"+i));
                    	out.println("<label>点击次数：</label>");
                    	out.println(request.getAttribute("news click"+i));
                    	//out.println("<br>");
                    	//out.println("<br>");
                    	//out.println("</span>");
                    	//out.println("</span>");
                    	out.println("</div>");
                    	
                    	out.println(" </div>");
                    }
                    %>
                    	
                   
                </div>
 					
            </div>
        </div><!-- End of main -->
    </div><!--End of bd-->
	<%@ include file="pages.jsp" %>
</div>

<div id="foot"></div>
</body>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/pagination.js"></script>

</html>
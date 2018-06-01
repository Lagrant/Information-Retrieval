<%@ page language="java"  contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<c:if test="${requestScope.maxPages != 1}">
    <%
        int currentPage = Integer.parseInt(request.getAttribute("currentPage").toString());
        int maxPages = Integer.parseInt(request.getAttribute("maxPages").toString());
        String inputMessage = request.getAttribute("inputMessage").toString();
    %>
    <div style="margin-left: 120px">
        <table >
            <tr>
                <td width="70">
                    每页${requestScope.itemsPerPage}条
                </td>
                <td width="50">
                    第${requestScope.currentPage }页
                </td>
                <td width="70">
                    共${requestScope.maxPages }页
                </td>
                <td width="70">
                    <c:choose>
                        <c:when test="${requestScope.currentPage == 1}">
                            首页 上一页
                        </c:when>

                        <c:otherwise>
                            <A HREF="loginAction.do?inputMessage=<%=inputMessage%>&currentPage=1">首页</A>
                            <A HREF="loginAction.do?inputMessage=<%=inputMessage%>&currentPage=<%=currentPage-1%>">上一页</A>
                        </c:otherwise>
                    </c:choose>
                </td>
                <%
                    for(int i = currentPage; i < currentPage+10; i++){
                %>
                <td width="30"><a href="loginAction.do?inputMessage=<%=inputMessage%>&currentPage=<%=i%>"><%=i%></a></td>
                <%
                    }
                %>
                <td width="70">
                    <c:choose>
                        <c:when test="${requestScope.currentPage == requestScope.maxPages}">
                            下一页 尾页
                        </c:when>

                        <c:otherwise>
                            <A HREF="loginAction.do?inputMessage=<%=inputMessage%>&currentPage=<%=currentPage+1%>">下一页</A>
                            <A HREF="loginAction.do?inputMessage=<%=inputMessage%>&currentPage=<%=maxPages%>">尾页</A>
                        </c:otherwise>
                    </c:choose>
                </td>
                <%
                    request.setAttribute("currentPage",currentPage);
                %>

                    <%--</td>  --%>
            </tr>
        </table>
    </div>
</c:if>

</body>
</html>
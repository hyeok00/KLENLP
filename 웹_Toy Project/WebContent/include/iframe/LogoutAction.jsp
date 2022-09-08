<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="alba.user" %>
<%@ page import="java.io.PrintWriter" %>

<jsp:useBean id="user" class="alba.user" scope="session"/>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
	session.invalidate();
	%>
	<script>
		location.href='/Web_Project/page/borad_page1.jsp';
		
	</script>

</body>
</html>
<%@page import="java.lang.ProcessBuilder.Redirect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>

<%@ page import="alba.boradDTO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="alba.boradDAO"%>
<%@ page import="java.io.PrintWriter" %>

<%@ page import="java.io.*" %>

<%
	int no = Integer.parseInt(request.getParameter("no"));
	System.out.println(no);
	int category = Integer.parseInt(request.getParameter("category"));
	
	boradDAO bao=new boradDAO();
	bao.delboard(no, category);
	
	String s="/Web_Project/include/iframe/table"+category+".jsp";
	System.out.print(s);
	response.sendRedirect(s);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
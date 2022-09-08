<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="alba.user" %>
<%@ page import="alba.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>


<jsp:useBean id="user" class="alba.user" scope="page"/>
<jsp:setProperty name="user" property="email"/>
<jsp:setProperty name="user" property="answer"/>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body> 
	<form id="find-id-form" action="checkID.jsp" method="post">
	<%
	
	String result="";

	if ( user.getAnswer()==null && user.getEmail()==null){
		
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력이 안 된 사항이 있습니다.')");
		script.println("history.back()");
		script.println("</script>");
	} 
	
	else{
	UserDAO userDAO = new UserDAO(); //인스턴스생성
	result = userDAO.find_Id(user.getEmail(),user.getAnswer());				
	}
	out.println("<script>alert('귀하의 ID는" +result +"입니다.'); location.href='/Web_Project/include/iframe/login.jsp';</script>");

%>
</form>
	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="alba.user" %>
<%@ page import="alba.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>


<jsp:useBean id="user" class="alba.user" scope="session"/>
<jsp:setProperty name="user" property="id" />
<jsp:setProperty name="user" property="pw"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
		
	UserDAO userDAo = new UserDAO();
	String userID=null;

	if(session.getAttribute("id")!=null){
		userID=(String)session.getAttribute("id");
	}
	
	if(userID!=null){
		PrintWriter script = response.getWriter();

		script.println("<script>");
		script.println("alert('이미 로그인 되어있습니다.')");
		script.println("location.href = '/Web_Project/page/borad1.jsp'");
		script.println("</script>");	
	}
	
	int result=userDAo.Login(user.getId(),user.getPw());
		
		if(result == 1){
			session.setAttribute("id", user.getId()); 
			response.sendRedirect("/Web_Project/page/borad_page1.jsp");
		}
		
		else if(result == 0){
			PrintWriter script = response.getWriter();

			script.println("<script>");
			script.println("alert('비밀번호가 틀립니다.')");
			script.println("history.back()");
			script.println("</script>");
		}

		//아이디 없음

		else if(result == -1){
			PrintWriter script = response.getWriter();

			script.println("<script>");
			script.println("alert('존재하지 않는 아이디 입니다.')");
			script.println("history.back()");
			script.println("</script>");
		}

		//DB오류

		else if(result == -2){
			PrintWriter script = response.getWriter();
			
			script.println("<script>");
			script.println("alert('DB오류가 발생했습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}		

	%>
</body>
</html>
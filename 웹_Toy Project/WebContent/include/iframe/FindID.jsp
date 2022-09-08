<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 
<%@ page import="java.io.PrintWriter" %>
<%request.setCharacterEncoding("UTF-8");%>



<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" href="/Web_Project/resources/css/findIdform.css">
</head>
<body>
	<form id="find-id-form" action="FindID_Action.jsp" method="post">
		<div class="user">
			<label class="legend">이메일</label> <input id="email" name="email"
				type="email" >
		</div>
		<div class="user">
			<label class="legend">질문에 대한 답</label> <input id="answer" name="answer"
				type="text" >
		</div>
		<input id="find_id_btn" type="submit" value="아이디 찾기" >
		</form>
</body>
</html>
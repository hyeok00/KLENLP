<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>

<head>
<title>login</title>
<link rel="stylesheet" href="/Web_Project/resources/css/login.css">
</head>

<body>
	<form id="login-form" action="LoginAction.jsp" method="post">
		<div class="user">
			<label class="legend">아이디</label> <input id="id" name="id"
				type="text">
		</div>
		<div class="user">
			<label class="legend">비밀번호</label> <input id="pw" name="pw"
				type="password">
		</div>
		<input id="login_btn" type="submit" value="로그인" >
		

		<div class="position_a">
			<div class="find_info">
				<a  id="signup"
					href="/Web_Project/include/iframe/sign_up.jsp">회원가입</a>
				<span class="bar" aria-hidden="true">|</span> <a target="_blank"
					id="findId" href='/Web_Project/include/iframe/FindID.jsp'>아이디찾기</a>
			</div>
		</div>

	</form>
 	

</body>

</html>
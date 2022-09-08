<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>sign_up</title>
<link rel="stylesheet" href="/Web_Project/resources/css/sign_up.css">
</head>

<body>
   <form id="login-form" method="post" action="joinAction.jsp">
      <div id="title">회 원 가 입</div>
      <div class="user" >
         <label id="id">아이디</label> <input class="input" name="id" type="text"
            required>
      </div>
      <div class="user" >
         <label id="pw" >비밀번호</label> <input class="input" name="pw"
            type="password" required>
      </div>
      <div class="user" >
         <label id="name" >이름</label> <input class="input" name="name" type="text"
            required>
      </div>
      <div class="user" >
         <label id="mail">이메일</label> <input class="input" name="email"
            type="email" required>
      </div>
      <div class="user">
         <label id="answer">당신이 좋아하는 음식은 무엇입니까?</label>
      </div>
      <div class="user">
         <input id="input_answer"  name="answer" type="text" required>
      </div>


      <button id="sign_up_btn">가입</button>

   </form>

</body>

</html>
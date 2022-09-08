<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>

    
<%@ page import="alba.user" %>
<%@ page import="alba.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>


<jsp:useBean id="user" class="alba.user" scope="page"/>
<jsp:setProperty name="user" property="id"/>
<jsp:setProperty name="user" property="pw"/>
<jsp:setProperty name="user" property="name" />
<jsp:setProperty name="user" property="email"/>
<jsp:setProperty name="user" property="answer"/>
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body> 

<%

   String userID=null;
   /* if(session.getAttribute("id")!=null){
      userID=(String)session.getAttribute("id");
   }
   
   if(userID!=null){
      PrintWriter script = response.getWriter();

      script.println("<script>");
      script.println("alert('이미 로그인 되어있습니다.')");
      script.println("location.href = '/Web_Project/page/borad_page.jsp'");
      script.println("</script>");   


   } */
   
   if ( user.getAnswer()==null || user.getEmail() ==null || user.getId()==null || user.getName()==null || user.getPw()==null){
      
      PrintWriter script = response.getWriter();
      
      script.println("<script>");
      script.println("alert('입력이 안 된 사항이 있습니다.')");
      script.println("history.back()");
      script.println("</script>");
   } 
   
   else{

   UserDAO userDAO = new UserDAO(); //인스턴스생성

   int result = userDAO.join(user);            

   if(result == -1){ // 아이디가 기본키기. 중복되면 오류.
      PrintWriter script = response.getWriter();

      script.println("<script>");
      script.println("alert('이미 존재하는 아이디 입니다.')");
      script.println("history.back()");
      script.println("</script>");
   }

   //가입성공

   else {
      PrintWriter script = response.getWriter();
      response.sendRedirect("/Web_Project/page/main_page.jsp");
   }
   }


%>

</body>
</html>
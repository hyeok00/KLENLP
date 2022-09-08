<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%
   request.setCharacterEncoding("UTF-8");
%>

<html>
<head>
<link rel="stylesheet" href="/Web_Project/resources/css/write.css">

</head>

<body>
   <div id="head">글쓰기</div>
   <hr>
   <hr>
   <form
      action="/Web_Project/boradRegister.jsp?category=<%=Integer.parseInt(request.getParameter("category"))%>"
      method="post">
      <div>
         <label>제목</label> <input id="title" type="text"
            placeholder="제목을 입력하세요. " name="title" />
      </div>
      <hr>
      <hr>
      <div>
         <label>폰트 크기</label> <select id="font_size" name="size"
            onchange="chageLangSelectSize()"> <!-- onchange="chageLangSelectSize()" -->
            <option value="16">16px</option>
            <option value="24" selected>24px</option>
            <option value="32">32px</option>
            <option value="40">40px</option>
            <option value="48">48px</option>
            <option value="64">64px</option>
         </select> <label>폰트 색깔</label> <select id="font_color" name="color"
            onchange="chageLangSelectColor()">
            <option value="red">빨강</option>
            <option value="blue">파랑</option>
            <option value="yellow">노랑</option>
            <option value="green">초록</option>
            <option value="white">하양</option>
            <option value="black" selected>검정</option>
         </select> <label>배경 색깔</label> <select id="background_color" name="background"
            onchange="chageLangSelectBackground()">
            <option value="red">빨강</option>
            <option value="blue">파랑</option>
            <option value="yellow">노랑</option>
            <option value="green">초록</option>
            <option value="white" selected>하양</option>
            <option value="black">검정</option>
         </select> <label>정렬 기준</label> <select id="text_align" name="align"
            onchange="chageLangSelectAlign()">
            <option value="center">가운데 정렬</option>
            <option value="left">왼쪽 정렬</option>
            <option value="right">오른쪽 정렬</option>
         </select>
         <script src="/Web_Project/resources/js/write.js"></script>
      </div>
      <hr>
      <hr>
      <div>
         <textarea id="content" placeholder="내용을 입력하세요. " name="content"></textarea>
      </div>

      <input type="submit" value="게시하기"></input> <input type="reset"
         value="다시하기"></input>
   </form>


   </div>
</body>

</html>

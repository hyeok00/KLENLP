<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%
   request.setCharacterEncoding("UTF-8");
%>
<%@ page import="alba.articleDAO"%>
<%@ page import="alba.articleDTO"%>

<%
   int changeCount = 0;
   int bbsID = Integer.parseInt(request.getParameter("no"));
   int bbsCategory = Integer.parseInt(request.getParameter("category"));

   System.out.print(bbsID);
   articleDTO adt = new articleDAO().loadArticle(bbsID, bbsCategory);
   System.out.print(bbsID);
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
      action="/Web_Project/boradUpdate.jsp?category=<%=bbsCategory%>&no=<%=bbsID%>"
      method="post">
      <div>
         <label>제목</label> <input id="title" type="text"
            placeholder="제목을 입력하세요. " name="title" required />
      </div>
      <hr>
      <hr>
      <div>
         <label>폰트 크기</label> <select id="font_size" name="size"
            onchange="chageLangSelectSize()">
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
      <%
         if (changeCount == 0) {
      %>
      <div>
         <textarea id="content" name="content"
            style="font-size:<%=adt.getSize()%>px; color:<%=adt.getColor()%>; background-color:<%=adt.getBackground()%>; text-align:<%=adt.getAlign()%>">
               <%=adt.getArticle()%></textarea>
      </div>
      <%
         } else {
      %>
      <div>
         <textarea id="content" placeholder="내용을 입력하세요. " name="content"></textarea>
      </div>
      <%
         }
         changeCount++;
      %>
      <input type="submit" value="수정하기"></input> <input type="reset"
         value="다시하기"></input>
   </form>


   </div>
</body>

</html>

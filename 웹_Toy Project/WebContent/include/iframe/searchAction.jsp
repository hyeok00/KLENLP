<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<%@ page import="alba.boradDAO" %>
<%@ page import="alba.boradDTO" %>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/Web_Project/resources/css/table.css">
<title>Insert title here</title>
</head>
<body>
 <%  

    boradDAO dao = new boradDAO();
    String type=request.getParameter("type");
    String SearchContent=request.getParameter("text");
    int category = Integer.parseInt(request.getParameter("category"));
    System.out.print(request.getParameter("category"));
    ArrayList<boradDTO> dtos = new ArrayList<boradDTO>();
    
  
       if(type.equals("title")) {         
          type="title";
          dtos = dao.search(SearchContent, type,category);             
       }
       
       else if (type.equals("writer")){         
          type="writer";
          dtos = dao.search(SearchContent,type,category);     
       }
 
 %>
 <table class="type04">
      <colgroup>
         <col width="50" />
         <col width="450" />
         <col width="100" />
         <col widht="100" />
         <col width="50" />
      </colgroup>

      <thead>
         <tr>
            <th style="width: 15%">번호</th>
            <th style="width: 45%">제목</th>
            <th style="width: 10%">작성자</th>
            <th style="width: 15%">날짜</th>
            <th style="width: 15%">조회수</th>
         </tr>
      </thead>


      <tbody class="table_tbody">
         <%    // type : 페이지 종류, pageNum : 아래 페이지 번호 개수 조정, pageSize : 한 페이지 보여지는 게시글 수 <- 이거만 건들일 것 

            for (int i = 0; i<dtos.size(); i++) {
               boradDTO dto = dtos.get(i);

               int no = dto.getNo();
               String title = dto.getTitle();
               String writer = dto.getWriter();
               String writedate = dto.getWritedate();
               int count = dto.getCount();

               out.print("<tr>");
               out.print("<td>" + no + "</td>");
               out.print("<td> <a href=/Web_Project/include/iframe/borad.jsp?no="
                     + no + "&category="+category +">" + title
                     + "</a></td>");
               out.print("<td>" + writer + "</td>");
               out.print("<td>" + writedate + "</td>");
               out.print("<td>" + count + "</td>");
               out.print("</tr>");
            }
         %>
      </tbody>
    </table>
 
    <form class="bottom" action="searchAction.jsp">
      <select id="search_select" name="type">
         <option value="title" >제목</option>
         <option value="writer" >닉네임</option>
      </select>
       <input id="search" name="text" type="text">
      <button id="search_btn" name="category" value="<%=category%>">검색</button>     
   </form>
</body>

</html>
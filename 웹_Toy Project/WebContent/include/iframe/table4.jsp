<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>

<%@ page import="alba.boradDTO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="alba.boradDAO"%>
<%@ page import="java.io.PrintWriter"%>

<head>
<link rel="stylesheet" href="/Web_Project/resources/css/table.css">

</head>

<body>
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
            boradDAO dao = new boradDAO();

            int type = 4; // 페이지 분류 번호
            int pageNum = 3; // 아래 페이지 번호 최대 개수
            int curPage = 1; // 현재 페이지
            int absolutenum = 1; // 페이지 번호
            int pageSize = 5; // 페이지 사이즈
            int totalPage = dao.getTotalPage(type, pageSize); // 총 페이지 수

            if (request.getParameter("curPage") != null) {
               curPage = Integer.parseInt(request.getParameter("curPage"));
               absolutenum = (curPage - 1) * pageSize + 1;
            }

            ArrayList<boradDTO> dtos = dao.loadBorad(type, absolutenum, pageSize);

            for (int i = 0; i <dtos.size(); i++) {
               boradDTO dto = dtos.get(i);

               int no = dto.getNo();
               String title = dto.getTitle();
               String writer = dto.getWriter();
               String writedate = dto.getWritedate();
               int count = dto.getCount();

               out.print("<tr>");
               out.print("<td>" + no + "</td>");
               out.print("<td> <a href=/Web_Project/include/iframe/borad.jsp?no="
                     + no + "&category="+ type +">" + title
                     + "</a></td>");
               out.print("<td>" + writer + "</td>");
               out.print("<td>" + writedate + "</td>");
               out.print("<td>" + count + "</td>");
               out.print("</tr>");
            }
         %>
      </tbody>


   </table>
   <form class="bottom" action="searchAction.jsp?">
		<select id="search_select" name="type">
			<option value="title">제목</option>
			<option value="writer">닉네임</option>
		</select> <input id="search" name="text" type="text">
		<button id="search_btn" name="category" value="4">검색</button>
	</form>

      <button id="write_btn" onclick='click_write()'>글쓰기</button>
   <!--페이지번호-->
   <div class="pagination">
      <%
         int stratPage = curPage - ((curPage - 1) % pageNum);
         if (stratPage - pageNum > 0) {
            curPage = stratPage - 2;
         }
      %>
      <a href="table4.jsp?curPage=<%=curPage%>">◀</a>
      <%
         for (int i = stratPage; i < stratPage + pageNum; i++) {
      %>
      <a href="table4.jsp?curPage=<%=i%>"><%=i%></a>
      <%
         if (i >= totalPage)
               break;
         }
         if (stratPage + pageNum <= totalPage) {
            curPage = stratPage + pageNum;
      %>
      <a href="table4.jsp?curPage=<%=curPage%>">▶</a>
      <%
         }
      %>

   </div>

</body>
<script>
   function click_write(){
      
      if(<%=session.getAttribute("id") != null%>){
         location.href='/Web_Project/include/iframe/write.jsp?category=4'
      }else  {
         alert('로그인 후 이용가능합니다.');
      }   
    }
</script>
</html>






<%@page import="java.nio.channels.SeekableByteChannel"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="alba.articleDAO"%>
<%@ page import="alba.articleDTO"%>
<%@ page import="alba.commentDAO"%>
<%@ page import="alba.commentDTO"%>
<%@ page import="java.io.*"%>

<%@ page import="java.io.PrintWriter"%>
<%
	request.setCharacterEncoding("UTF-8");
%>

<%
	int bbsID = Integer.parseInt(request.getParameter("no"));
	int bbsCategory = Integer.parseInt(request.getParameter("category"));

	articleDTO adt = new articleDAO().loadArticle(bbsID, bbsCategory);
	ArrayList<commentDTO> cdt = new commentDAO().loadComment(bbsID, bbsCategory);
%>
<jsp:useBean id="articledto" class="alba.articleDTO" scope="page" />

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/Web_Project/resources/css/borad.css">

</head>

<body>
	<div class="content" id="content">
		<div class="board">
			<div class="board_read">
				<!-- 게시글 제목 -->
				<div class="read_header">
					<h1>
						<%=adt.getTitle()%>
					</h1>
					<p class="meta">
						<span class="author"> 작성자 : <%=adt.getWriter()%>
						</span> <span class="time"> 작성일 : <%=adt.getWriteDate()%>
						</span> <span class="read_count"> 조회 수 : <%=adt.getCount()%>
						</span>
					</p>
					<hr>
				</div>

				<!-- 본문 부분 -->
				<div class="read_body"
					style="font-size:<%=adt.getSize()%>px; color:<%=adt.getColor()%>; background-color:<%=adt.getBackground()%>; text-align:<%=adt.getAlign()%>">
					<%=adt.getArticle()%>
					<br> <br> <br> <br> <br> <br> <br>

				</div>
				<div class="read_footer">
					<div class="btnArea">
						<ul>
							<li>
								<button id="botbtn1">수정</button>
							</li>
							<li>
								<button id="botbtn2">삭제</button>
							</li>
							<li>
								<button id="botbtn"
									>목록</button>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<hr>

			<!-- 댓글 부분 -->
			<div>
				<table class="type04">
					<colgroup>
						<col width="50" />
						<col width="500" />
					</colgroup>
					<tbody>
						<%
							for (int i = 0; i < cdt.size(); i++) {
								String writer = cdt.get(i).getWriter();
								String content = cdt.get(i).getContent();
								String writedate = cdt.get(i).getWriteDate();
								out.print("<tr>");
								out.print("<td>" + writer + "</td>");
								out.print("<td>" + content + "</td>");
								out.print("<td>" + writedate + "</td>");
								out.print("</tr>");
							}
						%>
					</tbody>
				</table>

				<!--댓글 쓰기-->
				<form
					action="/Web_Project/include/iframe/comments.jsp?no=<%=bbsID%>&category=<%=bbsCategory%>"
					method="post" onsubmit="return check()" name="fr">
					<hr>
					<div>
						<textarea id="comments" placeholder="내용을 입력하세요" name="comment"></textarea>
						<button id="btn_insert" type="submit">댓글 등록</button>
					</div>
					<hr>
				</form>

			</div>
		</div>
	</div>
<script>
function check(){    
    if(<%=session.getAttribute("id") == null%>){
    	alert('로그인 후 이용가능합니다.');
    	return false;
    }
    return true;
  }
  
function table(){
	 location.href='/Web_Project/include/iframe/table<%=bbsCategory%>.jsp'
  }
  
var ccc=document.getElementById('botbtn');
ccc.addEventListener('click',table);
  
var aaa=document.getElementById('botbtn1');
aaa.addEventListener('click',update_click);

function update_click() {
    if(<%=adt.getWriter().equals(session.getAttribute("id"))%>){
       location.href='/Web_Project/include/iframe/update_write.jsp?no=<%=bbsID%>&category=<%=bbsCategory%>'
		} else {
			alert('작성자가 다릅니다.');
		}
	}
	
var bbb=document.getElementById('botbtn2');
bbb.addEventListener('click',delete_click);

function delete_click() {
    if(<%=adt.getWriter().equals(session.getAttribute("id"))%>){
    	if(confirm('정말 삭제하시겠습니까?')){
    		location.href='/Web_Project/include/iframe/deleteArticle.jsp?no=<%=bbsID%>&category=<%=bbsCategory%>'
			}
		} else {
			alert('작성자가 다릅니다.');
		}
	}
</script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	request.setCharacterEncoding("UTF-8");
%>

<%@ page import="alba.user"%>
<%@ page import="alba.UserDAO"%>
<jsp:useBean id="user" class="alba.user" scope="session" />
<%
						UserDAO a=new UserDAO();
					%>
<header>



	<div class="header">
		<div class="brand">
			<a href="/Web_Project/page/main_page.jsp" style="color: white; margin-right: 200px; margin-left:200px; text-align: right;">후기
				천국</a>
				<span style="font-size: 15px; color:black;">총 방문자 수 :<script src="../resources/js/clo.js"></script>
				<script>
					visitors.setCount(<%=a.getvisitor()%>);
					document.write(visitors.getCount());
					<%
						a.updatevisitor();
					%>
				</script></span>
		</div>
		<div class="menu">
			<ul>
				<li><a href="/Web_Project/page/borad_page1.jsp">알바 후기</a></li>
				<li><a href="/Web_Project/page/borad_page2.jsp">꿀알바 추천</a></li>
				<li><a href="/Web_Project/page/borad_page3.jsp">헬알바 공유</a></li>
				<li><a href="/Web_Project/page/borad_page4.jsp">자유 게시판</a></li>
				<li><a href="/Web_Project/page/calculator_page.jsp">시급 계산기</a></li>
			</ul>
		</div>
		<%
			if (session.getAttribute("id") == null) {
				out.print(
						"<button id='login' onclick=location.href='/Web_Project/include/iframe/login.jsp'>로그인</button>");
			}

			else if (session.getAttribute("id") == user.getId()) {

				out.print(
						"<button id='login' onclick=location.href='/Web_Project/include/iframe/LogoutAction.jsp'>로그아웃</button>");
			}
		%>
	</div>

</header>


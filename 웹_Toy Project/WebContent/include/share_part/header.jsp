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
			<a href="/Web_Project/page/main_page.jsp" style="color: white; margin-right: 200px; margin-left:200px; text-align: right;">�ı�
				õ��</a>
				<span style="font-size: 15px; color:black;">�� �湮�� �� :<script src="../resources/js/clo.js"></script>
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
				<li><a href="/Web_Project/page/borad_page1.jsp">�˹� �ı�</a></li>
				<li><a href="/Web_Project/page/borad_page2.jsp">�ܾ˹� ��õ</a></li>
				<li><a href="/Web_Project/page/borad_page3.jsp">��˹� ����</a></li>
				<li><a href="/Web_Project/page/borad_page4.jsp">���� �Խ���</a></li>
				<li><a href="/Web_Project/page/calculator_page.jsp">�ñ� ����</a></li>
			</ul>
		</div>
		<%
			if (session.getAttribute("id") == null) {
				out.print(
						"<button id='login' onclick=location.href='/Web_Project/include/iframe/login.jsp'>�α���</button>");
			}

			else if (session.getAttribute("id") == user.getId()) {

				out.print(
						"<button id='login' onclick=location.href='/Web_Project/include/iframe/LogoutAction.jsp'>�α׾ƿ�</button>");
			}
		%>
	</div>

</header>


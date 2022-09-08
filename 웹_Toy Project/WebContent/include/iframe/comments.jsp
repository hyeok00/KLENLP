<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="alba.articleDAO"%>
<%@ page import="alba.articleDTO"%>
<%@ page import="alba.commentDAO"%>
<%@ page import="alba.commentDTO"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		request.setCharacterEncoding("UTF-8");
		String s = request.getParameter("comment");
		int no = Integer.parseInt(request.getParameter("no"));
		int category = Integer.parseInt(request.getParameter("category"));
		String content = request.getParameter("comment");

		articleDAO ado = new articleDAO();
		ado.decreaseCount(no, category);

		String writer = session.getAttribute("id").toString();

		Date today = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		
		String str = date.format(today);

		commentDAO cdo = new commentDAO();
		commentDTO cto = new commentDTO(cdo.getCommentCount(no, category)+1, no, category, writer, content, str);
		cdo.addComment(cto);

		String url = "/Web_Project/include/iframe/borad.jsp?no=" + no + "&category=" + category;
		response.sendRedirect(url);
	%>
</body>
</html>
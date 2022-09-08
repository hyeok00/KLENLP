<%@page import="javafx.scene.control.Alert"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%@ page import="alba.boradDAO"%>

<%
	boradDAO dao = new boradDAO();

	int category = Integer.parseInt(request.getParameter("category"));
	int no = Integer.parseInt(request.getParameter("no"));
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	int size = Integer.parseInt(request.getParameter("size"));
	String color = request.getParameter("color");
	String backgroungColor = request.getParameter("background");
	String align = request.getParameter("align");

	dao.boradUpdate(no, category, title, content, size, color, backgroungColor, align);

	String redirectUrl = "/Web_Project/include/iframe/table" + category + ".jsp";
	response.sendRedirect(redirectUrl);
%>



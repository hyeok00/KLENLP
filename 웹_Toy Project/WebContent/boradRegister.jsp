<%@page import="javafx.scene.control.Alert"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="utf-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<%@ page import="alba.boradDAO"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
   Date today = new Date();
   SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

   boradDAO dao = new boradDAO();
   
   int category = Integer.parseInt(request.getParameter("category"));
   int no = dao.getCount(category);
   String title = request.getParameter("title");
   String writer = session.getAttribute("id").toString();
   String writedate = date.format(today);
   int size = Integer.parseInt(request.getParameter("size"));
   String color = request.getParameter("color");
   String backgroungColor = request.getParameter("background");
   String align = request.getParameter("align");
   String content = request.getParameter("content");
   
   
   dao.boradRegister(no,category, title, writer, writedate, size, color, backgroungColor, align, content);

   String redirectUrl = "/Web_Project/include/iframe/table"+category+".jsp";
   response.sendRedirect(redirectUrl);
%>
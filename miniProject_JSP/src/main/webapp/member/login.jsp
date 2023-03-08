<%@page import="member.dao.MemberDAO"%>
<%@page import="member.bean.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
   <%
   	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	
	//DB
			MemberDAO memberDAO = MemberDAO.getInstance();	//클래스 생성
			MemberDTO memberDTO = memberDAO.memberLogin(id, pwd);
	%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% if(memberDTO == null) {
	//페이지 이동
	response.sendRedirect("loginFail.jsp");
	
}else {
	//쿠키
	/*
	Cookie cookie = new Cookie("memName", name);
	cookie.setMaxAge(30 * 60); //초 단위
	response.addCookie(cookie);//클라이언트에 보내기
	
	Cookie cookie2 = new Cookie("memId", id);
	cookie2.setMaxAge(30 * 60	); //초 단위
	response.addCookie(cookie2);//클라이언트에 보내기
	*/
	
	//세션
	//HttpSession session = request.getSession(); - JSP는 세션이 내장객체로 존재한다.
	session.setAttribute("memName", memberDTO.getName());
	session.setAttribute("memId", id);
	session.setAttribute("memPwd", pwd);
	session.setAttribute("memEmail", memberDTO.getEmail1()+"@"+memberDTO.getEmail2());
	
	//페이지이동

	response.sendRedirect("loginOk.jsp");
	
} %>
</body>
</html>
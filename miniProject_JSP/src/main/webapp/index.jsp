<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
a:link {color: red; text-decoration: none;}
a:visited {color: blue; text-decoration: none;}
a:hover {color: green; text-decoration: underline;}
a:active {color: cyan; text-decoration: none;}
</style>
</head>
<body>
<div>
<h2>*** 메인화면 ***</h2>
<h3>
<%if(session.getAttribute("memId") == null ) { %>
	<a href="../miniProject_JSP/member/writeForm.jsp">회원가입</a><br>
	<a href="../miniProject_JSP/member/loginForm.jsp">로그인</a><br>
	
<%}else{%>
	<a href="../miniProject_JSP/member/logout.jsp">로그아웃</a><br>
	<a href="../miniProject_JSP/member/updateForm.jsp">회원정보수정</a><br>
	<a href="../miniProject_JSP/member/deleteForm.jsp">회원탈퇴</a><br>
	<a href="../miniProject_JSP/board/boardWriteForm.jsp">글쓰기</a><br>
<%}%>	

<a href="">목록</a><br>
</h3>
</div>
</body>
</html>
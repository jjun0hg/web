<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="member.dao.MemberDAO"%>
    
<%
	//데이터
	
	//세션
	String id = (String)session.getAttribute("memId");

	String pwd = request.getParameter("pwd");	// 넘어온 데이터
	System.out.println("pwd = " + pwd); //맨처음에는 pwd=null
	
	//DB
	boolean exist = false;
	if(pwd != null){
		MemberDAO memberDAO = MemberDAO.getInstance();	// 클래스 생성
	 	exist = memberDAO.isExistPwd(id, pwd);		// 비밀번호가 있으면 true, 없으면 false
	}
	
	if(exist) response.sendRedirect("delete.jsp");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
div#pwdDiv{
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
<form name="deleteForm" method="post" action="deleteForm.jsp">
	<div style="text-align: center;">
		비밀번호 입력 : <input type="password" name="pwd" id="pwd" size="40">
		<input type="button" value="검색" onclick="checkDelete()">
		<br><br>
		<div id ="pwdDiv">
			<%if (pwd !=null && !exist ){ %>
				비밀번호가 맞지 않습니다.
			<%} %>
		</div>
	</div>
</form>

<script type="text/javascript">

function checkDelete(){
	document.getElementById("pwdDiv").innerText ="";
	
	if(document.getElementById("pwd").value == "") 
		document.getElementById("pwdDiv").innerText = "비밀번호를 입력하시오";
	else
		document.deleteForm.submit();
}
</script>
</body>
</html>
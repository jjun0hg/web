<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
div{
	font-size:8pt;
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
<form name="boardWriteForm" method="post" action="boardWrite.jsp">
		<h3>
		<img src="../image/pug.png" width="80" height="80" alt="pug"
		onclick="location.href='../index.jsp'" style="cursor: pointer;"> 글쓰기
		</h3>
		<table border="1" cellpadding="5" cellspacing="0">
			<tr>
				<th>제목</th>
				<td>
				<textarea name="subject" id="subject" cols="50" rows="1"></textarea>
					<div id="subjectDiv"></div></td>
			</tr>
			
			<tr>
				<th>내용</th>
				<td>
				<textarea name="content" id="content" cols="50" rows="15"></textarea>
					<div id="contentDiv"></div></td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="글쓰기" onclick="checkBoardWrite()">
					<input type="reset" value="다시작성">
				</td>
			</tr>
</table>
</form>	
<script type="text/javascript" src="../js/member.js"></script>		
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="board.dao.BoardDAO"%>    
<%@page import="board.bean.BoardDTO"%>
<%@page import="java.util.List"%>

<%
	//데이터
	
	
	//DB
	BoardDAO boardDAO = BoardDAO.getInstance();
	List<BoardDTO> list = boardDAO.boardList();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<img src="../image/pug.png" width="80" height="80" alt="pug"
onclick="location.href='../index.jsp'" style="cursor: pointer;">
<table border="1" cellpadding="5" cellspacing="0" frame="hsides" rules="rows">
	<tr>
		<th width ="100">글번호</th>
		<th width ="300">제목</th>
		<th width ="150">작성자</th>
		<th width ="100">조회수</th>
		<th width ="150">작성일</th>
	</tr>
	
<%if(list != null){ %>
	<% for(BoardDTO boardDTO :list) { %>
	<tr>
		<td align="center"><%=boardDTO.getSeq() %></td>
		<td align="center"><%=boardDTO.getSubject() %></td>
		<td align="center"><%=boardDTO.getId() %></td>
		<td align="center"><%=boardDTO.getHit() %></td>
		<td align="center"><%=boardDTO.getLogtime() %></td>
	</tr>
	<%} %>
<%}  %>
</table>
</body>
</html>


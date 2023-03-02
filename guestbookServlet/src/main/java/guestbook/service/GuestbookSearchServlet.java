package guestbook.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import guestbook.bean.GuestbookDTO;
import guestbook.dao.GuestbookDAO;


@WebServlet("/GuestbookSearchServlet")
public class GuestbookSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//데이터
		String seq = request.getParameter("seq"); // "" -> name 이름이 들어오는것 id X
				
		
		//DB
		GuestbookDAO guestbookDAO = GuestbookDAO.getInstance();
		GuestbookDTO guestbookDTO = guestbookDAO.guestbookSearch(seq);
				
		
		//응답
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); //스트림 생성
		// 지금의 out 객체는 웹(브라우저)로 보낸다
		// 그럼 브라우저 번역을 해서 화면 결과 보여준다.
		if(guestbookDTO != null) {
			out.println("<table border='1' cellpadding = '5' celspacing='0'>");
			
			out.println("<tr>");
			out.println("<td>작성자</td>");
			out.println("<td></td>");
			out.println("<td>" + guestbookDTO.getName() + "</td>");
			out.println("<td>작성일</td>");
			out.println("<td>" + guestbookDTO.getLogtime() + "</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>이메일</td>");
			out.println("<td colspan='3'>" + guestbookDTO.getEmail() + "</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>홈페이지</td>");
			out.println("<td colspan='3'>" + guestbookDTO.getHomepage() + "</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>제목</td>");
			out.println("<td colspan='3'>" + guestbookDTO.getSubject() + "</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td colspan='4'><pre>" + guestbookDTO.getContent() + "</pre></td>");
			out.println("</tr>");
			
			out.println("</table>");
		}else {
			out.println("<h3>글번호가 없습니다</h3>");
		}
		out.println("<html>");
		out.println("<body>");
		out.println("</body>");
		out.println("</html>");
	}

}

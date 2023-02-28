package member.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.dao.MemberDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		
		MemberDAO dao = new MemberDAO();
		
		String name = dao.memberLogin(id, pwd);
		
		PrintWriter out = response.getWriter();
	
		out.println("<html>");
		out.println("<body>");
		if(name==null) {
			out.println("<h3>존재하는 이름이 없습니다.</h3>");
		
		}else {
			out.println(name+"님이 로그인했습니다.");
		}
		out.println("</body>");
		out.println("</html>");
		
		
		
	}


}

	
	
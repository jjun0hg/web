package guestbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import guestbook.bean.GuestbookDTO;

public class GuestbookDAO {
	private static GuestbookDAO guestbookDAO = new GuestbookDAO();
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##java";
	private String password = "1234";
	
	
	public static GuestbookDAO getInstance() {
		
		return guestbookDAO;
	}
	public GuestbookDAO() {
		try {
			Class.forName(driver); // Class타입으로 생성
			System.out.println("driver loading 성공");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		// 접속은 한번만 하는것이 아니기 때문에 생성자에서 하면 안됨.
	}

	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password);/* 오라클 드라이버 */
			System.out.println("connection 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstmt) {
		try {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
			if(rs != null) 
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void guestbookWrite(GuestbookDTO guestbookDTO) {
		String sql = "insert into guestbook values(seq_guestbook.nextval,?,?,?,?,?,sysdate)";
		 
		 this.getConnection();
		 
		 
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, guestbookDTO.getName());
			pstmt.setString(2, guestbookDTO.getEmail());
			pstmt.setString(3, guestbookDTO.getHomepage());
			pstmt.setString(4, guestbookDTO.getSubject());
			pstmt.setString(5, guestbookDTO.getContent());
			
			pstmt.executeUpdate();	//오라클 실행
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			GuestbookDAO.close(conn, pstmt);
		}
		 
	}
	public GuestbookDTO guestbookSearch(String seq) {
		GuestbookDTO guestbookDTO = null;
		String sql = "SELECT seq, name, email, homepage, subject, content, to_char(logtime, 'YYYY.MM.DD') as logtime FROM GUESTBOOK WHERE SEQ=?";
		
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,Integer.parseInt(seq));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				guestbookDTO = new GuestbookDTO();
				guestbookDTO.setSeq(rs.getInt("seq"));
				guestbookDTO.setName(rs.getString("name"));
				guestbookDTO.setEmail(rs.getString("email"));
				guestbookDTO.setHomepage(rs.getString("homepage"));
				guestbookDTO.setSubject(rs.getString("subject"));
				guestbookDTO.setContent(rs.getString("content"));
				guestbookDTO.setLogtime(rs.getString("logtime"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GuestbookDAO.close(conn, pstmt, rs);
		}
		return guestbookDTO;
	}
	public ArrayList<GuestbookDTO> guestbookList(int startNum, int endNum) {
		ArrayList<GuestbookDTO> list = new ArrayList<GuestbookDTO>();
		String sql = "SELECT * FROM"
				+ "(select ROWNUM RN, AA.* FROM"
				+ "(SELECT seq, name, email, homepage, subject, content, to_char(logtime, 'YYYY.MM.DD')"
				+ " as logtime FROM GUESTBOOK ORDER BY SEQ DESC) aa) WHERE RN>=? AND RN<=?";
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startNum);
			pstmt.setInt(2, endNum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GuestbookDTO guestbookDTO = new GuestbookDTO();
				guestbookDTO.setSeq(rs.getInt("seq"));
				guestbookDTO.setName(rs.getString("name"));
				guestbookDTO.setEmail(rs.getString("email"));
				guestbookDTO.setHomepage(rs.getString("homepage"));
				guestbookDTO.setSubject(rs.getString("subject"));
				guestbookDTO.setContent(rs.getString("content"));
				guestbookDTO.setLogtime(rs.getString("logtime"));
				list.add(guestbookDTO);
			}//while
			
		} catch (SQLException e) {
			e.printStackTrace();
			list = null;
		}finally {
			GuestbookDAO.close(conn, pstmt, rs);
		}
		
		return list;
	}
	public int getTotalA() {
		int totalA = 0;
		String sql = "SELECT COUNT(*) FROM GUESTBOOK";
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			rs.next();
			totalA = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			GuestbookDAO.close(conn, pstmt, rs);
		}
		
		return totalA;
	}
}

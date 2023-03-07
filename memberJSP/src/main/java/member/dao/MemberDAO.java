package member.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import member.bean.MemberDTO;

public class MemberDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##java";
	private String password = "1234";
	
	private static MemberDAO memberDAO = new MemberDAO();

	public static MemberDAO getInstance() {

		return memberDAO;
	}
	
	

	public MemberDAO() {
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

	public int memberWrite(MemberDTO memberDTO) {
		int su = 0;

		this.getConnection(); // DB접속
		String sql = "insert into member values(?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";

		try {
			pstmt = conn.prepareStatement(sql);

			// ?에 데이터 주입
			pstmt.setString(1, memberDTO.getName());
			pstmt.setString(2, memberDTO.getId());
			pstmt.setString(3, memberDTO.getPwd());
			pstmt.setString(4, memberDTO.getGender());
			pstmt.setString(5, memberDTO.getEmail1());
			pstmt.setString(6, memberDTO.getEmail2());
			pstmt.setString(7, memberDTO.getTel1());
			pstmt.setString(8, memberDTO.getTel2());
			pstmt.setString(9, memberDTO.getTel3());
			pstmt.setString(10, memberDTO.getZipcode());
			pstmt.setString(11, memberDTO.getAddr1());
			pstmt.setString(12, memberDTO.getAddr2());

			su = pstmt.executeUpdate();// 실행 - 개수 리턴

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			MemberDAO.close(conn, pstmt);
		}

		return su;
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
	
	public String memberLogin(String id, String pwd){
		String name = null;
		String sql = "SELECT * FROM MEMBER where id=? and pwd=?";
		getConnection();	//오라클 접속
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				name = rs.getString("name");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			MemberDAO.close(conn, pstmt, rs);
		}
		
		
		return name;
	}
	
	public MemberDTO getMember(String id){ //updateForm.jsp의 memberDAO.getMember(id)의 값을 전달
		MemberDTO memberDTO = null;
		String sql = "select * from member where id=?";
		
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);//생성
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();	//ResultSet 리턴
			
			if(rs.next()) {
				memberDTO = new MemberDTO();	//생성
				memberDTO.setName(rs.getString("name"));
				memberDTO.setId(rs.getString("id"));
				memberDTO.setPwd(rs.getString("pwd"));
				memberDTO.setGender(rs.getString("gender"));
				memberDTO.setEmail1(rs.getString("email1"));
				memberDTO.setEmail2(rs.getString("email2"));
				memberDTO.setTel1(rs.getString("tel1"));
				memberDTO.setTel2(rs.getString("tel2"));
				memberDTO.setTel3(rs.getString("tel3"));
				memberDTO.setZipcode(rs.getString("zipcode"));
				memberDTO.setAddr1(rs.getString("addr1"));
				memberDTO.setAddr2(rs.getString("addr2"));
		
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			MemberDAO.close(conn, pstmt, rs);
		}
		
		return memberDTO;
	}

	public void memberUpdate(MemberDTO memberDTO){
		String sql = "update member set name=?"
									+ ", pwd=?"
									+ ", gender=?"
									+ ", email1=?"
									+ ", email2=?"
									+ ", tel1=?"
									+ ", tel2=?"
									+ ", tel3=?"
									+ ", zipcode=?"
									+ ", addr1=?"
									+ ", addr2=?"
									+ ", logtime=sysdate"
									+ " where id=?";
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getName());
			pstmt.setString(2, memberDTO.getPwd());
			pstmt.setString(3, memberDTO.getGender());
			pstmt.setString(4, memberDTO.getEmail1());
			pstmt.setString(5, memberDTO.getEmail2());
			pstmt.setString(6, memberDTO.getTel1());
			pstmt.setString(7, memberDTO.getTel2());
			pstmt.setString(8, memberDTO.getTel3());
			pstmt.setString(9, memberDTO.getZipcode());
			pstmt.setString(10, memberDTO.getAddr1());
			pstmt.setString(11, memberDTO.getAddr2());
			pstmt.setString(12, memberDTO.getId());
			
			pstmt.executeUpdate(); //개수 리턴
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			MemberDAO.close(conn, pstmt);
		}
		
	}
	
	public boolean isExistPwd(String id, String pwd) {
		boolean exist = false;
		String sql ="SELECT * FROM MEMBER WHERE ID=? AND PWD=?";
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			
			rs = pstmt.executeQuery();
			if(rs.next()) exist = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MemberDAO.close(conn, pstmt, rs);
		}
				
		return exist;
	}
	
	public void memberDelete(String id){
		String sql = "delete member where id=?";
		
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MemberDAO.close(conn, pstmt);
		}

	}
	
}

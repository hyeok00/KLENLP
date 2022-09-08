package alba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/webproject";
			String dbID = "root";
			String dbPwd = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int Login(String UserID, String UserPwd) {
		String Sql = "SELECT pw FROM user WHERE id=?";
		try {
			pstmt = conn.prepareStatement(Sql);
			pstmt.setString(1, UserID);
			rs = pstmt.executeQuery();

			if (rs.next()) // 결과가 존재한다면
			{
				if (rs.getString(1).equals(UserPwd)) {
					return 1; // 로그인성공
				} else
					return 0; // 비밀번호 불일치

			}
			return -1; // 아이디없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // 디비에러
	}

	public String find_Id(String User_email, String User_answer) {
		String Sql = "select id From user where email=? and answer=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String user_id = null; // 찾을아이디

		try {
			pstmt = conn.prepareStatement(Sql);
			pstmt.setString(1, User_email);
			pstmt.setString(2, User_answer);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user_id = rs.getString("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user_id;
	}

	public int join(user user) {

		String SQL = "INSERT INTO user VALUES (?,?,?,?,?)";

		try {

			pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, user.getId());

			pstmt.setString(2, user.getPw());

			pstmt.setString(3, user.getName());

			pstmt.setString(4, user.getEmail());

			pstmt.setString(5, user.getAnswer());

			return pstmt.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return -1; // DB 오류

	}

	public int getvisitor() {
		String sql = "SELECT visitor FROM visitor WHERE id=1";
		int a=0;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) // 결과가 존재한다면
				a=rs.getInt(1);
				System.out.println(a);
				return a;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void updatevisitor() {
		String sql = "UPDATE visitor SET visitor=visitor+1 WHERE id=1";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

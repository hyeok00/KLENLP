package alba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

public class boradDAO {
	String dbURL = "jdbc:mysql://localhost:3306/webproject";
	String dbID = "root";
	String dbPwd = "root";

	public boradDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getCount(int category) { // 카테고리
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		// String sql = "SELECT count(*) FROM webproject.board where category=?";
		String sql = "select * from board where category=? order by no desc";

		try {
			conn = DriverManager.getConnection(dbURL, dbID, dbPwd);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, category);
			rs = pstmt.executeQuery();

			if (rs.next())
				return rs.getInt(1) + 1;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void boradRegister(int no, int category, String title, String writer, String writedate, int size,
			String color, String backgroungColor, String align, String content) {
		Connection conn = null;
		Statement stmt = null;

		Connection conn2 = null;
		Statement stmt2 = null;
		try {
			String sql = "INSERT INTO webproject.board VALUES(" + no + "," + category + ",'" + title + "','" + writer
					+ "','" + writedate + "',0)";
			conn = DriverManager.getConnection(dbURL, dbID, dbPwd);
			conn2 = DriverManager.getConnection(dbURL, dbID, dbPwd);
			stmt = conn.createStatement();
			stmt2 = conn2.createStatement();
			stmt.executeUpdate(sql);

			sql = "INSERT INTO webproject.article VALUES(" + no + "," + category + ",'" + content + "', 0, 0," + size
					+ ",'" + color + "','" + backgroungColor + "','" + align + "')";
			stmt2.executeUpdate(sql);

			stmt.close();
			conn.close();
			stmt2.close();
			conn2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<boradDTO> loadBorad(int type, int absolutenum, int pageSize) {
		ArrayList<boradDTO> dtos = new ArrayList<boradDTO>();

		String boradType = Integer.toString(type);

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from (select no as num,board.* from board where category='" + boradType
					+ "')as s where num between ? and ?";
			conn = DriverManager.getConnection(dbURL, dbID, dbPwd);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, absolutenum);
			pstmt.setInt(2, absolutenum + pageSize - 1);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String writer = rs.getString("writer");
				String writedate = rs.getString("writedate");
				int count = rs.getInt("count");

				boradDTO dto = new boradDTO(no, title, writer, writedate, count);
				dtos.add(dto);
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dtos;
	}

	public int getTotalPage(int type, int pageSize) {
		String boradType = Integer.toString(type);

		int totalRecord = 0;
		int totalPage = 0;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// 게시판 카테고리에 맞는 개수 찾기
			String sql = "select COUNT(*) from board where category='" + boradType + "'";
			conn = DriverManager.getConnection(dbURL, dbID, dbPwd);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			totalRecord = rs.getInt(1);

			if (totalRecord != 0) {
				if ((totalRecord % pageSize) == 0) {
					totalPage = (totalRecord / pageSize);
				} else {
					totalPage = (totalRecord / pageSize + 1);
				}
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalPage;
	}

	public void delboard(int no, int category) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "DELETE FROM `webproject`.`board` WHERE (`no` = ?) and (`category` = ?)";
			conn = DriverManager.getConnection(dbURL, dbID, dbPwd);
			pst = conn.prepareStatement(sql);
			pst.setInt(1, no);
			pst.setInt(2, category);
			pst.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public ArrayList<boradDTO> search(String searchValue, String type, int category) {
	      Connection conn = null;
	      Statement stmt = null;
	      ResultSet rs = null;
	      ArrayList<boradDTO> list = new ArrayList<boradDTO>();
	      String sql = "SELECT * FROM webproject.board WHERE category="+ category +" and title LIKE '%";
	      String sql2 = "SELECT * FROM webproject.board WHERE category="+ category +" and writer LIKE '%";
	      try {
	         conn = DriverManager.getConnection(dbURL, dbID, dbPwd);
	         if (type.equals("title")) {
	            sql=sql+searchValue+"%'";
	            System.out.println(sql);
	            stmt=conn.createStatement();
	            rs=stmt.executeQuery(sql);
	            while (rs.next()) {
	               int no = rs.getInt(1);
	               String tmptitle = rs.getString(3);
	               String tmpwriter = rs.getString(4);
	               String writedate = rs.getString(5);
	               int count = rs.getInt(6);
	               boradDTO dto = new boradDTO(no, tmptitle, tmpwriter, writedate, count);
	               list.add(dto);
	            }
	         } else if (type.equals("writer")) {
	        	 sql2=sql2+searchValue+"%'";
		            stmt=conn.createStatement();
		            rs=stmt.executeQuery(sql2);
		            while (rs.next()) {
		               int no = rs.getInt(1);
		               String tmptitle = rs.getString(3);
		               String tmpwriter = rs.getString(4);
		               String writedate = rs.getString(5);
		               int count = rs.getInt(6);
		               boradDTO dto = new boradDTO(no, tmptitle, tmpwriter, writedate, count);
		               list.add(dto);
		            }
	         }

	         rs.close();
	         stmt.close();
	         conn.close();

	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return list;
	   }

	public void boradUpdate(int no, int category, String title, String content, int size, String color,
	         String backgroungColor, String align) {
	      String sql;
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      try {
	         sql = "UPDATE webproject.article set content=?, size=?, color=?, background=? ,align=? where category=? and no=?";
	         conn = DriverManager.getConnection(dbURL, dbID, dbPwd);
	         pstmt = conn.prepareStatement(sql);

	         pstmt.setString(1, content);
	         pstmt.setInt(2, size);
	         pstmt.setString(3, color);
	         pstmt.setString(4, backgroungColor);
	         pstmt.setString(5, align);
	         pstmt.setInt(6, category);
	         pstmt.setInt(7, no);
	         pstmt.executeUpdate();

	         sql="UPDATE webproject.board set title=? where category=? and no=?";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, title);
	         pstmt.setInt(2, category);
	         pstmt.setInt(3, no);
	         pstmt.executeUpdate();
	         
	         
	      } catch (Exception e) {
	         System.out.println(e.getMessage());
	      }
	   }
	
}
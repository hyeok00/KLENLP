package alba;

import java.sql.*;
import java.util.ArrayList;

public class commentDAO {
	public Connection conn = null;
	public Statement st = null;
	public PreparedStatement pst = null;
	public PreparedStatement pst2 = null;
	public ResultSet rs = null;
	public ResultSet rs2 = null;

	public commentDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/webproject?serverTimezone=UTC";
			String id = "root";
			String pwd = "root";

			conn = DriverManager.getConnection(url, id, pwd);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dbClose() {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
			System.out.println("Disconnection Succeeded.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<commentDTO> loadComment(int no, int category) {
		String sql = "SELECT * from comments where no=? and category=?";
		ArrayList<commentDTO> list = new ArrayList<commentDTO>();

		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, no);
			pst.setInt(2, category);
			rs = pst.executeQuery();
			while(rs.next()) {
				commentDTO cdt=new commentDTO();
				cdt.setWriter(rs.getString(4));
				cdt.setContent(rs.getString(5));
				cdt.setWriteDate(rs.getString(6));
				list.add(cdt);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public int getCommentCount(int no, int category) {
		String sql = "SELECT count(*) FROM webproject.comments where no=? and category=?";
	
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, no);
			pst.setInt(2, category);
			rs = pst.executeQuery();
			
			if(rs.next())
			return rs.getInt(1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public void addComment(commentDTO cto) {
		
		String sql = "INSERT INTO webproject.comments VALUES(?,?,?,?,?,?)";
		
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, cto.getNo());
			pst.setInt(2, cto.getCategory());
			pst.setInt(3, cto.getCommentNo());
			pst.setString(4, cto.getWriter());
			pst.setString(5, cto.getContent());
			pst.setString(6, cto.getWriteDate());
			
			int res = pst.executeUpdate();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package alba;

import java.sql.*;
import java.util.ArrayList;

public class articleDAO {

   public Connection conn = null;
   public Statement st = null;
   public PreparedStatement pst = null;
   public PreparedStatement pst2 = null;
   public ResultSet rs = null;
   public ResultSet rs2 = null;

   public articleDAO() {
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

   public articleDTO loadArticle(int no, int category) {
      String sql = "UPDATE board SET count=count+1 WHERE no=? and category=?";

      try {
         pst = conn.prepareStatement(sql);
         pst.setInt(1, no);
         pst.setInt(2, category);
         int res = pst.executeUpdate();

         sql = "SELECT * from article where no=? and category=?";
         articleDTO adt = new articleDTO();
         pst = conn.prepareStatement(sql);
         pst.setInt(1, no);
         pst.setInt(2, category);
         rs = pst.executeQuery();
         if (rs.next()) {
            adt.setNo(rs.getInt(1));
            adt.setCategory(rs.getInt(2));
            adt.setArticle(rs.getString(3));
            adt.setGoodCount(rs.getInt(4));
            adt.setBadCount(rs.getInt(5));
            adt.setSize(rs.getInt(6));
            adt.setColor(rs.getString(7));
            adt.setBackground(rs.getString(8));
            adt.setAlign(rs.getString(9));
         }
         sql = "SELECT * from board where no=? and category=?";
         pst = conn.prepareStatement(sql);
         pst.setInt(1, no);
         pst.setInt(2, category);
         rs = pst.executeQuery();
         if (rs.next()) {
            adt.increaseCount();
            adt.setTitle(rs.getString(3));
            adt.setWriter(rs.getString(4));
            adt.setWriteDate(rs.getString(5));
            adt.setCount(rs.getInt(6));
            return adt;
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }

   public void decreaseCount(int no, int category) {
      String sql = "UPDATE board SET count=count-1 WHERE no=? and category=?";

      try {
         pst = conn.prepareStatement(sql);
         pst.setInt(1, no);
         pst.setInt(2, category);
         int res = pst.executeUpdate();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public void deleteArticle() {
	      String sql = "DELETE FROM board WHERE no=? and category=?";

   }
   
}
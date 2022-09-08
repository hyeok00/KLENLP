package ReturnAndDiscardManagement;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBControl {
	public Connection conn = null;
	public Statement st = null;
	public static PreparedStatement pst = null;
	public static PreparedStatement pst2 = null;
	public static ResultSet rs = null;
	public static ResultSet rs2 = null;

	public DBControl(){}

    // 데이터베이스를 연결한다.
    public Connection dbConn() {
 
        try {
            Class.forName(SystemManagement.DBControl.FOR_NAME);
            String url = SystemManagement.DBControl.URL;
            String id = SystemManagement.DBControl.ID; 
            String pwd = SystemManagement.DBControl.PASSWORD; 

            conn = DriverManager.getConnection(url, id, pwd);		

        } catch (Exception e) {			
            e.printStackTrace();
        }
        return conn;
 
    }
	
	// 데이터베이스를 닫는다.
	public void dbClose(){
		try{
			if (rs != null) rs.close();
			if (st != null) st.close();
			if (conn != null) conn.close();
			System.out.println("Disconnection Succeeded.");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

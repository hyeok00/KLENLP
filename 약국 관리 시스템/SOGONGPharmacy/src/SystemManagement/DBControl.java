/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	SystemManagement
 * @className 			DBControl
 * @author 					최정봉
 */
package SystemManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBControl {
	public static final String FOR_NAME = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/pharmacy?useUnicode=true&characterEncoding=utf8";
	public static final String ID = "root"; 
	public static final String PASSWORD = "1346";
    
	public Connection conn = null;
	public Statement st = null;
	public PreparedStatement pst = null;
	public PreparedStatement pst2 = null;
	public ResultSet rs = null;
	public ResultSet rs2 = null;

	public DBControl(){}

    // 데이터베이스를 연결한다.
    public Connection dbConn() {
 
        try {
            Class.forName(FOR_NAME); 
            String url = URL;
            String id = ID; 
            String pwd = PASSWORD;

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

package ProductManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import SystemManagement.DBControl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnecter {
	public Connection conn = null;
	public Statement st = null;
	public PreparedStatement pst = null;
	public PreparedStatement pst2 = null;
	public ResultSet rs = null;
	public ResultSet rs2 = null;

	public DBConnecter(){}

    // 데이터베이스를 연결한다.
    public Connection dbConn() {
 
        try {
            Class.forName(DBControl.FOR_NAME); 
            String url = DBControl.URL;
            String id = DBControl.ID; 
            String pwd = DBControl.PASSWORD; 

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

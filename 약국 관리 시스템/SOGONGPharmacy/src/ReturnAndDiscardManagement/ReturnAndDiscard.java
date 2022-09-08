package ReturnAndDiscardManagement;
/**
 * @systemName		SOGONG Pharmacy Management
 * @subSystemName	ReturnAndDiscard Management
 * @className 		ReturnAndDiscard
 * @author 			이성애
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ReturnAndDiscardManagement.DBControl;

public class ReturnAndDiscard {
	DBControl db=new DBControl();
	Connection conn=db.dbConn();
	PreparedStatement pstmt= DBControl.pst;
	PreparedStatement pstmt2= DBControl.pst2;
	ResultSet rs= DBControl.rs;
	
	String[] productCode=new String[30];
	String[] quantity=new String[30];
	String[] category=new String[30];
	String[] reason=new String[30];
	String[] state=new String[30];
	String[]  date=new String[30];
	
	public String[] getProductCode(int code) {
		if(code==1) {
			String radQuery="select productCode from returnanddiscard where state=0";// 상품 코드, 수량, 상품 상테, 처리 이유, 처리 날짜를 가져옴
			try {
				pstmt=conn.prepareStatement(radQuery);
				rs=pstmt.executeQuery();
				int i=0;
				while(rs.next()) {
					productCode[i]=rs.getString("productCode");//상품코드
					i+=1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(code==2) {
				String radQuery="select productCode from returnanddiscard";// 상품 코드, 수량, 상품 상테, 처리 이유, 처리 날짜를 가져옴
				try {
					pstmt=conn.prepareStatement(radQuery);
					rs=pstmt.executeQuery();
					int i=0;
					while(rs.next()) {
						productCode[i]=rs.getString("productCode");//상품코드
						i+=1;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return productCode;
		
	}
	public String[] getQuantity(int code) {
		if(code==1) {
			String radQuery="select quantity from returnanddiscard where state=0";// 상품 코드, 수량, 상품 상테, 처리 이유, 처리 날짜를 가져옴
			try {
				pstmt=conn.prepareStatement(radQuery);
				rs=pstmt.executeQuery();
				int i=0;
				while(rs.next()) {
					quantity[i]=rs.getString("quantity");//수량
					i+=1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(code==2) {
			String radQuery="select quantity from returnanddiscard";// 상품 코드, 수량, 상품 상테, 처리 이유, 처리 날짜를 가져옴
			try {
				pstmt=conn.prepareStatement(radQuery);
				rs=pstmt.executeQuery();
				int i=0;
				while(rs.next()) {
					quantity[i]=rs.getString("quantity");//수량
					i+=1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return quantity;
	}
	public String[] getCategory(int code) {
		if(code==1) {
		String radQuery="select category from returnanddiscard where state=0";// 상품 코드, 수량, 상품 상테, 처리 이유, 처리 날짜를 가져옴
		try {
			pstmt=conn.prepareStatement(radQuery);
			rs=pstmt.executeQuery();
			int i=0;
			while(rs.next()) {
				category[i]=rs.getString("category");//처리상태
				i+=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		else if(code==2) {
			String radQuery="select category from returnanddiscard";// 상품 코드, 수량, 상품 상테, 처리 이유, 처리 날짜를 가져옴
			try {
				pstmt=conn.prepareStatement(radQuery);
				rs=pstmt.executeQuery();
				int i=0;
				while(rs.next()) {
					category[i]=rs.getString("category");//처리상태
					i+=1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return category;
	}
	public String[] getReason(int code) {
		if(code==1) {
		String radQuery="select reason from returnanddiscard where state=0";// 상품 코드, 수량, 상품 상테, 처리 이유, 처리 날짜를 가져옴
		try {
			pstmt=conn.prepareStatement(radQuery);
			rs=pstmt.executeQuery();
			int i=0;
			while(rs.next()) {
				reason[i]=rs.getString("reason");//처리사유
				i+=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		else if(code==2) {
			String radQuery="select reason from returnanddiscard";// 상품 코드, 수량, 상품 상테, 처리 이유, 처리 날짜를 가져옴
			try {
				pstmt=conn.prepareStatement(radQuery);
				rs=pstmt.executeQuery();
				int i=0;
				while(rs.next()) {
					reason[i]=rs.getString("reason");//처리사유
					i+=1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return reason;
	}

	public String[] getDate(int code) {
		if(code==1) {
		String radQuery="select date from returnanddiscard where state=0";// 상품 코드, 수량, 상품 상테, 처리 이유, 처리 날짜를 가져옴
		try {
			pstmt=conn.prepareStatement(radQuery);
			rs=pstmt.executeQuery();
			int i=0;
			while(rs.next()) {
				date[i]=rs.getString("date");//처리일
				i+=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else if(code==2) {
			String radQuery="select date from returnanddiscard";// 상품 코드, 수량, 상품 상테, 처리 이유, 처리 날짜를 가져옴
			try {
				pstmt=conn.prepareStatement(radQuery);
				rs=pstmt.executeQuery();
				int i=0;
				while(rs.next()) {
					date[i]=rs.getString("date");//처리일
					i+=1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}
	public void setDate(String productCode,String date) {
		try {
			String query="update returnanddiscard set date="+"\""+date+"\", state=1 "+"where productCode="+"\'"+productCode+"\'";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.executeUpdate(query);
			//date 업로드
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ReturnAndDiscard(String productCode, int quantity, String category, String reason, int state){
		String query="insert into ReturnAndDiscard(productCode,state,quantity,category,reason) values(?,?,?,?,?)";
		String pdquery="update productstock set quantity=\"0\" where productCode=\""+productCode+"\"";
			try {
				pstmt=conn.prepareStatement(query);
				pstmt.setString(1,productCode);
				pstmt.setInt(2,state);
				pstmt.setInt(3,quantity);
				pstmt.setString(4, category);
				pstmt.setString(5, reason);
				pstmt.executeUpdate();
				pstmt=conn.prepareStatement(pdquery);
				pstmt.executeUpdate(pdquery);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public ReturnAndDiscard() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

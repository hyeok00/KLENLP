/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	RecordManagement
 * @className 			RecordManager
 * @author 					최정봉
 */
package RecordManagement;

import java.sql.SQLException;
import java.util.ArrayList;

import SystemManagement.DBControl;

public class RecordManager {
	DBControl db = new DBControl();
	
	public RecordManager() {
		
	}
	//상품이력 검색(상품명)
	public ArrayList<Record> readProductRecord(String productName) {
		ArrayList<Record> list = new ArrayList<Record>();
		Record pr;
		
		try {
			db.dbConn();
			String sql ="SELECT productCode, productName, expirationDate, quantity FROM productstock WHERE productName LIKE ?";	
			db.pst = db.conn.prepareStatement(sql);
			db.pst.setString(1, "%"+productName+"%");
			db.rs = db.pst.executeQuery();
			
			while(db.rs.next()) {
				pr = new Record();
				String code = db.rs.getString("productCode");
				String name = db.rs.getString("productName");
				String date = db.rs.getString("expirationDate");
				int quantity = db.rs.getInt("quantity");
				
				pr.setCode(code);
				pr.setProductName(name);
				pr.setdate(date);
				pr.setquantity(quantity);
				
				list.add(pr);
			}
			db.rs.close();
			db.dbClose();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	//입고이력 검색(상품명)
	public ArrayList<Record> readEnteringRecord(String productName) {
		ArrayList<Record> list = new ArrayList<Record>();
		Record pr;
		
		try {
			db.dbConn();
			String sql ="SELECT enteringCode, enteringDate, productName, quantity FROM enteringlist WHERE productName LIKE ? ";
			db.pst = db.conn.prepareStatement(sql);
			db.pst.setString(1, "%"+productName+"%");
			db.rs = db.pst.executeQuery();
						
			while(db.rs.next()) {
				pr = new Record();
				String code = db.rs.getString("enteringCode");
				String date = db.rs.getString("enteringDate");
				String name = db.rs.getString("productName");
				int quantity = db.rs.getInt("quantity");
				
				pr.setCode(code);
				pr.setProductName(name);
				pr.setdate(date);
				pr.setquantity(quantity);
				
				list.add(pr);
			}
			db.rs.close();
			db.dbClose();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	//입고이력 검색(입고일)
	public ArrayList<Record> readEnteringRecordforDate(String productName, String enteringDate) {
		ArrayList<Record> list = new ArrayList<Record>();
		Record pr;
		
		try {
			db.dbConn();
			String sql ="select enteringCode, enteringDate, productName, quantity from enteringlist where productName LIKE ? AND enteringDate = ?";
			db.pst = db.conn.prepareStatement(sql);
			db.pst.setString(1, "%" + productName +"%");
			db.pst.setString(2, enteringDate);
			db.rs = db.pst.executeQuery();
						
			while(db.rs.next()) {
				pr = new Record();
				String code = db.rs.getString("enteringCode");
				String date = db.rs.getString("enteringDate");
				String name = db.rs.getString("productName");
				int quantity = db.rs.getInt("quantity");
				
				pr.setCode(code);
				pr.setdate(date);
				pr.setProductName(name);
				pr.setquantity(quantity);
				
				list.add(pr);
			}
			db.rs.close();
			db.dbClose();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//마감이력 검색(마감일)
	public ArrayList<ClosingRecord> readClosingRecord(String closingDate) {
		ArrayList<ClosingRecord> list = new ArrayList<ClosingRecord>();
		ClosingRecord cr;
		
		try {
			db.dbConn();
			String sql = "SELECT salesmaster.salesAmount, salesmaster.memberID, salesmaster.refundState, salesdetail.productName, salesdetail.quantity " + 
					"FROM salesmaster " + 
					"RIGHT OUTER JOIN salesdetail ON salesmaster.salesCode = salesdetail.salesCode " + 
					"where CAST(salesDate AS DATE)=? AND ((CAST(salesDate AS date)=? AND NOT CAST(refundDate AS date)=?) OR " + 
					"(NOT CAST(salesDate AS date)=? AND CAST(refundDate AS date)=?))";
			db.pst = db.conn.prepareStatement(sql);
			db.pst.setString(1, closingDate);
			db.pst.setString(2, closingDate);
			db.pst.setString(3, closingDate);
			db.pst.setString(4, closingDate);
			db.pst.setString(5, closingDate);
			db.rs = db.pst.executeQuery();
			
			while(db.rs.next()) {
				cr = new ClosingRecord();
				//int amount = db.rs.getInt("salesAmount");
				String member = db.rs.getString("memberID");
				int refundState = db.rs.getInt("refundState");
				String productName = db.rs.getString("productName");
				int salesQuantity = db.rs.getInt("quantity");
				
				cr.setSalesAmount(salesQuantity * getSalesPrice(productName));
				cr.setMemberID(member);
				cr.setRefundState(refundState);
				cr.setProductName(productName);
				cr.setSalesQuantity(salesQuantity);
				cr.setProductStock(getProductStock(productName));
				list.add(cr);
			}
			
			db.rs.close();
			db.dbClose();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	//제품의 남은 재고 수 조회
	public int getProductStock(String productName) {
		int stock = 0;
		
		try {
			String sql = "SELECT quantity FROM productlist WHERE productName=?";
			db.pst2 = db.conn.prepareStatement(sql);
			db.pst2.setString(1, productName);
			db.rs2 = db.pst2.executeQuery();
			
			while(db.rs2.next()) {
				stock = db.rs2.getInt("quantity");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return stock;
	}
	//물품 판매가격 가져오기
		public int getSalesPrice(String productName) {
				int price = 0;
				
				 try { 
					  String sql = "SELECT salesPrice FROM productlist WHERE productName=?";
					  db.pst2 = db.conn.prepareStatement(sql); 
					  db.pst2.setString(1, productName);
					  db.rs2 = db.pst2.executeQuery();
			  
					 while(db.rs2.next()) { 
					  price = db.rs2.getInt("salesPrice"); 
					 }
			  
			  } catch (SQLException e) { 
				  e.printStackTrace(); 
			 } 
			return price;
		}
	
		//설계서에는 있으나 용도를 모르겠음
	public boolean essentialCheck() {
		boolean result = true;
		
		return result;
	}
	
	public boolean formatCheck(String productName, String enteringDate, String closingDate) {
		boolean result = true;
		
		return result;
	}
	
}

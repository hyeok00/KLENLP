/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	DailySalesManagement
 * @className 			DailySalesManager
 * @author 					최정봉
 */
package DailySalesManagement;

import java.sql.SQLException;
import java.util.ArrayList;

import RecordManagement.Record;
import RecordManagement.RecordManager;
import SystemManagement.DBControl;

public class DailySalesManager {
		DBControl db = new DBControl();
		
		//매출 실적 가져오기
		public ArrayList<DailySales> dailyClosing(String date) {
			ArrayList<DailySales> list = new ArrayList<DailySales>();
			DailySales ds;
			
			try {
				db.dbConn();
				String sql ="SELECT salesCode, payMethod, salesAmount, refundState FROM salesmaster " + 
						"WHERE (CAST(salesDate AS date)=? AND NOT CAST(refundDate AS date)=?) OR " + 
						"(NOT CAST(salesDate AS date)=? AND CAST(refundDate AS date)=?)";		//판매 후 당일 환불한 물품은 제외함
				db.pst = db.conn.prepareStatement(sql);
				db.pst.setString(1, date);
				db.pst.setString(2, date);
				db.pst.setString(3, date);
				db.pst.setString(4, date);
				db.rs = db.pst.executeQuery();
				
				while(db.rs.next()) {
					ds = new DailySales();
					String code = db.rs.getString("salesCode");
					String method = db.rs.getString("payMethod");
					int amount = db.rs.getInt("salesAmount");
					int state = db.rs.getInt("refundState");
					
					ds.setSalesCode(code);
					ds.setPayMethod(method);
					ds.setSalesAmount(amount);
					ds.setRefundState(state);
					list.add(ds);
				}
				db.rs.close();
				db.dbClose();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return list;
		}
		//상세 매출 실적 가져오기
		public ArrayList<DailySalesDetail> dailyClosingDetail(String date) {
			ArrayList<DailySalesDetail> list = new ArrayList<DailySalesDetail>();
			DailySalesDetail dsd;
			
			try {
				db.dbConn();
				//판매 후 당일 환불한 물품은 제외함
				String sql ="SELECT salesdetail.salesCode, salesmaster.payMethod, salesmaster.refundState, salesdetail.productName, salesdetail.quantity " + 
						"FROM salesmaster " + 
						"RIGHT OUTER JOIN salesdetail ON salesmaster.salesCode = salesdetail.salesCode " + 
						"where CAST(salesDate AS DATE)=? AND ((CAST(salesDate AS date)=? AND NOT CAST(refundDate AS date)=?) OR " + 
								"(NOT CAST(salesDate AS date)=? AND CAST(refundDate AS date)=?))";	
				db.pst = db.conn.prepareStatement(sql);
				db.pst.setString(1, date);
				db.pst.setString(2, date);
				db.pst.setString(3, date);
				db.pst.setString(4, date);
				db.pst.setString(5, date);
				db.rs = db.pst.executeQuery();
				
				while(db.rs.next()) {
					dsd = new DailySalesDetail();
					String code = db.rs.getString("salesCode");
					String method = db.rs.getString("payMethod");
					int state = db.rs.getInt("refundState");
					String name = db.rs.getString("productName");
					int quantity = db.rs.getInt("quantity");
					
					dsd.setSalesCode(code);
					dsd.setPayMethod(method);
					dsd.setRefundState(state);
					dsd.setProductName(name);
					dsd.setSalesQuantity(quantity);
					dsd.setSalesAmount(quantity * getSalesPrice(name));
					list.add(dsd);
				}
				db.rs.close();
				db.dbClose();
			}catch(Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
		//마감 처리
		public void dailyClose(String date, int cashAmount, int cardAmount, int refundAmount) {
		
			try {
				db.dbConn();
				String sql ="INSERT INTO dailysales VALUES (?, ?, ?, ?)";
				db.pst = db.conn.prepareStatement(sql);
				db.pst.setString(1, date);
				db.pst.setInt(2, cashAmount);
				db.pst.setInt(3, cardAmount);
				db.pst.setInt(4, refundAmount);
				db.pst.executeUpdate();
				db.dbClose();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
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
		
		//물품 매입가격 가져오기
	  public int getProductCost(String salesCode, String productName) {
		  int cost = 0;
	  
		  try { 
			  String sql = "SELECT cost " + 
			  		"FROM (SELECT productName, supplierCode FROM enteringlist WHERE enteringCode " + 
			  		"IN (SELECT enteringCode FROM productstock WHERE productCode " + 
			  		"IN (SELECT productCode FROM salesdetail WHERE salesCode=? AND productName=?))) AS ps " + 
			  		"JOIN supplierproduct AS sp " + 
			  		"ON sp.supplierCode=ps.supplierCode AND sp.productName=ps.productName";
			  db.pst2 = db.conn.prepareStatement(sql); 
			  db.pst2.setString(1, salesCode);
			  db.pst2.setString(2, productName);
			  db.rs2 = db.pst2.executeQuery();
	  
			  while(db.rs2.next()) { 
				  cost = db.rs2.getInt("cost"); 
			  }
	  
		  } catch (SQLException e) { 
			  e.printStackTrace(); 
		  	} 
		  return cost; 
		}
	 
		//일보조회 
		public ArrayList<DailySalesDetail> dailyReportRead(String date) {
			ArrayList<DailySalesDetail> list = new ArrayList<DailySalesDetail>();
			DailySalesDetail dsd;
			
			try {
				db.dbConn();
				String sql ="SELECT salesmaster.memberID, salesdetail.productName, salesdetail.quantity " + 
						"FROM salesmaster " + 
						"RIGHT OUTER JOIN salesdetail ON salesmaster.salesCode = salesdetail.salesCode " + 
						"WHERE CAST(salesDate AS DATE)=? AND ((CAST(salesDate AS date)=? AND NOT CAST(refundDate AS date)=?) OR " + 
								"(NOT CAST(salesDate AS date)=? AND CAST(refundDate AS date)=?))";	
				db.pst = db.conn.prepareStatement(sql);
				db.pst.setString(1, date);
				db.pst.setString(2, date);
				db.pst.setString(3, date);
				db.pst.setString(4, date);
				db.pst.setString(5, date);
				db.rs = db.pst.executeQuery();
				
				while(db.rs.next()) {
					dsd = new DailySalesDetail();
					String member = db.rs.getString("memberID");
					String name = db.rs.getString("productName");
					int quantity = db.rs.getInt("quantity");
					
					dsd.setMemberID(member);
					dsd.setProductName(name);
					dsd.setSalesQuantity(quantity);
					dsd.setSalesAmount(quantity * getSalesPrice(name));
					list.add(dsd);
				}
				db.rs.close();
				db.dbClose();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return list;
		}
		
		//월보 조회
		public ArrayList<MonthlyReport> monthlyReportRead(String date) {
			ArrayList<MonthlyReport> list = new ArrayList<MonthlyReport>();
			MonthlyReport mr;
			
			try {
				db.dbConn();
				String sql = "SELECT * FROM dailysales WHERE date >= ? AND date <= LAST_DAY(?) ";
				db.pst = db.conn.prepareStatement(sql);
				db.pst.setString(1, date+"01");
				db.pst.setString(2, date+"01");
				db.rs = db.pst.executeQuery();
				
				while(db.rs.next()) {
					mr = new MonthlyReport();
					String salesdate = db.rs.getString("date");
					int cash = db.rs.getInt("cashAmount");
					int card = db.rs.getInt("cardAmount");
					int refund = db.rs.getInt("refundAmount");
					
					mr.setDate(salesdate);
					mr.setCashAmount(cash);
					mr.setCardAmount(card);
					mr.setRefundAmount(refund);
					list.add(mr);
				}
				db.rs.close();
				db.dbClose();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return list;
		}
		
		//설계에서 에는 있으나 용도를 모르겠음
		public int calculationValueAddedTax(DailySales saleHistory) {
			
			return 0;
		}
		int calculationMoneyAmount(DailySales saleHistory) {
			
			return 0;
		}
		int calculationCardAmount(DailySales saleHistory) {
			
			return 0;
		}
		int calculationRefundAmount(DailySales saleHistory) {
			
			return 0;
		}
}

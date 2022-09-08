/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	SaleManagement
 * @className 			SaleManager
 * @author 					김태민
 */
package SaleManagement;

import java.beans.Customizer;
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import MemberManagement.Member;
import ProductManagement.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import SaleManagement.SaleHistory;
import SystemManagement.DBControl;

public class SaleManager {
	private DBControl db = new DBControl();
	
	public boolean saleProduct(Product[] productList, Member member, String creditCardInfo, SaleMessage message) {
		VanIO vanIO = null;
		SaleHistory saleHistory = null;
		int amount = 0;
		for (int i = 0; i < productList.length; i++)
		{
			amount = productList[i].getSalePrice() * productList[i].getStockQuantity();
		}
		if (amount == 0) return false;
		
		String memberCode = "비회원";
		if (member != null) {
			amount = amount - amount/10;
			memberCode = member.getMemberCode();
		}
		
		String saleHistoryCode = nextSaleHisotryCode();
		if (saleHistoryCode == null) return false;
		
		String paymentMathod = "현금";
		if (creditCardInfo != null) {
			vanIO = new VanIO();
			if (! vanIO.requestPayment(creditCardInfo, amount, saleHistoryCode, message)) {
				return false;	
			}
			paymentMathod = "카드";
		}
		
		saleHistory = new SaleHistory(saleHistoryCode, paymentMathod, new Date() , amount, memberCode, false, null, productList);
		registerSaleHistory(saleHistory);
		
		printReceipt(saleHistory);
		
		return true;
	}
	
	public String nextSaleHisotryCode() {
		Statement stmt = null;
		ResultSet rs = null;
		Connection connection = null;
		
		String saleHistoryCode = null;

		try {
			connection = db.dbConn();
			System.out.println("connection success");
		
			stmt = connection.createStatement();
			String sql;
			
			
			// 판매코드 생성
			sql = "select codeName, codeStatus from pharmacy.codemanagement where codeName LIKE '%S%';";
			rs = stmt.executeQuery(sql);
			String codeName = "";
			int codeStatus = 0;
			if(rs.next()) {
				codeName = rs.getString("codeName");
				codeStatus = rs.getInt("codeStatus") + 1;
			}
			
			int digit = 1;	// 자릿수
			int temp = codeStatus;
			while ((temp=temp / 10) != 0) {digit++;}
			saleHistoryCode = codeName ;
			for (int i = 0; i < 4 - digit; i++) saleHistoryCode += "0";
			saleHistoryCode += Integer.toString(codeStatus);
			
			System.out.println(saleHistoryCode);
		}
		catch (Exception e) { e.printStackTrace(); }
		finally {
			if (stmt != null) { try { stmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (connection != null) {try { connection.close();}  catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return saleHistoryCode;
	}
	
	public void registerSaleHistory(SaleHistory saleHistory) {
		Statement stmt = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = db.dbConn();
			System.out.println("connection success");
		
			stmt = connection.createStatement();
			String sql;
			// 판매이력 등록
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			saleHistory.setSaleHistryCode(saleHistory.getSaleHistryCode());
			sql = "insert into pharmacy.salesmaster values(?, ?, ?, ?, ?, ?, ?)";
			// (salesCode, salesDate, payMethod, salesAmount, memberID, refundState, refundDate)
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, saleHistory.getSaleHistryCode());
			pstmt.setString(2, sdf.format(saleHistory.getSaleDate()));
			pstmt.setString(3, saleHistory.getPaymentMethod());
			pstmt.setInt(4, saleHistory.getSaleAmount());
			pstmt.setString(5, saleHistory.getMemberCode());
			pstmt.setInt(6, ((saleHistory.getRefundFlag()) ? 1 : 0) );
			pstmt.setString(7, "0001-01-01 00:00:00");
			pstmt.executeUpdate();
			pstmt.close();
			
			// 판매코드 업데이트 + 1
			sql = "update pharmacy.codemanagement set codeStatus=codeStatus+1 where codeName='" + saleHistory.getSaleHistryCode().substring(0, 5) + "'";
			stmt.executeUpdate(sql);
			
			// 판매이력의 판매물품 등록
			Product[] pdList = saleHistory.getProductList();
			for (int i = 0; i < pdList.length; i++) {
				sql = "select productCode from pharmacy.productstock where productName='"+ pdList[i].getProductName()
						+ "' and expirationDate='" + pdList[i].getExpirationDate() + "'";
				rs = stmt.executeQuery(sql);
				
				String productCode = "";
				if (rs.next()) productCode = rs.getString("productCode");
				sql = "insert into pharmacy.salesdetail values(?, ?, ?, ?)";
				// (salesCode, productCode, productName, quantity)
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, saleHistory.getSaleHistryCode());
				pstmt.setString(2, productCode);
				pstmt.setString(3, pdList[i].getProductName());
				pstmt.setInt(4, pdList[i].getStockQuantity());
				pstmt.executeUpdate();
				pstmt.close();
				
				// 판매된 상품 재고 업데이트
				sql = "update pharmacy.productlist set quantity=quantity-"+pdList[i].getStockQuantity()+" where productName='" + pdList[i].getProductName() + "'";
				stmt.executeUpdate(sql);
				sql = "update pharmacy.productstock set quantity=quantity-"+pdList[i].getStockQuantity()+" where productCode='" + productCode + "'";
				stmt.executeUpdate(sql);
			}

		}
		catch (Exception e) { e.printStackTrace(); }
		finally {
			if (stmt != null) { try { stmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (pstmt != null) { try { pstmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (connection != null) {try { connection.close();}  catch (SQLException e) { e.printStackTrace(); } }
		}
	}
	
	public Member identificationCheck(String memberCode) {
		Statement stmt = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		
		Member member = null;
		try {
			connection = db.dbConn();
			System.out.println("connection success");
			
			stmt = connection.createStatement();
			String sql;
			sql = "select * from pharmacy.member where memberID='" + memberCode + "'";
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				String memberName = rs.getString("name");
				String memberPhone = rs.getString("phone");
				Integer concernedStatus = rs.getInt("Interest");
				String concernedReason = rs.getString("reason");
				member = new Member(memberCode, memberName, memberPhone, concernedStatus, concernedReason);
			}
		}
		catch (Exception e) { e.printStackTrace(); }
		finally {
			if (stmt != null) { try { stmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (pstmt != null) { try { pstmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (connection != null) {try { connection.close();}  catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return member;
	}
	
	public boolean cancelSale(String saleHistoryCode, SaleMessage message) {	
		Statement stmt = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		
		VanIO vanIO = null;
		
		try {
			connection = db.dbConn();
			System.out.println("connection success");
			
			stmt = connection.createStatement();
			String sql = "select * from pharmacy.salesmaster where salesCode='" + saleHistoryCode + "'";
			rs = stmt.executeQuery(sql);
			
			SaleHistory saleHistory = null;
			String paymentMethod = "";
			Date saleDate = null;
			int saleAmount = 0;
			String memberCode = "";
			boolean refundFlag = true;
			Date refundDate = new Date();
			Product[] pdList = null;
			
			if (rs.next()) {
				paymentMethod = rs.getString("payMethod");
				saleDate = rs.getDate("salesDate");
				saleAmount = rs.getInt("salesAmount");
				memberCode = rs.getString("memberID");
				if (paymentMethod.equals("카드")) {
					vanIO = new VanIO();
					if (! vanIO.requestRefund(saleHistoryCode, message))
						return false;
				}
			}
			else {
				return false;
			}
			// 판매이력 환불 처리 업데이트
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql = "update pharmacy.salesmaster set refundState=?, refundDate=? where salesCode=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, (refundFlag ? 1 : 0));
			pstmt.setString(2, sdf.format(refundDate));
			pstmt.setString(3, saleHistoryCode);
			pstmt.executeUpdate();
			pstmt.close();
		
			// 판매물품 업데이트 재고 등록
			Vector<Product> productList = new Vector<Product>();
			sql = "select * from pharmacy.salesdetail where salesCode='" + saleHistoryCode + "'";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				String productCode = rs.getString("productCode");
				String productName = rs.getString("productName");
				int quantity = rs.getInt("quantity");
				
				sql = "select expirationDate from pharmacy.productstock where productCode='" + productCode + "'";
				ResultSet rsTemp = stmt.executeQuery(sql);
				String expirationDate = "";
				if (rsTemp.next()) expirationDate = rsTemp.getString("expirationDate");
				
				sql = "select salesPrice from pharmacy.productlist where productName='" + productName + "'";
				rsTemp = stmt.executeQuery(sql);
				int salePrice = 0;
				if (rsTemp.next()) salePrice = rsTemp.getInt("salesPrice");
				
				
				productList.add(new Product(productName, expirationDate, salePrice, quantity));

				sql = "update pharmacy.productstock set quantity=quantity+" + quantity + " where productCode='" + productCode + "'";
				stmt.executeUpdate(sql);
				sql = "update pharmacy.productlist set quantity=quantity+" + quantity + " where productName='" + productName + "'";
				stmt.executeUpdate(sql);
			}
		
			pdList = new Product[productList.size()];
			pdList = (Product[])productList.toArray(pdList);
			
			// 영수증 출력 요청
			saleHistory = new SaleHistory(saleHistoryCode, paymentMethod, saleDate, saleAmount, memberCode, refundFlag, refundDate, pdList);
			printReceipt(saleHistory);
		}
		catch (Exception e) { e.printStackTrace(); }
		finally {
			if (stmt != null) { try { stmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (pstmt != null) { try { pstmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (connection != null) {try { connection.close();}  catch (SQLException e) { e.printStackTrace(); } }
		}
		return true;
	}
	
	public SaleHistory[] readSaleHistory(String saleHistoryCode) {
		Statement stmt = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		SaleHistory[] saleHistoryList = null;
		
		Vector<SaleHistory> sHistorys = new Vector<SaleHistory>();
		try {
			connection = db.dbConn();
			System.out.println("connection success");
			
			stmt = connection.createStatement();
			String sql="select * from pharmacy.salesmaster where salesCode='"+saleHistoryCode+"'" ;
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				String salesCode = rs.getString("salesCode");
				Date salesDate = rs.getTimestamp("salesDate");
				String payMethod = rs.getString("payMethod");
				int salesAmount = rs.getInt("salesAmount");
				String memberCode = rs.getString("memberID");
				boolean refundState = (rs.getInt("refundState") != 0);
				Date refundDate = rs.getTimestamp("refundDate");
				
				sHistorys.add(new SaleHistory(salesCode, payMethod, salesDate, salesAmount, memberCode, refundState, refundDate, null));
			}
			if (sHistorys.size() != 0) {
				saleHistoryList = new SaleHistory[sHistorys.size()];
				saleHistoryList = (SaleHistory[]) sHistorys.toArray(saleHistoryList);
				
				for (int i = 0; i < saleHistoryList.length; i++)
				{
					Product[] pdList = null;
					sql = "select * from pharmacy.salesdetail where salesCode ='" + saleHistoryList[i].getSaleHistryCode() +"'";
					pstmt = connection.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					Vector<Product> productList = new Vector<Product>();
					while (rs.next()) {
						String productCode = rs.getString("productCode");
						String productName = rs.getString("productName");
						int quantity = rs.getInt("quantity");
						
						sql = "select expirationDate from pharmacy.productstock where productCode='" + productCode + "'";
						ResultSet rsTemp = stmt.executeQuery(sql);
						String expirationDate = "";
						if (rsTemp.next()) expirationDate = rsTemp.getString("expirationDate");
						
						sql = "select salesPrice from pharmacy.productlist where productName='" + productName + "'";
						rsTemp = stmt.executeQuery(sql);
						int salePrice = 0;
						if (rsTemp.next()) salePrice = rsTemp.getInt("salesPrice");
						
						productList.add(new Product(productName, expirationDate, salePrice, quantity));
					}
					pdList = new Product[productList.size()];
					pdList = (Product[])productList.toArray(pdList);
					
					saleHistoryList[i].setProductList(pdList);
				}
			}
		}
		catch (Exception e) { e.printStackTrace(); }
		finally {
			if (stmt != null) { try { stmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (pstmt != null) { try { pstmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (connection != null) {try { connection.close();}  catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return saleHistoryList;
	}
	
	public SaleHistory[] readSaleHistory(Date startDate, Date endDate) {
		Statement stmt = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		SaleHistory[] saleHistoryList = null;
		
		Vector<SaleHistory> sHistorys = new Vector<SaleHistory>();
		try {
			connection = db.dbConn();
			System.out.println("connection success");
			
			stmt = connection.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql="select * from pharmacy.salesmaster where salesDate >= '" + sdf.format(startDate) + "' and salesDate <= '" + sdf.format(endDate) + "'" ;
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				String salesCode = rs.getString("salesCode");
				Date salesDate = rs.getTimestamp("salesDate");
				String payMethod = rs.getString("payMethod");
				int salesAmount = rs.getInt("salesAmount");
				String memberCode = rs.getString("memberID");
				boolean refundState = (rs.getInt("refundState") != 0);
				Date refundDate = rs.getTimestamp("refundDate");
				
				sHistorys.add(new SaleHistory(salesCode, payMethod, salesDate, salesAmount, memberCode, refundState, refundDate, null));
			}
			if (sHistorys.size() != 0) {
				saleHistoryList = new SaleHistory[sHistorys.size()];
				saleHistoryList = (SaleHistory[]) sHistorys.toArray(saleHistoryList);
				
				for (int i = 0; i < saleHistoryList.length; i++)
				{
					Product[] pdList = null;
					sql = "select * from pharmacy.salesdetail where salesCode ='" + saleHistoryList[i].getSaleHistryCode() +"'";
					pstmt = connection.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					Vector<Product> productList = new Vector<Product>();
					while (rs.next()) {
						String productCode = rs.getString("productCode");
						String productName = rs.getString("productName");
						int quantity = rs.getInt("quantity");
						
						sql = "select expirationDate from pharmacy.productstock where productCode='" + productCode + "'";
						ResultSet rsTemp = stmt.executeQuery(sql);
						String expirationDate = "";
						if (rsTemp.next()) expirationDate = rsTemp.getString("expirationDate");
						
						sql = "select salesPrice from pharmacy.productlist where productName='" + productName + "'";
						rsTemp = stmt.executeQuery(sql);
						int salePrice = 0;
						if (rsTemp.next()) salePrice = rsTemp.getInt("salesPrice");
						
						productList.add(new Product(productName, expirationDate, salePrice, quantity));
					}
					pdList = new Product[productList.size()];
					pdList = (Product[])productList.toArray(pdList);
					
					saleHistoryList[i].setProductList(pdList);
				}
			}
		}
		catch (Exception e) { e.printStackTrace(); }
		finally {
			if (stmt != null) { try { stmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (pstmt != null) { try { pstmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (connection != null) {try { connection.close();}  catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return saleHistoryList;
	}
	
	public SaleHistory[] readSaleHistory(String saleHistoryCode, Date startDate, Date endDate) {
		Statement stmt = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		SaleHistory[] saleHistoryList = null;
		
		Vector<SaleHistory> sHistorys = new Vector<SaleHistory>();
		try {
			connection = db.dbConn();
			System.out.println("connection success");
			
			stmt = connection.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql="select * from pharmacy.salesmaster where salesCode='"+saleHistoryCode+"' and salesDate >= '" + sdf.format(startDate) + "' and salesDate <= '" + sdf.format(endDate) + "'" ;
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				String salesCode = rs.getString("salesCode");
				Date salesDate = rs.getTimestamp("salesDate");
				String payMethod = rs.getString("payMethod");
				int salesAmount = rs.getInt("salesAmount");
				String memberCode = rs.getString("memberID");
				boolean refundState = (rs.getInt("refundState") != 0);
				Date refundDate = rs.getTimestamp("refundDate");
				
				sHistorys.add(new SaleHistory(salesCode, payMethod, salesDate, salesAmount, memberCode, refundState, refundDate, null));
			}
			if (sHistorys.size() != 0) {
				saleHistoryList = new SaleHistory[sHistorys.size()];
				saleHistoryList = (SaleHistory[]) sHistorys.toArray(saleHistoryList);
				
				for (int i = 0; i < saleHistoryList.length; i++)
				{
					Product[] pdList = null;
					sql = "select * from pharmacy.salesdetail where salesCode ='" + saleHistoryList[i].getSaleHistryCode() +"'";
					pstmt = connection.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					Vector<Product> productList = new Vector<Product>();
					while (rs.next()) {
						String productCode = rs.getString("productCode");
						String productName = rs.getString("productName");
						int quantity = rs.getInt("quantity");
						
						sql = "select expirationDate from pharmacy.productstock where productCode='" + productCode + "'";
						ResultSet rsTemp = stmt.executeQuery(sql);
						String expirationDate = "";
						if (rsTemp.next()) expirationDate = rsTemp.getString("expirationDate");
						
						sql = "select salesPrice from pharmacy.productlist where productName='" + productName + "'";
						rsTemp = stmt.executeQuery(sql);
						int salePrice = 0;
						if (rsTemp.next()) salePrice = rsTemp.getInt("salesPrice");
						
						productList.add(new Product(productName, expirationDate, salePrice, quantity));
					}
					pdList = new Product[productList.size()];
					pdList = (Product[])productList.toArray(pdList);
					
					saleHistoryList[i].setProductList(pdList);
				}
			}
		}
		catch (Exception e) { e.printStackTrace(); }
		finally {
			if (stmt != null) { try { stmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (pstmt != null) { try { pstmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (connection != null) {try { connection.close();}  catch (SQLException e) { e.printStackTrace(); } }
		}
		
		return saleHistoryList;
	}
	
	public void printReceipt(SaleHistory saleHistory) {
		PrintReceiptIO printReceiptIO = new PrintReceiptIO();
		printReceiptIO.printReceipt(saleHistory);
	}
	
	public Product readProduct(String barcode) {
		Statement stmt = null;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		
		Product product = null;
		try {
			connection = db.dbConn();
			System.out.println("connection success");
			
			stmt = connection.createStatement();
			String sql;
			sql = "select productName, expirationDate from pharmacy.productstock where productCode='"+ barcode +"'";
			rs = stmt.executeQuery(sql);
			String productName = "";
			String expirationDate = null;
			int salePrice=0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			if (rs.next()) { 
				productName = rs.getString("productName");
				expirationDate = sdf.format(rs.getDate("expirationDate"));
				
				sql = "select salesPrice from pharmacy.productlist where productName='"+ productName +"'";
				rs = stmt.executeQuery(sql);
				if (rs.next()) salePrice = rs.getInt("salesPrice");
				
				product = new Product(productName, expirationDate, salePrice, 1);
			}
		}
		catch (Exception e) { e.printStackTrace(); }
		finally {
			if (stmt != null) { try { stmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (pstmt != null) { try { pstmt.close(); }  catch (SQLException e) {e.printStackTrace(); }  }
			if (connection != null) {try { connection.close();}  catch (SQLException e) { e.printStackTrace(); } }
		}
		return product;
	}
}

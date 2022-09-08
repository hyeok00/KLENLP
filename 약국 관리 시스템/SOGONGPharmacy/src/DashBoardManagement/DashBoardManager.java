/**
 * @systemName			SOGONG Pharmacy Management System
 * @subSystemName	DashBoardManagement
 * @className 			DashBoardManager
 * @author 					최의준
 */	
package DashBoardManagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import SystemManagement.DBControl;

public class DashBoardManager {
	DBControl db = new DBControl();

	public ArrayList<SalesData> SalesDataOutput(String startDate, String lastDate) {
		ArrayList<SalesData> list = new ArrayList<SalesData>();
		SalesData sd;
		
		try {
			db.dbConn();
			String sql = "select date, cashAmount, cardAmount, refundAmount " + 
					"From dailysales " + 
					"WHERE date >= ? AND date <= ?";
			db.pst = db.conn.prepareStatement(sql);
			db.pst.setString(1, startDate);
			db.pst.setString(2, lastDate);
			db.rs = db.pst.executeQuery();
			
			String sql2 = "SELECT salesmaster.salesDate, salesdetail.productName, salesdetail.quantity " + 
					"FROM salesmaster " + 
					"RIGHT OUTER JOIN salesdetail ON salesmaster.salesCode = salesdetail.salesCode " + 
					"WHERE salesDate >= ? AND salesDate <= ?";
			db.pst2 = db.conn.prepareStatement(sql2);
			db.pst2.setString(1, startDate);
			db.pst2.setString(2, lastDate);
			db.rs2 = db.pst2.executeQuery();
			
			int total = 0;
			
			while (db.rs.next()) {
				sd = new SalesData();
				String date = db.rs.getString("date");
				int cash = db.rs.getInt("cashAmount");
				int card = db.rs.getInt("cardAmount");
				int refund = db.rs.getInt("refundAmount");
				sd.setSalesDate(date);
				sd.setSales(card + cash - refund);
				sd.setProductName(null);
				sd.setSalesQuantity(0);
				sd.setSalesProportion(0);
				list.add(sd);
			}
			db.rs.close();
			
			while (db.rs2.next()) {
				total += db.rs2.getInt("salesdetail.quantity");
			}
			db.rs2.first();
			do {
				sd = new SalesData();
				String date = db.rs2.getString("salesmaster.salesDate");
				String name = db.rs2.getString("salesdetail.productName");
				int quantity = db.rs2.getInt("salesdetail.quantity");
				double proportion = quantity * 100.0 / total;
				sd.setSalesDate(date);
				sd.setSales(0);
				sd.setProductName(name);
				sd.setSalesQuantity(quantity);
				sd.setSalesProportion(proportion);
				list.add(sd);
			} while (db.rs2.next());
			db.rs2.close();
			db.dbClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	public int getSales(String startDate, String lastDate) {
		int sales = 0;
	
		try {
			db.dbConn();
			String sql = "SELECT dailysales.cashAmount, dailysales.cardAmount FROM dailysales "
					+ "WHERE Date >= ? AND Date <= ?";
			
			db.pst = db.conn.prepareStatement(sql);
			db.pst.setString(1, startDate);
			db.pst.setString(2, lastDate);
			db.rs = db.pst.executeQuery();
			while(db.rs.next()) {
				sales += db.rs.getInt("cashAmount") + db.rs.getInt("cardAmount");
			}
				
			db.rs.close();
			db.dbClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sales;
	}
	
	public int getExpense(String startDate, String lastDate) {
		int expense = 0;
		
		
		try {
			db.dbConn();
			String sql = "SELECT SupplierProduct.cost, EnteringList.enteringDate FROM SupplierProduct, EnteringList "
					+ "WHERE SupplierProduct.supplierCode = EnteringList.supplierCode AND SupplierProduct.productName = EnteringList.productName AND enteringDate >= ? AND enteringDate <= ?";
			
			db.pst = db.conn.prepareStatement(sql);
			db.pst.setString(1, startDate);
			db.pst.setString(2, lastDate);
			db.rs = db.pst.executeQuery();
			while(db.rs.next()) {
				expense += db.rs.getInt("cost");
			}
				
			db.rs.close();
			db.dbClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return expense;
	}
	public int getMargin(String startDate, String lastDate) {
		return getSales(startDate, lastDate) - getExpense(startDate, lastDate);
	}
}

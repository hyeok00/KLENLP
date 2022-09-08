/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	SupplierManagement
 * @className 		SupplierManager
 * @author 			이수하
 */
package SupplierManagement;

import java.sql.SQLException;
import java.util.ArrayList;

import SystemManagement.DBControl;

import java.sql.PreparedStatement;


public class SupplierManager {
	DBControl db = new DBControl();
	ArrayList<Supplier> list;
	
	public void registerSupplier(Supplier supplier) {
		try {
			db.dbConn();	
			String sql = "insert into supplier values(?,?,?,?);";
			db.pst = db.conn.prepareStatement(sql);
			
			db.pst.setString(1, supplier.getSupplierCode());
			db.pst.setString(2, supplier.getSupplierName());
			db.pst.setString(3, supplier.getContactNum());
			db.pst.setString(4, supplier.getClosed());
			int result = db.pst.executeUpdate();
			System.out.println(result);
			
			String sql2 = "insert into supplierproduct values(?,?,?);";
			db.pst = db.conn.prepareStatement(sql2);
			db.pst.setString(1, supplier.getSupplierCode());
			db.pst.setString(2, supplier.getProductClassify());
			db.pst.setInt(3, supplier.getCost());
			int result2 = db.pst.executeUpdate();
			System.out.println(result2);
//			db.rs.close();
//			db.dbClose();
		}catch(SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

	}
	
	public void changeSupplier(Supplier supplier) {
		try {
			db.dbConn();	
			String sql = "update supplier set supplier.supplierCode=?, supplier.name=?, supplier.phone=?, supplier.closedDay=? where supplier.supplierCode=?";
			db.pst = db.conn.prepareStatement(sql);
			
			db.pst.setString(1, supplier.getSupplierCode());
			db.pst.setString(2, supplier.getSupplierName());
			db.pst.setString(3, supplier.getContactNum());
			db.pst.setString(4, supplier.getClosed());
			db.pst.setString(5, supplier.getSupplierCode());
			
			int result = db.pst.executeUpdate();
			System.out.println(result);
			
			String sql2 = "update supplierproduct set supplierproduct.supplierCode=?, supplierproduct.productName=?, supplierproduct.cost=? where supplierproduct.supplierCode=? and supplierproduct.productName=?";
			db.pst = db.conn.prepareStatement(sql2);
			
			db.pst.setString(1, supplier.getSupplierCode());
			db.pst.setString(2, supplier.getProductClassify());
			db.pst.setInt(3, supplier.getCost());
			db.pst.setString(4, supplier.getSupplierCode());
			db.pst.setString(5, supplier.getProductClassify());
			
			int result2 = db.pst.executeUpdate();
			System.out.println(result2);
			
		}catch(SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

	}
	
	public void deleteSupplier(Supplier supplier) {
			try {
				db.dbConn();
				String sql = "delete from supplierproduct where supplierproduct.supplierCode=?";
				db.pst = db.conn.prepareStatement(sql);
				db.pst.setString(1, supplier.getSupplierCode());
				db.pst.executeUpdate();
				
				String sql2 = "delete from supplier where supplier.supplierCode=?";
				db.pst = db.conn.prepareStatement(sql2);
				db.pst.setString(1, supplier.getSupplierCode());
				db.pst.executeUpdate();
			}catch(SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
	
	public ArrayList<Supplier> readSuppiler(String name) {
		Supplier supplier;
		
		list = new ArrayList<Supplier>();
		
		try {
			db.dbConn();
			String sql ="select supplier.*, supplierproduct.productName, supplierproduct.cost  from supplier inner join supplierproduct on supplier.supplierCode=supplierproduct.supplierCode where supplierproduct.productName='"+name+"'";
			
			db.pst = db.conn.prepareStatement(sql);
			db.rs = db.pst.executeQuery();

			
			while(db.rs.next()) {
				
				supplier = new Supplier();
				
				String supplierCode = db.rs.getString("supplierCode");
				String supplierName = db.rs.getString("name");
				String contactNum = db.rs.getString("phone");
				String closed  = db.rs.getString("closedDay");
				String productClassify = db.rs.getString("productName");
				int cost = db.rs.getInt("cost");
				
			
				
				supplier.setSupplierCode(supplierCode);
				supplier.setSupplierName(supplierName);
				supplier.setContactNum(contactNum);
				supplier.setClosed(closed);
				supplier.setProductClassify(productClassify);
				supplier.setCost(cost);	
				
				list.add(supplier);
				
				
			}
//			db.rs.close();
//			db.dbClose();
		}catch(SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return list;
	}
	public  ArrayList<Supplier> readAllSuppliers() {
		Supplier supplier;
		
		list = new ArrayList<Supplier>();
		
		try {
			db.dbConn();
			String sql ="select supplier.*, supplierproduct.productName, supplierproduct.cost  from supplier inner join supplierproduct on supplier.supplierCode=supplierproduct.supplierCode";
			
			db.pst = db.conn.prepareStatement(sql);
			db.rs = db.pst.executeQuery();

			int count = 0;
			SupplierReadView srv = new SupplierReadView();
			
			while(db.rs.next()) {
				
				supplier = new Supplier();
				
				String supplierCode = db.rs.getString("supplierCode");
				String supplierName = db.rs.getString("name");
				String contactNum = db.rs.getString("phone");
				String closed  = db.rs.getString("closedDay");
				String productClassify = db.rs.getString("productName");
				int cost = db.rs.getInt("cost");
				
			
				
				supplier.setSupplierCode(supplierCode);
				supplier.setSupplierName(supplierName);
				supplier.setContactNum(contactNum);
				supplier.setClosed(closed);
				supplier.setProductClassify(productClassify);
				supplier.setCost(cost);	
				
				list.add(supplier);
				
				
			}
//			db.rs.close();
//			db.dbClose();
		}catch(SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return list;
	}
}

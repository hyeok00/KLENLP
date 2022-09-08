/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	Product Management System
 * @className 		ProductManager
 * @author 			류기혁
 */
package ProductManagement;

import java.io.*;
import java.sql.ResultSet;
import java.util.*;

import SystemManagement.DBControl;


public class ProductManager {
	DBConnecter db = new DBConnecter();

	public ProductManager() {
	}

	public void registerProduct(String productName, int productPrice, int productParStock) { // 상품 등록
		try {
			db.dbConn();
			String sql = "insert into productlist(productName, salesPrice, quantity, optimumStock) values(?,?,?,?)";
			db.pst = db.conn.prepareStatement(sql);

			db.pst.setString(1, productName);
			db.pst.setInt(2, productPrice);
			db.pst.setInt(3, 0);
			db.pst.setInt(4, productParStock);
			System.out.println(db.pst);
			int res = db.pst.executeUpdate();
			System.out.println();

			/*
			 * db.pst = db.conn.prepareStatement(sql); db.rs = db.pst.executeQuery();
			 * 
			 * while (db.rs.next()) { pr = new Product(); String name =
			 * db.rs.getString("productName"); int price = db.rs.getInt("salesPrice"); int
			 * quantity = db.rs.getInt("quantity"); int stock =
			 * db.rs.getInt("optimumStock");
			 * 
			 * pr.setProductName(name); pr.setSalePrice(price);
			 * pr.setStockQuantity(quantity); pr.setOptimumStock(stock); list.add(pr); }
			 */
			db.pst.close();
			db.dbClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Product> readProduct() { // 모든 상품 출력,
		ArrayList<Product> list = new ArrayList<Product>();
		Product pr;

		try {
			db.dbConn();
			String sql = "SELECT * FROM productList";
			db.pst = db.conn.prepareStatement(sql);
			db.rs = db.pst.executeQuery();

			while (db.rs.next()) {
				pr = new Product();
				String name = db.rs.getString("productName");
				int price = db.rs.getInt("salesPrice");
				int quantity = db.rs.getInt("quantity");
				int stock = db.rs.getInt("optimumStock");

				pr.setProductName(name);
				pr.setSalePrice(price);
				pr.setStockQuantity(quantity);
				pr.setOptimumStock(stock);
				list.add(pr);
			}
			db.rs.close();
			db.dbClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> readStock(String s) { // 특정 상품의 재고 정보 조회
		ArrayList<String> list = new ArrayList<String>();

		try {
			db.dbConn();
			String sql = "select productstock.productCode,productlist.productName, productstock.expirationDate, productstock.quantity from productlist inner join productstock on productlist.productName=productstock.productName where productlist.productName=\"";
			sql += s + "\"";
			db.pst = db.conn.prepareStatement(sql);
			db.rs = db.pst.executeQuery();
			while (db.rs.next()) {
				String code = db.rs.getString("productCode");
				String name = db.rs.getString("productName");
				String date = db.rs.getString("expirationDate");
				String quantity = String.valueOf(db.rs.getInt("quantity"));
				String str = code + "," + name + "," + date + "," + quantity;
				list.add(str);
			}
			db.rs.close();
			db.dbClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void changeProduct(String productName, int productPrice, int productParStock) { // 상품의 정보를 입력해 해당 상품의 정보 수정
		try {
			db.dbConn();
			String sql = "update productlist set salesPrice=?,optimumStock=? where productName=?";
			db.pst = db.conn.prepareStatement(sql);

			db.pst.setInt(1, productPrice);
			db.pst.setInt(2, productParStock);
			db.pst.setString(3, productName);
			int res = db.pst.executeUpdate();

			db.pst.close();
			db.dbClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteProduct(String productName) { // 상품코드와 수량을 입력 받아 해당 상품의 적정재고량 갱신
		try {
			db.dbConn();
			String sql = "delete from productlist where productName=?";
			db.pst = db.conn.prepareStatement(sql);

			db.pst.setString(1, productName);
			int res = db.pst.executeUpdate();
			
			db.pst.close();
			db.dbClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean redundancyCheck(String productName, int productPrice, String productExpirationDate,
			int productParStock) { // 상품의 중복 체크

		return true;
	}

	public boolean essentialCheck(String productName, int productPrice, String productExpirationDate,
			int productParStock) { // 상품 정보 필수 항목 검사
		if (productName == "" || productPrice == 0 || productExpirationDate == "" || productParStock == 0)
			return false;

		return true;
	}

	public boolean formatCheck(String productName, int productPrice, String productExpirationDate,
			int productParStock) { // 상품 정보 양식 검사
		if (productPrice < 0 || productParStock < 0)
			return false;

		return true;
	}
}

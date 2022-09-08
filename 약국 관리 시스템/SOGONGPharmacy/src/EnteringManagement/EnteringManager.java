/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	EnteringManagement
 * @className 		EnteringManager
 * @author 			강정환
 */	


package EnteringManagement;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSeparatorUI;

import SystemManagement.DBControl;

import java.sql.Date;
import java.sql.PreparedStatement;


public class EnteringManager {
	DBControl db = new DBControl();
	ArrayList<Entering> list;
	
	
	public void registerEntering(Entering entering) {
		try {
			
			String supplierCode="";
			String enteringCode="";
			int count = 0;
			db.dbConn();
			
			// productlist 재고 추가
			String sql5 = "select * from productlist where productName = '" + entering.getProductName() + "'";
			int addQuantity = 0;
			
			db.pst = db.conn.prepareStatement(sql5);
			db.rs = db.pst.executeQuery();
			
			if(db.rs.next()) {
				addQuantity = db.rs.getInt("quantity");
			}
			
			int sum = 0;
			sum = addQuantity + entering.getEnteringQuantity();

			String sql6 = "UPDATE productlist SET quantity='" + sum + "' where productName = '" + entering.getProductName() + "'";
			db.pst = db.conn.prepareStatement(sql6);
			int result2 = db.pst.executeUpdate();
			System.out.println(result2);
			
			
			// supplier 테이블에서 거래처 코드 따오기
			String sql1 = "select supplierCode from supplier where name = '" + entering.getSupplierName() + "'";
			db.pst = db.conn.prepareStatement(sql1);
			db.rs = db.pst.executeQuery();
			
			if(db.rs.next()) {
				supplierCode = db.rs.getString("supplierCode");
			}
			
			// enteringlist 에서 갯수 파악 후 입고 코드 설정
			String sql2 = "select enteringCode from enteringlist";
			
			db.pst = db.conn.prepareStatement(sql2);
			db.rs = db.pst.executeQuery();
			
			while(db.rs.next()) {
				count++;
			}
			
			int temp_count = count - 1;
			String temp_code = "";
			
			String codeSql = "select * from enteringlist order by enteringCode limit " + temp_count + "," + count;
			db.pst = db.conn.prepareStatement(codeSql);
			db.rs = db.pst.executeQuery();
			
			if(db.rs.next()) {
				temp_code = db.rs.getString("enteringCode");
			}
			
			String temp_code2 = temp_code.substring(1);
			int convert_temp = Integer.parseInt(temp_code2);	
			int add_temp = convert_temp + 1;
			
			enteringCode = "E" + add_temp;

			// insert 문 삽입
			
			// Date 형으로 형변환
			java.sql.Date entering_date = java.sql.Date.valueOf(entering.getEnteringDate());
			java.sql.Date expiration_date = java.sql.Date.valueOf(entering.getExpirationDate());
			
			String sql4 = "insert into enteringlist values('"+enteringCode + "','" + entering_date + "','" + entering.getProductName() + "','" + expiration_date + "','" + entering.getEnteringQuantity() + "','" + supplierCode + "')";
			db.pst = db.conn.prepareStatement(sql4);

			int result = db.pst.executeUpdate();
			System.out.println(result);

			String productCode = "";

			productCode = "P" + add_temp;
			
			String sql7 = "insert into productstock values('" + productCode + "','" + entering.getProductName() + "','" + expiration_date + "','" + entering.getEnteringQuantity() + "','" + enteringCode + "')";
			db.pst = db.conn.prepareStatement(sql7);
			int result3 = db.pst.executeUpdate();
			System.out.println(result3);
			
			
			db.pst.clearParameters();
			db.rs.close();
			db.dbClose();
			
			

		}catch(SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

	}
	
	public int changeEntering(Entering entering, int pageNumber, int ori) {
		
		String entering_code = "";
		int oriQuantity = 0;
		
		

		if(pageNumber == 1) {	// 1번 페이지 (수정 가능 여부 검사)
			int flag = 0;
			
			try {
				db.dbConn();
				int quantity = 0;
				int quantity2 = 0;

				
				quantity = entering.getEnteringQuantity();
				
				entering_code = entering.getEnteringCode();
				oriQuantity = entering.getEnteringQuantity();
				
				
				// productstock에 quantity와 같은지 확인
				// 같지 않으면 입고 취소 불가
				
				String sql4 = "select * from productstock where enteringCode = '" + entering.getEnteringCode() + "'";
				db.pst = db.conn.prepareStatement(sql4);
				db.rs = db.pst.executeQuery();
				
				int productQuantity = 0;
				
				if(db.rs.next()) {
					productQuantity = db.rs.getInt("quantity");
				}
				
				if(productQuantity != entering.getEnteringQuantity()) {
					flag = 0;
					
				}
				
				else {
					
					
					
					
					
					db.pst.clearParameters();
					db.rs.close();
					db.dbClose();
					
					flag = 1;
				}
				
				
				
			}catch(SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
			
			return flag;
		}
		
		
		
		else {	// 2번페이지로부터 데이터를 받음, 입고정보 수정
			try {
				int count = 0;
				int result = 0;
				String supplierCode="";
				db.dbConn();
				
				
				// productlist 재고 추가
				

				
				
				
				String sql5 = "select * from productlist where productName = '" + entering.getProductName() + "'";
				int addQuantity = 0;
				
				db.pst = db.conn.prepareStatement(sql5);
				db.rs = db.pst.executeQuery();
				
				if(db.rs.next()) {
					addQuantity = db.rs.getInt("quantity");
				}
				
				result = entering.getEnteringQuantity() - ori;
				int sum = addQuantity + result;
		
				
				
				String sql6 = "UPDATE productlist SET quantity='" + sum + "' where productName = '" + entering.getProductName() + "'";
				db.pst = db.conn.prepareStatement(sql6);
				int result2 = db.pst.executeUpdate();
				System.out.println(result2);
				
				
				// supplier 테이블에서 거래처 코드 따오기
				String sql1 = "select supplierCode from supplier where name = '" + entering.getSupplierName() + "'";
				db.pst = db.conn.prepareStatement(sql1);
				db.rs = db.pst.executeQuery();
				
				if(db.rs.next()) {
					supplierCode = db.rs.getString("supplierCode");
				}
				
				
				// insert 문 삽입
				
				// Date 형으로 형변환
				java.sql.Date entering_date = java.sql.Date.valueOf(entering.getEnteringDate());
				java.sql.Date expiration_date = java.sql.Date.valueOf(entering.getExpirationDate());
				

				
				String sql4 = "update enteringlist set enteringDate = '" + entering_date + "', expirationDate = '" + expiration_date + "', quantity = '" + entering.getEnteringQuantity() + "', supplierCode = '" + supplierCode + "' where enteringCode = '" + entering.getEnteringCode() + "'";
				db.pst = db.conn.prepareStatement(sql4);

				
				System.out.println(entering_date + " / " + expiration_date + " / " + entering.getEnteringQuantity() + " / " + entering.getEnteringCode());
				System.out.println(sql4);
				
				
				
				int result3 = db.pst.executeUpdate();
				System.out.println(result3);
				
				
				String productCode = "";

				
				String sql7 = "update productstock set expirationDate = '" + expiration_date + "', quantity = '" + entering.getEnteringQuantity() + "' where enteringCode = '" + entering.getEnteringCode() + "'";
				db.pst = db.conn.prepareStatement(sql7);
				int result4 = db.pst.executeUpdate();
				System.out.println(result4);
				
				
				db.pst.clearParameters();
				db.rs.close();
				db.dbClose();
				
				
				
				
				
				
			}
			
			
		catch(SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
			
			
			
			
			return 1;
		}
		
		
		
		
		
		
		
	}
	
	
	public int deleteEntering(Entering entering) {
		int flag = 0;
		
		try {
			db.dbConn();
			int quantity = 0;
			int quantity2 = 0;

			
			quantity = entering.getEnteringQuantity();
			
			
			// productstock에 quantity와 같은지 확인
			// 같지 않으면 입고 취소 불가
			
			String sql4 = "select * from productstock where enteringCode = '" + entering.getEnteringCode() + "'";
			db.pst = db.conn.prepareStatement(sql4);
			db.rs = db.pst.executeQuery();
			
			int productQuantity = 0;
			
			if(db.rs.next()) {
				productQuantity = db.rs.getInt("quantity");
			}
			
			if(productQuantity != entering.getEnteringQuantity()) {
				flag = 0;
				
			}
			
			else {
				
				
				String sql5 = "delete from productstock where enteringCode = '" + entering.getEnteringCode() + "'";
				db.pst = db.conn.prepareStatement(sql5);
				int result3 = db.pst.executeUpdate();
				System.out.println(result3);
				
				
				
				String sql = "delete from enteringlist where enteringCode = '" + entering.getEnteringCode() + "'";
				db.pst = db.conn.prepareStatement(sql);
				int result = db.pst.executeUpdate();
				System.out.println(result);
				
				
				
				String sql2 = "select * from productlist where productName = '" + entering.getProductName() + "'";
				db.pst = db.conn.prepareStatement(sql2);
				db.rs = db.pst.executeQuery();
				
				if(db.rs.next()) {
					quantity2 = db.rs.getInt("quantity");
				}
				
				quantity2 = quantity2 - quantity;
				
				String sql3 = "update productlist set quantity = '" + quantity2 + "' where productName = '" + entering.getProductName() + "'";
				db.pst = db.conn.prepareStatement(sql3);
				int result2 = db.pst.executeUpdate();
				System.out.println(result2);
				
				
				db.pst.clearParameters();
				db.rs.close();
				db.dbClose();
				
				flag = 1;
			}
			
			
			
		}catch(SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		return flag;
		
	}
	
	public ArrayList<Entering> readEntering(String name) {
		Entering entering;
		
		list = new ArrayList<Entering>();
		
		
		try {
			db.dbConn();
			String sql ="select * from enteringlist, supplier where enteringlist.supplierCode=supplier.supplierCode and productName='"+name+"'";
			
			
			db.pst = db.conn.prepareStatement(sql);
			db.rs = db.pst.executeQuery();
			
			
		
			EnteringReadView eiv = new EnteringReadView();
			
			while(db.rs.next()) {
				
				entering = new Entering();
				
				String enteringCode = db.rs.getString("enteringCode");
				String enteringDate = db.rs.getString("enteringDate");
				String productName = db.rs.getString("productName");
				int quantity = db.rs.getInt("quantity");
				String supplierName  = db.rs.getString("name");
				String expirationDate = db.rs.getString("expirationDate");
				
			
				entering.setEnteringCode(enteringCode);
				entering.setEnteringDate(enteringDate);
				entering.setProductName(productName);
				entering.setEnteringQuantity(quantity);
				entering.setSupplierName(supplierName);
				entering.setExpirationDate(expirationDate);
				
			
				list.add(entering);
				
				
			}
			
		
			db.rs.close();
			db.dbClose();
		}catch(SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return list;
	}
}

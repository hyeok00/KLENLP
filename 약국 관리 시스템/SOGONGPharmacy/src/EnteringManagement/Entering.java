/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	EnteringManagement
 * @className 		Entering
 * @author 			강정환
 */	


package EnteringManagement;

import java.sql.Date;

public class Entering {
	
	
	public String productName = null;
	public int enteringQuantity = 0;
	public String enteringDate = null;
	public String expirationDate = null;
	public String supplierName = null;
	public String enteringCode = null;
	
	
	
	public String getEnteringCode() {
		return enteringCode;
	}
	public void setEnteringCode(String enteringCode) {
		this.enteringCode = enteringCode;
	}
	public Entering(String productName, int enteringQuantity, String enteringDate, String supplierName,String expirationDate) {
		this.productName = productName;
		this.enteringQuantity = enteringQuantity;
		this.enteringDate = enteringDate;
		this.supplierName = supplierName;
		this.expirationDate = expirationDate;

	}
	public Entering() {
		
	}
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getEnteringQuantity() {
		return enteringQuantity;
	}
	public void setEnteringQuantity(int enteringQuantity) {
		this.enteringQuantity = enteringQuantity;
	}
	public String getEnteringDate() {
		return enteringDate;
	}
	public void setEnteringDate(String enteringDate) {
		this.enteringDate = enteringDate;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	
}

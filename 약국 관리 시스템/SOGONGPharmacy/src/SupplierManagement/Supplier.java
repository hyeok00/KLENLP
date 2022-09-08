/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	SupplierManagement
 * @className 		Supplier
 * @author 			이수하
 */
package SupplierManagement;


public class Supplier {
	public String supplierCode=null;
	public String supplierName=null;
	public String contactNum=null;
	public String closed=null;
	public String productClassify=null;
	public int cost=0;
	
	public Supplier(String supplierCode, String supplierName, String contactNum, String closed, String productClassify, int cost) {
		this.supplierCode = supplierCode;
		this.supplierName = supplierName;
		this.contactNum = contactNum;
		this.closed = closed;
		this.productClassify = productClassify;
		this.cost = cost;
	}
	public Supplier() {
		
	}
	
	public String getSupplierCode() {
		return supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getContactNum() {
		return contactNum;
	}
	public String getClosed() {
		return closed;
	}
	public String getProductClassify() {
		return productClassify;
	}
	public int getCost() {
		return cost;
	}
	
	
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
	public void setClosed(String closed) {
		this.closed = closed;
	}
	public void setProductClassify(String productClassify) {
		this.productClassify = productClassify;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
}

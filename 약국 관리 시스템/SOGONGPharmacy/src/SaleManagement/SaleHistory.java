/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	SaleManagement
 * @className 			SaleHistory
 * @author 					김태민
 */
package SaleManagement;

import java.util.Date;

import ProductManagement.Product;

public class SaleHistory {

	private String saleHistoryCode;
	private String paymentMethod;
	private Date saleDate;
	private int saleAmount;
	private String memberCode;
	private boolean refundFlag;
	private Date refundDate;
	private Product[] productList;
	
	public SaleHistory() {
		this.saleHistoryCode = "";
		this.paymentMethod = "";
		this.saleDate = null;
		this.saleAmount = 0;
		this.memberCode = "";
		this.refundFlag = false;
		this.refundDate = null;
		this.productList = null;
	}
	public SaleHistory(String saleHistoryCode, String paymentMethod, Date saleDate, int saleAmount, String memberCode,
			boolean refundFlag, Date refundDate, Product[] productList) {
		this.saleHistoryCode = saleHistoryCode;
		this.paymentMethod = paymentMethod;
		this.saleDate = saleDate;
		this.saleAmount = saleAmount;
		this.memberCode = memberCode;
		this.refundFlag = refundFlag;
		this.refundDate = refundDate;
		this.productList = productList;
	}
	
	public void setSaleHistryCode(String saleHistoryCode) {
		this.saleHistoryCode = saleHistoryCode;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public void setSaleAmount(int saleAmount) {
		this.saleAmount = saleAmount;
	}
	
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public void setRefundFlag(boolean refundFlag) {
		this.refundFlag = refundFlag;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public void setProductList(Product[] productList) {
		this.productList = productList;
	}
	
	public String getSaleHistryCode() {
		return saleHistoryCode;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public int getSaleAmount() {
		return saleAmount;
	}

	public String getMemberCode() {
		return memberCode;
	}
	
	public boolean getRefundFlag() {
		return refundFlag;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public Product[] getProductList() {
		return productList;
	}
}

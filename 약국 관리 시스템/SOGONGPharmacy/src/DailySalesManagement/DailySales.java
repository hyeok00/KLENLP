/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	DailySalesManagement
 * @className 			DailySales
 * @author 					최정봉
 */
package DailySalesManagement;

public class DailySales {
	private String salesCode;
	private String payMethod;
	private int salesAmount;
	private int refundState;
	
	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public void setSalesAmount(int salesAmount) {
		this.salesAmount = salesAmount;
	}
	public void setRefundState(int refundState) {
		this.refundState = refundState;
	}
	
	public String getSalesCode() {
		return salesCode;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public int getSalesAmount() {
		return salesAmount;
	}
	public int getRefundState() {
		return refundState;
	}
}

class DailySalesDetail{
	private String salesCode;
	private String payMethod;
	private String memberID;
	private int refundState;
	private String productName;
	private int salesQuantity;
	private int salesAmount;
	
	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public void setRefundState(int refundState) {
		this.refundState = refundState;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setSalesQuantity(int salesQuantity) {
		this.salesQuantity = salesQuantity;
	}
	public void setSalesAmount(int salesAmount) {
		this.salesAmount = salesAmount;
	}
	
	public String getSalesCode() {
		return salesCode;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public String getMemberID() {
		return memberID;
	}
	public int getRefundState() {
		return refundState;
	}
	public String getProductName() {
		return productName;
	}
	public int getSalesQuantity() {
		return salesQuantity;
	}
	public int getSalesAmount() {
		return salesAmount;
	}
}

class MonthlyReport {
	private String date;
	private int cashAmount;
	private int cardAmount;
	private int refundAmount;

	public void setDate(String date) {
		this.date = date;
	}
	public void setCashAmount(int cashAmount) {
		this.cashAmount = cashAmount;
	}
	public void setCardAmount(int cardAmount) {
		this.cardAmount = cardAmount;
	}
	public void setRefundAmount(int refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	public String getDate() {
		return date;
	}
	public int getCashAmount() {
		return cashAmount;
	}
	public int getCardAmount() {
		return cardAmount;
	}
	public int getRefundAmount() {
		return refundAmount;
	}
}

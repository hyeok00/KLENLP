/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	RecordManagement
 * @className 			Record
 * @author 					최정봉
 */
package RecordManagement;

public class Record {
	private String code;
	private String productName;
	private String date;
	private int quantity;
	
	public void setCode(String code) {
		this.code = code;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setdate(String date) {
		this.date = date;
	}
	public void setquantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getCode() {
		return code;
	}
	public String getProductName() {
		return productName;
	}
	public String getdate() {
		return date;
	}
	public int getquantity() {
		return quantity;
	}
}

class ClosingRecord{
	private int refundState;
	private String productName;
	private int salesQuantity;
	private String memberID;
	private int salesAmount;
	private int productStock;
	

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
	public void setProductStock(int productStock) {
		this.productStock = productStock;
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
	public int getProductStock() {
		return productStock;
	}
}
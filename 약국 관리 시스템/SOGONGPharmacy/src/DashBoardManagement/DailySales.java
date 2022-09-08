/**
 * @systemName			SOGONG Pharmacy Management System
 * @subSystemName	DashBoardManagement
 * @className 			DailySales
 * @author 					최의준
 */	
package DashBoardManagement;

public class DailySales {
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

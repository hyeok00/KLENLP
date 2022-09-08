/**
 * @systemName			SOGONG Pharmacy Management System
 * @subSystemName	DashBoardManagement
 * @className 			SalesData
 * @author 					최의준
 */	
package DashBoardManagement;

public class SalesData {
	private String salesDate;
	private int sales;	
	private String productName;
	private int salesQuantity;
	private double salesProportion;
	
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setSalesQuantity(int salesQuantity) {
		this.salesQuantity = salesQuantity;
	}
	public void setSalesProportion(double salesProportion) {
		this.salesProportion = salesProportion;
	}
	
	public String getSalesDate() {
		return salesDate;
	}
	public int getSales() {
		return sales;
	}
	public String getProductName() {
		return productName;
	}
	public int getSalesQuantity() {
		return salesQuantity;
	}
	public double getSalesProportion() {
		return salesProportion;
	}
}

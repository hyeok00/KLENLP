/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	Product Management System
 * @className 		Product
 * @author 			류기혁
 */
package ProductManagement;

import java.io.*;

public class Product {
	String productName; // 상품명
	String expirationDate; // 유효기간
	int salePrice; // 상품가격
	int stockQuantity; // 재고량
	int optimumStock;

	public Product() {
	}

	public Product(String productName, String expirationDate, int salePrice, int stockQuantity) {
		this.productName = productName;
		this.expirationDate = expirationDate;
		this.salePrice = salePrice;
		this.stockQuantity = stockQuantity;
	}

	// getter & setter
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public int getOptimumStock() {
		return optimumStock;
	}

	public void setOptimumStock(int optimumStock) {
		this.optimumStock = optimumStock;
	}
}

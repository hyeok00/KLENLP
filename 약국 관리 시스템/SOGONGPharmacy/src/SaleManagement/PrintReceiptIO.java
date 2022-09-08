/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	SaleManagement
 * @className 			PrintReceiptIO
 * @author 					김태민
 */
package SaleManagement;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ProductManagement.Product;


public class PrintReceiptIO {
	ReceiptFrame receipt = null; 
	 
	public boolean printReceipt(SaleHistory saleHistory) 
	{
		receipt = new ReceiptFrame(saleHistory);
		
		receipt.performPrint();
		
		receipt.show();
		
		return true; 
	}
	
	private static class ReceiptFrame extends JFrame implements Printable {

		 private Vector<Product> productList;
		 private JPanel receiptPnl = null;
		
		public ReceiptFrame(SaleHistory saleHistory) {
			super("Receipt");
			 
			productList = new Vector<Product>();
			Product[] products = saleHistory.getProductList();
			int length = 0;
			if (products != null)
				length = products.length;
			
			for (int i = 0; i < length; i++)
			{
				int size = productList.size();
				boolean isExist = false;
				for (int j = 0; j < size; j++)
				{
					if ( products[i].getProductName().equals( productList.get(j).getProductName() ) )
					{
						productList.get(j).setStockQuantity(productList.get(j).getStockQuantity() + products[i].getStockQuantity());
						isExist = true;
					}
				}
				if (! isExist) {
					productList.add(products[i]);
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Container panel = getContentPane();
			receiptPnl = new JPanel();
			receiptPnl.setBackground(Color.WHITE);
			receiptPnl.setBounds(0, 0, 500, 539);
			panel.add(receiptPnl);
			receiptPnl.setLayout(null);
			
			JLabel saleCodeLb = new JLabel("판매코드");
			saleCodeLb.setBounds(11, 15, 62, 18);
			receiptPnl.add(saleCodeLb);
			JTextField saleCodeTb = new JTextField();
			saleCodeTb.setHorizontalAlignment(SwingConstants.CENTER);
			saleCodeTb.setEditable(false);
			saleCodeTb.setColumns(10);
			saleCodeTb.setBackground(Color.WHITE);
			saleCodeTb.setBounds(74, 12, 300, 24);
			receiptPnl.add(saleCodeTb);
			saleCodeTb.setText(saleHistory.getSaleHistryCode());
			
			JLabel saleDateLb = new JLabel("판매일시");
			saleDateLb.setBounds(11, 48, 62, 18);
			receiptPnl.add(saleDateLb);
			JTextField saleDateTb = new JTextField();
			saleDateTb.setBackground(Color.WHITE);
			saleDateTb.setEditable(false);
			saleDateTb.setHorizontalAlignment(SwingConstants.CENTER);
			saleDateTb.setBounds(74, 45, 300, 24);
			receiptPnl.add(saleDateTb);
			saleDateTb.setColumns(10);
			saleDateTb.setText(sdf.format(saleHistory.getSaleDate()));
			
			JLabel refundDateLb = new JLabel("환불일시");
			refundDateLb.setBounds(11, 103, 70, 18);
			receiptPnl.add(refundDateLb);
			JTextField refundDateTb = new JTextField();
			refundDateTb.setBackground(Color.WHITE);
			refundDateTb.setEditable(false);
			refundDateTb.setHorizontalAlignment(SwingConstants.CENTER);
			refundDateTb.setBounds(74, 100, 300, 24);
			receiptPnl.add(refundDateTb);
			refundDateTb.setColumns(10);
			if (saleHistory.getRefundFlag())
				refundDateTb.setText(sdf.format(saleHistory.getRefundDate()));
			else 
				refundDateTb.setText("-");
			
			JLabel refundFlagLb = new JLabel("환불여부");
			refundFlagLb.setBounds(11, 77, 62, 18);
			receiptPnl.add(refundFlagLb);
			JCheckBox refundFlagCb = new JCheckBox("");
			refundFlagCb.setBackground(Color.WHITE);
			refundFlagCb.setToolTipText("");
			refundFlagCb.setBounds(72, 73, 27, 27);
			receiptPnl.add(refundFlagCb);
			refundFlagCb.setSelected(saleHistory.getRefundFlag());
			

			JLabel label = new JLabel("상품명");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(50, 145, 62, 18);
			receiptPnl.add(label);
			
			JLabel price = new JLabel("판매가격");
			price.setHorizontalAlignment(SwingConstants.CENTER);
			price.setBounds(200, 145, 62, 18);
			receiptPnl.add(price);
			
			JLabel quantity = new JLabel("판매수량");
			quantity.setHorizontalAlignment(SwingConstants.CENTER);
			quantity.setBounds(300, 145, 62, 18);
			receiptPnl.add(quantity);
			
			int y = 145;
			y = y + 25;
			int size = productList.size();
			for (int i = 0; i < size; i++) {
				JLabel itemName = new JLabel(productList.get(i).getProductName());
				itemName.setHorizontalAlignment(SwingConstants.CENTER);
				itemName.setBounds(50, y, 62, 18);
				receiptPnl.add(itemName);
				
				JLabel itmeprice = new JLabel(Integer.toString(productList.get(i).getSalePrice()) );
				itmeprice.setHorizontalAlignment(SwingConstants.CENTER);
				itmeprice.setBounds(200, y, 62, 18);
				receiptPnl.add(itmeprice);
				
				JLabel itemQuntity = new JLabel(Integer.toString(productList.get(i).getStockQuantity()) );
				itemQuntity.setHorizontalAlignment(SwingConstants.CENTER);
				itemQuntity.setBounds(300, y, 62, 18);
				receiptPnl.add(itemQuntity);
				y += 20;
			}
			
			y += 30;
			JLabel saleAmountLb = new JLabel("판매금액");
			saleAmountLb.setBounds(11, y, 62, 18);
			receiptPnl.add(saleAmountLb);
			JTextField saleAmountTb = new JTextField();
			saleAmountTb.setBackground(Color.WHITE);
			saleAmountTb.setEditable(false);
			saleAmountTb.setHorizontalAlignment(SwingConstants.RIGHT);
			saleAmountTb.setBounds(76, y, 300, 24);
			receiptPnl.add(saleAmountTb);
			saleAmountTb.setColumns(10);
			saleAmountTb.setText(Integer.toString(saleHistory.getSaleAmount()) + " 원");
			
			y += 30;
			JLabel saleMethodLb = new JLabel("결제수단");
			saleMethodLb.setBounds(11, y, 62, 18);
			receiptPnl.add(saleMethodLb);
			JTextField saleMethodTb = new JTextField();
			saleMethodTb.setBackground(Color.WHITE);
			saleMethodTb.setEditable(false);
			saleMethodTb.setHorizontalAlignment(SwingConstants.CENTER);
			saleMethodTb.setBounds(76, y, 300, 24);
			receiptPnl.add(saleMethodTb);
			saleMethodTb.setColumns(10);
			saleMethodTb.setText(saleHistory.getPaymentMethod());
			
			y += 30;
			JLabel memberCodeLb = new JLabel("고객 코드");
			memberCodeLb.setBounds(11, y, 62, 18);
			receiptPnl.add(memberCodeLb);
			JTextField memberCodeTb = new JTextField();
			memberCodeTb.setBackground(Color.WHITE);
			memberCodeTb.setEditable(false);
			memberCodeTb.setHorizontalAlignment(SwingConstants.CENTER);
			memberCodeTb.setBounds(76, y, 300, 24);
			receiptPnl.add(memberCodeTb);
			memberCodeTb.setColumns(10);
			memberCodeTb.setText(saleHistory.getMemberCode());
			
			y += 80;
			setBounds(500, 200, 420, y);
			setResizable(false);
		}
		
		protected void performPrint() { 	
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(this);
			try{
				job.print();
			}catch(PrinterException pe){};
		}
		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
			if(pageIndex == 0) {
				graphics.translate( (int)(pageFormat.getImageableX()), (int)(pageFormat.getImageableY()) );
				Container pane = receiptPnl;
				pane.paint(graphics);
				return Printable.PAGE_EXISTS;
			}
			return Printable.NO_SUCH_PAGE;
		}
	}
}

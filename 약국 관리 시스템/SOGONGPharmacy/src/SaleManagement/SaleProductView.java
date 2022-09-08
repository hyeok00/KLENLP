/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	SaleManagement
 * @className 			SaleProductView
 * @author 					김태민
 */
package SaleManagement;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import MemberManagement.Member;
import ProductManagement.Product;
import SystemManagement.MainMenuView;

import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;

public class SaleProductView extends JPanel {
	private boolean status = false;
	
	private JTextField productCodeTb;
	private JTextField productNameTb;
	private JTextField expirationDateTb;
	private JTextField productInfoTb;
	private JTextField saleProductListTb;
	private JTextField salesAmountTb;
	private JTable productTable;
	private JRadioButton isMemberCb;
	private JRadioButton isNotMemberCb;
	private JButton backBtn;
	private JButton cardPayBtn;
	private JButton payCashBtn;
	private JButton CheckidentificationBtn;
	private JPanel panel;
	private CheckIdentificationView ciView;
	private ReadCardDialog readCardDialog;
	
	private ReadThread readBarcodeThread;
	
	private boolean isMember = false;
	private Vector<Product> productList;
	private Member member = null;
	private String creditCardInfo = null;
	private int amount = 0;
	private boolean isDiscount = false;
	
	private Object[] objColNms = new Object[] { "No", "상품명", "판매가격", "판매수량", "" };
	
	public void initial() {
		setSize(1500, 800);
		
		setLayout(null);
		panel = new JPanel();
		panel.setBackground(SystemColor.activeCaptionBorder);
		panel.setBounds(25, 175, 700, 350);
		add(panel);
		panel.setLayout(null);
		
		productCodeTb = new JTextField();
		productCodeTb.setFont(new Font("굴림", Font.PLAIN, 20));
		productCodeTb.setBackground(Color.WHITE);
		productCodeTb.setEditable(false);
		productCodeTb.setHorizontalAlignment(SwingConstants.CENTER);
		productCodeTb.setBounds(150, 40, 500, 30);
		panel.add(productCodeTb);
		productCodeTb.setColumns(10);
		
		productNameTb = new JTextField();
		productNameTb.setFont(new Font("굴림", Font.PLAIN, 20));
		productNameTb.setBackground(Color.WHITE);
		productNameTb.setEditable(false);
		productNameTb.setHorizontalAlignment(SwingConstants.CENTER);
		productNameTb.setColumns(10);
		productNameTb.setBounds(150, 100, 500, 30);
		panel.add(productNameTb);
		
		expirationDateTb = new JTextField();
		expirationDateTb.setFont(new Font("굴림", Font.PLAIN, 20));
		expirationDateTb.setBackground(Color.WHITE);
		expirationDateTb.setEditable(false);
		expirationDateTb.setHorizontalAlignment(SwingConstants.CENTER);
		expirationDateTb.setColumns(10);
		expirationDateTb.setBounds(150, 170, 500, 30);
		panel.add(expirationDateTb);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaptionBorder);
		panel_1.setBounds(775, 175, 700, 350);
		add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane saleProductPnl = new JScrollPane();
		saleProductPnl.setBounds(5, 5, 690, 340);
		panel_1.add(saleProductPnl);
		
		productTable = new JTable(new DefaultTableModel(
			new Object[][] {
			},
			objColNms
		) );
		productTable.setFont(new Font("굴림", Font.PLAIN, 15));
		productTable.setFillsViewportHeight(true);
		productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		productTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
		productTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor());
		
		saleProductPnl.setViewportView(productTable);

		productInfoTb = new JTextField();
		productInfoTb.setFont(new Font("굴림", Font.PLAIN, 20));
		productInfoTb.setBackground(Color.WHITE);
		productInfoTb.setEditable(false);
		productInfoTb.setHorizontalAlignment(SwingConstants.CENTER);
		productInfoTb.setText("상품 정보");
		productInfoTb.setBounds(25, 145, 150, 30);
		add(productInfoTb);
		productInfoTb.setColumns(10);
		
		saleProductListTb = new JTextField();
		saleProductListTb.setFont(new Font("굴림", Font.PLAIN, 20));
		saleProductListTb.setBackground(Color.WHITE);
		saleProductListTb.setEditable(false);
		saleProductListTb.setHorizontalAlignment(SwingConstants.CENTER);
		saleProductListTb.setText("판매상품 목록 ");
		saleProductListTb.setColumns(10);
		saleProductListTb.setBounds(775, 145, 150, 30);
		add(saleProductListTb);
		
		CheckidentificationBtn = new JButton("신분증 확인");
		CheckidentificationBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		CheckidentificationBtn.setBounds(25, 679, 150, 50);
		add(CheckidentificationBtn);

		payCashBtn = new JButton("현금결제");
		payCashBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		payCashBtn.setBounds(1008, 679, 150, 50);
		add(payCashBtn);
		
		cardPayBtn = new JButton("카드결제");
		cardPayBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		cardPayBtn.setBounds(1172, 679, 150, 50);
		add(cardPayBtn);
		
		isMemberCb = new JRadioButton("회원");
		isMemberCb.setFont(new Font("굴림", Font.PLAIN, 20));
		isMemberCb.setForeground(Color.BLACK);
		isMemberCb.setBounds(25, 642, 75, 30);
		add(isMemberCb);
		
		isNotMemberCb = new JRadioButton("비회원");
		isNotMemberCb.setFont(new Font("굴림", Font.PLAIN, 20));
		isNotMemberCb.setForeground(Color.BLACK);
		isNotMemberCb.setSelected(true);
		isNotMemberCb.setBounds(108, 642, 100, 30);
		add(isNotMemberCb);
		
		salesAmountTb = new JTextField();
		salesAmountTb.setText("0 원");
		salesAmountTb.setFont(new Font("굴림", Font.PLAIN, 20));
		salesAmountTb.setBackground(Color.WHITE);
		salesAmountTb.setEditable(false);
		salesAmountTb.setHorizontalAlignment(SwingConstants.RIGHT);
		salesAmountTb.setBounds(1175, 552, 300, 30);
		add(salesAmountTb);
		salesAmountTb.setColumns(10);
		
		backBtn = new JButton("이전화면");
		backBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		backBtn.setBounds(1336, 679, 150, 50);
		add(backBtn);
	}
	public void initialLabel() {
		JLabel titleLb = new JLabel("상품 판매");
		titleLb.setHorizontalAlignment(SwingConstants.CENTER);
		titleLb.setBounds(650, 50, 200, 50);
		titleLb.setFont(new Font("굴림", Font.PLAIN, 40));
		add(titleLb);
		
		JLabel productCodeLb = new JLabel("상품코드");
		productCodeLb.setFont(new Font("굴림", Font.PLAIN, 20));
		productCodeLb.setBounds(32, 40, 100, 30);
		panel.add(productCodeLb);
		
		JLabel productName = new JLabel("상품명");
		productName.setFont(new Font("굴림", Font.PLAIN, 20));
		productName.setBounds(32, 100, 100, 30);
		panel.add(productName);
		
		JLabel expirationDateLb = new JLabel("유통기한");
		expirationDateLb.setFont(new Font("굴림", Font.PLAIN, 20));
		expirationDateLb.setBounds(32, 170, 100, 30);
		panel.add(expirationDateLb);
		
		JLabel saleAmountLb = new JLabel("판매 금액");
		saleAmountLb.setFont(new Font("굴림", Font.PLAIN, 20));
		saleAmountLb.setHorizontalAlignment(SwingConstants.CENTER);
		saleAmountLb.setBounds(1061, 552, 100, 30);
		add(saleAmountLb);
		
	}
	public void initialListener() {
		CheckidentificationBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ciView = new CheckIdentificationView(null, true);
				ciView.setVisible(true);

				Member m = ciView.getMember();
				if (m != null) {
					member = m;
					isMember = true;
					isMemberCb.setSelected(isMember);
					isNotMemberCb.setSelected(!isMember);
					
					if (! isDiscount) {
						amount = amount - amount / 10;	// 할인 적용
						salesAmountTb.setText(Integer.toString(amount) + " 원");
						isDiscount = true;
					}
				} else {
					isMember = false;
					isMemberCb.setSelected(isMember);
					isNotMemberCb.setSelected(!isMember);
					if (isDiscount) {
						int size = productList.size();
						amount = 0;
						for (int i = 0; i < size; i++)
						{
							amount = amount + productList.get(i).getSalePrice() * productList.get(i).getStockQuantity();
						}
						salesAmountTb.setText(Integer.toString(amount) + " 원");
						isDiscount = false;
					}
				}
				ciView = null;
			}
        });
		
		payCashBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (amount == 0) {
					JOptionPane.showMessageDialog(null, "등록된 상품이 없습니다", "error", JOptionPane.ERROR_MESSAGE);
				} else {
					SaleManager sManager = new SaleManager();
					
					Product[] products = new Product[productList.size()];
					products = (Product[])productList.toArray(products);

					SaleMessage message = new SaleMessage();
					boolean result = sManager.saleProduct(products, member, creditCardInfo, message);
					if (result) {
						JOptionPane.showMessageDialog(null, "판매완료", "sucess", JOptionPane.INFORMATION_MESSAGE);
						
						DefaultTableModel model = (DefaultTableModel)productTable.getModel();
						model.setColumnCount(0);
						for (int i = 0; i < 5; i++)
							model.addColumn(objColNms[i]);
						model.setRowCount(0);
						productList.removeAllElements();

						amount = 0;
						member = null;
						isMember = false;
						isDiscount = false;
						creditCardInfo = null;
						
						productCodeTb.setText("");
						productNameTb.setText("");
						expirationDateTb.setText("");
						salesAmountTb.setText("0 원");
						isMemberCb.setSelected(isMember);
						isNotMemberCb.setSelected(! isMember);
					}
					else {
						JOptionPane.showMessageDialog(null, "판매처리중 오류가 발생했습니다 : " + message.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		cardPayBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (amount == 0) {
					JOptionPane.showMessageDialog(null, "등록된 상품이 없습니다", "error", JOptionPane.ERROR_MESSAGE);
				} else {
					readCardDialog.pack();
					readCardDialog.show();
				}
			}
		});
		
		isMemberCb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				isMemberCb.setSelected(isMember);
			}
		});
		isNotMemberCb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				isNotMemberCb.setSelected(!isMember);
			}
		});
		
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setStatus(false);
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
	}
	private class ReadThread extends Thread{
		
		@Override
		public void run() {
			Scanner s = new Scanner(System.in);
			while (true) {
				if (s.hasNext()) {
					String response = s.next();
					if (response.length() < 10)
						readBarcode(response);
					else
						readCard(response);
				}
			}

		}
	}

	public SaleProductView() {
		readBarcodeThread = new ReadThread();
		readBarcodeThread.start();

		readCardDialog = new ReadCardDialog(new JFrame(), "", true);
		
		productList = new Vector<Product>();
		
		initial();
		initialLabel();
		initialListener();
	}


	public void setStatus(boolean status) { 
		this.status = status; 
	}
	
	public void stopThread() {
		if (readBarcodeThread != null) {
			readBarcodeThread.stop();
			readBarcodeThread = null;
		}
	}
	
	public void readBarcode(String barcode) {
		if (! status) return;
		
		SaleManager sManager = new SaleManager();
		
		Product product = sManager.readProduct(barcode);
		if (product == null) {
			JOptionPane.showMessageDialog(null, "조회 결과가 없습니다", "error", JOptionPane.ERROR_MESSAGE);
			productCodeTb.setText("");
			productNameTb.setText("");
			expirationDateTb.setText("");
		}
		else {
			DefaultTableModel model = (DefaultTableModel)productTable.getModel();
			
			String name = product.getProductName();
			String expirationDate = product.getExpirationDate();
			int price = product.getSalePrice();
			int quantity = product.getStockQuantity();
			
			productCodeTb.setText(barcode);
			productNameTb.setText(name);
			expirationDateTb.setText(expirationDate);

			int cnt = model.getRowCount();
			boolean isExist = false;
			for (int i = 0; i < cnt; i++)
			{
				if (model.getValueAt(i, 1).equals(name) ) {
					Integer q = Integer.parseInt((String)model.getValueAt(i, 3)) + quantity;
					model.setValueAt(q.toString(), i, 3);
					isExist = true;
					break;
				}
			}
			if ( ! isExist) {
				int nextIdx = model.getRowCount();
				String[] item = new String[5];
				item[0] = Integer.toString(nextIdx + 1);
				item[1] = name;
				item[2] = ((Integer)price).toString() + "원";
				item[3] = ((Integer)quantity).toString();
				item[4] = "";
				
				model.addRow(item);
			}
			
			cnt = productList.size();
			isExist = false;
			for (int i = 0; i < cnt; i++) {
				String pName = productList.get(i).getProductName();
				String pExpiration = productList.get(i).getExpirationDate();
				if (pName.equals(name) && pExpiration.equals(expirationDate)) {
					productList.get(i).setStockQuantity(productList.get(i).getStockQuantity() + quantity);
					isExist = true;
					break;
				}
			}
			if (!isExist)
				productList.add(product);
			
			int size = productList.size();
			amount = 0;
			for (int i = 0; i < size; i++)
			{
				amount = amount + productList.get(i).getSalePrice() * productList.get(i).getStockQuantity();
			}
			if (isMember)  amount = amount - amount / 10;	// 할인 적용
			salesAmountTb.setText(Integer.toString(amount) + " 원");
		}
	}
	
	public void readCard(String cardInfo) {

		System.out.println("2");
		if (readCardDialog.isVisible()) {
			System.out.println("3");
			readCardDialog.setVisible(false);
			creditCardInfo = cardInfo;
			
			SaleManager sManager = new SaleManager();
			
			Product[] products = new Product[productList.size()];
			products = (Product[])productList.toArray(products);

			SaleMessage message = new SaleMessage();
			
			boolean result = sManager.saleProduct(products, member, creditCardInfo, message);
			if (result) {
				JOptionPane.showMessageDialog(null, "판매완료, 승인코드 : " + message.getMessage(), "sucess", JOptionPane.INFORMATION_MESSAGE);
				
				DefaultTableModel model = (DefaultTableModel)productTable.getModel();
				model.setColumnCount(0);
				for (int i = 0; i < 5; i++)
					model.addColumn(objColNms[i]);
				model.setRowCount(0);
				productList.removeAllElements();

				amount = 0;
				member = null;
				isMember = false;
				isDiscount = false;
				creditCardInfo = null;
				
				productCodeTb.setText("");
				productNameTb.setText("");
				expirationDateTb.setText("");
				salesAmountTb.setText("0 원");
				isMemberCb.setSelected(isMember);
				isNotMemberCb.setSelected(! isMember);
			}
			else {
				JOptionPane.showMessageDialog(null, "판매처리중 오류가 발생했습니다 : " + message.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
			}
			creditCardInfo = null;
		}
	}
	
	private class ButtonRenderer implements TableCellRenderer 
	{     
		JButton button;     
		boolean enabled = true; 

		public ButtonRenderer() 
		{     
			button = new JButton();     
		}     
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
		{   
			button.setText("삭제");     
			button.setEnabled(enabled);     
			return button;     
		}     
		public void setEnabled(boolean enabled) 
		{     
			this.enabled = enabled;     
		}	     
	}    
	private class ButtonEditor extends AbstractCellEditor implements TableCellEditor,ActionListener 
	{     
		private static final long serialVersionUID = 1L;
		JButton button;
		JTable currTable;
		boolean enabled = true;     
		int clickCountToStart = 1;     

		public ButtonEditor() 
		{     
			button = new JButton();     
			button.addActionListener(this);     
		}     
	
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {     
			currTable=table;
			button.setText("삭제");     
			button.setEnabled(enabled);     
			return button;     
		}     
	
		public void setEnabled(boolean enabled) {     
			this.enabled = enabled;     
		}     
	
		public Object getCellEditorValue() {     
			return button.getText();     
		}     
	
		public boolean isCellEditable(EventObject anEvent) {     
			if (anEvent instanceof MouseEvent) {
				return ((MouseEvent)anEvent).getClickCount() >= clickCountToStart;     
			}
			return true;
		}
	
		public void actionPerformed(ActionEvent e) 
		{
			int index = productTable.getSelectedRow();
			//Business logic execution 
			if (index >= 0) {
				productCodeTb.setText("");
				productNameTb.setText("");
				expirationDateTb.setText("");
						
				DefaultTableModel model = (DefaultTableModel)productTable.getModel();
				String pName = (String)model.getValueAt(index, 1);
					  		  
				int size = productList.size();
				for (int i = 0; i < size; i++)
				{
					if ( pName.equals(productList.get(i).getProductName()) )
					{
						productList.remove(i);
						size--;
						i--;
					}
				}
					  
				model.setColumnCount(0);
				for (int i = 0; i < 5; i++) {
					model.addColumn(objColNms[i]);
				}
				model.setRowCount(0);
					  
				size = productList.size();
				for (int i = 0; i < size; i++)
				{
					String name = productList.get(i).getProductName();
					String expirationDate = productList.get(i).getExpirationDate();
					int price = productList.get(i).getSalePrice();
					int quantity = productList.get(i).getStockQuantity();
						
					boolean isExist = false;
					int cnt = model.getRowCount();
					for (int j = 0; j < cnt; j++)
					{
						if (model.getValueAt(j, 1).equals(name) ) {
							Integer q = Integer.parseInt((String)model.getValueAt(j, 3)) + quantity;
							model.setValueAt(q.toString(), j, 3);
							isExist = true;
							break;
						}
					}
					if ( ! isExist) {
						int nextIdx = model.getRowCount();
						String[] item = new String[5];
						item[0] = Integer.toString(nextIdx + 1);
						item[1] = name;
						item[2] = ((Integer)price).toString() + "원";
						item[3] = ((Integer)quantity).toString();
						item[4] = "";
									
						model.addRow(item);
					}
				}
					  
				productTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
				productTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor());
			}
			int size = productList.size();
			amount = 0;
			for (int i = 0; i < size; i++)
			{
				amount = amount + productList.get(i).getSalePrice() * productList.get(i).getStockQuantity();
			}
			if (isMember)  amount = amount - amount / 10;	// 할인 적용
				salesAmountTb.setText(Integer.toString(amount) + " 원");
		}
	}
	
	private class ReadCardDialog extends JDialog {

		public ReadCardDialog(JFrame owner, String title, boolean modal) {
			super(owner, title, modal);
			setResizable(false);
			setLocation(850, 400);
			setPreferredSize(new Dimension(300,150));
			getContentPane().setLayout(new BorderLayout(0, 0));
			JLabel cardInfoLb = new JLabel("카드정보를 입력해주세요");
			cardInfoLb.setHorizontalAlignment(SwingConstants.CENTER);
			cardInfoLb.setFont(new Font("굴림", Font.BOLD, 15));
			getContentPane().add(cardInfoLb, BorderLayout.CENTER);
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
	}
}

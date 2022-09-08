/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	SaleManagement
 * @className 			CancelSaleView
 * @author 					김태민
 */
package SaleManagement;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import ProductManagement.Product;
import SystemManagement.MainMenuView;
import SystemManagement.MainMenuView.Views;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CancelSaleView extends JPanel {
	private JTextField saleCodeTb;
	private JTextField saleDateTb;
	private JTextField payMethodTb;
	private JTextField saleAmountTb;
	private JTextField memberCodeTb;
	private JTable saleProductTable;
	private JTextField refundDateTb;
	private JCheckBox refundFlagCb;
	private JCheckBox memberFlagCb;
	private boolean refundFlag;
	private boolean memberFlag;
	private JButton readBtn;
	private JButton backBtn;
	private JButton refundBtn;
	
	private String saleHistoryCode;

	
	public void initial() {
		setSize(1500, 800);
		setLayout(null);
		
		saleCodeTb = new JTextField();
		saleCodeTb.setFont(new Font("굴림", Font.PLAIN, 20));
		saleCodeTb.setHorizontalAlignment(SwingConstants.CENTER);
		saleCodeTb.setBounds(191, 182, 350, 30);
		add(saleCodeTb);
		saleCodeTb.setColumns(10);
		
		readBtn = new JButton("조회");
		readBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		readBtn.setBounds(555, 182, 100, 30);
		add(readBtn);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaptionBorder);
		panel.setBounds(15, 218, 1472, 470);
		add(panel);
		panel.setLayout(null);
		
		saleDateTb = new JTextField();
		saleDateTb.setBackground(Color.WHITE);
		saleDateTb.setEditable(false);
		saleDateTb.setFont(new Font("굴림", Font.PLAIN, 20));
		saleDateTb.setHorizontalAlignment(SwingConstants.CENTER);
		saleDateTb.setBounds(176, 65, 350, 30);
		panel.add(saleDateTb);
		saleDateTb.setColumns(10);
		
		payMethodTb = new JTextField();
		payMethodTb.setBackground(Color.WHITE);
		payMethodTb.setEditable(false);
		payMethodTb.setFont(new Font("굴림", Font.PLAIN, 20));
		payMethodTb.setHorizontalAlignment(SwingConstants.CENTER);
		payMethodTb.setBounds(176, 215, 350, 30);
		panel.add(payMethodTb);
		payMethodTb.setColumns(10);
		
		saleAmountTb = new JTextField();
		saleAmountTb.setBackground(Color.WHITE);
		saleAmountTb.setEditable(false);
		saleAmountTb.setFont(new Font("굴림", Font.PLAIN, 20));
		saleAmountTb.setHorizontalAlignment(SwingConstants.CENTER);
		saleAmountTb.setBounds(176, 115, 350, 30);
		panel.add(saleAmountTb);
		saleAmountTb.setColumns(10);
		
		memberCodeTb = new JTextField();
		memberCodeTb.setBackground(Color.WHITE);
		memberCodeTb.setEditable(false);
		memberCodeTb.setFont(new Font("굴림", Font.PLAIN, 20));
		memberCodeTb.setHorizontalAlignment(SwingConstants.CENTER);
		memberCodeTb.setBounds(176, 265, 350, 30);
		panel.add(memberCodeTb);
		memberCodeTb.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(775, 65, 683, 393);
		panel.add(scrollPane);
		
		saleProductTable = new JTable();
		saleProductTable.setFillsViewportHeight(true);
		scrollPane.setViewportView(saleProductTable);
		saleProductTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No", "\uC0C1\uD488\uBA85", "\uD310\uB9E4 \uAC00\uACA9", "\uD310\uB9E4 \uC218\uB7C9"
			}
		));
		
		refundDateTb = new JTextField();
		refundDateTb.setBackground(Color.WHITE);
		refundDateTb.setEditable(false);
		refundDateTb.setFont(new Font("굴림", Font.PLAIN, 20));
		refundDateTb.setHorizontalAlignment(SwingConstants.CENTER);
		refundDateTb.setBounds(176, 165, 350, 30);
		panel.add(refundDateTb);
		refundDateTb.setColumns(10);
		
		refundFlagCb = new JCheckBox("환불여부");
		refundFlagCb.setFont(new Font("굴림", Font.PLAIN, 20));
		refundFlagCb.setBackground(SystemColor.activeCaptionBorder);
		refundFlagCb.setBounds(547, 165, 150, 30);
		panel.add(refundFlagCb);
		
		memberFlagCb = new JCheckBox("할인여부");
		memberFlagCb.setFont(new Font("굴림", Font.PLAIN, 20));
		memberFlagCb.setBackground(SystemColor.activeCaptionBorder);
		memberFlagCb.setBounds(547, 265, 150, 30);
		panel.add(memberFlagCb);
		
		backBtn = new JButton("이전화면");
		backBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		backBtn.setBounds(1337, 700, 150, 50);
		add(backBtn);
		
		refundBtn = new JButton("환불처리");
		refundBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		refundBtn.setBounds(1173, 700, 150, 50);
		add(refundBtn);
		
		
		JLabel refundDateLb = new JLabel("환불 일시");
		refundDateLb.setHorizontalAlignment(SwingConstants.CENTER);
		refundDateLb.setFont(new Font("굴림", Font.PLAIN, 20));
		refundDateLb.setBounds(62, 165, 100, 30);
		panel.add(refundDateLb);
		
		JLabel titleLb = new JLabel("판매 취소");
		titleLb.setHorizontalAlignment(SwingConstants.CENTER);
		titleLb.setBounds(650, 50, 200, 50);
		titleLb.setFont(new Font("굴림", Font.PLAIN, 40));
		add(titleLb);
		
		JLabel saleCodeLb = new JLabel("판매 코드");
		saleCodeLb.setFont(new Font("굴림", Font.PLAIN, 20));
		saleCodeLb.setHorizontalAlignment(SwingConstants.CENTER);
		saleCodeLb.setBounds(77, 182, 100, 30);
		add(saleCodeLb);
		
		JLabel saleDateLb = new JLabel("판매 일시");
		saleDateLb.setHorizontalAlignment(SwingConstants.CENTER);
		saleDateLb.setFont(new Font("굴림", Font.PLAIN, 20));
		saleDateLb.setBounds(62, 65, 100, 30);
		panel.add(saleDateLb);
		
		JLabel payMethodLb = new JLabel("결제 수단");
		payMethodLb.setHorizontalAlignment(SwingConstants.CENTER);
		payMethodLb.setFont(new Font("굴림", Font.PLAIN, 20));
		payMethodLb.setBounds(62, 215, 100, 30);
		panel.add(payMethodLb);
		
		JLabel saleAmountLb = new JLabel("판매 금액");
		saleAmountLb.setHorizontalAlignment(SwingConstants.CENTER);
		saleAmountLb.setFont(new Font("굴림", Font.PLAIN, 20));
		saleAmountLb.setBounds(62, 115, 100, 30);
		panel.add(saleAmountLb);
		
		JLabel saleProductLb = new JLabel("판매 상품");
		saleProductLb.setFont(new Font("굴림", Font.PLAIN, 20));
		saleProductLb.setBounds(775, 30, 100, 30);
		panel.add(saleProductLb);
		
		JLabel memberCodeLb = new JLabel("고객 코드");
		memberCodeLb.setHorizontalAlignment(SwingConstants.CENTER);
		memberCodeLb.setFont(new Font("굴림", Font.PLAIN, 20));
		memberCodeLb.setBounds(62, 265, 100, 30);
		panel.add(memberCodeLb);
	}
	
	public void initialListener() {
		readBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputSaleCode = saleCodeTb.getText();
				
				readSaleHistory(inputSaleCode);
			}
		});
		
		refundFlagCb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				refundFlagCb.setSelected(refundFlag);
			}
		});
		
		memberFlagCb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				memberFlagCb.setSelected(memberFlag);
			}
		});
		
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		
		refundBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaleManager saleManager = new SaleManager();
				
				if (saleHistoryCode == null) {
					JOptionPane.showMessageDialog(null, "판매이력을 조회해 주세요", "error", JOptionPane.ERROR_MESSAGE);
				}
				else if (refundFlag) {
					JOptionPane.showMessageDialog(null, "이미 환불 처리된 판매이력입니다", "error", JOptionPane.ERROR_MESSAGE);
				} 
				else {
					SaleMessage message = new SaleMessage();
					boolean result = saleManager.cancelSale(saleHistoryCode, message);
					if (result) {
						JOptionPane.showMessageDialog(null, "환불완료, 승인코드 : " + message.getMessage(), "sucess", JOptionPane.INFORMATION_MESSAGE);

						readSaleHistory(saleHistoryCode);
					}
					else {
						JOptionPane.showMessageDialog(null, "환불처리중 오류가 발생했습니다 : " + message.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
	
	public CancelSaleView() {		
		refundFlag = false;
		memberFlag = false;
		
		saleHistoryCode = null;
		initial();
		initialListener();
	}
	
	private void readSaleHistory(String inputSaleCode) {
		SaleManager saleManager = new SaleManager();
		SaleHistory[] salesHistoryList = null;
		
		if (inputSaleCode.length() == 0) {
			JOptionPane.showMessageDialog(null, "판매코드를 입력해 주세요", "error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			salesHistoryList = saleManager.readSaleHistory(inputSaleCode);
			
			if (salesHistoryList == null) {
				JOptionPane.showMessageDialog(null, "조회 결과가 없습니다", "error", JOptionPane.ERROR_MESSAGE);
				saleHistoryCode = null;
				saleDateTb.setText("");
				refundFlag = false;
				refundFlagCb.setSelected(refundFlag);
				refundDateTb.setText("");
				saleAmountTb.setText("");
				payMethodTb.setText("");
				memberCodeTb.setText("");
				memberFlag = false;
				memberFlagCb.setSelected(memberFlag);
				
				DefaultTableModel model = (DefaultTableModel)saleProductTable.getModel();
				model.setNumRows(0);
			}
			else {
				saleHistoryCode = inputSaleCode;
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				saleDateTb.setText(sdf.format(salesHistoryList[0].getSaleDate()));
				refundFlag = salesHistoryList[0].getRefundFlag();
				refundFlagCb.setSelected(refundFlag);
				if (salesHistoryList[0].getRefundFlag())
					refundDateTb.setText(sdf.format(salesHistoryList[0].getRefundDate()));
				else
					refundDateTb.setText("-");
				saleAmountTb.setText( ((Integer)salesHistoryList[0].getSaleAmount()).toString() + "원" );
				payMethodTb.setText(salesHistoryList[0].getPaymentMethod());
				memberCodeTb.setText(salesHistoryList[0].getMemberCode());
				if (memberCodeTb.getText().equals("비회원")) memberFlag = false;
				else memberFlag = true;
				memberFlagCb.setSelected(memberFlag);
				
				DefaultTableModel model = (DefaultTableModel)saleProductTable.getModel();
				model.setNumRows(0);
				Product[] productList = salesHistoryList[0].getProductList();
				for (int i = 0; i < productList.length; i++)
				{
					String[] item = new String[4];
					item[0] = Integer.toString(i+1);
					item[1] = productList[i].getProductName();
					item[2] = ((Integer)productList[i].getSalePrice()).toString() + "원";
					item[3] = ((Integer)productList[i].getStockQuantity()).toString();
					
					boolean flag = true;
					for (int j = 0; j < model.getRowCount(); j++)
					{
						if (model.getValueAt(j, 1).equals(item[1])) {
							Integer quantity = Integer.parseInt((String)model.getValueAt(j, 3)) + Integer.parseInt(item[3]);
							model.setValueAt(quantity.toString(), j, 3);
							flag = false;
							break;
						}
					}
					if (flag)
						model.addRow(item);
				}
				DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
				tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				TableColumnModel tcmSchedule = saleProductTable.getColumnModel();
				for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
					tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
				}
			}
		}
	}
}

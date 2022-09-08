/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	SaleManagement
 * @className 			SaleHistoryView
 * @author 					김태민
 */
package SaleManagement;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;

import ProductManagement.Product;
import SystemManagement.MainMenuView;
import SystemManagement.MainMenuView.Views;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.util.Date;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SaleHistoryView extends JPanel {
	private JPanel panel ;
	private JScrollPane saleHistoryTable;
	private JPanel receiptPnl;
	private JScrollPane saleProductSP;
	
	private JTable table;
	private JTextField saleCodeTb;
	private JTextField saleDateTb;
	private JTable saleProductTable;
	private JTextField refundDateTb;
	private JTextField saleAmountTb;
	private JTextField saleMethodTb;
	private JTextField memberColdTb;
	
	private JDateChooser startDateChooser;
	private JDateChooser endDateChooser;
	private JSpinner sdHourSpn;
	private JSpinner sdSecondSpn;
	private JSpinner sdMinuteSpn;
	private JSpinner edHourSpn;
	private JSpinner edMinuteSpn;
	private JSpinner edSecondSpn;
	
	private JButton readBtn;
	private JCheckBox refundFlagCb;
	private JButton backBtn;
	private JButton printReceiptBtn;
	
	private boolean refundFlag;
	
	private Date startDate;
	private Date endDate;
	private SaleHistory[] salesHistoryList = null;
	private SaleHistory currClickedSaleHistory = null;

	
	public void initialLabel() {
		JLabel SaleHistoryLb = new JLabel("판매 이력");
		SaleHistoryLb.setHorizontalAlignment(SwingConstants.CENTER);
		SaleHistoryLb.setBounds(650, 50, 200, 50);
		SaleHistoryLb.setFont(new Font("굴림", Font.PLAIN, 40));
		add(SaleHistoryLb);
		
		JLabel readDateLb = new JLabel("조회 기간");
		readDateLb.setHorizontalAlignment(SwingConstants.LEFT);
		readDateLb.setBounds(25, 143, 80, 20);
		add(readDateLb);
		
		JLabel saleCodeLb = new JLabel("판매 코드");
		saleCodeLb.setHorizontalAlignment(SwingConstants.LEFT);
		saleCodeLb.setBounds(25, 185, 80, 18);
		add(saleCodeLb);
		
		JLabel saleDateLb = new JLabel("판매일시");
		saleDateLb.setBounds(11, 11, 62, 18);
		receiptPnl.add(saleDateLb);
		
		JLabel refundDateLb = new JLabel("환불일시");
		refundDateLb.setBounds(11, 66, 70, 18);
		receiptPnl.add(refundDateLb);
		JLabel refundFlagLb = new JLabel("환불여부");
		refundFlagLb.setBounds(11, 40, 62, 18);
		receiptPnl.add(refundFlagLb);
		
		JLabel saleAmountLb = new JLabel("판매금액");
		saleAmountLb.setBounds(11, 447, 62, 18);
		receiptPnl.add(saleAmountLb);
		JLabel saleMethodLb = new JLabel("결제수단");
		saleMethodLb.setBounds(11, 477, 62, 18);
		receiptPnl.add(saleMethodLb);
		JLabel memberCodeLb = new JLabel("고객 코드");
		memberCodeLb.setBounds(11, 507, 62, 18);
		receiptPnl.add(memberCodeLb);
		
		JLabel lblNewLabel = new JLabel("~");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(400, 148, 62, 18);
		add(lblNewLabel);
		
		JLabel sdHourLb = new JLabel("시");
		sdHourLb.setToolTipText("");
		sdHourLb.setHorizontalAlignment(SwingConstants.CENTER);
		sdHourLb.setBounds(252, 146, 21, 18);
		add(sdHourLb);
		
		JLabel sdMinuteLb = new JLabel("분");
		sdMinuteLb.setToolTipText("");
		sdMinuteLb.setHorizontalAlignment(SwingConstants.CENTER);
		sdMinuteLb.setBounds(314, 146, 21, 18);
		add(sdMinuteLb);
		
		JLabel sdSecondLb = new JLabel("초");
		sdSecondLb.setToolTipText("");
		sdSecondLb.setHorizontalAlignment(SwingConstants.CENTER);
		sdSecondLb.setBounds(378, 146, 21, 18);
		add(sdSecondLb);
		JLabel edHourLb = new JLabel("시");
		edHourLb.setToolTipText("");
		edHourLb.setHorizontalAlignment(SwingConstants.CENTER);
		edHourLb.setBounds(614, 146, 21, 18);
		add(edHourLb);
		
		JLabel edMinuteLb = new JLabel("분");
		edMinuteLb.setToolTipText("");
		edMinuteLb.setHorizontalAlignment(SwingConstants.CENTER);
		edMinuteLb.setBounds(676, 146, 21, 18);
		add(edMinuteLb);
		
		JLabel edSecondLb = new JLabel("초");
		edSecondLb.setToolTipText("");
		edSecondLb.setHorizontalAlignment(SwingConstants.CENTER);
		edSecondLb.setBounds(740, 146, 21, 18);
		add(edSecondLb);
	}
	public void initialListener() {
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		
		readBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date chooedStartDate = startDateChooser.getDate();
				startDate.setYear(chooedStartDate.getYear());
				startDate.setMonth(chooedStartDate.getMonth());
				startDate.setDate(chooedStartDate.getDate());
				startDate.setHours((Integer)sdHourSpn.getValue());
				startDate.setMinutes((Integer)sdMinuteSpn.getValue());
				startDate.setSeconds((Integer)sdSecondSpn.getValue());
				
				Date chooedEndDate = endDateChooser.getDate();
				endDate.setYear(chooedEndDate.getYear());
				endDate.setMonth(chooedEndDate.getMonth());
				endDate.setDate(chooedEndDate.getDate());
				endDate.setHours((Integer)edHourSpn.getValue());
				endDate.setMinutes((Integer)edMinuteSpn.getValue());
				endDate.setSeconds((Integer)edSecondSpn.getValue());
				
				String inputSaleCode = saleCodeTb.getText();
				
				SaleManager saleManager = new SaleManager();
				salesHistoryList = null;
				if (inputSaleCode.length() == 0)
					salesHistoryList = saleManager.readSaleHistory(startDate, endDate);
				else
					salesHistoryList = saleManager.readSaleHistory(inputSaleCode);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.setNumRows(0);
				if (salesHistoryList == null) {
					JOptionPane.showMessageDialog(null, "조회 결과가 없습니다", "error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					for (int i = 0; i < salesHistoryList.length; i++)
					{
						String[] item = new String[6];
						item[0] = Integer.toString(i + 1);
						item[1] = sdf.format(salesHistoryList[i].getSaleDate());
						if (salesHistoryList[i].getRefundFlag())
							item[2] = sdf.format(salesHistoryList[i].getRefundDate());
						else
							item[2] = "-";
						item[3] = Integer.toString(salesHistoryList[i].getSaleAmount());
						item[4] = salesHistoryList[i].getPaymentMethod();
						item[5] = salesHistoryList[i].getMemberCode();

						model.addRow(item);
					}
					DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
					tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
					TableColumnModel tcmSchedule = table.getColumnModel();
					for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
						tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
					}
				}
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				int index = table.getSelectedRow();
				if (salesHistoryList == null) return;
				if (index < salesHistoryList.length)
				{
					currClickedSaleHistory = salesHistoryList[index];
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					saleDateTb.setText(sdf.format(salesHistoryList[index].getSaleDate()));
					refundFlag = salesHistoryList[index].getRefundFlag();
					refundFlagCb.setSelected(refundFlag);
					refundDateTb.setText(sdf.format(salesHistoryList[index].getRefundDate()));
					if (refundFlag)
						refundDateTb.setText(sdf.format(salesHistoryList[index].getRefundDate()));
					else
						refundDateTb.setText("-");
					saleAmountTb.setText( ((Integer)salesHistoryList[index].getSaleAmount()).toString() + "원" );
					saleMethodTb.setText(salesHistoryList[index].getPaymentMethod());
					memberColdTb.setText(salesHistoryList[index].getMemberCode());
					
					DefaultTableModel model = (DefaultTableModel)saleProductTable.getModel();
					model.setNumRows(0);
					Product[] productList = salesHistoryList[index].getProductList();
					for (int i = 0; i < productList.length; i++)
					{
						String[] item = new String[3];
						item[0] = productList[i].getProductName();
						item[1] = ((Integer)productList[i].getSalePrice()).toString() + "원";
						item[2] = ((Integer)productList[i].getStockQuantity()).toString();
						
						boolean flag = true;
						for (int j = 0; j < model.getRowCount(); j++)
						{
							if (model.getValueAt(j, 0).equals(item[0])) {
								Integer quantity = Integer.parseInt((String)model.getValueAt(j, 2)) + Integer.parseInt(item[2]);
								model.setValueAt(quantity.toString(), j, 2);
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
		});
	}
	public void initial() {
		setPreferredSize(new Dimension(1500,800));
		setLayout(null);
		
		saleCodeTb = new JTextField();
		saleCodeTb.setHorizontalAlignment(SwingConstants.CENTER);
		saleCodeTb.setBounds(106, 182, 221, 24);
		add(saleCodeTb);
		saleCodeTb.setColumns(10);
		
		readBtn = new JButton("조회");
		readBtn.setFont(new Font("굴림", Font.PLAIN, 15));
		readBtn.setBounds(341, 178, 100, 30);
		add(readBtn);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.activeCaptionBorder);
		panel.setBounds(25, 222, 900, 467);
		add(panel);
		panel.setLayout(null);
		
		saleHistoryTable = new JScrollPane();
		saleHistoryTable.setBounds(3, 4, 894, 458);
		panel.add(saleHistoryTable);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		saleHistoryTable.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"No", "\uD310\uB9E4\uC77C\uC2DC", "\uD658\uBD88\uC77C\uC2DC", "\uD310\uB9E4 \uAE08\uC561", "\uACB0\uC81C\uC218\uB2E8", "\uACE0\uAC1D\uCF54\uB4DC"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(29);
		table.getColumnModel().getColumn(1).setPreferredWidth(177);
		table.getColumnModel().getColumn(2).setPreferredWidth(190);
		table.getColumnModel().getColumn(5).setPreferredWidth(73);
		
		receiptPnl = new JPanel();
		receiptPnl.setBackground(SystemColor.activeCaptionBorder);
		receiptPnl.setBounds(975, 150, 500, 539);
		add(receiptPnl);
		receiptPnl.setLayout(null);
		
		saleDateTb = new JTextField();
		saleDateTb.setBackground(Color.WHITE);
		saleDateTb.setEditable(false);
		saleDateTb.setHorizontalAlignment(SwingConstants.CENTER);
		saleDateTb.setBounds(74, 8, 300, 24);
		receiptPnl.add(saleDateTb);
		saleDateTb.setColumns(10);
		
		saleProductSP = new JScrollPane();
		saleProductSP.setBounds(11, 96, 475, 339);
		receiptPnl.add(saleProductSP);
		
		saleProductTable = new JTable();
		saleProductTable.setFillsViewportHeight(true);
		saleProductTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uC0C1\uD488\uBA85", "\uD310\uB9E4\uAC00\uACA9", "\uD310\uB9E4\uC218\uB7C9"
			}
		));
		saleProductSP.setViewportView(saleProductTable);
		
		
		refundDateTb = new JTextField();
		refundDateTb.setBackground(Color.WHITE);
		refundDateTb.setEditable(false);
		refundDateTb.setHorizontalAlignment(SwingConstants.CENTER);
		refundDateTb.setBounds(74, 63, 300, 24);
		receiptPnl.add(refundDateTb);
		refundDateTb.setColumns(10);
		
		refundFlagCb = new JCheckBox("");
		refundFlagCb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				refundFlagCb.setSelected(refundFlag);
			}
		});
		refundFlagCb.setBackground(SystemColor.activeCaptionBorder);
		refundFlagCb.setToolTipText("");
		refundFlagCb.setBounds(72, 36, 27, 27);
		receiptPnl.add(refundFlagCb);
		
		saleAmountTb = new JTextField();
		saleAmountTb.setBackground(Color.WHITE);
		saleAmountTb.setEditable(false);
		saleAmountTb.setHorizontalAlignment(SwingConstants.RIGHT);
		saleAmountTb.setBounds(76, 444, 410, 24);
		receiptPnl.add(saleAmountTb);
		saleAmountTb.setColumns(10);
		
		saleMethodTb = new JTextField();
		saleMethodTb.setBackground(Color.WHITE);
		saleMethodTb.setEditable(false);
		saleMethodTb.setHorizontalAlignment(SwingConstants.CENTER);
		saleMethodTb.setBounds(77, 473, 300, 24);
		receiptPnl.add(saleMethodTb);
		saleMethodTb.setColumns(10);
		
		memberColdTb = new JTextField();
		memberColdTb.setBackground(Color.WHITE);
		memberColdTb.setEditable(false);
		memberColdTb.setHorizontalAlignment(SwingConstants.CENTER);
		memberColdTb.setBounds(76, 503, 300, 24);
		receiptPnl.add(memberColdTb);
		memberColdTb.setColumns(10);
		
		backBtn = new JButton("이전화면");
		backBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		backBtn.setBounds(1325, 700, 150, 50);
		add(backBtn);
		
		printReceiptBtn = new JButton("영수증 출력");
		printReceiptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedSaleHistory == null) {
					JOptionPane.showMessageDialog(null, "판매이력을 조회해 주세요", "error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					SaleManager sManager = new SaleManager();
					sManager.printReceipt(currClickedSaleHistory);
				}
			}
		});
		printReceiptBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		printReceiptBtn.setBounds(1161, 700, 150, 50);
		add(printReceiptBtn);

		
		
		startDateChooser = new JDateChooser();
		startDateChooser.setDateFormatString("yyyy-M-d");
		startDateChooser.setBounds(106, 143, 99, 24);
		add(startDateChooser);
		sdHourSpn = new JSpinner();
		sdHourSpn.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		sdHourSpn.setBounds(211, 143, 42, 24);
		add(sdHourSpn);
		sdMinuteSpn = new JSpinner();
		sdMinuteSpn.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		sdMinuteSpn.setBounds(271, 143, 42, 24);
		add(sdMinuteSpn);
		sdSecondSpn = new JSpinner();
		sdSecondSpn.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		sdSecondSpn.setBounds(334, 143, 42, 24);
		add(sdSecondSpn);
		
		startDateChooser.setDate(startDate);
		sdHourSpn.setValue(startDate.getHours());
		sdMinuteSpn.setValue(startDate.getMinutes());
		sdSecondSpn.setValue(startDate.getSeconds());
		
		endDateChooser = new JDateChooser();
		endDateChooser.setDateFormatString("yyyy-M-d");
		endDateChooser.setBounds(468, 143, 99, 24);
		add(endDateChooser);
		edHourSpn = new JSpinner();
		edHourSpn.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		edHourSpn.setBounds(573, 143, 42, 24);
		add(edHourSpn);
		edMinuteSpn = new JSpinner();
		edMinuteSpn.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		edMinuteSpn.setBounds(633, 143, 42, 24);
		add(edMinuteSpn);
		edSecondSpn = new JSpinner();
		edSecondSpn.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		edSecondSpn.setBounds(696, 143, 42, 24);
		add(edSecondSpn);
		
		endDateChooser.setDate(endDate);
		edHourSpn.setValue(endDate.getHours());
		edMinuteSpn.setValue(endDate.getMinutes());
		edSecondSpn.setValue(endDate.getSeconds());
	}
	
	public SaleHistoryView() {
		startDate = new Date();
		startDate.setHours(0); 
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		endDate = new Date();
		
		initial();
		initialLabel();
		initialListener();
	}
	
}

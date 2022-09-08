/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	DailySalesManagement
 * @className 			MonthlyReportReadView
 * @author 					최정봉
 */
package DailySalesManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class MonthlyReportReadView extends JFrame {
	ArrayList<MonthlyReport> list = new ArrayList<MonthlyReport>();
	
	private JPanel contentPane;
	private JTable table;
	private JLabel label;
	private JButton ReadButton;
	
	private JComboBox yearBox;
	private JLabel label_1;
	private JComboBox monthBox;
	private JLabel label_2;
	
	private JLabel totalcashBox;
	private JLabel totalcardBox;
	private JLabel totalrefundBox;
	private JLabel totalsalesBox;
	
	public int totalcash = 0;
	public int totalcard = 0;
	public int totalrefund = 0;
	/**
	 * Launch the application.
	 */
	//화면에 정보들 출력
	public void showMonthlyReportRead() {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setNumRows(0);
		totalcash = 0;
		totalcard = 0;
		totalrefund = 0;
		
		if(list.size()==0) {
			JOptionPane.showMessageDialog(null, yearBox.getSelectedItem() + "년 " + monthBox.getSelectedItem() + "월  매출 실적이 없습니다.");
		}
		else {
			for(int i=0;i<list.size();i++) {
				MonthlyReport mr = list.get(i);
				
				String salesDate = mr.getDate();
				String cash = String.valueOf(mr.getCashAmount());
				String card = String.valueOf(mr.getCardAmount());
				String refund = String.valueOf(mr.getRefundAmount());
				String total = String.valueOf(mr.getCashAmount() + mr.getCardAmount() - mr.getRefundAmount());
				
				totalcash += mr.getCashAmount();
				totalcard += mr.getCardAmount();
				totalrefund += mr.getRefundAmount();
				
				model.addRow(new String[] {salesDate, cash, card, refund, total});
			}
			model.fireTableDataChanged();
		}
		totalcashBox.setText("월 현금매출 : " + String.valueOf(totalcash));
		totalcardBox.setText("월 카드매출 : " + String.valueOf(totalcard));
		totalrefundBox.setText("월 환불총액 : " + String.valueOf(totalrefund));
		totalsalesBox.setText("월 매출액 : " + String.valueOf(totalcash + totalcard - totalrefund));
	}
	//설계서에는 있으나 용도 불명
	public void showinputPeriod() {
		
	}
	//화면에 출력할 정보 가져오기
	public void requestShowMonthlyReportRead(String date) {
		DailySalesManager dsm = new DailySalesManager();
		list = dsm.monthlyReportRead(date);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MonthlyReportReadView frame = new MonthlyReportReadView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MonthlyReportReadView() {
		setTitle("\uC6D4\uBCF4 \uC870\uD68C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLb = new JLabel("월보 조회");
	    titleLb.setHorizontalAlignment(SwingConstants.CENTER);
	    titleLb.setBounds(550, 50, 400, 50);
	    titleLb.setFont(new Font("굴림", Font.PLAIN, 40));
	    contentPane.add(titleLb);
	    
	    JPanel panel = new JPanel();
	    panel.setBounds(100, 200, 1300, 450);
	    contentPane.add(panel);
	    panel.setLayout(new GridLayout(0, 1, 0, 0));
	    
	    table = new JTable();
	    table.setModel(new DefaultTableModel(
	    	new Object[][] {
	    		{null, null, null, null, null},
	    	},
	    	new String[] {
	    		"\uB144-\uC6D4-\uC77C", "\uD604\uAE08 \uB9E4\uCD9C", "\uCE74\uB4DC \uB9E4\uCD9C", "\uD658\uBD88\uC561", "\uC77C \uB9E4\uCD9C\uC561"
	    	}
	    ));
	    panel.add(new JScrollPane(table), BorderLayout.CENTER);
	    
	    label = new JLabel("\uC870\uD68C \uAE30\uAC04");
	    label.setBounds(859, 153, 82, 21);
	    contentPane.add(label);
	    
	    ReadButton = new JButton("\uC870\uD68C");
	    ReadButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String date = (String)yearBox.getSelectedItem() + (String)monthBox.getSelectedItem();
	    		requestShowMonthlyReportRead(date);
	    		showMonthlyReportRead();
	    	}
	    });
	    ReadButton.setBounds(1271, 149, 129, 29);
	    contentPane.add(ReadButton);
	    
	    yearBox = new JComboBox();
	    yearBox.setModel(new DefaultComboBoxModel(new String[] {"2019", "2020", "2021"}));
	    yearBox.setBounds(958, 150, 107, 27);
	    contentPane.add(yearBox);
	    
	    label_1 = new JLabel("\uB144");
	    label_1.setBounds(1082, 153, 23, 21);
	    contentPane.add(label_1);
	    
	    monthBox = new JComboBox();
	    monthBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
	    monthBox.setBounds(1122, 150, 93, 27);
	    contentPane.add(monthBox);
	    
	    label_2 = new JLabel("\uC6D4");
	    label_2.setBounds(1231, 153, 23, 21);
	    contentPane.add(label_2);
	    
	    totalcashBox = new JLabel("\uC6D4 \uD604\uAE08\uB9E4\uCD9C : ");
	    totalcashBox.setBounds(384, 679, 238, 21);
	    contentPane.add(totalcashBox);
	    
	    totalcardBox = new JLabel("\uC6D4 \uCE74\uB4DC\uB9E4\uCD9C : ");
	    totalcardBox.setBounds(672, 679, 209, 21);
	    contentPane.add(totalcardBox);
	    
	    totalrefundBox = new JLabel("\uC6D4 \uD658\uBD88\uCD1D\uC561 : ");
	    totalrefundBox.setBounds(908, 679, 228, 21);
	    contentPane.add(totalrefundBox);
	    
	    totalsalesBox = new JLabel("\uC6D4 \uB9E4\uCD9C\uC561 : ");
	    totalsalesBox.setBounds(1172, 679, 228, 21);
	    contentPane.add(totalsalesBox);
	    
		JButton backBtn = new JButton("이전화면");
		backBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		backBtn.setBounds(100, 50, 150, 50);
		contentPane.add(backBtn);
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
	}

}

/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	DailySalesManagement
 * @className 			DailyReportReadView
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
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class DailyReportReadView extends JFrame {
	ArrayList<DailySalesDetail> list = new ArrayList<DailySalesDetail>();
	
	private JPanel contentPane;
	private JTable DailyReadTable;
	private JLabel DayAmountBox;
	/**
	 * Launch the application.
	 */
	public String date1;
	public int dayAmount = 0;
	
	//화면에 정보들 출력
	public void showDailyReportRead() {
		DefaultTableModel model = (DefaultTableModel)DailyReadTable.getModel();
		model.setNumRows(0);
		dayAmount = 0;
		
		if(list.size()==0) {
			JOptionPane.showMessageDialog(null, date1 + "일자 매출 실적이 없습니다.");
		}
		else {
			for(int i=0;i<list.size();i++) {
				DailySalesDetail dsd = list.get(i);
				
				String quantity = String.valueOf(dsd.getSalesQuantity());
				
				String surtax = String.valueOf(dsd.getSalesAmount() / 11);
				String supplyPrice = String.valueOf(dsd.getSalesAmount() - Integer.parseInt(surtax));
				String salesPrice = String.valueOf(dsd.getSalesAmount() / dsd.getSalesQuantity());
				String amount; 
				String member;
				if(dsd.getMemberID().equals("비회원")) {
					member = dsd.getMemberID();
					amount = String.valueOf(dsd.getSalesAmount());
					dayAmount += dsd.getSalesAmount();
				}else {
					member = "회원";
					amount = String.valueOf(dsd.getSalesAmount()*0.9);
					dayAmount += (dsd.getSalesAmount()*0.9);
				}
				
				model.addRow(new String[] {date1, dsd.getProductName(), salesPrice, quantity, member, supplyPrice, surtax, amount});
			}
			model.fireTableDataChanged();
			
		}
		DayAmountBox.setText("총 매출 : " + String.valueOf(dayAmount) + " 원");
	}
	
	//설계서에는 있으나 용도 불명
	public void showInputPeriod() {
		
	}
	
	//화면에 띄울 정보 가져오기
	void requestShowDailyReportRead(String date) {
		DailySalesManager dsm = new DailySalesManager();
		list = dsm.dailyReportRead(date);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DailyReportReadView frame = new DailyReportReadView();
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
	public DailyReportReadView() {
		setTitle("\uC77C\uBCF4 \uC870\uD68C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLb = new JLabel("일보 조회");
	    titleLb.setHorizontalAlignment(SwingConstants.CENTER);
	    titleLb.setBounds(550, 50, 400, 50);
	    titleLb.setFont(new Font("굴림", Font.PLAIN, 40));
	    contentPane.add(titleLb);
	    
	    JPanel panel = new JPanel();
	    panel.setBounds(100, 200, 1300, 450);
	    contentPane.add(panel);
	    panel.setLayout(new GridLayout(1, 0, 0, 0));
	    
	    DailyReadTable = new JTable();
	    DailyReadTable.setModel(new DefaultTableModel(
	    	new Object[][] {
	    		{null, null, null, null, null, null, null, null},
	    	},
	    	new String[] {
	    		"\uB144-\uC6D4-\uC77C", "\uD310\uB9E4 \uC81C\uD488", "\uD310\uB9E4\uAC00", "\uD310\uB9E4 \uC218\uB7C9", "\uD68C\uC6D0/\uBE44\uD68C\uC6D0", "\uACF5\uAE09\uC561", "\uBD80\uAC00\uC138", "\uD310\uB9E4 \uAE08\uC561"
	    	}
	    ));
	    panel.add(new JScrollPane(DailyReadTable), BorderLayout.CENTER);
	    
	    JLabel lblNewLabel = new JLabel("\uC870\uD68C \uAE30\uAC04");
	    lblNewLabel.setBounds(763, 147, 82, 21);
	    contentPane.add(lblNewLabel);
	    
	    JComboBox yearBox = new JComboBox();
	    yearBox.setModel(new DefaultComboBoxModel(new String[] {"2019", "2020", "2021"}));
	    yearBox.setBounds(862, 144, 107, 27);
	    contentPane.add(yearBox);
	    
	    JLabel lblNewLabel_1 = new JLabel("\uB144");
	    lblNewLabel_1.setBounds(986, 147, 50, 21);
	    contentPane.add(lblNewLabel_1);
	    
	    JComboBox monthBox = new JComboBox();
	    monthBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
	    monthBox.setBounds(1026, 144, 61, 27);
	    contentPane.add(monthBox);
	    
	    JLabel lblNewLabel_2 = new JLabel("\uC6D4");
	    lblNewLabel_2.setBounds(1104, 147, 35, 21);
	    contentPane.add(lblNewLabel_2);
	    
	    JComboBox dayBox = new JComboBox();
	    dayBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
	    dayBox.setBounds(1135, 144, 87, 27);
	    contentPane.add(dayBox);
	    
	    JLabel label = new JLabel("\uC77C");
	    label.setBounds(1239, 147, 35, 21);
	    contentPane.add(label);
	    
	    JButton ReadButton = new JButton("\uC870\uD68C");
	    ReadButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		String date = (String) yearBox.getSelectedItem() + (String)monthBox.getSelectedItem() + (String)dayBox.getSelectedItem();
	    		date1 = date;
	    		
	    		requestShowDailyReportRead(date);
	    		showDailyReportRead();
	    	}
	    });
	    ReadButton.setBounds(1271, 143, 129, 29);
	    contentPane.add(ReadButton);
	    
	    DayAmountBox = new JLabel("\uCD1D \uB9E4\uCD9C : ");
	    DayAmountBox.setBounds(1239, 665, 161, 21);
	    contentPane.add(DayAmountBox);
	    
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

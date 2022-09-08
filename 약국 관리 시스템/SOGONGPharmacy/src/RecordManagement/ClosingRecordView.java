/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	RecordManagement
 * @className 			ClosingRecordView
 * @author 					최정봉
 */
package RecordManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClosingRecordView extends JDialog {
	ArrayList<ClosingRecord> list = new ArrayList<ClosingRecord>();
	
	private final JPanel ClosingRecordView = new JPanel();
	private JTextField dateBox;
	private JTable table;
	private JLabel salesBox;
	private JLabel refundBox;
	private JLabel totalBox;
	
	public int sales = 0;
	public int refund = 0;
	public int total = 0;
	/**
	 * Launch the application.
	 */
	//화면에 표시
	public void showView() {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setNumRows(0);
		
		sales = 0;
		refund = 0;
		total = 0;
		
		if(list.size()==0) {
			
		}
		else {
			for(int i=0;i<list.size();i++) {
				ClosingRecord cr = list.get(i);
				
				String member = cr.getMemberID();
				int salesAmount;
				String refundState;
				if(cr.getRefundState()==0) {
					if(cr.getMemberID().equals("비회원")) {
						salesAmount = cr.getSalesAmount();
						sales += salesAmount;
					}
					else {
						salesAmount = cr.getSalesAmount() / 10 * 9;
						sales += salesAmount;
						member="회원";
					}
					refundState = "판매";
					
				}
				else {
					if(cr.getMemberID().equals("비회원")) {
						salesAmount = cr.getSalesAmount() * -1;
						refund += salesAmount;
					}
					else {
						salesAmount = cr.getSalesAmount() / 10 * 9 * -1;
						refund += salesAmount;
						member="회원";
					}
					refundState = "환불";
				}
				String quantity = String.valueOf(cr.getSalesQuantity());
				model.addRow(new String[] {refundState, cr.getProductName(), quantity, member, String.valueOf(salesAmount), String.valueOf(cr.getProductStock())});
			}
			model.fireTableDataChanged();
			
			salesBox.setText("총 판매액 : " + String.valueOf(sales));
			refundBox.setText("총 환불액 : " + String.valueOf(refund));
			totalBox.setText("총 매출액 : " + String.valueOf(sales - refund));
		}
		
	}
	//정보 요청
	public void requestClosingRecordInfo(String date) {
		RecordManager rm = new RecordManager();
		list = rm.readClosingRecord(date);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClosingRecordView dialog = new ClosingRecordView();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public ClosingRecordView() {
		setTitle("\uB9C8\uAC10 \uC774\uB825 \uC870\uD68C");
		setBounds(100, 100, 1500, 800);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(ClosingRecordView);
		ClosingRecordView.setLayout(null);
		
		JLabel titleLb = new JLabel("마감이력 조회");
	    titleLb.setHorizontalAlignment(SwingConstants.CENTER);
	    titleLb.setBounds(550, 50, 400, 50);
	    titleLb.setFont(new Font("굴림", Font.PLAIN, 40));
	    ClosingRecordView.add(titleLb);
	    
	    JPanel panel = new JPanel();
	    panel.setBounds(100, 200, 900, 450);
	    ClosingRecordView.add(panel);
	    panel.setLayout(new GridLayout(0, 1, 0, 0));
	    
	    table = new JTable();
	    table.setModel(new DefaultTableModel(
	    	new Object[][] {
	    		{null, null, null, null, null, null},
	    	},
	    	new String[] {
	    		"\uAD6C\uBD84", "\uC0C1\uD488\uBA85", "\uD310\uB9E4 \uAC1C\uC218", "\uD68C\uC6D0/\uBE44\uD68C\uC6D0", "\uB9E4\uCD9C", "\uB0A8\uC740 \uC0C1\uD488 \uC7AC\uACE0"
	    	}
	    ));
	    panel.add(new JScrollPane(table), BorderLayout.CENTER);
	    
	    JButton button = new JButton("\uAC80\uC0C9");
	    button.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		requestClosingRecordInfo(dateBox.getText());
	    		showView();
	    	}
	    });
	    button.setBounds(929, 162, 72, 23);
	    ClosingRecordView.add(button);
	    
	    dateBox = new JTextField();
	    dateBox.setToolTipText("\uC0C1\uD488\uBA85\uC744 \uC785\uB825\uD558\uC138\uC694");
	    dateBox.setColumns(10);
	    dateBox.setBounds(696, 163, 214, 21);
	    ClosingRecordView.add(dateBox);
	    
	    JLabel label = new JLabel("\uB9C8\uAC10 \uB0A0\uC9DC");
	    label.setHorizontalAlignment(SwingConstants.CENTER);
	    label.setBounds(563, 163, 116, 15);
	    ClosingRecordView.add(label);
	    
	    salesBox = new JLabel("\uCD1D \uD310\uB9E4\uC561");
	    salesBox.setBounds(1067, 208, 214, 21);
	    ClosingRecordView.add(salesBox);
	    
	    refundBox = new JLabel("\uCD1D \uD658\uBD88\uC561");
	    refundBox.setBounds(1067, 261, 192, 21);
	    ClosingRecordView.add(refundBox);
	    
	    totalBox = new JLabel("\uCD1D \uB9E4\uCD9C\uC561");
	    totalBox.setBounds(1067, 324, 192, 21);
	    ClosingRecordView.add(totalBox);
		
		JButton backBtn = new JButton("이전화면");
		backBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		backBtn.setBounds(1300, 679, 150, 50);
		ClosingRecordView.add(backBtn);
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(ClosingRecordView.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
	}

}

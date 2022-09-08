/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	RecordManagement
 * @className 			EnteringRecordView
 * @author 					최정봉
 */
package RecordManagement;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class EnteringRecordView extends JDialog {
	private ArrayList<Record> list;
	
	private final JPanel EnteringRecordView = new JPanel();
	private JTextField searchName;
	private JTextField searchDate;
	private JTable table;
	
	/**
	 * Launch the application.
	 */
	//정보 요청
	public void requestProductRecordInfo() {
		RecordManager rm = new RecordManager();
		
		String name = searchName.getText();
		String date = searchDate.getText();
		
		if(name.equals("")) {
			if(date.equals("")) {
				
			}
			else {
				list = rm.readEnteringRecordforDate(name, date);
			}
		}
		else {
			if(date.equals("")) {
				list = rm.readEnteringRecord(name);
			}
			else {
				list = rm.readEnteringRecordforDate(name, date);
			}
		}

	}
	//화면에 표시
	public void showView() {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setNumRows(0);
		
		String name = searchName.getText();
		String date = searchDate.getText();
		
		if(list.size()==0) {
			if(name.equals("")) {
				if(date.equals("")) {
					
				}
				else {
					JOptionPane.showMessageDialog(null, "\"" + date + "\"" + " 에 해당하는 검색결과가 없습니다.");
				}
			}
			else {
				if(date.equals("")) {
					JOptionPane.showMessageDialog(null, "\"" + name + "\"" + " 을(를) 포함하는 검색결과가 없습니다.");
				}
				else {
					JOptionPane.showMessageDialog(null, "\"" + date + "\"에 " + "\"" + name + "\"" + " 을(를) 포함하는 검색결과가 없습니다." );
				}
			}
			
		}
		else {
			for(int i=0;i<list.size();i++) {
				Record pr = list.get(i);
				String quan = String.valueOf(pr.getquantity());
				model.addRow(new String[] {pr.getCode(),pr.getProductName(),pr.getdate(),quan});
			}
			model.fireTableDataChanged();
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnteringRecordView dialog = new EnteringRecordView();
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
	public EnteringRecordView() {
		
		setTitle("\uC785\uACE0 \uC774\uB825 \uC870\uD68C");
		setBounds(100, 100, 1500, 800);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(EnteringRecordView);
		EnteringRecordView.setLayout(null);
		
		JLabel titleLb = new JLabel("입고이력 조회");
	    titleLb.setHorizontalAlignment(SwingConstants.CENTER);
	    titleLb.setBounds(550, 50, 400, 50);
	    titleLb.setFont(new Font("굴림", Font.PLAIN, 40));
	    EnteringRecordView.add(titleLb);
	    
	   
		
		JButton btnNewButton = new JButton("\uAC80\uC0C9");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestProductRecordInfo();
				showView();
			}
		});
		btnNewButton.setBounds(1084, 141, 116, 75);
		EnteringRecordView.add(btnNewButton);
		
		searchName = new JTextField();
		searchName.setToolTipText("\uC0C1\uD488\uBA85\uC744 \uC785\uB825\uD558\uC138\uC694");
		searchName.setColumns(10);
		searchName.setBounds(908, 141, 139, 21);
		EnteringRecordView.add(searchName);
		
		JLabel label = new JLabel("\uC0C1\uD488\uBA85");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(822, 141, 57, 15);
		EnteringRecordView.add(label);
		
		searchDate = new JTextField();
		searchDate.setToolTipText("\uC0C1\uD488\uBA85\uC744 \uC785\uB825\uD558\uC138\uC694");
		searchDate.setColumns(10);
		searchDate.setBounds(908, 188, 139, 21);
		EnteringRecordView.add(searchDate);
		
		JLabel label_1 = new JLabel("\uB0A0\uC9DC");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(822, 188, 57, 15);
		EnteringRecordView.add(label_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(100, 250, 1100, 400);
		EnteringRecordView.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
			
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"\uC785\uACE0\uCF54\uB4DC", "\uC0C1\uD488\uBA85", "\uC785\uACE0\uC77C", "\uC785\uACE0 \uC218\uB7C9"
			}
		));
		
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JButton backBtn = new JButton("이전화면");
		backBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		backBtn.setBounds(1300, 679, 150, 50);
		EnteringRecordView.add(backBtn);
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(EnteringRecordView.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
	}
}

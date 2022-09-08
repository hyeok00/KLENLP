/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	SupplierManagement
 * @className 		SupplierReadView
 * @author 			이수하
 */
package SupplierManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SupplierReadView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JTextField textField;
	private DefaultTableModel tableModel; 
	private String[][] data_list;
	int count = 0;
	
	/**
	 * Launch the application.
	 */
	public void showView() {
		String name = textField.getText();
		SupplierManager sm = new SupplierManager();
		
		
		DefaultTableModel model2=(DefaultTableModel) table.getModel();
		model2.setNumRows(0);
		
		sm.readSuppiler(name);
		
		ArrayList<Supplier> al = sm.list;
	
		for(int i = 0; i < al.size(); i++) {
			Supplier s = al.get(i);

			DefaultTableModel model=(DefaultTableModel) table.getModel();
			 Object [] temp_row= new Object[]{s.getSupplierCode(), s.getSupplierName(), s.getContactNum(), s.getClosed(), s.getProductClassify(), s.getCost()};
			 model.addRow(temp_row);
			 model.fireTableDataChanged();
		}
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupplierReadView frame = new SupplierReadView();
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
	public SupplierReadView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uAC70\uB798\uCC98 \uC870\uD68C");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 40));
		lblNewLabel.setBounds(602, 24, 213, 38);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("\uC774\uC804\uD654\uBA74");
		btnNewButton_1.setBounds(14, 677, 139, 64);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("\uC758\uC57D\uD488 \uC81C\uD488\uBA85");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_1.setBounds(64, 89, 134, 18);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(212, 88, 283, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("\uC870\uD68C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showView();
			}
		});
		btnNewButton.setBounds(551, 87, 105, 27);
		contentPane.add(btnNewButton);
		 
		Object supplierColumn[]  = new Object[6];
		String[] supplierColumnList = {"거래처코드","거래처 명", "연락처", "휴무일", "취급제품명", "거래상품 가격"};
		tableModel = new DefaultTableModel(supplierColumnList,0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		
		
		scrollPane.setBounds(53, 156, 1300, 450);
		contentPane.add(scrollPane);
		

		
		JPanel panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		getContentPane().add(scrollPane, "supplier");		
		
	}	
}

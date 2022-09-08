/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	EnteringManagement
 * @className 		EnteringDeleteView
 * @author 			강정환
 */	


package EnteringManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
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
import java.util.ArrayList;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;


public class EnteringDeleteView extends JFrame {

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
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnteringDeleteView frame = new EnteringDeleteView();
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
	public EnteringDeleteView() {
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("입고 삭제");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 40));
		lblNewLabel.setBounds(602, 24, 213, 38);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("\uCDE8\uC18C");
		btnNewButton_1.setBounds(14, 677, 139, 64);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		
		JButton button = new JButton("삭제");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnteringManager sm = new EnteringManager();
				
				
				
				int row = table.getSelectedRow();
				DefaultTableModel model=(DefaultTableModel) table.getModel();
				
				Entering entering = new Entering();
				
				String enteringCode = (String)table.getValueAt(row, 0);
				String enteringDate = (String)table.getValueAt(row, 1);
				String productName = (String)table.getValueAt(row, 2);
				int stringEnteringQuantity = (Integer)table.getValueAt(row, 3);
				String supplierName = (String)table.getValueAt(row, 4);
				String expirationDate = (String)table.getValueAt(row, 5);
				
//				int enteringQuantity = Integer.parseInt(stringEnteringQuantity);
				
				
				
				entering.setEnteringCode(enteringCode);
				entering.setEnteringDate(enteringDate);
				entering.setProductName(productName);
				entering.setEnteringQuantity(stringEnteringQuantity);
				entering.setSupplierName(supplierName);
				entering.setExpirationDate(expirationDate);
				
				
				int flag = sm.deleteEntering(entering);
				
				
				
				if(flag == 1) {
					model.removeRow(row);
					model.fireTableDataChanged();
					JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.");
				}
				
				else {
					JOptionPane.showMessageDialog(null, "판매 내역이 있는 상품은 삭제가 불가능합니다!");
				}

				
				
				
				
				
			}
		});
		button.setBounds(1322, 669, 139, 60);
		contentPane.add(button);
		
		JLabel lblNewLabel_1 = new JLabel("\uC758\uC57D\uD488 \uC81C\uD488\uBA85");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
		lblNewLabel_1.setBounds(64, 89, 134, 18);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(212, 88, 283, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("조회");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = textField.getText();
				EnteringManager sm = new EnteringManager();
				
				
				DefaultTableModel model2=(DefaultTableModel) table.getModel();
				model2.setNumRows(0);
				
				sm.readEntering(name);
				
				ArrayList<Entering> al = sm.list;
				
				
				
				for(int i = 0; i < al.size(); i++) {
					Entering s = al.get(i);
					
					
					
					
					DefaultTableModel model=(DefaultTableModel) table.getModel();
					 Object [] temp_row= new Object[]{s.getEnteringCode(), s.getEnteringDate(), s.getProductName(), s.getEnteringQuantity(), s.getSupplierName(), s.getExpirationDate()};
					 model.addRow(temp_row);
					 model.fireTableDataChanged();

				}
				

			}
		});
		btnNewButton.setBounds(551, 87, 105, 27);
		contentPane.add(btnNewButton);
		 
		Object supplierColumn[]  = new Object[6];
		String[] supplierColumnList = {"입고코드","입고일", "상품명", "수량", "거래처","유통기한"};
		tableModel = new DefaultTableModel(supplierColumnList,0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		
		
		scrollPane.setBounds(53, 156, 1300, 450);
		contentPane.add(scrollPane);
		

		
		JPanel panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		getContentPane().add(scrollPane, "entering");
		
		EnteringManager sm = new EnteringManager();
	
		
		

		
	}
	}






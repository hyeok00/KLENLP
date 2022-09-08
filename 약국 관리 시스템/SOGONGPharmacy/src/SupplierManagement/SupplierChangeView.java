/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	SupplierManagement
 * @className 		SupplierChangeView
 * @author 			이수하
 */
package SupplierManagement;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.JButton;
import javax.swing.JDialog;

public class SupplierChangeView extends JFrame {
	private JPanel contentPane;
	private JTable table;
	private JTable supplierTable;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel; 

	public int supplierAmount = 0;
	public String supplierCodeNone;
	SupplierManager supplierInfo = new SupplierManager();

	ArrayList<Supplier> list = new ArrayList<Supplier>();
	
	public void showView() {
		SupplierManager sm = new SupplierManager();
		
		sm.readAllSuppliers();
		
		ArrayList<Supplier> al = sm.list;
		
		
		for(int i = 0; i < al.size(); i++) {
			Supplier s = al.get(i);
					
			DefaultTableModel model=(DefaultTableModel) table.getModel();
			Object [] temp_row= new Object[]{s.getSupplierCode(), s.getSupplierName(), s.getContactNum(), s.getClosed(), s.getProductClassify(), s.getCost()};
			
			model.addRow(temp_row);
			model.fireTableDataChanged();
		}
    }
	public void RequestChangeSupplierInfo() {
		tableModel = (DefaultTableModel)table.getModel();
		
		int row = table.getSelectedRow();
		
		if(row < 0) {
			return ;
		}

		String code = (String) tableModel.getValueAt(row, 0);
		String name = (String) tableModel.getValueAt(row, 1);
		String phone = (String) tableModel.getValueAt(row, 2);
		String closed = (String) tableModel.getValueAt(row, 3);
		String product = (String) tableModel.getValueAt(row, 4);
		int cost = (Integer) tableModel.getValueAt(row, 5);
		
		System.out.println(code);
		System.out.println(name);
		System.out.println(phone);
		System.out.println(closed);
		System.out.println(product);
		System.out.println(cost);
	
		
		Supplier s = new Supplier(code, name, phone, closed, product, cost);
		supplierInfo.changeSupplier(s);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupplierChangeView frame = new SupplierChangeView();
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
	public SupplierChangeView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uAC70\uB798\uCC98 \uC218\uC815");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 40));
		lblNewLabel.setBounds(624, 34, 213, 38);
		contentPane.add(lblNewLabel);

		String[] supplierColumnList = {"거래처코드","거래처 명", "연락처", "휴무일", "취급제품명", "거래상품 가격"};
		tableModel = new DefaultTableModel(supplierColumnList,0);
		
		JButton btnNewButton = new JButton("수정");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RequestChangeSupplierInfo();
				if (arg0.getSource().equals(btnNewButton))
				{
					JOptionPane.showMessageDialog(contentPane, "수정이 완료되었습니다.");
				}
				
				tableModel.setRowCount(0);
				
			}
		});
		btnNewButton.setBounds(1329, 665, 139, 64);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("이전화면");
		btnNewButton_1.setBounds(29, 665, 139, 64);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		
		table = new JTable(new DefaultTableModel(
			new Object[][] {

			},
			new String[] {
				"\uAC70\uB798\uCC98\uCF54\uB4DC", "\uAC70\uB798\uCC98 \uBA85", "\uC5F0\uB77D\uCC98", "\uD734\uBB34\uC77C", "\uCDE8\uAE09\uC81C\uD488\uBA85", "\uAC70\uB798\uC0C1\uD488 \uAC00\uACA9"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Object.class, Object.class, Object.class, Object.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		JScrollPane scrollPane_2 = new JScrollPane(table);
		scrollPane_2.setBounds(79, 129, 1298, 433);
		contentPane.add(scrollPane_2);
		JScrollPane scrollPane_1 = new JScrollPane(supplierTable);
		scrollPane_2.setColumnHeaderView(scrollPane_1);
		
		JPanel panel = new JPanel();
		scrollPane_2.setColumnHeaderView(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnNewButton_2 = new JButton("\uAC70\uB798\uCC98 \uC870\uD68C");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showView();
			}
		});
		btnNewButton_2.setBounds(1272, 78, 105, 27);
		contentPane.add(btnNewButton_2);

	}
}


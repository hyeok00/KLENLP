/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	SupplierManagement
 * @className 		SupplierRegisterView
 * @author 			이수하
 */	
package SupplierManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import javax.swing.border.TitledBorder;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SupplierRegisterView extends JFrame{

	private JPanel contentPane;
	private JTextField textSupplierName;
	private JTextField textContactNum;
	private JTextField textClosed;
	private JTextField textSupplierCode;
	
	SupplierManager supplierInfo = new SupplierManager();
	private JTextField textProductName;
	private JTextField textCost;

	/**
	 * Launch the application.
	 */
	public void showView() {
		String supplierCode = textSupplierCode.getText();
		String supplierName = textSupplierName.getText();
		String contactNum = textContactNum.getText();
		String closed = textClosed.getText();
		String productClassify = textProductName.getText();
		String costToString = textCost.getText();
		int cost = Integer.parseInt(costToString);
		
		Supplier s = new Supplier(supplierCode, supplierName, contactNum, closed, productClassify, cost);
		supplierInfo.registerSupplier(s);
		
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupplierRegisterView frame = new SupplierRegisterView();
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
	//설계엔 있지만 구현x
	public void RequestRegisterSupplierInfo(Supplier supplier) {
		
	}
	public SupplierRegisterView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\uAC70\uB798\uCC98 \uB4F1\uB85D");
		label.setFont(new Font("굴림", Font.PLAIN, 40));
		label.setBounds(647, 31, 225, 50);
		contentPane.add(label);
		
		Panel panel = new Panel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(383, 110, 729, 523);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label_1 = new JLabel("\uAC70\uB798\uCC98 \uBA85");
		label_1.setFont(new Font("굴림", Font.BOLD, 20));
		label_1.setBounds(151, 159, 134, 24);
		panel.add(label_1);
		
		JLabel lblNewLabel = new JLabel("\uC5F0\uB77D\uCC98");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel.setBounds(151, 214, 93, 18);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("\uD734\uBB34\uC77C");
		lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel_2.setBounds(151, 265, 93, 18);
		panel.add(lblNewLabel_2);
		
		textSupplierName = new JTextField();
		textSupplierName.setBounds(292, 159, 280, 24);
		panel.add(textSupplierName);
		textSupplierName.setColumns(10);
		
		textContactNum = new JTextField();
		textContactNum.setBounds(292, 211, 280, 24);
		panel.add(textContactNum);
		textContactNum.setColumns(10);
		
		textClosed = new JTextField();
		textClosed.setBounds(292, 262, 280, 24);
		panel.add(textClosed);
		textClosed.setColumns(10);
		
		JLabel label_3 = new JLabel("\uAC70\uB798\uCC98 \uAE30\uBCF8 \uC815\uBCF4");
		label_3.setFont(new Font("굴림", Font.BOLD, 20));
		label_3.setBounds(291, 48, 163, 18);
		panel.add(label_3);
		
		JLabel lblNewLabel_1 = new JLabel("\uAC70\uB798\uCC98 \uCF54\uB4DC");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel_1.setBounds(151, 116, 113, 18);
		panel.add(lblNewLabel_1);
		
		textSupplierCode = new JTextField();
		textSupplierCode.setBounds(292, 115, 280, 24);
		panel.add(textSupplierCode);
		textSupplierCode.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("\uC0C1\uD488 \uBA85");
		lblNewLabel_3.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel_3.setBounds(151, 312, 101, 24);
		panel.add(lblNewLabel_3);
		
		textProductName = new JTextField();
		textProductName.setBounds(292, 314, 280, 24);
		panel.add(textProductName);
		textProductName.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("\uC0C1\uD488 \uAC00\uACA9");
		lblNewLabel_4.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel_4.setBounds(151, 358, 113, 18);
		panel.add(lblNewLabel_4);
		
		textCost = new JTextField();
		textCost.setBounds(292, 364, 280, 24);
		panel.add(textCost);
		textCost.setColumns(10);
		
		JButton button = new JButton("\uC800\uC7A5");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showView();
				if (arg0.getSource().equals(button))
				{
					JOptionPane.showMessageDialog(contentPane, "등록이 완료되었습니다.");
				}
			}
		});
		button.setBounds(1318, 681, 139, 60);
		contentPane.add(button);
		
		JButton btnNewButton = new JButton("\uC774\uC804\uD654\uBA74");
		btnNewButton.setBounds(22, 681, 139, 60);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
	}
}


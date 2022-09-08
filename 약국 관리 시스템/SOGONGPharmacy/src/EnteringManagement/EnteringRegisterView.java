/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	EnteringManagement
 * @className 		EnteringRegisterView
 * @author 			강정환
 */	


package EnteringManagement;

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

public class EnteringRegisterView extends JFrame{

	private JPanel contentPane;
	private JTextField textEnteringQuantity;
	private JTextField textEnteringDate;
	private JTextField textClosed;
	private JTextField textSupplierCode;
	private JTextField textSupplierName;
	private JTextField textExpirationDate;
	
	
	EnteringManager enteringInfo = new EnteringManager();
	private JTextField textProductName;

	/**
	 * Launch the application.
	 */
	public void showView() {
		
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnteringRegisterView frame = new EnteringRegisterView();
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
	public void RequestRegisterEnteringInfo(Entering entering) {
		EnteringManager em = new EnteringManager();
		em.registerEntering(entering);
	}
	public EnteringRegisterView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("입고 등록");
		label.setFont(new Font("굴림", Font.PLAIN, 40));
		label.setBounds(647, 31, 225, 50);
		contentPane.add(label);
		
		Panel panel = new Panel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(383, 110, 729, 523);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label_1 = new JLabel("입고 수량");
		label_1.setFont(new Font("굴림", Font.BOLD, 20));
		label_1.setBounds(151, 159, 134, 24);
		panel.add(label_1);
		
		JLabel lblNewLabel = new JLabel("입고일");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel.setBounds(151, 214, 93, 18);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("거래처 명");
		lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel_2.setBounds(151, 265, 93, 18);
		panel.add(lblNewLabel_2);
		
		textEnteringQuantity = new JTextField();
		textEnteringQuantity.setBounds(292, 159, 280, 24);
		panel.add(textEnteringQuantity);
		textEnteringQuantity.setColumns(10);
		
		textEnteringDate = new JTextField();
		textEnteringDate.setBounds(292, 211, 280, 24);
		panel.add(textEnteringDate);
		textEnteringDate.setColumns(10);
		
		textSupplierName = new JTextField();
		textSupplierName.setBounds(292, 262, 280, 24);
		panel.add(textSupplierName);
		textSupplierName.setColumns(10);
		
		JLabel label_3 = new JLabel("입고 기본 정보");
		label_3.setFont(new Font("굴림", Font.BOLD, 20));
		label_3.setBounds(291, 48, 163, 18);
		panel.add(label_3);
		
		JLabel lblNewLabel_1 = new JLabel("상품명");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel_1.setBounds(151, 116, 113, 18);
		panel.add(lblNewLabel_1);
		
		textProductName = new JTextField();
		textProductName.setBounds(292, 115, 280, 24);
		panel.add(textProductName);
		textProductName.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("유통 기한");
		lblNewLabel_3.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel_3.setBounds(151, 312, 101, 24);
		panel.add(lblNewLabel_3);
		
		textExpirationDate = new JTextField();
		textExpirationDate.setBounds(292, 314, 280, 24);
		panel.add(textExpirationDate);
		textExpirationDate.setColumns(10);
		
		JButton btnNewButton = new JButton("취소");
		btnNewButton.setBounds(22, 681, 139, 60);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		
		JButton button = new JButton("저장");
		button.setBounds(1322, 669, 139, 60);
		contentPane.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				String productName = textProductName.getText();
				String quantityToString = textEnteringQuantity.getText();
				int quantity = Integer.parseInt(quantityToString);
				String enteringDate = textEnteringDate.getText();
				String supplierCode = textSupplierName.getText();
				String expirationDate = textExpirationDate.getText();
				
				Entering e = new Entering(productName, quantity, enteringDate, supplierCode, expirationDate);
				enteringInfo.registerEntering(e);
				
				textProductName.setText("");
				textEnteringQuantity.setText("");
				textEnteringDate.setText("");
				textSupplierName.setText("");
				textExpirationDate.setText("");
				
				JOptionPane.showMessageDialog(null, "등록이 완료되었습니다.");
			

			}
		});
	}
}

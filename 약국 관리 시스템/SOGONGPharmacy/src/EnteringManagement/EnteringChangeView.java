/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	EnteringManagement
 * @className 		EnteringChangeView
 * @author 			강정환
 */	


package EnteringManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;

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


public class EnteringChangeView extends JFrame {

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
					EnteringChangeView frame = new EnteringChangeView();
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
	public EnteringChangeView() {
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("입고 수정");
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
		
		JButton button = new JButton("수정");
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
				
				
				int flag = sm.changeEntering(entering, 1, 0);
				
				
				
				if(flag == 1) {
					new newWindow(productName, enteringCode, stringEnteringQuantity);
					
				}
				
				else {
					JOptionPane.showMessageDialog(null, "판매 내역이 있는 상품은 수정이 불가능합니다!");
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



// 입고 정보를 수정하기 위한 새창 출력
class newWindow extends JFrame {
	
	private JTextField textEnteringQuantity;
	private JTextField textEnteringDate;
	private JTextField textClosed;
	private JTextField textSupplierCode;
	private JTextField textSupplierName;
	private JTextField textExpirationDate;
	
	
	EnteringManager enteringInfo = new EnteringManager();
	
    // 버튼이 눌러지면 만들어지는 새 창을 정의한 클래스
    newWindow(String p_name, String e_code, int ori_quantity) {
        setTitle("새로 띄운 창");
        // 주의, 여기서 setDefaultCloseOperation() 정의를 하지 말아야 한다
        // 정의하게 되면 새 창을 닫으면 모든 창과 프로그램이 동시에 꺼진다
        
        JPanel NewWindowContainer = new JPanel();
        setContentPane(NewWindowContainer);
        
      
        Panel panel = new Panel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(383, 110, 729, 523);
		NewWindowContainer.add(panel);
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
		


		
		JLabel lblNewLabel_3 = new JLabel("유통 기한");
		lblNewLabel_3.setFont(new Font("굴림", Font.BOLD, 20));
		lblNewLabel_3.setBounds(151, 312, 101, 24);
		panel.add(lblNewLabel_3);
		
		textExpirationDate = new JTextField();
		textExpirationDate.setBounds(292, 314, 280, 24);
		panel.add(textExpirationDate);
		textExpirationDate.setColumns(10);
		
		JButton btnNewButton = new JButton("수정");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	
				String productName = p_name;
				String quantityToString = textEnteringQuantity.getText();
				int quantity = Integer.parseInt(quantityToString);
				String enteringDate = textEnteringDate.getText();
				String supplierCode = textSupplierName.getText();
				String expirationDate = textExpirationDate.getText();
				
				Entering e = new Entering(productName, quantity, enteringDate, supplierCode, expirationDate);
				e.setEnteringCode(e_code);
				enteringInfo.changeEntering(e, 2, ori_quantity);
				
				textEnteringQuantity.setText("");
				textEnteringDate.setText("");
				textSupplierName.setText("");
				textExpirationDate.setText("");
				
				JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
			}
		});
		
		
		btnNewButton.setBounds(536, 600, 139, 60);
		NewWindowContainer.add(btnNewButton);
		
		
        
        setSize(800,600);
        setResizable(false);
        setVisible(true);
    }
}






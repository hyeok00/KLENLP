/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	Product Management System
 * @className 		ProductRegisterView
 * @author 			류기혁
 */
package ProductManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

public class ProductRegisterView extends JFrame {

	private JPanel contentPane;
	private JTextField productName;
	private JTextField parStock;
	private JTextField productPrice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductRegisterView frame = new ProductRegisterView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void registerProductinfo(String name, int price, int parStock) {
		ProductManager rm = new ProductManager();
		rm.registerProduct(name, price, parStock);
	}

	/**
	 * Create the frame.
	 */
	public ProductRegisterView() {
		setTitle("\uC0C1\uD488\uB4F1\uB85D");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel mainTitle = new JLabel("상품 등록");
		mainTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mainTitle.setBounds(650, 50, 200, 50);
		mainTitle.setFont(new Font("굴림", Font.PLAIN, 40));
		contentPane.add(mainTitle);

		JLabel lblNewLabel = new JLabel("\uC0C1\uD488\uBA85");
		lblNewLabel.setBounds(517, 197, 100, 30);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("가격");
		lblNewLabel_1.setBounds(517, 269, 100, 30);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("적정 재고");
		lblNewLabel_2.setBounds(517, 337, 100, 30);
		contentPane.add(lblNewLabel_2);

		productName = new JTextField();
		productName.setBounds(761, 198, 300, 30);
		contentPane.add(productName);
		productName.setColumns(10);

		parStock = new JTextField();
		parStock.setBounds(761, 338, 300, 30);
		contentPane.add(parStock);
		parStock.setColumns(10);

		productPrice = new JTextField();
		productPrice.setBounds(761, 270, 300, 30);
		contentPane.add(productPrice);
		productPrice.setColumns(10);

		JButton button = new JButton("\uC774\uC804\uD654\uBA74");
		button.setBounds(826, 529, 200, 30);
		contentPane.add(button);

		JButton registerBtn = new JButton("등록");
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(productName.getText().equals("") || productPrice.getText().equals("") || parStock.getText().equals(""))
					JOptionPane.showMessageDialog(null, "항목을 모두 입력하세요");
				else
					registerProductinfo(productName.getText(),Integer.parseInt(productPrice.getText()),Integer.parseInt(parStock.getText()));
			}
		});

		registerBtn.setBounds(552, 529, 200, 30);
		contentPane.add(registerBtn);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});

	}
}

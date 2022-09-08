/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	Product Management System
 * @className 		ProductChangeView
 * @author 			류기혁
 */
package ProductManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ProductChangeView extends JFrame {
	private ArrayList<Product> list;
	private JPanel contentPane;
	private JTextField productName;
	private JTextField productPrice;
	private JTextField parStock;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductChangeView frame = new ProductChangeView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 상단 상품정보 표시
	public void requestReadProductList() {
		ProductManager rm = new ProductManager();
		list = rm.readProduct();
	}

	public void showProductView(String str) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setNumRows(0);

		if (list.size() == 0) {
			JOptionPane.showMessageDialog(null, " 검색결과가 없습니다.");
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (!list.get(i).getProductName().equals(str)) {
					continue;
				} else {
					Product pr = list.get(i);
					String price = String.valueOf(pr.getSalePrice());
					String stock = String.valueOf(pr.getStockQuantity());
					String optimum = String.valueOf(pr.getOptimumStock());

					model.addRow(new String[] { pr.getProductName(), price, stock, optimum });
				}
			}
			model.fireTableDataChanged();
		}
	}

	public void requestStockQuantityUpate(String name, int price, int stock) {
		ProductManager rm = new ProductManager();
		rm.changeProduct(name, price, stock);
	}

	/**
	 * Create the frame.
	 */
	public ProductChangeView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("상품정보 수정");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(650, 50, 300, 50);
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 40));
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(576, 267, 452, 44);
		contentPane.add(scrollPane);

		String header[] = {};
		String contents[][] = { {},

		};

		table = new JTable(contents, header);
		table.setModel(
				new DefaultTableModel(new Object[][] { { "", "", "", "" }, }, new String[] { "\uC0C1\uD488\uBA85",
						"\uD310\uB9E4\uAC00\uACA9", "\uC7AC\uACE0 \uC218\uB7C9", "\uC801\uC815 \uC7AC\uACE0 \uC218" }));

		scrollPane.setViewportView(table);

		JLabel lblNewLabel_1 = new JLabel("선택한 상품 정보");
		lblNewLabel_1.setBounds(392, 274, 100, 30);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("상품명");
		lblNewLabel_2.setBounds(538, 387, 100, 30);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("상품 가격");
		lblNewLabel_3.setBounds(538, 472, 100, 30);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("적정 재고 수");
		lblNewLabel_4.setBounds(538, 566, 100, 30);
		contentPane.add(lblNewLabel_4);

		productName = new JTextField();
		productName.setBounds(722, 388, 300, 30);
		contentPane.add(productName);
		productName.setColumns(10);

		productPrice = new JTextField();
		productPrice.setBounds(722, 473, 300, 30);
		contentPane.add(productPrice);
		productPrice.setColumns(10);

		parStock = new JTextField();
		parStock.setBounds(722, 567, 300, 30);
		contentPane.add(parStock);
		parStock.setColumns(10);

		JButton deleteBtn = new JButton("삭제");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!productName.getText().equals("")) {
					ProductManager rm = new ProductManager();
					rm.deleteProduct(productName.getText());
				} else {
					JOptionPane.showMessageDialog(null, "상품명을 입력하세요");
				}
			}
		});
		deleteBtn.setBounds(674, 669, 97, 23);
		contentPane.add(deleteBtn);

		JButton changeBtn = new JButton("수정");
		changeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (productName.getText().equals(""))
					JOptionPane.showMessageDialog(null, "상품명을 입력하세요");
				else if (productPrice.getText().equals("") || parStock.getText().equals(""))
					JOptionPane.showMessageDialog(null, "가격과 재고량을 입력하세요");
				else {
					requestStockQuantityUpate(productName.getText(), Integer.parseInt(productPrice.getText()),
							Integer.parseInt(parStock.getText()));
					requestReadProductList();
					showProductView(productName.getText());
				}
			}
		});
		changeBtn.setBounds(803, 669, 97, 23);
		contentPane.add(changeBtn);

		JButton btn = new JButton("\uC774\uC804\uD654\uBA74");
		btn.setBounds(931, 669, 97, 23);
		contentPane.add(btn);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(getContentPane().getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});

		JButton searchBtn = new JButton("검색");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!productName.getText().equals("")) {
					requestReadProductList();
					showProductView(productName.getText());
				} else {
					JOptionPane.showMessageDialog(null, " 상품명을 입력하세요.");
				}
			}
		});
		searchBtn.setBounds(538, 669, 97, 23);
		contentPane.add(searchBtn);
	}
}

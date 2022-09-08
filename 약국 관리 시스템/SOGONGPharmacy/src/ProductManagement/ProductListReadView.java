/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	Product Management System
 * @className 		ProductListReadView
 * @author 			류기혁
 */
package ProductManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;

public class ProductListReadView extends JFrame {
	JTabbedPane t = new JTabbedPane(); // JTabbedPane생성
	private ArrayList<Product> list;
	private ArrayList<String> stocklist;

	JPanel p1 = new JPanel(); // JPanel 생성
	JPanel p2 = new JPanel();
	private final JTextField textField = new JTextField();
	private final JButton button = new JButton("조회");
	private JTable table;
	private JTable table2;

	public void requestProductListInfo() {
		ProductManager rm = new ProductManager();
		list = rm.readProduct();
	}

	public void requestStockListInfo(String s) {
		ProductManager rm = new ProductManager();
		stocklist = rm.readStock(s);
	}

	// 상품조회
	public void showProductView() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setNumRows(0);

		if (list.size() == 0) {
			JOptionPane.showMessageDialog(null, " 검색결과가 없습니다.");
		} else {
			for (int i = 0; i < list.size(); i++) {
				Product pr = list.get(i);
				String price = String.valueOf(pr.getSalePrice());
				String stock = String.valueOf(pr.getStockQuantity());
				String optimum = String.valueOf(pr.getOptimumStock());

				model.addRow(new String[] { pr.getProductName(), price, stock, optimum });
			}
			model.fireTableDataChanged();
			textField.setText("");
		}
	}

	// 재고 조회
	public void showStockView() {
		DefaultTableModel model = (DefaultTableModel) table2.getModel();
		model.setNumRows(0);

		if (stocklist.size() == 0) {
			JOptionPane.showMessageDialog(null, " 검색결과가 없습니다.");
		} else {
			for (int i = 0; i < stocklist.size(); i++) {
				String temp = stocklist.get(i);
				StringTokenizer st = new StringTokenizer(temp, ",");

				while (st.hasMoreTokens()) {
					String code = st.nextToken();
					String name = st.nextToken();
					String date = st.nextToken();
					String quantity = st.nextToken();
					model.addRow(new String[] { code, name, date, quantity });
				}
			}
			model.fireTableDataChanged();
		}
	}

	public ProductListReadView() { // 생성자
		super("TabTitleTextPosition");

		setTitle("상품 및 재고 조회");
		getContentPane().setLayout(null);
		t.setBounds(0, 0, 1500, 800);

		getContentPane().add(t);

		String header[] = {};
		String contents[][] = { {},

		};

		String header2[] = {};
		String contents2[][] = { {},

		};
		textField.setBounds(335, 147, 300, 30);
		textField.setColumns(10);

		t.add("재고 조회", p2); // JTabbedPane에 탭추가
		p2.setLayout(null);

		p2.add(textField);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField.getText().equals("")) {
					requestStockListInfo(textField.getText());
					showStockView();
				}
				else
					JOptionPane.showMessageDialog(null, "상품명을 입력하세요");
			}
		});
		button.setBounds(805, 146, 200, 30);

		p2.add(button);

		JLabel label2 = new JLabel("재고 조회");
		label2.setBounds(650, 50, 200, 50);
		p2.add(label2);
		// ------------------------------
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(335, 243, 670, 315);
		p2.add(scrollPane2);

		table2 = new JTable(contents2, header2);
		table2.setModel(new DefaultTableModel(
			new Object[][] {
				{"", "", "", ""},
				{"", "", "", ""},
				{"", "", "", ""},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"\uC0C1\uD488\uCF54\uB4DC", "\uC0C1\uD488\uBA85", "\uC720\uD6A8\uAE30\uAC04", "\uC218\uB7C9"
			}
		));
		table2.getColumnModel().getColumn(0).setPreferredWidth(176);
		table2.getColumnModel().getColumn(1).setPreferredWidth(212);
		table2.getColumnModel().getColumn(2).setPreferredWidth(194);
		table2.getColumnModel().getColumn(3).setPreferredWidth(102);

		scrollPane2.setViewportView(table2);

		p2.add(scrollPane2);

		JButton btnNewButton_2 = new JButton("\uC774\uC804\uD654\uBA74");
		btnNewButton_2.setBounds(607, 647, 97, 23);
		p2.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(getContentPane().getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		p1.setLayout(null);
		// Component javax.swing.JTabbedPane.add(String title, Component component)

		t.add("상품 조회", p1);

		// --------------------------------------------
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(367, 196, 670, 315);
		p1.add(scrollPane);

		table = new JTable(contents, header);
		table.setModel(new DefaultTableModel(
				new Object[][] { { "", "", "", "" }, { "", "", "", "" }, { "", "", "", "50" },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, },
				new String[] { "\uC0C1\uD488\uBA85", "\uAC00\uACA9", "\uC218\uB7C9", "\uC801\uC815\uC7AC\uACE0" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(176);
		table.getColumnModel().getColumn(1).setPreferredWidth(212);
		table.getColumnModel().getColumn(2).setPreferredWidth(194);
		table.getColumnModel().getColumn(3).setPreferredWidth(102);

		scrollPane.setViewportView(table);

		p1.add(scrollPane);

		JLabel label = new JLabel("상품조회");
		label.setBounds(650, 50, 200, 50);
		p1.add(label);

		JButton btnNewButton = new JButton("\uC870\uD68C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				requestProductListInfo();
				showProductView();
			}
		});
		btnNewButton.setBounds(511, 607, 97, 23);
		p1.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("\uC774\uC804\uD654\uBA74");
		btnNewButton_1.setBounds(819, 607, 97, 23);
		p1.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(getContentPane().getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});

		// frame.pack();
		setSize(1500, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}// 생성자 끝

	public static void main(String[] args) {
		ProductListReadView aListReadView = new ProductListReadView();
		aListReadView.setVisible(true);

	}// 메인메소드 끝
}

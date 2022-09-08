/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	RecordManagement
 * @className 			ProductRecordView
 * @author 					최정봉
 */
package RecordManagement;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ProductRecordView extends JDialog {
	private ArrayList<Record> list;
	
	private final JPanel ProductRecordView = new JPanel();
	private JTextField searchBox;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public void requestProductRecordInfo(String productName) {
		RecordManager rm = new RecordManager();
		list = rm.readProductRecord(productName);
	}
	
	public void showView() {
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setNumRows(0);
		
		if(list.size()==0) {
			JOptionPane.showMessageDialog(null, "\"" + searchBox.getText() + "\"" + " 을(를) 포함하는 검색결과가 없습니다.");
		}
		else {
			for(int i=0;i<list.size();i++) {
				Record pr = list.get(i);
				String quan = String.valueOf(pr.getquantity());
				model.addRow(new String[] {pr.getCode(),pr.getProductName(),pr.getdate(),quan});
			}
			model.fireTableDataChanged();
			searchBox.setText("");
		}
	}
	
	public static void main(String[] args) {
		try {
			ProductRecordView dialog = new ProductRecordView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ProductRecordView() {
		setTitle("상품 이력 조회");
		setBounds(100, 100, 1500, 800);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(ProductRecordView);
		ProductRecordView.setLayout(null);
		
		searchBox = new JTextField();
		searchBox.setToolTipText("\uC0C1\uD488\uBA85\uC744 \uC785\uB825\uD558\uC138\uC694");
		searchBox.setBounds(968, 113, 139, 21);
		ProductRecordView.add(searchBox);
		searchBox.setColumns(10);
		
		JLabel titleLb = new JLabel("상품이력 조회");
	    titleLb.setHorizontalAlignment(SwingConstants.CENTER);
	    titleLb.setBounds(550, 50, 400, 50);
	    titleLb.setFont(new Font("굴림", Font.PLAIN, 40));
	    ProductRecordView.add(titleLb);
	    
		JButton searchButton = new JButton("검색");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				requestProductRecordInfo(searchBox.getText());
				showView();
			}
		});
		searchButton.setBounds(1128, 112, 72, 23);
		ProductRecordView.add(searchButton);
		
		
		JLabel lblNewLabel = new JLabel("상품명");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(894, 116, 57, 15);
		ProductRecordView.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(100, 150, 1100, 500);
		ProductRecordView.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		table = new JTable();
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"\uC0C1\uD488\uBC88\uD638", "\uC0C1\uD488\uBA85", "\uC720\uD1B5 \uAE30\uAC04", "\uD604\uC7AC \uC7AC\uACE0 \uC218"
			}
		));
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JButton backBtn = new JButton("이전화면");
		backBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		backBtn.setBounds(1300, 679, 150, 50);
		ProductRecordView.add(backBtn);
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(ProductRecordView.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
	}
}

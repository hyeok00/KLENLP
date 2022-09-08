/**
 * @systemName			SOGONG Pharmacy Management System
 * @subSystemName	DashBoardManagement
 * @className 			StatisticsView
 * @author 					최의준
 */	
package DashBoardManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class StatisticsView extends JFrame {
	ArrayList<SalesData> list = new ArrayList<SalesData>();
	
	private JPanel contentPane;
	private JTextField setTermStart;
	private JTextField setTermLast;
	private JTable table;
	private JTable table_1;
	
	private DashBoardManager dbm;
	private String startDate;
	private String lastDate;
	
	/**
	 * Launch the application.
	 */
	
	public void showView(String startDate, String lastDate) {
		ArrayList<SalesData> viewList = new ArrayList<SalesData>();
		
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setNumRows(0);
		DefaultTableModel model_1 = (DefaultTableModel)table_1.getModel();
		model_1.setNumRows(0);
			
		if(list.size() == 0) {
			JOptionPane.showMessageDialog(null, "통계 자료가 없습니다.");
		}
		else {
			int i = 0;
			
			while (list.get(i).getProductName() == null) {
				model.addRow(new String[] {list.get(i).getSalesDate(), Integer.toString(list.get(i).getSales())});
				i++;
			}
			model.fireTableDataChanged();
			
			int j = i;
			SalesData sd = new SalesData();
			sd.setSalesDate(null);
			sd.setSales(0);
			sd.setProductName(list.get(j).getProductName());
			sd.setSalesQuantity(0);
			sd.setSalesProportion(0);
			viewList.add(sd);
			while (j < list.size()) {
				boolean dup = false;
				for (int k = 0; k < viewList.size(); k++) {
					if (viewList.get(k).getProductName().equals(list.get(j).getProductName()) == false) {
						for (int l = 0; l < viewList.size(); l++) { 
							if (viewList.get(l).getProductName().equals(list.get(j).getProductName())) {
								dup = true;
								break;
							}
						}
						if (dup == false) {
							sd = new SalesData();
							sd.setSalesDate(null);
							sd.setSales(0);
							sd.setProductName(list.get(j).getProductName());
							sd.setSalesQuantity(0);
							sd.setSalesProportion(0);
							viewList.add(sd);
							break;
						}
						dup = false;
					}
				}
				j++;
			}
			
			
			for (int k = 0; k < viewList.size(); k++) {
				j = i;
				while (j < list.size()) {
					if (viewList.get(k).getProductName().equals(list.get(j).getProductName())) {
						viewList.get(k).setSalesQuantity(viewList.get(k).getSalesQuantity() + list.get(j).getSalesQuantity());
						viewList.get(k).setSalesProportion(viewList.get(k).getSalesProportion() + list.get(j).getSalesProportion());
					}
					j++;
				}
				model_1.addRow(new String[] {viewList.get(k).getProductName(), Integer.toString(viewList.get(k).getSalesQuantity()), Double.toString(viewList.get(k).getSalesProportion()) + "%"});
			}
			model_1.fireTableDataChanged();
		}
	}
	public void requestData() {
		dbm = new DashBoardManager();
		list = dbm.SalesDataOutput(startDate, lastDate);
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StatisticsView frame = new StatisticsView();
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
	public StatisticsView() {
		setTitle("\uD1B5\uACC4 \uC790\uB8CC \uC870\uD68C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uAE30\uAC04 \uC785\uB825");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 30));
		lblNewLabel.setBounds(223, 77, 146, 44);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("\uC870\uD68C");
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 30));
		btnNewButton.setBounds(1134, 677, 146, 61);
		contentPane.add(btnNewButton);
		
		setTermStart = new JTextField();
		setTermStart.setBounds(223, 131, 473, 33);
		contentPane.add(setTermStart);
		setTermStart.setColumns(10);
		
		setTermLast = new JTextField();
		setTermLast.setBounds(795, 131, 485, 33);
		contentPane.add(setTermLast);
		setTermLast.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("~");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 40));
		lblNewLabel_1.setBounds(731, 131, 52, 33);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("이전화면");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 30));
		btnNewButton_1.setBounds(223, 677, 161, 61);
		contentPane.add(btnNewButton_1);
		
		table = new JTable();
		table.setEnabled(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"\uB0A0\uC9DC", "\uB9E4\uCD9C"},
			},
			new String[] {
				"\uB0A0\uC9DC", "\uB9E4\uCD9C"
			}
		));
		DefaultTableModel m = (DefaultTableModel)table.getModel();
		table.setBounds(223, 185, 473, 459);
		contentPane.add(table);
		
		table_1 = new JTable();
		table_1.setEnabled(false);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"\uC0C1\uD488\uBA85", "\uD310\uB9E4\uB7C9", "\uD310\uB9E4\uC728"},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		DefaultTableModel m1 = (DefaultTableModel)table_1.getModel();
		table_1.setBounds(795, 185, 485, 459);
		contentPane.add(table_1);
		
		JLabel lblNewLabel_2 = new JLabel("\uD1B5\uACC4 \uC790\uB8CC \uC870\uD68C");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 42));
		lblNewLabel_2.setBounds(607, 25, 287, 67);
		contentPane.add(lblNewLabel_2);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startDate = setTermStart.getText();
				lastDate = setTermLast.getText();
				
				requestData();
				showView(startDate, lastDate);
			}
		});
	}
}

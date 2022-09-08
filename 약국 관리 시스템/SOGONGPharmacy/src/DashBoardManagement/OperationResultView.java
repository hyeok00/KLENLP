/**
 * @systemName			SOGONG Pharmacy Management System
 * @subSystemName	DashBoardManagement
 * @className 			OperationResultView
 * @author 					최의준
 */	
package DashBoardManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import SystemManagement.MainMenuView;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OperationResultView extends JFrame {

	private JPanel contentPane;
	private JTextField txtSetTermStart;
	private JTextField txtSetTermLast;
	private JTextField txtProfitAll;
	private JTextField txtExpenseAll;
	private JTextField txtMarginAll;
	
	private DashBoardManager dbm;
	private String startDate;
	private String lastDate;

	/**
	 * Launch the application.
	 */
	
	void showView(String startDate, String lastDate) {
		txtProfitAll.setText(Integer.toString(dbm.getSales(startDate, lastDate)));
		txtExpenseAll.setText(Integer.toString(dbm.getExpense(startDate, lastDate)));
		txtMarginAll.setText(Integer.toString(dbm.getMargin(startDate, lastDate)));
	}
	void requestData() {
		dbm = new DashBoardManager();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OperationResultView frame = new OperationResultView();
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
	public OperationResultView() {
		setTitle("\uC6B4\uC601 \uC2E4\uC801 \uC870\uD68C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uAE30\uAC04 \uC785\uB825");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 28));
		lblNewLabel.setBounds(229, 195, 171, 55);
		contentPane.add(lblNewLabel);
		
		txtSetTermStart = new JTextField();
		txtSetTermStart.setBounds(229, 260, 395, 37);
		contentPane.add(txtSetTermStart);
		txtSetTermStart.setColumns(10);
		
		txtSetTermLast = new JTextField();
		txtSetTermLast.setBounds(727, 260, 395, 37);
		contentPane.add(txtSetTermLast);
		txtSetTermLast.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("~");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 45));
		lblNewLabel_1.setBounds(656, 242, 78, 76);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblProfitAll = new JLabel("\uCD1D\uC218\uC775");
		lblProfitAll.setFont(new Font("굴림", Font.PLAIN, 35));
		lblProfitAll.setBounds(221, 356, 143, 98);
		contentPane.add(lblProfitAll);
		
		JLabel lblExpenseAll = new JLabel("\uC9C0\uCD9C");
		lblExpenseAll.setFont(new Font("굴림", Font.PLAIN, 35));
		lblExpenseAll.setBounds(221, 479, 143, 98);
		contentPane.add(lblExpenseAll);
		
		JLabel lblMarginAll = new JLabel("\uC2E4\uC218\uC775");
		lblMarginAll.setFont(new Font("굴림", Font.PLAIN, 35));
		lblMarginAll.setBounds(221, 598, 143, 98);
		contentPane.add(lblMarginAll);
		
		txtProfitAll = new JTextField();
		txtProfitAll.setEditable(false);
		txtProfitAll.setBounds(395, 386, 727, 55);
		contentPane.add(txtProfitAll);
		txtProfitAll.setColumns(10);
		
		txtExpenseAll = new JTextField();
		txtExpenseAll.setEditable(false);
		txtExpenseAll.setBounds(395, 512, 727, 50);
		contentPane.add(txtExpenseAll);
		txtExpenseAll.setColumns(10);
		
		txtMarginAll = new JTextField();
		txtMarginAll.setEditable(false);
		txtMarginAll.setBounds(395, 628, 727, 55);
		contentPane.add(txtMarginAll);
		txtMarginAll.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("이전화면");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		btnNewButton_1.setFont(new Font("굴림", Font.PLAIN, 30));
		btnNewButton_1.setBounds(1221, 619, 161, 61);
		contentPane.add(btnNewButton_1);
		
		JButton btnView = new JButton("\uC870\uD68C");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startDate = txtSetTermStart.getText();
				lastDate = txtSetTermLast.getText();
				
				requestData();
				showView(startDate, lastDate);
			}
		});
		btnView.setFont(new Font("굴림", Font.PLAIN, 40));
		btnView.setBounds(1222, 477, 188, 98);
		contentPane.add(btnView);
		
		JLabel lblNewLabel_2 = new JLabel("\uC6B4\uC601 \uC2E4\uC801 \uC870\uD68C");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 40));
		lblNewLabel_2.setBounds(381, 37, 364, 76);
		contentPane.add(lblNewLabel_2);
	}
}
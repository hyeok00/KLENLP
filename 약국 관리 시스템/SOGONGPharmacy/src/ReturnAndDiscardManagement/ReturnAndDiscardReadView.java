package ReturnAndDiscardManagement;
/**
 * @systemName		SOGONG Pharmacy Management
 * @subSystemName	ReturnAndDiscard Management
 * @className 		ReturnAndDiscardReadView
 * @author 			이성애
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;

public class ReturnAndDiscardReadView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JLabel label;
	ReturnAndDiscardManager rndm=new ReturnAndDiscardManager();
	/**
	 * Launch the application.
	 */

	public Object[][] requestRead(int code) {
		return rndm.read(code);
	}
	public static void showRead() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReturnAndDiscardReadView frame = new ReturnAndDiscardReadView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void main(String[] args) {
		showRead();
	}

	/**
	 * Create the frame.
	 */
	public ReturnAndDiscardReadView() {
		super("반품 및 폐기 내역");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setBounds(49, 109, 1411, 623);
		contentPane.add(scrollPane);
		
		
		Object[][] obPdList=requestRead(3);
		String[] title= {"No.","상품 코드","상품명","유통기한","수량","처리 사유","처리일","처리 상태"};
		table = new JTable(obPdList,title);

		scrollPane.setViewportView(table);
		table.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		label = new JLabel("\uBC18\uD488 \uBC0F \uD3D0\uAE30 \uB0B4\uC5ED");
		label.setFont(new Font("굴림", Font.BOLD, 40));
		label.setBounds(616, 21, 380, 69);
		contentPane.add(label);
		
		JButton btnNewButton = new JButton("\uC774\uC804");
		btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		btnNewButton.setBounds(49, 32, 127, 48);
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

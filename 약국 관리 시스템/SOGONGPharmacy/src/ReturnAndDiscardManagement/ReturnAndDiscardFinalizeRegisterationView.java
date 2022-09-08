package ReturnAndDiscardManagement;
/**
 * @systemName		SOGONG Pharmacy Management
 * @subSystemName	ReturnAndDiscard Management
 * @className 		ReturnAndDiscardFinalizeRegisterationView
 * @author 			이성애
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReturnAndDiscardFinalizeRegisterationView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	JComboBox years,months,days;
	ReturnAndDiscardManager rndm=new ReturnAndDiscardManager();
	int year,month,day;
	String date;
	/**
	 * Launch the application.
	 */
	
	public static void showFinalizeRegisteration() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReturnAndDiscardFinalizeRegisterationView frame = new ReturnAndDiscardFinalizeRegisterationView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	public void requestFinalizeRegisteration(String productCode, String date) {
		rndm.FinalizeRegisteration(productCode, date);
	}
	public static void main(String[] args) {
		showFinalizeRegisteration();
	}

	/**
	 * Create the frame.
	 */
	//반품 및 폐기 내역 가져와서 테이블에 붙여주기
	public ReturnAndDiscardFinalizeRegisterationView() {
		super("반품 및 폐기 등록 확정");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uBC18\uD488 \uBC0F \uD3D0\uAE30 \uB4F1\uB85D \uD655\uC815");
		lblNewLabel.setBounds(582, 10, 414, 69);
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 40));
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(56, 437, 739, 287);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		textArea.setLineWrap(true);
		textArea.setBackground(Color.WHITE);
		textArea.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		textArea.setColumns(10);
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(56, 89, 1365, 318);
		contentPane.add(scrollPane);
		
		
		Object[][] obPdList=rndm.read(2);
		String[] title= {"No.","상품 코드","상품명","유통기한","수량","처리 사유","처리일","처리 상태"};
		table = new JTable(obPdList,title);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(71);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(59);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(47);
		table.getColumnModel().getColumn(6).setResizable(false);
		table.getColumnModel().getColumn(6).setPreferredWidth(107);
		table.getColumnModel().getColumn(7).setResizable(false);
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=table.getSelectedRow();
				String medicine="";
				for(int i=2;i<table.getColumnCount();i++)
				medicine+=table.getColumnName(i)+": "+table.getValueAt(row, i)+"\n";
				textArea.setText(medicine);
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("\uCC98\uB9AC\uC77C \uC785\uB825");
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblNewLabel_1.setBounds(445, 235, 108, 21);
		contentPane.add(lblNewLabel_1);

		years = new JComboBox();
		years.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				year=years.getSelectedIndex()+2015;
			}
		});
		years.setFont(new Font("굴림", Font.BOLD, 25));
		years.setModel(new DefaultComboBoxModel(new String[] {"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039"}));
		years.setBounds(867, 533, 108, 44);
		contentPane.add(years);
		
		
		months = new JComboBox();
		months.setFont(new Font("굴림", Font.BOLD, 25));
		months.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		months.setBounds(1058, 533, 59, 44);
		contentPane.add(months);
		months.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				month=months.getSelectedIndex()+1;
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("\uB144");
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		lblNewLabel_2.setBounds(987, 533, 88, 44);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\uC6D4");
		lblNewLabel_3.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		lblNewLabel_3.setBounds(1129, 534, 66, 34);
		contentPane.add(lblNewLabel_3);
		
		days = new JComboBox();
		days.setFont(new Font("굴림", Font.BOLD, 25));
		days.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		days.setBounds(1207, 533, 59, 44);
		contentPane.add(days);
		days.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				day=days.getSelectedIndex()+1;
			}
		});
		
		JLabel lblNewLabel_4 = new JLabel("\uC77C");
		lblNewLabel_4.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		lblNewLabel_4.setBounds(1278, 529, 88, 44);
		contentPane.add(lblNewLabel_4);
		JButton btnNewButton = new JButton("\uB4F1\uB85D \uD655\uC815");
		JOptionPane dialog=new JOptionPane();
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date=Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
				if (date.equals("0-0-0")) {
					date="2015-01-01";
				}
				int row=table.getSelectedRow();
				if(row==-1) {
					dialog.showMessageDialog(null,"상품을 선택해주십시오." ,"경고" , JOptionPane.WARNING_MESSAGE);
				}
				requestFinalizeRegisteration((table.getValueAt(row, 1)).toString(), date);
				boolean result;
				result=rndm.getResult();
				if (result==true) {
					dialog.showMessageDialog(null, "반품 및 폐기 등록 확정을 성공하였습니다.");
					dispose();
				}
				else {
					dialog.showMessageDialog(null, "반품 및 폐기 등록 확정을 실패하였습니다.", "실패", JOptionPane.WARNING_MESSAGE);
				}
				dispose();
			}
		});
		btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		btnNewButton.setBounds(914, 634, 161, 60);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\uCDE8\uC18C");
		btnNewButton_1.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		btnNewButton_1.setBounds(1116, 634, 141, 60);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
	}
}

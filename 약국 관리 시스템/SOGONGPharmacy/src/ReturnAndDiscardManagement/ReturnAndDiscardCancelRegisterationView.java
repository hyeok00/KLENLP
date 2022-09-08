package ReturnAndDiscardManagement;
/**
 * @systemName		SOGONG Pharmacy Management
 * @subSystemName	ReturnAndDiscard Management
 * @className 		ReturnAndDiscardCancelRegisterationView
 * @author 			이성애
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReturnAndDiscardCancelRegisterationView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private ReturnAndDiscardManager rndm=new ReturnAndDiscardManager();
	Date date=new Date();
	
	/**
	 * Launch the application.
	 */
	
	public static void showCancelRegisteration() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReturnAndDiscardCancelRegisterationView frame = new ReturnAndDiscardCancelRegisterationView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void requestCancelRegisteration(String productCode,Object quantity) {
		String num=quantity.toString();
		int n=Integer.parseInt(num);
		rndm.cancelRegisteration(productCode,n);
	}
	public static void main(String[] args) {
		showCancelRegisteration();
	}

	/**
	 * Create the frame.
	 */
	//반품 및 폐기 내역에서 가져오기
	@SuppressWarnings("deprecation")
	public ReturnAndDiscardCancelRegisterationView() {
		super("반품 및 폐기 등록 취소");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(71, 110, 1278, 385);
		contentPane.add(scrollPane);
		Object[][] obPdList=rndm.read(2);
		String[] title= {"No.","상품 코드","상품명","유통기한","수량","처리 사유","처리일","처리 상태"};
		table = new JTable(obPdList,title);
		table.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		table.getColumnModel().getColumn(0).setPreferredWidth(31);
		scrollPane.setViewportView(table);
		
		JLabel label = new JLabel("\uBC18\uD488 \uBC0F \uD3D0\uAE30 \uB4F1\uB85D \uCDE8\uC18C");
		label.setFont(new Font("굴림", Font.PLAIN, 40));
		label.setBounds(535, 31, 414, 69);
		contentPane.add(label);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		textArea.setEditable(false);
		textArea.setColumns(10);
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(71, 557, 842, 153);
		contentPane.add(textArea);
		JOptionPane dialog=new JOptionPane();
		
		JButton btnNewButton = new JButton("\uB4F1\uB85D \uCDE8\uC18C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				if(row==-1) {
					dialog.showMessageDialog(null,"상품을 선택해주십시오." ,"경고" , JOptionPane.WARNING_MESSAGE);
				}
				requestCancelRegisteration((table.getValueAt(row, 1).toString()),table.getValueAt(row,4));
				boolean result;
				result=rndm.getResult();
				if (result==true) {
					dialog.showMessageDialog(null, "반품 및 폐기 등록취소를 성공하였습니다.");
					dispose();
				}
				else {
					dialog.showMessageDialog(null, "반품 및 폐기 등록취소를 실패하였습니다.", "실패", JOptionPane.WARNING_MESSAGE);
				}
				dispose();
			}
		});
		btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		btnNewButton.setBounds(1080, 567, 157, 50);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\uCDE8\uC18C");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		btnNewButton_1.setBounds(1104, 656, 114, 54);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		
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
	}
}

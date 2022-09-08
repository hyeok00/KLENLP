package ReturnAndDiscardManagement;

/**
 * @systemName		SOGONG Pharmacy Management
 * @subSystemName	ReturnAndDiscard Management
 * @className 		ReturnAndDiscardRegisterView
 * @author 			이성애
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import SystemManagement.MainMenuView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.TextField;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class ReturnAndDiscardRegisterView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	ReturnAndDiscardManager radm=new ReturnAndDiscardManager();
	boolean result;
	Date date=new Date();
	
	/**
	 * Launch the application.
	 */
	public static void showRegister() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReturnAndDiscardRegisterView frame = new ReturnAndDiscardRegisterView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	public void requestRegister(String productCode, Object quantity, String category, String reason, int state) {
		String num=quantity.toString();
		int n=Integer.parseInt(num);
		radm.register(productCode, n,category,reason,state);
	}
	public static void main(String[] args) {
		showRegister();
	}

	/**
	 * Create the frame.
	 */
	
	public ReturnAndDiscardRegisterView() {
		super("반품 및 폐기 등록");

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		JRadioButton returnButton = new JRadioButton("\uBC18\uD488");
		JRadioButton discardButton = new JRadioButton("\uD3D0\uAE30");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						if(returnButton.isSelected()==true) {
							discardButton.setSelected(false);
			}
		}
		});
		returnButton.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		returnButton.setBounds(1268, 168, 145, 58);
		contentPane.add(returnButton);
		

		discardButton.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		discardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						if(discardButton.isSelected()==true) {
							returnButton.setSelected(false);
			}
		}
		});
		discardButton.setBounds(1268, 295, 145, 49);
		contentPane.add(discardButton);

		JLabel label = new JLabel("\uBC18\uD488 \uBC0F \uD3D0\uAE30 \uB4F1\uB85D");
		label.setFont(new Font("굴림", Font.PLAIN, 40));
		label.setBounds(521, 22, 334, 66);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\uBC18\uD488 \uBC0F \uD3D0\uAE30 \uC0AC\uC720");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_1.setBounds(78, 452, 192, 66);
		contentPane.add(label_1);

		JTextArea reason = new JTextArea();
		reason.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		reason.setWrapStyleWord(true);
		reason.setLineWrap(true);
		reason.setBounds(78, 514, 1109, 202);
		reason.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(reason);
		
		JScrollPane scrollPane = new JScrollPane();
		
		Object[][] obPdList=radm.read(1);
		String[] title= {"No.","상품 코드","상품명","유통기한","수량","거래처"};
		table = new JTable(obPdList,title);
		scrollPane.setViewportView(table);
		table.setBackground(Color.WHITE);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		JButton button_1 = new JButton("\uCDE8\uC18C");
		button_1.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		button_1.setBounds(1242, 645, 121, 49);
		contentPane.add(button_1);
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		scrollPane.setBounds(78, 103, 1109, 339);
		contentPane.add(scrollPane);
		JOptionPane dialog=new JOptionPane();
		JButton button = new JButton("\uD655\uC778");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				if(returnButton.isSelected()==true) {
					String productCode=(table.getValueAt(row, 1)).toString();
					Object quantity=table.getValueAt(row,4);
					requestRegister((table.getValueAt(row, 1)).toString(), table.getValueAt(row,4), "반품", reason.getText(), 0);
				result=radm.getResult();
				if (result==true) {
					dialog.showMessageDialog(null, "반품 및 폐기 등록을 성공하였습니다.");
					dispose();
				}
				else {
					dialog.showMessageDialog(null, "반품 및 폐기 등록에 실패하였습니다.", "실패", JOptionPane.WARNING_MESSAGE);
				}
				}
				else if(row==-1) {
					dialog.showMessageDialog(null,"상품을 선택해주십시오." ,"경고" , JOptionPane.WARNING_MESSAGE);
				}
				else if ((reason.getText()).equals("")) {
					dialog.showMessageDialog(null,"반품 및 폐기 사유를 입력해주십시오." ,"경고" , JOptionPane.WARNING_MESSAGE);
				}
				else if(discardButton.isSelected()==true) {
					result=radm.getResult();
					if (result==true) {
						requestRegister((table.getValueAt(row, 1)).toString(), (int)table.getValueAt(row,4), "폐기", reason.getText(), 0);
						dialog.showMessageDialog(null, "반품 및 폐기 등록을 성공하였습니다.");
						dispose();
					}
					else {
						dialog.showMessageDialog(null, "반품 및 폐기 등록에 실패하였습니다.", "실패", JOptionPane.WARNING_MESSAGE);
					}
				}
				else {
					dialog.showMessageDialog(null,"반품 혹은 폐기를 선택해주세요." ,"경고" , JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		button.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		button.setBounds(1242, 531, 121, 49);
		contentPane.add(button);
		
	}
}

/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	MemberManagement
 * @className 		RegisterMemberView
 * @author 			정윤서
 */
package MemberManagement;

import MemberManagement.MemberManager;
import SystemManagement.MainMenuView;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterMemberView extends JFrame {

	private JPanel contentPane;
	private JTextField txt_memberCode;
	private JTextField txt_memberName;
	private JTextField txt_memberPhone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterMemberView frame = new RegisterMemberView();
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
	public RegisterMemberView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lb_registerMemebr = new JLabel("고객 등록");
		lb_registerMemebr.setFont(new Font("굴림", Font.PLAIN, 40));
		lb_registerMemebr.setBounds(406, 62, 178, 51);
		contentPane.add(lb_registerMemebr);

		JLabel lb_memberCode = new JLabel("고객 코드");
		lb_memberCode.setFont(new Font("굴림", Font.PLAIN, 20));
		lb_memberCode.setBounds(325, 172, 87, 19);
		contentPane.add(lb_memberCode);

		JLabel lb_memberName = new JLabel("고객 이름");
		lb_memberName.setFont(new Font("굴림", Font.PLAIN, 20));
		lb_memberName.setBounds(325, 224, 87, 19);
		contentPane.add(lb_memberName);

		JLabel lb_memberPhone = new JLabel("연락처");
		lb_memberPhone.setFont(new Font("굴림", Font.PLAIN, 20));
		lb_memberPhone.setBounds(325, 277, 75, 19);
		contentPane.add(lb_memberPhone);

		txt_memberCode = new JTextField();
		txt_memberCode.setFont(new Font("굴림", Font.PLAIN, 20));
		txt_memberCode.setBounds(469, 166, 193, 29);
		contentPane.add(txt_memberCode);
		txt_memberCode.setColumns(10);

		txt_memberName = new JTextField();
		txt_memberName.setFont(new Font("굴림", Font.PLAIN, 20));
		txt_memberName.setColumns(10);
		txt_memberName.setBounds(469, 218, 193, 29);
		contentPane.add(txt_memberName);

		txt_memberPhone = new JTextField();
		txt_memberPhone.setFont(new Font("굴림", Font.PLAIN, 20));
		txt_memberPhone.setColumns(10);
		txt_memberPhone.setBounds(469, 274, 193, 29);
		contentPane.add(txt_memberPhone);

		JButton btn_cancel = new JButton("이전 화면");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btn_cancel.setFont(new Font("굴림", Font.PLAIN, 20));
		btn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		btn_cancel.setBounds(325, 327, 135, 29);
		contentPane.add(btn_cancel);

		JButton btn_register = new JButton("등록");
		btn_register.setFont(new Font("굴림", Font.PLAIN, 20));
		btn_register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MemberManager m = new MemberManager();
				String memberCode = txt_memberCode.getText();
				String memberName = txt_memberName.getText();
				String memberPhone = txt_memberPhone.getText();

				if ((memberCode.length() == 0) || (memberName.length() == 0) || (memberPhone.length()==0)) {
					JOptionPane.showMessageDialog(contentPane, "필수 항목 미작성");
				} else if ((memberCode != "") && (memberName != "") && (memberPhone != "")) {
					try {
						m.registerMember(memberCode, memberName, memberPhone);
						// System.out.println(memberCode+","+memberName+","+memberPhone);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btn_register.setBounds(565, 327, 97, 29);
		contentPane.add(btn_register);
	}
}

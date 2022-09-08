/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	MemberManagement
 * @className 		ChangeMemberView
 * @author 			정윤서
 */
package MemberManagement;

import MemberManagement.MemberManager;
import SystemManagement.MainMenuView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangeMemberView1 extends JFrame {

	private JPanel contentPane;
	private JTextField txt_memberCode;
	private JTable table;
	private JTextField txt_changeName;
	private JTextField txt_changePhone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangeMemberView1 frame = new ChangeMemberView1();
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
	public ChangeMemberView1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lb_changeMember = new JLabel("\uACE0\uAC1D \uC218\uC815");
		lb_changeMember.setFont(new Font("굴림", Font.PLAIN, 40));
		lb_changeMember.setHorizontalAlignment(SwingConstants.CENTER);
		lb_changeMember.setBounds(661, 164, 200, 50);
		contentPane.add(lb_changeMember);

		JLabel lb_memberCode = new JLabel("\uACE0\uAC1D \uCF54\uB4DC");
		lb_memberCode.setFont(new Font("굴림", Font.PLAIN, 20));
		lb_memberCode.setBounds(515, 262, 90, 18);
		contentPane.add(lb_memberCode);

		txt_memberCode = new JTextField();
		txt_memberCode.setFont(new Font("굴림", Font.PLAIN, 20));
		txt_memberCode.setBounds(626, 255, 241, 29);
		contentPane.add(txt_memberCode);
		txt_memberCode.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(529, 310, 430, 45);
		contentPane.add(scrollPane);

		String header[] = { "", "", "" };
		String contents[][] = { { "", "", "" } };

		table = new JTable(contents, header);
		table.setFont(new Font("굴림", Font.PLAIN, 15));
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setModel(new DefaultTableModel(new Object[][] { { "", "", "" }, },
				new String[] { "\uACE0\uAC1D \uCF54\uB4DC", "\uC774\uB984", "\uC5F0\uB77D\uCC98" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(65);
		table.getColumnModel().getColumn(1).setPreferredWidth(67);
		table.getColumnModel().getColumn(2).setPreferredWidth(97);

		scrollPane.setViewportView(table);

		getContentPane().add(table.getTableHeader(), BorderLayout.NORTH);

		JButton btn_read = new JButton("\uC870\uD68C");
		btn_read.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btn_read.setFont(new Font("굴림", Font.PLAIN, 20));
		btn_read.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MemberManager m = new MemberManager();
				String memberCode = txt_memberCode.getText();
				String result = null;
				if (memberCode.length() == 0) {
					JOptionPane.showMessageDialog(contentPane, "고객 코드 미작성");
				} else {
					try {
						result = m.readMember(memberCode);

						if (result == "해당 고객 없음") {
							JOptionPane.showMessageDialog(contentPane, result);
						} else {
							// 1. 쉼표(,)로 문자열 잘라서 배열에 넣기
							String[] array = result.split(",");

							table.setModel(new DefaultTableModel(new Object[][] { { array[0], array[1], array[2] }, },
									new String[] { "\uACE0\uAC1D \uCF54\uB4DC", "\uC774\uB984",
											"\uC5F0\uB77D\uCC98" }));
						}
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
		btn_read.setBounds(917, 256, 90, 30);
		contentPane.add(btn_read);

		JLabel lb_changeName = new JLabel("\uC218\uC815\uD560 \uC774\uB984");
		lb_changeName.setFont(new Font("굴림", Font.PLAIN, 20));
		lb_changeName.setBounds(515, 382, 111, 18);
		contentPane.add(lb_changeName);

		JLabel lb_changePhone = new JLabel("\uC218\uC815\uD560 \uC5F0\uB77D\uCC98");
		lb_changePhone.setFont(new Font("굴림", Font.PLAIN, 20));
		lb_changePhone.setBounds(515, 430, 134, 18);
		contentPane.add(lb_changePhone);

		txt_changeName = new JTextField();
		txt_changeName.setFont(new Font("굴림", Font.PLAIN, 20));
		txt_changeName.setBounds(661, 376, 181, 29);
		contentPane.add(txt_changeName);
		txt_changeName.setColumns(10);

		txt_changePhone = new JTextField();
		txt_changePhone.setFont(new Font("굴림", Font.PLAIN, 20));
		txt_changePhone.setColumns(10);
		txt_changePhone.setBounds(661, 424, 181, 29);
		contentPane.add(txt_changePhone);

		JButton btn_change = new JButton("\uC218\uC815");
		btn_change.setFont(new Font("굴림", Font.PLAIN, 20));
		btn_change.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MemberManager m = new MemberManager();
				String memberCode = txt_memberCode.getText();
				String changeName = txt_changeName.getText();
				String changePhone = txt_changePhone.getText();

				if ((changeName != null) && (changePhone != null)) {
					try {
						m.changeMember(memberCode, changeName, changePhone);
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
		btn_change.setBounds(917, 377, 90, 29);
		contentPane.add(btn_change);

		JButton btn_cancel = new JButton("\uC774\uC804 \uD654\uBA74");
		btn_cancel.setFont(new Font("굴림", Font.PLAIN, 20));
		btn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		btn_cancel.setBounds(873, 424, 134, 30);
		contentPane.add(btn_cancel);
	}
}

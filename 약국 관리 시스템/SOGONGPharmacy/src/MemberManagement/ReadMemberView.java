/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	MemberManagement
 * @className 		ReadMemberView
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class ReadMemberView extends JFrame {

	private JPanel contentPane;
	private JTextField txt_memberCode;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReadMemberView frame = new ReadMemberView();
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
	public ReadMemberView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lb_readMember = new JLabel("\uACE0\uAC1D \uC870\uD68C");
		lb_readMember.setFont(new Font("굴림", Font.PLAIN, 40));
		lb_readMember.setBounds(394, 37, 188, 47);
		contentPane.add(lb_readMember);

		JLabel lb_memberCode = new JLabel("\uACE0\uAC1D \uCF54\uB4DC");
		lb_memberCode.setFont(new Font("굴림", Font.PLAIN, 20));
		lb_memberCode.setBounds(220, 127, 87, 18);
		contentPane.add(lb_memberCode);

		txt_memberCode = new JTextField();
		txt_memberCode.setFont(new Font("굴림", Font.PLAIN, 20));
		txt_memberCode.setBounds(330, 121, 213, 29);
		contentPane.add(txt_memberCode);
		txt_memberCode.setColumns(10);

		JButton btn_read = new JButton("\uC870\uD68C");
		btn_read.setFont(new Font("굴림", Font.PLAIN, 20));
		btn_read.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MemberManager m = new MemberManager();
				String memberCode = txt_memberCode.getText();
				String result = null;
				if (txt_memberCode.getText().length() == 0) {
					JOptionPane.showMessageDialog(contentPane, "고객코드 미작성");
				} else {
					try {
						result = m.readMember(memberCode);
						if (result == "해당 고객 없음") {
							JOptionPane.showMessageDialog(contentPane, result);
						} else {
							// 1. 쉼표(,)로 문자열 잘라서 배열에 넣기
							String[] array = result.split(",");

							table.setModel(new DefaultTableModel(
									new Object[][] { { array[0], array[1], array[2], array[3], array[4] }, },
									new String[] { "\uACE0\uAC1D \uCF54\uB4DC", "\uC774\uB984", "\uC5F0\uB77D\uCC98",
											"관심고객 여부", "관심고객 이유" }));
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
		btn_read.setBounds(569, 122, 87, 29);
		contentPane.add(btn_read);

		JButton btn_cancel = new JButton("\uC774\uC804 \uD654\uBA74");
		btn_cancel.setFont(new Font("굴림", Font.PLAIN, 20));
		btn_cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(contentPane.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
		btn_cancel.setBounds(685, 122, 135, 29);
		contentPane.add(btn_cancel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(210, 190, 610, 45);
		contentPane.add(scrollPane);

		String header[] = { "", "", "", "", "" };
		String contents[][] = { { "", "", "", "", "" } };

		table = new JTable(contents, header);
		table.setFont(new Font("굴림", Font.PLAIN, 15));
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setModel(new DefaultTableModel(new Object[][] { { "", "", "", "", "" }, }, new String[] {
				"\uACE0\uAC1D \uCF54\uB4DC", "\uC774\uB984", "\uC5F0\uB77D\uCC98", "관심고객 여부", "관심고객 이유" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(65);
		table.getColumnModel().getColumn(1).setPreferredWidth(67);
		table.getColumnModel().getColumn(2).setPreferredWidth(97);

		scrollPane.setViewportView(table);

		getContentPane().add(table.getTableHeader(), BorderLayout.NORTH);

		JLabel lb_description = new JLabel(
				"(\uAD00\uC2EC\uACE0\uAC1D \uC5EC\uBD80 : 1=\uAD00\uC2EC\uACE0\uAC1DO 0=\uAD00\uC2EC\uACE0\uAC1DX)");
		lb_description.setFont(new Font("굴림", Font.PLAIN, 18));
		lb_description.setBounds(456, 259, 364, 18);
		contentPane.add(lb_description);
	}

}

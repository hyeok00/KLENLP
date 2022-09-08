import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ShowBankTransferGUI extends JFrame {
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	public static SocketController sc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowBankTransferGUI frame = new ShowBankTransferGUI(sc);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param sc
	 */
	public ShowBankTransferGUI(SocketController sc) {
		ShowBankTransferGUI owner = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 471, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JRadioButton btDeposit = new JRadioButton("\uC785\uAE08");
		btDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("신청자명");
				textField_1.setText("신청번호");
				textField_2.setText("입금액");
				textField_3.setText("입금일");

			}
		});
		buttonGroup.add(btDeposit);
		btDeposit.setBounds(74, 26, 113, 23);
		contentPane.add(btDeposit);

		JRadioButton btWithdraw = new JRadioButton("\uCDE8\uC18C");
		btWithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("신청자명");
				textField_1.setText("신청번호");
				textField_2.setText("취소액");
				textField_3.setText("취소일");
			}
		});
		buttonGroup.add(btWithdraw);
		btWithdraw.setBounds(302, 26, 113, 23);
		contentPane.add(btWithdraw);

		textField = new JTextField();
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textField.setText(null);
			}
		});
		textField.setBounds(22, 68, 64, 30);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("\uB4F1\uB85D");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btDeposit.isSelected()) {
					String theLine = String.format("%s<>%s<>%s<>%s<>%s<>%s", "T4", "T", textField.getText(),
							textField_1.getText(), textField_2.getText(), textField_3.getText());
					sc.sendMessage(theLine);

				} else if (btWithdraw.isSelected()) {
					String theLine = String.format("%s<>%s<>%s<>%s<>%s<>%s", "T4", "F", textField.getText(),
							textField_1.getText(), textField_2.getText(), textField_3.getText());
					sc.sendMessage(theLine);
				}
				JOptionPane.showMessageDialog(null, "등록되었습니다.");

				if (btDeposit.isSelected()) {
					textField.setText("신청자명");
					textField_1.setText("신청번호");
					textField_2.setText("입금액");
					textField_3.setText("입금일");
				} else if (btWithdraw.isSelected()) {
					textField.setText("신청자명");
					textField_1.setText("신청번호");
					textField_2.setText("환불액");
					textField_3.setText("환불일");
				}

			}
		});
		btnNewButton.setBounds(173, 151, 91, 41);
		contentPane.add(btnNewButton);

		textField_1 = new JTextField();
		textField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				textField_1.setText(null);
			}
		});
		textField_1.setColumns(10);
		textField_1.setBounds(98, 68, 102, 30);
		contentPane.add(textField_1);

		textField_2 = new JTextField();
		textField_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				textField_2.setText(null);
			}
		});
		textField_2.setColumns(10);
		textField_2.setBounds(212, 68, 113, 30);
		contentPane.add(textField_2);

		textField_3 = new JTextField();
		textField_3.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				textField_3.setText(null);
			}
		});
		textField_3.setColumns(10);
		textField_3.setBounds(337, 68, 102, 30);
		contentPane.add(textField_3);
	}
}
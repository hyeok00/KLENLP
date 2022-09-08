import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class FoodApplicationGUI extends JFrame {

	private JPanel contentPane;
	private JTextField Date, count1;
	private JButton OK, Pay;
	private JLabel Label, Total;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField textField;
	private JTextField count2;
	private JTextField count3;
	private JTextField textField_1;
	private JCheckBox chbMenu1;
	private JCheckBox chbMenu2;
	private JCheckBox chbMenu3;
	public static Student s;
	public static SocketController sc;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FoodApplicationGUI frame = new FoodApplicationGUI(sc, s);
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
	 * @param s
	 * @param sc
	 */
	public FoodApplicationGUI(SocketController sc, Student s) {
		FoodApplicationGUI owner = this;
		setTitle("\uC2DD\uC7AC\uB8CC \uC2E0\uCCAD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 479, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Date = new JTextField();
		Date.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Date.setText(null);
			}
		});
		Date.setText("ex)2018-12-23");
		Date.setBounds(22, 37, 146, 34);
		contentPane.add(Date);
		Date.setColumns(10);

		OK = new JButton("OK");
		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String theLine = String.format("%s<>%s<>%s", "T3", Date.getText(), textField_1.getText());
				sc.sendMessage(theLine);
				String rtn = "";
				while ((rtn = sc.reciveMessage()) != null) {
					rtn = sc.reciveMessage();
					String word[] = theLine.split("<>");
					chbMenu1.setText(word[0]);
					chbMenu2.setText(word[1]);
					chbMenu3.setText(word[2]);
				}
			}
		});
		OK.setBounds(344, 39, 65, 29);
		contentPane.add(OK);

		count1 = new JTextField();
		count1.setBounds(175, 92, 57, 21);
		contentPane.add(count1);
		count1.setColumns(10);

		Label = new JLabel("\uC778\uBD84");
		Label.setBounds(244, 95, 57, 15);
		contentPane.add(Label);

		Total = new JLabel("");
		Total.setBounds(12, 108, 57, 15);
		contentPane.add(Total);

		Pay = new JButton("\uC2E0\uCCAD");
		Pay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chbMenu3.isSelected()) {
					String theLine = String.format("%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s", "S1", Date.getText(),
							textField_1.getText(), textField_2.getText(), textField.getText(), chbMenu3.getText(),
							count3.getText());
					sc.sendMessage(theLine);
				}
				if (chbMenu2.isSelected()) {
					String theLine = String.format("%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s", "S1", Date.getText(),
							textField_1.getText(), textField_2.getText(), textField.getText(), chbMenu2.getText(),
							count2.getText());
					sc.sendMessage(theLine);
				}
				if (chbMenu1.isSelected()) {
					String theLine = String.format("%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s", "S1", Date.getText(),
							textField_1.getText(), textField_2.getText(), textField.getText(), chbMenu1.getText(),
							count1.getText());
					sc.sendMessage(theLine);
				}
				if (chbMenu1.isSelected() && chbMenu2.isSelected()) {
					String theLine = String.format("%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s", "S1", Date.getText(),
							textField_1.getText(), textField_2.getText(), textField.getText(), chbMenu1.getText(),
							count1.getText(), chbMenu2.getText(), count2.getText());
					sc.sendMessage(theLine);
				}
				if (chbMenu1.isSelected() && chbMenu3.isSelected()) {
					String theLine = String.format("%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s", "S1", Date.getText(),
							textField_1.getText(), textField_2.getText(), textField.getText(), chbMenu1.getText(),
							count1.getText(), chbMenu3.getText(), count3.getText());
					sc.sendMessage(theLine);
				}
				if (chbMenu2.isSelected() && chbMenu3.isSelected()) {
					String theLine = String.format("%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s", "S1", Date.getText(),
							textField_1.getText(), textField_2.getText(), textField.getText(), chbMenu2.getText(),
							count2.getText(), chbMenu3.getText(), count3.getText());
					sc.sendMessage(theLine);
				}
				if (chbMenu1.isSelected() && chbMenu2.isSelected() && chbMenu3.isSelected()) {
					String theLine = String.format("%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s<>%s", "S1", Date.getText(),
							textField_1.getText(), textField_2.getText(), textField.getText(), chbMenu1.getText(),
							count1.getText(), chbMenu2.getText(), count2.getText(), chbMenu3.getText(),
							count3.getText());
					sc.sendMessage(theLine);
				}
				String rtn = "";
				String word[] = new String[2];
				while ((rtn = sc.reciveMessage()) != null) {
					rtn = sc.reciveMessage();
					word = rtn.split("<>");
				}
				JOptionPane
						.showMessageDialog(
								null, "입금기한 : 수강 2일 전까지" + "\n" + "합산 금액 : " + word[0] + "... " + "\n" + "신청번호 : "
										+ word[1] + "\n" + "입금시 신청번호를 꼭 기재해 주세요!!",
								"신청완료", JOptionPane.INFORMATION_MESSAGE);
				s.setCode(word[1]);
				owner.setVisible(false);
			}
		});
		Pay.setBounds(134, 379, 108, 34);
		contentPane.add(Pay);

		lblNewLabel = new JLabel("\uC2E0\uCCAD\uC77C");
		lblNewLabel.setBounds(22, 12, 65, 15);
		contentPane.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("\uC804\uD654\uBC88\uD638");
		lblNewLabel_1.setBounds(254, 290, 72, 21);
		contentPane.add(lblNewLabel_1);

		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				textField.setText(null);
			}
		});
		textField.setText("-\uB97C \uBE7C\uACE0 \uC785\uB825\uD574\uC8FC\uC138\uC694");
		textField.setBounds(252, 321, 146, 29);
		contentPane.add(textField);
		textField.setColumns(10);

		chbMenu1 = new JCheckBox("");
		chbMenu1.setBounds(22, 91, 146, 22);
		contentPane.add(chbMenu1);

		chbMenu2 = new JCheckBox("");
		chbMenu2.setBounds(22, 155, 146, 22);
		contentPane.add(chbMenu2);

		chbMenu3 = new JCheckBox("");
		chbMenu3.setBounds(22, 228, 146, 22);
		contentPane.add(chbMenu3);

		count2 = new JTextField();
		count2.setColumns(10);
		count2.setBounds(175, 156, 57, 21);
		contentPane.add(count2);

		count3 = new JTextField();
		count3.setColumns(10);
		count3.setBounds(175, 229, 57, 21);
		contentPane.add(count3);

		JLabel label = new JLabel("\uC778\uBD84");
		label.setBounds(244, 162, 57, 15);
		contentPane.add(label);

		JLabel label_1 = new JLabel("\uC778\uBD84");
		label_1.setBounds(244, 235, 57, 15);
		contentPane.add(label_1);

		JLabel lblNewLabel_2 = new JLabel("\uC218\uAC15\uC9C0\uC810");
		lblNewLabel_2.setBounds(180, 9, 65, 21);
		contentPane.add(lblNewLabel_2);

		textField_1 = new JTextField();
		textField_1.setBounds(180, 37, 146, 34);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(22, 325, 146, 29);
		contentPane.add(textField_2);

		JLabel label_2 = new JLabel("\uC774\uB984");
		label_2.setBounds(22, 293, 72, 21);
		contentPane.add(label_2);
	}
}
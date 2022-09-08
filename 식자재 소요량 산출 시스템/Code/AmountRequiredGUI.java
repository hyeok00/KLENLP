import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class AmountRequiredGUI extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtStart;
	private JTextField txtFin;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	public JLabel lblNewLabel_2;
	public static SocketController sc;
	private JTextField textField;
	private JLabel lblNewLabel_3;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AmountRequiredGUI frame = new AmountRequiredGUI(sc);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param sc 
	 */
	public AmountRequiredGUI(SocketController sc) {
		setTitle("\uC18C\uC694\uB7C9 \uBAA9\uB85D \uC870\uD68C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 649, 517);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JRadioButton btDay = new JRadioButton("\uC77C\uC790\uBCC4 \uC870\uD68C");
		btDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblNewLabel.setVisible(false);
				txtFin.setVisible(false);
				txtStart.setText("ex) 2018-12-23");
			}
		});
		buttonGroup.add(btDay);
		btDay.setBounds(41, 25, 128, 32);
		contentPane.add(btDay);

		JRadioButton btTerm = new JRadioButton("\uAE30\uAC04\uBCC4 \uC870\uD68C");
		btTerm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblNewLabel.setVisible(true);
				txtFin.setVisible(true);

				txtStart.setText("조회시작일 ex) 2018-12-23");
				txtFin.setText("조회마지막날 ex) 2018-12-26");
			}
		});
		buttonGroup.add(btTerm);
		btTerm.setBounds(231, 25, 128, 32);
		contentPane.add(btTerm);

		txtStart = new JTextField();
		txtStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtStart.setText(null);
			}
		});
		txtStart.setBounds(51, 63, 283, 32);
		contentPane.add(txtStart);
		txtStart.setColumns(10);

		lblNewLabel = new JLabel("~");
		lblNewLabel.setBounds(183, 105, 50, 15);
		contentPane.add(lblNewLabel);

		txtFin = new JTextField();
		txtFin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtFin.setText(null);
			}
		});
		txtFin.setColumns(10);
		txtFin.setBounds(51, 127, 283, 32);
		contentPane.add(txtFin);

		JButton btnNewButton = new JButton("\uC870\uD68C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btDay.isSelected()) {
					String theLine = String.format("%s<>%s", "T3", txtStart.getText());
					sc.sendMessage(theLine);
				}
				else if(btTerm.isSelected()) {
					String theLine = String.format("%s<>%s<>%s", "T3", txtStart.getText(), txtFin.getText());
					sc.sendMessage(theLine);
				}
				String rtn = "";
				while ((rtn = sc.reciveMessage()) != null) {
					rtn = sc.reciveMessage();
					lblNewLabel_2.setText(rtn + "\n");
				}
			}
		});
		btnNewButton.setBounds(441, 111, 91, 62);
		contentPane.add(btnNewButton);

		lblNewLabel_1 = new JLabel(
				"------------------------------------------------------------------------------------------------");
		lblNewLabel_1.setBounds(12, 183, 611, 15);
		contentPane.add(lblNewLabel_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 208, 429, 262);
		contentPane.add(scrollPane);

		lblNewLabel_2 = new JLabel("");
		scrollPane.setViewportView(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(401, 63, 146, 32);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_3 = new JLabel("\uC9C0\uC810");
		lblNewLabel_3.setBounds(401, 34, 80, 19);
		contentPane.add(lblNewLabel_3);
	}
}
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MenuInquiryGUI extends JFrame {
	private JPanel contentPane;
	private JTextField txtEx;
	public JLabel lblMenu1;
	public JLabel lblPrice1;
	public static SocketController sc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuInquiryGUI frame = new MenuInquiryGUI(sc);
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
	public MenuInquiryGUI(SocketController sc) {

		setTitle("\uC2E4\uC2B5\uBA54\uB274 \uC870\uD68C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("\uB0A0\uC9DC\uC785\uB825");
		label.setBounds(12, 25, 56, 25);
		contentPane.add(label);

		txtEx = new JTextField();
		txtEx.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtEx.setText(null);
			}
		});
		txtEx.setText("ex)2018-12-23");
		txtEx.setBounds(12, 56, 196, 33);
		contentPane.add(txtEx);
		txtEx.setColumns(10);

		JLabel lblMenuName = new JLabel("\uBA54\uB274\uBA85");
		lblMenuName.setBounds(18, 112, 81, 25);
		contentPane.add(lblMenuName);

		JLabel lblPrice = new JLabel("\uAC00\uACA9");
		lblPrice.setBounds(217, 112, 81, 25);
		contentPane.add(lblPrice);

		lblMenu1 = new JLabel("");
		lblMenu1.setBounds(18, 133, 176, 168);
		contentPane.add(lblMenu1);

		lblPrice1 = new JLabel("");
		lblPrice1.setBounds(217, 133, 176, 168);
		contentPane.add(lblPrice1);

		JButton btnNewButton = new JButton("\uC870\uD68C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String theLine = String.format("%s<>%s", "T1", txtEx.getText());
				sc.sendMessage(theLine);
				String rtn = "";
				while ((rtn = sc.reciveMessage()) != null) {
					rtn = sc.reciveMessage();
					String word[] = rtn.split("<>");
					lblMenu1.setText(word[0] + "\n");
					lblPrice1.setText(word[1] + "\n");
				}
			}
		});
		btnNewButton.setBounds(220, 56, 64, 33);
		contentPane.add(btnNewButton);
	}
}

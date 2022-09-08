import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TeacherGUI extends JFrame {

	private JPanel contentPane;
	public static SocketController sc;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherGUI frame = new TeacherGUI(sc);
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
	public TeacherGUI(SocketController sc) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 433, 369);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("\uC2E4\uC2B5\uBA54\uB274 \uC870\uD68C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuInquiryGUI mi = new MenuInquiryGUI(sc);
				mi.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				mi.setVisible(true);
			}
		});
		button.setBounds(56, 23, 305, 41);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\uC2E0\uCCAD\uC790 \uBA85\uB2E8 \uC870\uD68C");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ApplicantInquiryGUI ai = new ApplicantInquiryGUI(sc);
				ai.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				ai.setVisible(true);
			}
		});
		button_1.setBounds(56, 83, 305, 41);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("\uC18C\uC694\uB7C9 \uBAA9\uB85D \uC870\uD68C");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AmountRequiredGUI ar = new AmountRequiredGUI(sc);
				ar.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				ar.setVisible(true);
			}
		});
		button_2.setBounds(56, 141, 305, 41);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("\uACC4\uC88C\uC774\uCCB4 \uB0B4\uC5ED \uB4F1\uC7AC");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ShowBankTransferGUI bt = new ShowBankTransferGUI(sc);
				bt.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				bt.setVisible(true);
			}
		});
		button_3.setBounds(56, 200, 305, 41);
		contentPane.add(button_3);
		
		JButton btexit = new JButton("\uC885\uB8CC");
		btexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		btexit.setBounds(311, 272, 83, 50);
		contentPane.add(btexit);
	}
}

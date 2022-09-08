import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {
	private String ID;
	private String PW;
	private JPanel contentPane;
	public JTextField txtID;
	public JPasswordField txtPW;
	public SocketController sc;
	public int state = -1;

	JButton btOK;
	JButton btCancel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	 * @throws IOException
	 */
	public Login() {

		try {
			sc = new SocketController();
		} catch (IOException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}

		Login owner = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLogIn = new JLabel("LogIn");
		lblLogIn.setBounds(64, 60, 42, 29);
		contentPane.add(lblLogIn);

		JLabel lblPassword = new JLabel("PassWord");
		lblPassword.setBounds(56, 143, 73, 29);
		contentPane.add(lblPassword);

		txtID = new JTextField();
		txtID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtPW.requestFocus();
			}
		});
		txtID.setBounds(151, 64, 233, 29);
		contentPane.add(txtID);
		txtID.setColumns(10);

		txtPW = new JPasswordField();
		txtPW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btOK.doClick();
			}
		});
		txtPW.setBounds(151, 147, 233, 21);
		contentPane.add(txtPW);

		btOK = new JButton("OK");
		btOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String theLine = String.format("%s<>%s<>%s", "L1", txtID.getText(), txtPW.getText());
				sc.sendMessage(theLine);
				String rtn = sc.reciveMessage();
				state = Integer.parseInt(rtn);
				if (state == 0) {
					Admin ad = new Admin(txtID.getText(), txtPW.getText());
					AdminGUI AG = new AdminGUI(sc);
					AG.setVisible(true);
					owner.setVisible(false);
				} else if (state == 1) {
					Teacher tc = new Teacher(txtID.getText(), txtPW.getText());
					TeacherGUI TG = new TeacherGUI(sc);
					TG.setVisible(true);
					owner.setVisible(false);
				} else if (state == 2) {
					Student s = new Student(txtID.getText(), txtPW.getText());
					StudentGUI SG = new StudentGUI(sc, s);
					SG.setVisible(true);
					owner.setVisible(false);
				} else {
					JOptionPane.showConfirmDialog(null, "다시 입력해 주세요","오류", 2);

				}
			}
		});
		btOK.setBounds(76, 194, 115, 41);
		contentPane.add(btOK);

		btCancel = new JButton("Cancel");
		btCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btCancel.setBounds(235, 192, 115, 45);
		contentPane.add(btCancel);
	}
}
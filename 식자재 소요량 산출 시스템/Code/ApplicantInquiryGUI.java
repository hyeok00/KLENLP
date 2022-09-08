import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ApplicantInquiryGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtEx;
	public JTextArea textArea;
	public JTextArea textArea_1;
	public static SocketController sc;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicantInquiryGUI frame = new ApplicantInquiryGUI(sc);
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
	public ApplicantInquiryGUI(SocketController sc) {
		setTitle("\uC2E0\uCCAD\uC790\uBA85\uB2E8 \uC870\uD68C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 473, 408);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblApplicant = new JLabel("\uC2E0\uCCAD\uC790\uBA85");
		lblApplicant.setBounds(67, 102, 92, 25);
		contentPane.add(lblApplicant);
		
		JLabel lblPrice = new JLabel("\uC785\uAE08\uC561");
		lblPrice.setBounds(280, 102, 53, 25);
		contentPane.add(lblPrice);
		
		txtEx = new JTextField();
		txtEx.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtEx.setText(null);
			}
		});
		txtEx.setText("ex) 20181223");
		txtEx.setBounds(28, 35, 253, 31);
		contentPane.add(txtEx);
		txtEx.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\uC2E4\uC2B5\uC77C");
		lblNewLabel.setBounds(28, 10, 70, 15);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("\uC870\uD68C");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String theLine = String.format("%s<>%s", "T2", txtEx.getText());
				sc.sendMessage(theLine);
				String rtn = "";
				while ((rtn = sc.reciveMessage()) != null) {
					rtn = sc.reciveMessage();
					String word[] = rtn.split("<>");
					textArea.setText(word[0] + "\n");
					textArea_1.setText(word[1] + "\n");
				}
				
			}
		});
		btnNewButton.setBounds(300, 35, 92, 31);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 144, 168, 204);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(224, 144, 168, 204);
		contentPane.add(scrollPane_1);
		
		textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		textArea_1.setEditable(false);
	}
}

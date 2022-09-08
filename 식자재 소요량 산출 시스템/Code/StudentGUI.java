import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class StudentGUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton ShowMenu;
	private JButton IngrediantApply;
	private JButton IngrediantCancle;
	private JButton close;
	public static Student s;
	public static SocketController sc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentGUI frame = new StudentGUI(sc, s);
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
	 * @param s
	 */
	public StudentGUI(SocketController sc, Student s) {
		super("test");
		this.sc = sc;
		this.s = s;
		setTitle("\uD559\uC0DD");
		initializeComponents();
	}

	public void initializeComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ShowMenu = new JButton("\uC2E4\uC2B5\uBA54\uB274 \uC870\uD68C");
		ShowMenu.setBounds(157, 31, 120, 45);
		contentPane.add(ShowMenu);

		IngrediantApply = new JButton("\uC2DD\uC7AC\uB8CC \uC2E0\uCCAD");
		IngrediantApply.setBounds(157, 107, 120, 45);
		contentPane.add(IngrediantApply);

		IngrediantCancle = new JButton("\uC2DD\uC7AC\uB8CC \uCDE8\uC18C");
		IngrediantCancle.setBounds(157, 183, 120, 45);
		contentPane.add(IngrediantCancle);

		close = new JButton("\uC885\uB8CC");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == close)
					System.exit(0);
			}
		});
		close.setBounds(337, 238, 97, 23);
		contentPane.add(close);

		ShowMenu.addActionListener(this);
		IngrediantApply.addActionListener(this);
		IngrediantCancle.addActionListener(this);
		close.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == ShowMenu) {
			MenuInquiryGUI M = new MenuInquiryGUI(sc);
			M.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			M.setVisible(true);
		} else if (e.getSource() == IngrediantApply) {
			FoodApplicationGUI F = new FoodApplicationGUI(sc, s);
			F.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			F.setVisible(true);
		} else if (e.getSource() == IngrediantCancle) {
			CancleApplicationGUI C = new CancleApplicationGUI(sc, s);
			C.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			C.setVisible(true);
		} else if (e.getSource() == close) {
			System.exit(0);
		}

	}

}
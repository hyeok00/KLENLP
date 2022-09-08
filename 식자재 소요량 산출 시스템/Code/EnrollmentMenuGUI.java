import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EnrollmentMenuGUI extends JFrame {

   private JPanel contentPane, panel, panel_1;
   private JLabel Menu1, Menu2, Menu3;
   private JButton Check, OK;
   private JComboBox comboBox_1, comboBox_2, comboBox_3;
   private JTextField txtEx;
   private JLabel lblNewLabel;
   private JLabel lblNewLabel_1;
   public static SocketController sc;
   private JTextField textField;
   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               EnrollmentMenuGUI frame = new EnrollmentMenuGUI(sc);
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
   public EnrollmentMenuGUI(SocketController sc) {
      setTitle("\uAC15\uC758\uC77C\uC790\uBCC4 \uBA54\uB274 \uB4F1\uB85D");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 461, 431);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      panel = new JPanel();
      panel.setBounds(0, 0, 434, 174);
      contentPane.add(panel);
      panel.setLayout(null);
      
      Check = new JButton("Check");
      Check.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      		
      	}
      });
      Check.setBounds(248, 79, 76, 35);
      panel.add(Check);
      
      txtEx = new JTextField();
      txtEx.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent arg0) {
            txtEx.setText(null);
         }
      });
      txtEx.setText("ex)2018-12-23");
      txtEx.setBounds(12, 41, 203, 30);
      panel.add(txtEx);
      txtEx.setColumns(10);
      
      lblNewLabel = new JLabel("\uAC15\uC758\uC77C\uC790");
      lblNewLabel.setBounds(12, 10, 97, 21);
      panel.add(lblNewLabel);
      
      lblNewLabel_1 = new JLabel("\uAC15\uC758\uC9C0\uC810");
      lblNewLabel_1.setBounds(12, 80, 97, 21);
      panel.add(lblNewLabel_1);
      
      textField = new JTextField();
      textField.setColumns(10);
      textField.setBounds(12, 111, 203, 30);
      panel.add(textField);
      
      panel_1 = new JPanel();
      panel_1.setBounds(0, 184, 434, 210);
      contentPane.add(panel_1);
      panel_1.setLayout(null);
      
      Menu1 = new JLabel("\uBA54\uB2741");
      Menu1.setBounds(12, 41, 57, 15);
      panel_1.add(Menu1);
      
      Menu2 = new JLabel("\uBA54\uB2742");
      Menu2.setBounds(12, 97, 57, 15);
      panel_1.add(Menu2);
      
      Menu3 = new JLabel("\uBA54\uB2743");
      Menu3.setBounds(12, 153, 57, 15);
      panel_1.add(Menu3);
      
      comboBox_1 = new JComboBox();
      comboBox_1.setBounds(81, 41, 130, 15);
      panel_1.add(comboBox_1);
      
      comboBox_2 = new JComboBox();
      comboBox_2.setBounds(81, 94, 130, 15);
      panel_1.add(comboBox_2);
      
      comboBox_3 = new JComboBox();
      comboBox_3.setBounds(81, 150, 130, 15);
      panel_1.add(comboBox_3);
      
      OK = new JButton("OK");
      OK.setBounds(325, 177, 97, 23);
      panel_1.add(OK);
   }
}
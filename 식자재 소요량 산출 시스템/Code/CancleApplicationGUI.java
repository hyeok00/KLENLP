import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CancleApplicationGUI extends JFrame {

   private JPanel contentPane;
   private JTextField txtPnum;
   private JTextField txtOrderNum;
   private JLabel lblPnum;
   private JLabel lblOderNum;
   private JButton btnNewButton;
   private static Student s;
   public static SocketController sc;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               CancleApplicationGUI frame = new CancleApplicationGUI(sc,s);
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
   public CancleApplicationGUI(SocketController sc, Student s) {
      setTitle("\uC2DD\uC7AC\uB8CC \uCDE8\uC18C");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 450, 300);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      txtPnum = new JTextField();
      txtPnum.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent arg0) {
            txtPnum.setText(null);
         }
      });
      txtPnum.setText("'-' \uB294 \uBE7C\uACE0 \uC785\uB825\uD574\uC8FC\uC138\uC694");
      txtPnum.setBounds(51, 66, 229, 33);
      contentPane.add(txtPnum);
      txtPnum.setColumns(10);
      
      txtOrderNum = new JTextField();
      txtOrderNum.setColumns(10);
      txtOrderNum.setBounds(51, 129, 229, 33);
      contentPane.add(txtOrderNum);
      
      lblPnum = new JLabel("\uC804\uD654\uBC88\uD638");
      lblPnum.setBounds(51, 41, 50, 15);
      contentPane.add(lblPnum);
      
      lblOderNum = new JLabel("\uC2E0\uCCAD\uBC88\uD638");
      lblOderNum.setBounds(51, 109, 50, 15);
      contentPane.add(lblOderNum);
      
      btnNewButton = new JButton("\uCDE8\uC18C");
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
        	 String theLine = String.format("%s<>%s<>%s", "S2", txtPnum.getText(), s.getCode() );
				sc.sendMessage(theLine);
            //전화번호, 신청번호  서버에 전송후 둘다 있는경우 취소메시지 출력
            JOptionPane.showMessageDialog(null, "취소 되었습니다.");
         }
      });
      btnNewButton.setBounds(291, 81, 91, 70);
      contentPane.add(btnNewButton);
   }

}
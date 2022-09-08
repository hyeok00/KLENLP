import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class DeleteRecipeGUI extends JFrame {

   private JPanel contentPane;
   private JTextField textField;
   private JLabel lblNewLabel;
   public static SocketController sc;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               DeleteRecipeGUI frame = new DeleteRecipeGUI(sc);
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
   public DeleteRecipeGUI(SocketController sc) {
      setTitle("\uB808\uC2DC\uD53C \uC0AD\uC81C");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 450, 300);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      textField = new JTextField();
      textField.setBounds(54, 69, 297, 40);
      contentPane.add(textField);
      textField.setColumns(10);
      
      JButton btnNewButton = new JButton("\uC0AD\uC81C");
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
        	 if(textField.getText().length() == 0) {
        		 
        	 }
        	 String theLine = String.format("%s<>%s<>%s", "A1", "F", textField.getText());
        	 sc.sendMessage(theLine);
         }
      });
      btnNewButton.setBounds(136, 137, 121, 40);
      contentPane.add(btnNewButton);
      
      lblNewLabel = new JLabel("\uC74C\uC2DD\uC774\uB984");
      lblNewLabel.setBounds(56, 30, 112, 29);
      contentPane.add(lblNewLabel);
   }

}
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class RecipeLoadGUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnAdd, btnDelete;
	private String txt;
	public static SocketController sc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecipeLoadGUI frame = new RecipeLoadGUI(sc);
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
	 */
	public RecipeLoadGUI(SocketController sc) {
		setTitle("\uB808\uC2DC\uD53C \uB85C\uB4DC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnAdd = new JButton("\uCD94\uAC00");
		btnAdd.setBounds(168, 71, 97, 23);
		contentPane.add(btnAdd);

		btnDelete = new JButton("\uC0AD\uC81C");
		btnDelete.setBounds(168, 165, 97, 23);
		contentPane.add(btnDelete);

		btnAdd.addActionListener(this);
		btnDelete.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ev) {
		String transStr = "";

		if (ev.getSource() == btnAdd) {
			FileInputStream fis;
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter defaultFilter;
			jfc.addChoosableFileFilter(defaultFilter = new FileNameExtensionFilter("EXCEL ����(*csv)", "csv"));
			jfc.setFileFilter(defaultFilter); // ����Ʈ ���� ����
			int response = jfc.showOpenDialog(null);
			if (response != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(this, "������ �������� �ʾҽ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
				return;
			}

			File SelectedFile = jfc.getSelectedFile();
			String path = SelectedFile.getAbsolutePath(); // ��� ����

			try {
				// ������ ��ĭ�ִ°� �����ϸ� nullPoint ���� ��
				fis = new FileInputStream(path); // ������ ���ϰ�ü�� ���� //��θ� �����ϸ� .xlsx Ȯ���� �������ϸ� ����������
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				XSSFSheet sheet = workbook.getSheet("Sheet1"); // Sheet1(ù��°��Ʈ)

				int rowSize = sheet.getLastRowNum(); // row ũ��
				int count = sheet.getLastRowNum();
				int cellSize = sheet.getRow(0).getLastCellNum(); // cell ũ��
				while (count==0) {
					for (int i = 1; i <= rowSize; i++) { // i=1 �� ���� : 0��°�� ����κ�
						XSSFRow row = sheet.getRow(i); // row = i��° row��
						for (int j = 0; j < cellSize; j++) {
							XSSFCell cell = row.getCell(j); // cell = i��° row���� j��° cell ��ġ�� ��
							transStr += cell.toString() + "<>";
						}
						transStr = "A1<>T<>" + transStr;
						// write();

					}
					sc.sendMessage(transStr);
					count--;
				}
				workbook.close();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (ev.getSource() == btnDelete) { // ������ ����
			DeleteRecipeGUI dr = new DeleteRecipeGUI(sc);
			dr.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			dr.setVisible(true);

		}

		/*
		 * File f = jfc.getSelectedFile(); imageLabel.setIcon(new
		 * ImageIcon(f.getPath())); pack();
		 */
	}

}
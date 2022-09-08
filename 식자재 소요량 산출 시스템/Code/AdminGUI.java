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

public class AdminGUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton RecipeLoad;
	private JButton ScheduleLoad;
	private JButton UpdateMenu;
	private JButton close;
	public static SocketController sc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI(sc);
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
	public AdminGUI(SocketController sc) {
		setTitle("\uAD00\uB9AC\uC790");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		RecipeLoad = new JButton("Recipe Load");
		RecipeLoad.setBounds(154, 27, 125, 50);
		contentPane.add(RecipeLoad);

		ScheduleLoad = new JButton("\uAC15\uC758\uC77C\uC815\uD45C Load");
		ScheduleLoad.setBounds(154, 104, 125, 50);
		contentPane.add(ScheduleLoad);

		UpdateMenu = new JButton("\uBA54\uB274\uB4F1\uB85D");
		UpdateMenu.setBounds(154, 181, 125, 50);
		contentPane.add(UpdateMenu);

		close = new JButton("\uC885\uB8CC");
		close.setBounds(337, 238, 97, 23);
		contentPane.add(close);

		RecipeLoad.addActionListener(this);
		ScheduleLoad.addActionListener(this);
		UpdateMenu.addActionListener(this);
		close.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == RecipeLoad) {
			RecipeLoadGUI R = new RecipeLoadGUI(sc);
			R.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			R.setVisible(true);
		} else if (e.getSource() == ScheduleLoad) {
			String transStr = "";
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
				while (count == 0) {
					for (int i = 1; i <= rowSize; i++) { // i=1 �� ���� : 0��°�� ����κ�
						XSSFRow row = sheet.getRow(i); // row = i��° row��
						for (int j = 0; j < cellSize; j++) {
							XSSFCell cell = row.getCell(j); // cell = i��° row���� j��° cell ��ġ�� ��
							transStr += cell.toString() + "<>";
						}
						transStr = "A2<>" + transStr;
						// write();

					}
					sc.sendMessage(transStr);
					count--;
				}
				workbook.close();
				fis.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource() == UpdateMenu) {
			String transStr = "";
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
				while (count == 0) {
					for (int i = 1; i <= rowSize; i++) { // i=1 �� ���� : 0��°�� ����κ�
						XSSFRow row = sheet.getRow(i); // row = i��° row��
						for (int j = 0; j < cellSize; j++) {
							XSSFCell cell = row.getCell(j); // cell = i��° row���� j��° cell ��ġ�� ��
							transStr += cell.toString() + "<>";
						}
						transStr = "A2<>" + transStr;
						// write();

					}
					sc.sendMessage(transStr);
					count--;
				}
				workbook.close();
				fis.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == close) {
			System.exit(0);
		}

	}
}
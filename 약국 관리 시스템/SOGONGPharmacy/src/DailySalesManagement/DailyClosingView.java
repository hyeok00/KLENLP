/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	DailySalesManagement
 * @className 			DailyClosingView
 * @author 					최정봉
 */
package DailySalesManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import RecordManagement.Record;
import SystemManagement.MainMenuView;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DailyClosingView extends JFrame {
	ArrayList<DailySales> list = new ArrayList<DailySales>();	//매출실적
	ArrayList<DailySalesDetail> list2 = new ArrayList<DailySalesDetail>();	//상세매출실적
	
	private JPanel DailyClosingView;
	private JTable amountTable;
	private JTable salesTable;
	private JTable salesDetailTable;
	
	public String date = "";
	public int cash = 0;
	public int card = 0;
	public int refund = 0;
	
	/**
	 * Launch the application.
	 */
	//화면에 출력
	public void showDailyClosing() {
		DefaultTableModel model = (DefaultTableModel)salesTable.getModel();
		model.setNumRows(0);
		
		DefaultTableModel model2 = (DefaultTableModel)salesDetailTable.getModel();
		model2.setNumRows(0);
		
		//매출실적 출력
		if(list.size()==0) {
			JOptionPane.showMessageDialog(null, "오늘 매출 실적이 없습니다.");
		}
		else {
			for(int i=0;i<list.size();i++) {
				DailySales ds = list.get(i);
				String refundState;
				int SalesAmount = 0;
				if(ds.getRefundState()==0) {
					refundState = "판매";
					SalesAmount = ds.getSalesAmount();
					
					if(ds.getPayMethod().equals("현금")) {
						cash += SalesAmount;
					}
					else if(ds.getPayMethod().equals("카드")) {
						card += SalesAmount;
					}
				}
				else {
					refundState = "환불";
					SalesAmount = ds.getSalesAmount() * -1;
					refund += SalesAmount;
				}
				
				String sAmount = String.valueOf(SalesAmount);
				model.addRow(new String[] {ds.getSalesCode(), refundState, ds.getPayMethod(), sAmount});
			}
			model.fireTableDataChanged();
			
		}
		
		//상세 매출실적 출력
		if(list2.size()==0) {
			
		}
		else {
			for(int i=0;i<list2.size();i++) {
				DailySalesDetail dsd = list2.get(i);
				String refundState;
				if(dsd.getRefundState()==0) {
					refundState = "판매";
					
				}
				else {
					refundState = "환불";
					
				}
				String quantity = String.valueOf(dsd.getSalesQuantity());
				String amount = String.valueOf(dsd.getSalesAmount());
				model2.addRow(new String[] {dsd.getSalesCode(), refundState, dsd.getPayMethod(), dsd.getProductName(), quantity, amount});
			}
			model2.fireTableDataChanged();
		}
		
		//하단 테이블 출력
		DefaultTableModel model3 = (DefaultTableModel)amountTable.getModel();
		model3.setNumRows(0);
		int Amount = cash + card + refund;
		model3.addRow(new String[] {"현금매출", String.valueOf(cash) });
		model3.addRow(new String[] {"환불액", String.valueOf(refund) });
		model3.addRow(new String[] {"총 금액", String.valueOf(Amount) });
	}
	//화면 테이블에 띄울 내용 가져오기
	public void requestDailyClosing(String date) {
		DailySalesManager dsm = new DailySalesManager();
		list = dsm.dailyClosing(date);
		list2 = dsm.dailyClosingDetail(date);
		
		showDailyClosing();
	}
	//마감 처리 신청
	public void requestClose() {
		DailySalesManager dsm = new DailySalesManager();
		dsm.dailyClose(date, cash, card, refund);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DailyClosingView frame = new DailyClosingView();
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
	public DailyClosingView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		DailyClosingView = new JPanel();
		DailyClosingView.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(DailyClosingView);
		DailyClosingView.setLayout(null);
		
		JLabel NowTime = new JLabel("\uD604\uC7AC \uC2DC\uAC01 : ");
		NowTime.setBounds(37, 39, 433, 21);
		DailyClosingView.add(NowTime);
		
		JLabel titleLb = new JLabel("일일 마감");
	    titleLb.setHorizontalAlignment(SwingConstants.CENTER);
	    titleLb.setBounds(550, 50, 400, 50);
	    titleLb.setFont(new Font("굴림", Font.PLAIN, 40));
	    DailyClosingView.add(titleLb);
			
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(37, 104, 1402, 448);
		DailyClosingView.add(tabbedPane);
		
		JPanel Sales = new JPanel();
		Sales.setToolTipText("\uB9E4\uCD9C \uC2E4\uC801");
		tabbedPane.addTab("매출 실적", null, Sales, null);
		Sales.setLayout(new GridLayout(0, 1, 0, 0));
		
		salesTable = new JTable();
		salesTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"\uD310\uB9E4\uCF54\uB4DC", "\uAD6C\uBD84", "\uACB0\uC81C\uC218\uB2E8", "\uD310\uB9E4 \uAE08\uC561"
			}
		));
		JScrollPane scrollPane = new JScrollPane(salesTable);
		Sales.add(scrollPane, BorderLayout.CENTER);
		
		JPanel SalesDetail = new JPanel();
		tabbedPane.addTab("상세 매출 실적", null, SalesDetail, null);
		SalesDetail.setLayout(new GridLayout(1, 0, 0, 0));
		
		salesDetailTable = new JTable();
		salesDetailTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
			},
			new String[] {
				"\uD310\uB9E4\uCF54\uB4DC", "\uAD6C\uBD84", "\uACB0\uC81C\uC218\uB2E8", "\uC0C1\uD488\uBA85", "\uD310\uB9E4\uC218\uB7C9", "\uB9E4\uCD9C"
			}
		));
		SalesDetail.add(new JScrollPane(salesDetailTable), BorderLayout.CENTER);
		
		JPanel AmountView = new JPanel();
		AmountView.setBounds(37, 583, 400, 100);
		DailyClosingView.add(AmountView);
		AmountView.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		
		JButton closingButton = new JButton("마감");
		closingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestClose();
			}
		});
		closingButton.setBounds(1292, 636, 147, 47);
		DailyClosingView.add(closingButton);
		
		amountTable = new JTable();
		amountTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"현금매출", null},
				{"카드매출", null},
				{"환불액", null},
				{"총 금액", null},
			},
			new String[] {
				"", "\uAC00\uACA9"
			}
		));
		amountTable.setBounds(53, 522, 400, 50);
		AmountView.add(new JScrollPane(amountTable), BorderLayout.CENTER);
		
		//시간 설정
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyy년 MM월 dd일  - HH시mm분ss초");
		SimpleDateFormat format2 = new SimpleDateFormat ("yyyy-MM-dd");
		Date nowtime = new Date();
		String datetime = format1.format(nowtime);
		NowTime.setText(datetime);
				
		date = format2.format(nowtime);
		//requestDailyClosing("2019-02-02");	//현재 날짜 대신 테스트 데이터 사용
		requestDailyClosing(date);	//현재 날짜 사용
		//시간 설정
		
		JButton backBtn = new JButton("이전화면");
		backBtn.setBounds(1130, 636, 147, 47);
		DailyClosingView.add(backBtn);
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainMenuView topFrame = (MainMenuView) SwingUtilities.windowForComponent(DailyClosingView.getParent());
				topFrame.changePanel(MainMenuView.Views.MAIN_MANU);
			}
		});
	}
}

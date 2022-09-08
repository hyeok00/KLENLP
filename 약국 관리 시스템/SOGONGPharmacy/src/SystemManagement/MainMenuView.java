/**
 * @systemName 			SOGONG Pharmacy Management System
 * @subSystemName 	SystemManagement
 * @className 			MainMenuView
 * @author 					김태민
 */
package SystemManagement;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import DailySalesManagement.DailyClosingView;
import DailySalesManagement.DailyReportReadView;
import DailySalesManagement.MonthlyReportReadView;
import DashBoardManagement.OperationResultView;
import DashBoardManagement.StatisticsView;
import EnteringManagement.BarcodePrintView;
import EnteringManagement.EnteringChangeView;
import EnteringManagement.EnteringDeleteView;
import EnteringManagement.EnteringReadView;
import EnteringManagement.EnteringRegisterView;
import MemberManagement.ChangeMemberView;
import MemberManagement.ReadMemberView;
import MemberManagement.RegisterMemberView;
import MemberManagement.SetConcernedView;
import ProductManagement.ProductChangeView;
import ProductManagement.ProductListReadView;
import ProductManagement.ProductRegisterView;
import RecordManagement.ClosingRecordView;
import RecordManagement.EnteringRecordView;
import RecordManagement.ProductRecordView;
import ReturnAndDiscardManagement.ReturnAndDiscardCancelRegisterationView;
import ReturnAndDiscardManagement.ReturnAndDiscardFinalizeRegisterationView;
import ReturnAndDiscardManagement.ReturnAndDiscardReadView;
import ReturnAndDiscardManagement.ReturnAndDiscardRegisterView;
import SaleManagement.CancelSaleView;
import SaleManagement.SaleHistoryView;
import SaleManagement.SaleProductView;
import SupplierManagement.SupplierChangeView;
import SupplierManagement.SupplierDeleteView;
import SupplierManagement.SupplierReadView;
import SupplierManagement.SupplierRegisterView;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenuView extends JFrame {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(
		   new Runnable() {
		      public void run() {
		    	  MainMenuView parmachyWindow = new MainMenuView();
		      }
		   });
	}
	
	// 화면들을 식별하기 위한 상수
	public static enum Views {
		MAIN_MANU, 
		PRODUCT_REGISTER, 		PRODUCTLIST_READ, 			PRODUCT_CHANGE,
		ENTERING_REGISTER, 	ENTERING_CHANGE, 			ENTERING_DELETE, 		ENTERING_READ, 		BARCODE_PRINT,
		RETURN_DISCARD_REGISTER,	RETURN_DISCARD_FINALIZE,			RETURN_DISCARD_CANCEL, 		RETURN_DISCARD_READ,
		SUPPLIER_REGISTER, 		SUPPLIER_CHANGE, 			SUPPLIER_DELETE, 		SUPPLIER_READ,
		SALE_PRODUCT, 				SALE_CANCEL, 					SALE_HISTORY,
		MEMBER_REGISTER, 		MEMBER_CHANGE, 				MEMBER_READ, 				MEMBER_SET_CONCERNED,
		DASHBOARD_STATISTICS, 			DASHBOARD_OPERATION_RESULT,
		DAILY_CLOSING, 				DAILY_REPORT_READ, 		MONTHLY_READ,
		RECORD_PRODUCT, 		RECORD_ENTERING, 			RECORD_CLOSING,
	}
	private JPanel currPanel;
	private JPanel mainMenuView;
	
	private JPanel currClickedPanel;	// 현제 클릭되어 보여줘야하는 각 서브기능 실행 버튼이 담긴 panel
	
	// 각 서브시스템 들에 포함된 각 서브기능 실행 버튼이 담긴 panel을 보여주는 이벤트를 처리하는 버튼
	private JButton productManagementBtn;
	private JButton enteringManagementBtn;
	private JButton returnDiscardManagementBtn;
	private JButton suplierManagementBtn;
	private JButton saleManagementBtn;
	private JButton memberManagementBtn;
	private JButton dashBoardManagementBtn;
	private JButton dailySalesManagementBtn;
	private JButton recordManagementBtn;
	private JButton settingBtn;
	
	// 각 서브시스템 들에 포함된 각 서브기능 실행 버튼이 담긴 panel
	private JPanel recordManagementPnl;
	private JPanel dailySalesManagementPnl;
	private JPanel memberManagementPnl;
	private JPanel dashBoardManagementPnl;
	private JPanel suplierManagementPnl;
	private JPanel saleManagementPnl;
	private JPanel returnDiscardManagementPnl;
	private JPanel enteringManagementPnl;
	private JPanel productManagementPnl;
	
	// 각 서브시스템 들에 포함된 각 서브기능 실행 버튼, 각 기능을 수행하는 화면을 출력한다

	// button in ProductManagement 
	private JButton productRegisterViewBtn;
	private JButton productListReadViewBtn;
	private JButton productChangeViewBtn;
	
	// button in EnteringManagement 
	private JButton enteringRegisterViewBtn;
	private JButton enteringChangeViewBtn;
	private JButton enteringDeleteViewBtn;
	private JButton enteringReadViewBtn;
	private JButton barcodePrintBtn;
	
	// button in ReturnAndDiscardManagement 
	private JButton returnAndDiscardRegisterViewBtn;
	private JButton returnAndDiscardFinalizeRegisterationViewBtn;
	private JButton returnAndDiscardCancelRegistrationViewBtn;
	private JButton returnAndDiscardReadViewBtn;
	
	// button in SupplierManagement 
	private JButton supplierRegisterViewBtn;
	private JButton supplierChangeViewBtn;
	private JButton supplierDeleteViewBtn;
	private JButton supplierReadViewBtn;
	
	// button in SaleManagement 
	private JButton saleProductBtn;
	private JButton cancelSaleBtn;
	private JButton readSaleHistoryBtn;
	
	// button in MemberManagement 
	private JButton registerMemberViewBtn;
	private JButton changeMemberViewBtn;
	private JButton readMemberViewBtn;
	private JButton setConcernedViewBtn;
	
	// button in DashBoardManagement 
	private JButton statisticsViewBtn;
	private JButton operationResultViewBtn;
	
	// button in DailySalesManagement
	private JButton dailyClosingViewBtn;
	private JButton dailyReportReadViewBtn;
	private JButton monthlyReportReadView;
	
	// button in RecordManagement
	private JButton productRecordViewBtn;
	private JButton enteringRecordViewBtn;
	private JButton closingRecordViewBtn;
	
	private SaleProductView saleProductView;
	
	public void initial() {
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200, 100);
		setPreferredSize(new Dimension(1505,835));
		getContentPane().add(currPanel);
	}
	public void showView() {
		pack();
		setVisible(true);
	}
	public MainMenuView() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				saleProductView.stopThread();
			}
		});
		saleProductView = new SaleProductView();
		mainMenuView = new JPanel();
		mainMenuViewInitial();
		MainMenuViewInitialListener();
		
		currPanel = mainMenuView;
		
		currClickedPanel = null;
		
		initial();
		showView();
	}
	
	public void mainMenuViewInitial() {
		mainMenuView.setPreferredSize(new Dimension(1500,800));
		mainMenuView.setLayout(null);
		
		JLabel label = new JLabel("메인 메뉴");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(575, 100, 350, 88);
		label.setFont(new Font("굴림", Font.PLAIN, 80));
		mainMenuView.add(label);
		
		JLabel label_1 = new JLabel("소공 약국");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("굴림", Font.PLAIN, 60));
		label_1.setBounds(575, 500, 350, 88);
		mainMenuView.add(label_1);
		
		JLabel label_2 = new JLabel("관리 시스템");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("굴림", Font.PLAIN, 60));
		label_2.setBounds(575, 600, 350, 88);
		mainMenuView.add(label_2);
		
		productManagementBtn = new JButton("상품 관리");
		productManagementBtn.setFont(new Font("굴림", Font.PLAIN, 30));
		productManagementBtn.setBounds(5, 5, 250, 150);
		mainMenuView.add(productManagementBtn);
		
		enteringManagementBtn = new JButton("입고 관리");
		enteringManagementBtn.setFont(new Font("굴림", Font.PLAIN, 30));
		enteringManagementBtn.setBounds(5, 165, 250, 150);
		mainMenuView.add(enteringManagementBtn);
		
		returnDiscardManagementBtn = new JButton("반품&폐기 관리");
		returnDiscardManagementBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		returnDiscardManagementBtn.setBounds(5, 325, 250, 150);
		mainMenuView.add(returnDiscardManagementBtn);
		
		suplierManagementBtn = new JButton("거래처 관리");
		suplierManagementBtn.setFont(new Font("굴림", Font.PLAIN, 30));
		suplierManagementBtn.setBounds(5, 485, 250, 150);
		mainMenuView.add(suplierManagementBtn);
		
		saleManagementBtn = new JButton("판매 관리");
		saleManagementBtn.setFont(new Font("굴림", Font.PLAIN, 30));
		saleManagementBtn.setBounds(5, 645, 250, 150);
		mainMenuView.add(saleManagementBtn);
		
		memberManagementBtn = new JButton("고객 관리");
		memberManagementBtn.setFont(new Font("굴림", Font.PLAIN, 30));
		memberManagementBtn.setBounds(1245, 5, 250, 150);
		mainMenuView.add(memberManagementBtn);
		
		dashBoardManagementBtn = new JButton("실시간 대시보드");
		dashBoardManagementBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		dashBoardManagementBtn.setBounds(1245, 165, 250, 150);
		mainMenuView.add(dashBoardManagementBtn);
		
		dailySalesManagementBtn = new JButton("마감 관리");
		dailySalesManagementBtn.setFont(new Font("굴림", Font.PLAIN, 30));
		dailySalesManagementBtn.setBounds(1245, 325, 250, 150);
		mainMenuView.add(dailySalesManagementBtn);
		
		recordManagementBtn = new JButton("이력 관리");
		recordManagementBtn.setFont(new Font("굴림", Font.PLAIN, 30));
		recordManagementBtn.setBounds(1245, 485, 250, 150);
		mainMenuView.add(recordManagementBtn);
		
		settingBtn = new JButton("Setting");
		settingBtn.setFont(new Font("굴림", Font.PLAIN, 30));
		settingBtn.setBounds(1245, 645, 250, 150);
		mainMenuView.add(settingBtn);
		
		productManagementPnl = new JPanel();
		productManagementPnl.setBackground(Color.LIGHT_GRAY);
		productManagementPnl.setBounds(261, 5, 300, 170);
		mainMenuView.add(productManagementPnl);
		productManagementPnl.setLayout(null);
		

		enteringManagementPnl = new JPanel();
		enteringManagementPnl.setBackground(Color.LIGHT_GRAY);
		enteringManagementPnl.setBounds(261, 165, 300, 280);
		mainMenuView.add(enteringManagementPnl);
		enteringManagementPnl.setLayout(null);

		returnDiscardManagementPnl = new JPanel();
		returnDiscardManagementPnl.setBackground(Color.LIGHT_GRAY);
		returnDiscardManagementPnl.setBounds(260, 327, 300, 225);
		mainMenuView.add(returnDiscardManagementPnl);
		returnDiscardManagementPnl.setLayout(null);
		
		suplierManagementPnl = new JPanel();
		suplierManagementPnl.setBounds(260, 487, 300, 225);
		mainMenuView.add(suplierManagementPnl);
		suplierManagementPnl.setBackground(Color.LIGHT_GRAY);
		suplierManagementPnl.setLayout(null);
		suplierManagementPnl.setVisible(false);
		
		saleManagementPnl = new JPanel();
		saleManagementPnl.setBackground(SystemColor.LIGHT_GRAY);
		saleManagementPnl.setBounds(260, 625, 300, 170);
		mainMenuView.add(saleManagementPnl);
		saleManagementPnl.setLayout(null);
		saleManagementPnl.setVisible(false);
		
		memberManagementPnl = new JPanel();
		memberManagementPnl.setBackground(Color.LIGHT_GRAY);
		memberManagementPnl.setBounds(939, 5, 300, 225);
		mainMenuView.add(memberManagementPnl);
		memberManagementPnl.setLayout(null);
		
		dashBoardManagementPnl = new JPanel();
		dashBoardManagementPnl.setBackground(Color.LIGHT_GRAY);
		dashBoardManagementPnl.setBounds(939, 165, 300, 114);
		mainMenuView.add(dashBoardManagementPnl);
		dashBoardManagementPnl.setLayout(null);
		
		dailySalesManagementPnl = new JPanel();
		dailySalesManagementPnl.setBackground(Color.LIGHT_GRAY);
		dailySalesManagementPnl.setBounds(939, 327, 300, 170);
		mainMenuView.add(dailySalesManagementPnl);
		dailySalesManagementPnl.setLayout(null);
		dailySalesManagementPnl.setVisible(false);
		
		recordManagementPnl = new JPanel();
		recordManagementPnl.setBackground(Color.LIGHT_GRAY);
		recordManagementPnl.setBounds(939, 485, 300, 170);
		mainMenuView.add(recordManagementPnl);
		recordManagementPnl.setLayout(null);
		recordManagementPnl.setVisible(false);
		
		// button in ProductManagement 
		productRegisterViewBtn = new JButton("상품 등록");
		productRegisterViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		productRegisterViewBtn.setBounds(5, 5, 290, 50);
		productManagementPnl.add(productRegisterViewBtn);
		
		productListReadViewBtn = new JButton("상품 및 재고 조회");
		productListReadViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		productListReadViewBtn.setBounds(5, 60, 290, 50);
		productManagementPnl.add(productListReadViewBtn);
		
		productChangeViewBtn = new JButton("상품 수정");
		productChangeViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		productChangeViewBtn.setBounds(5, 115, 290, 50);
		productManagementPnl.add(productChangeViewBtn);
		productManagementPnl.setVisible(false);
		
		// button in EnteringManagement 
		enteringRegisterViewBtn = new JButton("입고 등록");
		enteringRegisterViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		enteringRegisterViewBtn.setBounds(5, 5, 290, 50);
		enteringManagementPnl.add(enteringRegisterViewBtn);
		
		enteringChangeViewBtn = new JButton("입고 수정");
		enteringChangeViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		enteringChangeViewBtn.setBounds(5, 60, 290, 50);
		enteringManagementPnl.add(enteringChangeViewBtn);
		
		enteringDeleteViewBtn = new JButton("입고 삭제");
		enteringDeleteViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		enteringDeleteViewBtn.setBounds(5, 115, 290, 50);
		enteringManagementPnl.add(enteringDeleteViewBtn);
		
		enteringReadViewBtn = new JButton("입고 조회");
		enteringReadViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		enteringReadViewBtn.setBounds(5, 170, 290, 50);
		enteringManagementPnl.add(enteringReadViewBtn);
		
		barcodePrintBtn = new JButton("바코드 출력");
		barcodePrintBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		barcodePrintBtn.setBounds(5, 225, 290, 50);
		enteringManagementPnl.add(barcodePrintBtn);
		enteringManagementPnl.setVisible(false);
		
		// button in ReturnAndDiscardManagement
		returnAndDiscardRegisterViewBtn = new JButton("판품 및 폐기 등록");
		returnAndDiscardRegisterViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		returnAndDiscardRegisterViewBtn.setBounds(5, 5, 290, 50);
		returnDiscardManagementPnl.add(returnAndDiscardRegisterViewBtn);
		
		returnAndDiscardFinalizeRegisterationViewBtn = new JButton("반품 및 폐기 확정");
		returnAndDiscardFinalizeRegisterationViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		returnAndDiscardFinalizeRegisterationViewBtn.setBounds(5, 60, 290, 50);
		returnDiscardManagementPnl.add(returnAndDiscardFinalizeRegisterationViewBtn);
		
		returnAndDiscardCancelRegistrationViewBtn = new JButton("반품 및 폐기 등록 취소");
		returnAndDiscardCancelRegistrationViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		returnAndDiscardCancelRegistrationViewBtn.setBounds(5, 115, 290, 50);
		returnDiscardManagementPnl.add(returnAndDiscardCancelRegistrationViewBtn);
		
		returnAndDiscardReadViewBtn = new JButton("반품 및 폐기 조회");
		returnAndDiscardReadViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		returnAndDiscardReadViewBtn.setBounds(5, 170, 290, 50);
		returnDiscardManagementPnl.add(returnAndDiscardReadViewBtn);
		returnDiscardManagementPnl.setVisible(false);
		
		// button in SupplierManagement 
		supplierRegisterViewBtn = new JButton("거래처 등록");
		supplierRegisterViewBtn.setBounds(5, 5, 290, 50);
		supplierRegisterViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		suplierManagementPnl.add(supplierRegisterViewBtn);
		
		supplierChangeViewBtn = new JButton("거래처 수정");
		supplierChangeViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		supplierChangeViewBtn.setBounds(5, 60, 290, 50);
		suplierManagementPnl.add(supplierChangeViewBtn);
		
		supplierDeleteViewBtn = new JButton("거래처 삭제");
		supplierDeleteViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		supplierDeleteViewBtn.setBounds(5, 115, 290, 50);
		suplierManagementPnl.add(supplierDeleteViewBtn);
		
		supplierReadViewBtn = new JButton("거래처 조회");
		supplierReadViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		supplierReadViewBtn.setBounds(5, 170, 290, 50);
		suplierManagementPnl.add(supplierReadViewBtn);
		
		// button in SaleManagement
		saleProductBtn = new JButton("상품 판매");
		saleProductBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		saleProductBtn.setBounds(5, 5, 290, 50);
		saleManagementPnl.add(saleProductBtn);
		
		cancelSaleBtn = new JButton("판매 취소");
		cancelSaleBtn.setFont(new Font("Dialog", Font.PLAIN, 25));
		cancelSaleBtn.setBounds(5, 60, 290, 50);
		saleManagementPnl.add(cancelSaleBtn);
		
		readSaleHistoryBtn = new JButton("판매 이력 조회");
		readSaleHistoryBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		readSaleHistoryBtn.setBounds(5, 115, 290, 50);
		saleManagementPnl.add(readSaleHistoryBtn);
		
		// button in MemberManagement 
		registerMemberViewBtn = new JButton("고객 등록");
		registerMemberViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		registerMemberViewBtn.setBounds(5, 5, 290, 50);
		memberManagementPnl.add(registerMemberViewBtn);
		
		changeMemberViewBtn = new JButton("고객 수정");
		changeMemberViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		changeMemberViewBtn.setBounds(5, 60, 290, 50);
		memberManagementPnl.add(changeMemberViewBtn);
		
		readMemberViewBtn = new JButton("고객 삭제");
		readMemberViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		readMemberViewBtn.setBounds(5, 115, 290, 50);
		memberManagementPnl.add(readMemberViewBtn);
		
		setConcernedViewBtn = new JButton("관심 고객 등록");
		setConcernedViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		setConcernedViewBtn.setBounds(5, 170, 290, 50);
		memberManagementPnl.add(setConcernedViewBtn);
		memberManagementPnl.setVisible(false);
		
		// button in DashBoardManagement 
		statisticsViewBtn = new JButton("통계 자료 조회");
		statisticsViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		statisticsViewBtn.setBounds(5, 5, 290, 50);
		dashBoardManagementPnl.add(statisticsViewBtn);
		
		operationResultViewBtn = new JButton("운영 실적 조회");
		operationResultViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		operationResultViewBtn.setBounds(5, 60, 290, 50);
		dashBoardManagementPnl.add(operationResultViewBtn);
		dashBoardManagementPnl.setVisible(false);
		
		// button in DailySalesManagement
		dailyClosingViewBtn = new JButton("일일 마감");
		dailyClosingViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		dailyClosingViewBtn.setBounds(5, 5, 290, 50);
		dailySalesManagementPnl.add(dailyClosingViewBtn);
		
		dailyReportReadViewBtn = new JButton("일보 조회");
		dailyReportReadViewBtn.setFont(new Font("굴림", Font.PLAIN, 25));
		dailyReportReadViewBtn.setBounds(5, 60, 290, 50);
		dailySalesManagementPnl.add(dailyReportReadViewBtn);
		
		monthlyReportReadView = new JButton("월보 조회");
		monthlyReportReadView.setBounds(5, 115, 290, 50);
		dailySalesManagementPnl.add(monthlyReportReadView);
		monthlyReportReadView.setFont(new Font("굴림", Font.PLAIN, 25));
		
		// button in RecordManagement
		productRecordViewBtn = new JButton("상품 이력 조회");
		productRecordViewBtn.setFont(new Font("Dialog", Font.PLAIN, 25));
		productRecordViewBtn.setBounds(5, 5, 290, 50);
		recordManagementPnl.add(productRecordViewBtn);
		
		enteringRecordViewBtn = new JButton("입고 내역 조회");
		enteringRecordViewBtn.setFont(new Font("Dialog", Font.PLAIN, 25));
		enteringRecordViewBtn.setBounds(5, 60, 290, 50);
		recordManagementPnl.add(enteringRecordViewBtn);
		
		closingRecordViewBtn = new JButton("일일 마감 조회");
		closingRecordViewBtn.setFont(new Font("Dialog", Font.PLAIN, 25));
		closingRecordViewBtn.setBounds(5, 115, 290, 50);
		recordManagementPnl.add(closingRecordViewBtn);
	}
	
	public void MainMenuViewInitialListener() {
		mainMenuView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
			}
		});
		
		productManagementBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				currClickedPanel = productManagementPnl;
				currClickedPanel.setVisible(true);
			}
		});
		enteringManagementBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				currClickedPanel = enteringManagementPnl;
				currClickedPanel.setVisible(true);
			}
		});
		returnDiscardManagementBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				currClickedPanel = returnDiscardManagementPnl;
				currClickedPanel.setVisible(true);
			}
		});
		suplierManagementBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				currClickedPanel = suplierManagementPnl;
				currClickedPanel.setVisible(true);
			}
		});
		saleManagementBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				currClickedPanel = saleManagementPnl;
				currClickedPanel.setVisible(true);
			}
		});
		memberManagementBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				currClickedPanel = memberManagementPnl;
				currClickedPanel.setVisible(true);
			}
		});
		dashBoardManagementBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				currClickedPanel = dashBoardManagementPnl;
				currClickedPanel.setVisible(true);
			}
		});
		dailySalesManagementBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				currClickedPanel = dailySalesManagementPnl;
				currClickedPanel.setVisible(true);
			}
		});
		recordManagementBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				currClickedPanel = recordManagementPnl;
				currClickedPanel.setVisible(true);
			}
		});
		settingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
			}
		});
		
		// button in ProductManagement 
		productRegisterViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.PRODUCT_REGISTER);
			}
		});
		productListReadViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.PRODUCTLIST_READ);
			}
		});
		productChangeViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.PRODUCT_CHANGE);
			}
		});
		
		// button in EnteringManagement 
		enteringRegisterViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.ENTERING_REGISTER);
			}
		});
		enteringChangeViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.ENTERING_CHANGE);
			}
		});
		enteringDeleteViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.ENTERING_DELETE);
			}
		});
		enteringReadViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.ENTERING_READ);
			}
		});
		barcodePrintBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.BARCODE_PRINT);
			}
		});
		
		// button in ReturnAndDiscardManagement
		returnAndDiscardRegisterViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.RETURN_DISCARD_REGISTER);
			}
		});
		returnAndDiscardFinalizeRegisterationViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.RETURN_DISCARD_FINALIZE);
			}
		});
		returnAndDiscardCancelRegistrationViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.RETURN_DISCARD_CANCEL);
			}
		});
		returnAndDiscardReadViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.RETURN_DISCARD_READ);
			}
		});
		
		// button in SupplierManagement 
		supplierRegisterViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.SUPPLIER_REGISTER);
			}
		});
		supplierChangeViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.SUPPLIER_CHANGE);
			}
		});
		supplierDeleteViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.SUPPLIER_DELETE);
			}
		});
		supplierReadViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.SUPPLIER_READ);
			}
		});
		
		// button in SaleManagement 
		saleProductBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.SALE_PRODUCT);
			}
		});
		cancelSaleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.SALE_CANCEL);
			}
		});
		readSaleHistoryBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.SALE_HISTORY);
			}
		});
		
		// button in MemberManagement 
		registerMemberViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.MEMBER_REGISTER);
			}
		});
		changeMemberViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.MEMBER_CHANGE);
			}
		});
		readMemberViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.MEMBER_READ);
			}
		});
		setConcernedViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.MEMBER_SET_CONCERNED);
			}
		});
		
		// button in DashBoardManagement 
		statisticsViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.DASHBOARD_STATISTICS);
			}
		});
		operationResultViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.DASHBOARD_OPERATION_RESULT);
			}
		});
		
		// button in DailySalesManagement
		dailyClosingViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.DAILY_CLOSING);
			}
		});
		dailyReportReadViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.DAILY_REPORT_READ);
			}
		});
		monthlyReportReadView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.MONTHLY_READ);
			}
		});
		
		// button in RecordManagement
		productRecordViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.RECORD_PRODUCT);
			}
		});
		enteringRecordViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.RECORD_ENTERING);
			}
		});
		closingRecordViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currClickedPanel != null) currClickedPanel.setVisible(false);
				changePanel(Views.RECORD_CLOSING);
			}
		});
	}
	
	// 요청된 화면에 해당하는 panel을 currPanel로 지정하고 frame 화면 최신화된 currPanel로 초기화
	public void changePanel(Views view)
	{
		JPanel tmp = null;
		switch (view)
		{
		case MAIN_MANU: 				currPanel = mainMenuView; 				break;
		case PRODUCT_REGISTER: 	currPanel = (JPanel)(new ProductRegisterView()).getContentPane();		break;
		case PRODUCTLIST_READ: 	currPanel = (JPanel)(new ProductListReadView()).getContentPane();		break;
		case PRODUCT_CHANGE: 	currPanel = (JPanel)(new ProductChangeView()).getContentPane();			break;
		case ENTERING_REGISTER:	currPanel = (JPanel)(new EnteringRegisterView()).getContentPane(); 		break;
		case ENTERING_CHANGE: 	currPanel = (JPanel)(new EnteringChangeView()).getContentPane(); 		break;
		case ENTERING_DELETE: 	currPanel = (JPanel)(new EnteringDeleteView()).getContentPane(); 			break;
		case ENTERING_READ: 		currPanel = (JPanel)(new EnteringReadView()).getContentPane(); 			break;
		case BARCODE_PRINT: 		currPanel = (JPanel)(new BarcodePrintView()).getContentPane(); 			break;
		case RETURN_DISCARD_REGISTER: 	currPanel = (JPanel)(new ReturnAndDiscardRegisterView()).getContentPane();						break;
		case RETURN_DISCARD_FINALIZE: 	currPanel = (JPanel)(new ReturnAndDiscardFinalizeRegisterationView()).getContentPane();	break;
		case RETURN_DISCARD_CANCEL: 		currPanel = (JPanel)(new ReturnAndDiscardCancelRegisterationView()).getContentPane();		break;
		case RETURN_DISCARD_READ: 			currPanel = (JPanel)(new ReturnAndDiscardReadView()).getContentPane();								break;
		case SUPPLIER_REGISTER:	currPanel = (JPanel)(new SupplierRegisterView()).getContentPane();		break;
		case SUPPLIER_CHANGE:	currPanel = (JPanel)(new SupplierChangeView()).getContentPane();			break;
		case SUPPLIER_DELETE:		currPanel = (JPanel)(new SupplierDeleteView()).getContentPane();			break;
		case SUPPLIER_READ:		currPanel = (JPanel)(new SupplierReadView()).getContentPane();				break;
		case SALE_PRODUCT: 			currPanel = saleProductView; 			saleProductView.setStatus(true);		break;
		case SALE_CANCEL: 			currPanel = new CancelSaleView(); 															break;
		case SALE_HISTORY: 			currPanel = new SaleHistoryView(); 															break;
		case MEMBER_REGISTER: 
			currPanel = new JPanel();
			currPanel.setLayout(null);
			tmp = (JPanel)(new RegisterMemberView()).getContentPane();
			tmp.setBounds(250, 100, 1000, 600);
			currPanel.add(tmp);
			break;
		case MEMBER_CHANGE:
			currPanel = new JPanel();
			currPanel.setLayout(null);
			tmp = (JPanel)(new ChangeMemberView()).getContentPane();	
			tmp.setBounds(250, 100, 1000, 600);
			currPanel.add(tmp);
			break;
		case MEMBER_READ:
			currPanel = new JPanel();
			currPanel.setLayout(null);
			tmp = (JPanel)(new ReadMemberView()).getContentPane();	
			tmp.setBounds(250, 100, 1000, 600);
			currPanel.add(tmp);
			break;
		case MEMBER_SET_CONCERNED:
			currPanel = new JPanel();
			currPanel.setLayout(null);
			tmp = (JPanel)(new SetConcernedView()).getContentPane();
			tmp.setBounds(250, 100, 1000, 600);
			currPanel.add(tmp);
			break;
		case DASHBOARD_STATISTICS:					currPanel = (JPanel)(new StatisticsView()).getContentPane();				break;
		case DASHBOARD_OPERATION_RESULT: 	currPanel = (JPanel)(new OperationResultView()).getContentPane();		break;
		case DAILY_CLOSING:			currPanel = (JPanel)(new DailyClosingView()).getContentPane();				break;
		case DAILY_REPORT_READ:currPanel = (JPanel)(new DailyReportReadView()).getContentPane();		break;
		case MONTHLY_READ: 		currPanel = (JPanel)(new MonthlyReportReadView()).getContentPane();	break;
		case RECORD_PRODUCT: 	currPanel = (JPanel)(new ProductRecordView()).getContentPane();			break;
		case RECORD_ENTERING: 	currPanel = (JPanel)(new EnteringRecordView()).getContentPane();			break;
		case RECORD_CLOSING: 		currPanel = (JPanel)(new ClosingRecordView()).getContentPane();			break;
		default: return;
		}
		
		getContentPane().removeAll();
		setResizable(false);
		getContentPane().add(currPanel);
		showView();
	}
}

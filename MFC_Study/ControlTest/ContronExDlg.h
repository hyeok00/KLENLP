
// ContronExDlg.h: 헤더 파일
//

#pragma once


// CContronExDlg 대화 상자
class CContronExDlg : public CDialogEx
{
// 생성입니다.
public:
	CContronExDlg(CWnd* pParent = nullptr);	// 표준 생성자입니다.

// 대화 상자 데이터입니다.
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_CONTRONEX_DIALOG };
#endif

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV 지원입니다.


// 구현입니다.
protected:
	HICON m_hIcon;

	// 생성된 메시지 맵 함수
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()

public:
	int m_nScale;
	int m_nHorizontal;
	int m_nVertical;
	int m_nSelObject; // 0:원, 1:사각형, 2:마름모
	bool m_bSameRatio; // 정 비율 체크 여부
	int m_nCurHScale; // 현재 수평 비율
	int m_nCurVScale; // 현재 수직 비율
	int m_edit1;
	int m_edit2;
	bool check;
	CSliderCtrl m_sliderHorizontal;
	CSliderCtrl m_sliderScale;
	CSliderCtrl m_sliderVertical;
	CProgressCtrl m_pgrsScale;
	afx_msg void OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
	afx_msg void OnEnChangeEditHorizontal();
	afx_msg void OnEnChangeEditVertical();
	CStatic m_pic;
	afx_msg void OnBnClickedRadioRectangle();
	afx_msg void OnBnClickedRadioCircle();
	afx_msg void OnBnClickedRadioDiamond();
	CButton m_checkbutton;
	afx_msg void OnBnClickedCheckSameRatio();
	afx_msg void OnBnClickedButtonScale();
	afx_msg void OnTimer(UINT_PTR nIDEvent);
	CButton m_animationbtn;
};

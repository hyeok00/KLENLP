
// ContronExDlg.cpp: 구현 파일
//

#include "pch.h"
#include "framework.h"
#include "ContronEx.h"
#include "ContronExDlg.h"
#include "afxdialogex.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif
#define SIZE_X 340
#define SIZE_Y 370

// 응용 프로그램 정보에 사용되는 CAboutDlg 대화 상자입니다.

class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

	// 대화 상자 데이터입니다.
#ifdef AFX_DESIGN_TIME
	enum { IDD = IDD_ABOUTBOX };
#endif

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 지원입니다.

// 구현입니다.
protected:
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialogEx(IDD_ABOUTBOX)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
END_MESSAGE_MAP()


// CContronExDlg 대화 상자



CContronExDlg::CContronExDlg(CWnd* pParent /*=nullptr*/)
	: CDialogEx(IDD_CONTRONEX_DIALOG, pParent)
	, m_edit1(0)
	, m_edit2(0)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CContronExDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Text(pDX, IDC_EDIT1, m_edit1);
	DDX_Text(pDX, IDC_EDIT2, m_edit2);
	DDX_Control(pDX, IDC_SLIDER_HORIZONTAL, m_sliderHorizontal);
	DDX_Control(pDX, IDC_SLIDER_SCALE, m_sliderScale);
	DDX_Control(pDX, IDC_SLIDER_VERTICAL, m_sliderVertical);
	DDX_Control(pDX, IDC_PROGRESS_SCALE, m_pgrsScale);
	DDX_Control(pDX, IDC_STATIC_VIEW, m_pic);

	DDX_Control(pDX, IDC_CHECK_SAME_RATIO, m_checkbutton);
	DDX_Control(pDX, IDC_BUTTON_SCALE, m_animationbtn);
}

BEGIN_MESSAGE_MAP(CContronExDlg, CDialogEx)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_WM_HSCROLL()
	ON_EN_CHANGE(IDC_EDIT_HORIZONTAL, &CContronExDlg::OnEnChangeEditHorizontal)
	ON_EN_CHANGE(IDC_EDIT_VERTICAL, &CContronExDlg::OnEnChangeEditVertical)
	ON_BN_CLICKED(IDC_RADIO_CIRCLE, &CContronExDlg::OnBnClickedRadioCircle)
	ON_BN_CLICKED(IDC_RADIO_DIAMOND, &CContronExDlg::OnBnClickedRadioDiamond)
	ON_BN_CLICKED(IDC_RADIO_RECTANGLE, &CContronExDlg::OnBnClickedRadioRectangle)
	ON_BN_CLICKED(IDC_CHECK_SAME_RATIO, &CContronExDlg::OnBnClickedCheckSameRatio)
	ON_BN_CLICKED(IDC_BUTTON_SCALE, &CContronExDlg::OnBnClickedButtonScale)
	ON_WM_TIMER()
END_MESSAGE_MAP()


// CContronExDlg 메시지 처리기

BOOL CContronExDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// 시스템 메뉴에 "정보..." 메뉴 항목을 추가합니다.

	// IDM_ABOUTBOX는 시스템 명령 범위에 있어야 합니다.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != nullptr)
	{
		BOOL bNameValid;
		CString strAboutMenu;
		bNameValid = strAboutMenu.LoadString(IDS_ABOUTBOX);
		ASSERT(bNameValid);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// 이 대화 상자의 아이콘을 설정합니다.  응용 프로그램의 주 창이 대화 상자가 아닐 경우에는
	//  프레임워크가 이 작업을 자동으로 수행합니다.
	SetIcon(m_hIcon, TRUE);			// 큰 아이콘을 설정합니다.
	SetIcon(m_hIcon, FALSE);		// 작은 아이콘을 설정합니다.

	// TODO: 여기에 추가 초기화 작업을 추가합니다.

	m_nSelObject = 2;
	((CButton*)GetDlgItem(IDC_RADIO_DIAMOND))->SetCheck(TRUE);
	m_nCurHScale = m_nHorizontal = 50;
	m_nCurVScale = m_nVertical = 50;
	m_nScale = 0;
	SetDlgItemInt(IDC_EDIT1, m_nHorizontal);
	SetDlgItemInt(IDC_EDIT2, m_nVertical);

	m_sliderHorizontal.SetRange(0, 100);
	m_sliderVertical.SetRange(0, 100);
	m_sliderScale.SetRange(0, 100);
	m_pgrsScale.SetRange(0, 100);

	m_sliderHorizontal.SetPos(50);
	m_sliderVertical.SetPos(50);
	m_sliderScale.SetPos(0);
	m_pgrsScale.SetPos(0);
	check = false;
	return TRUE;  // 포커스를 컨트롤에 설정하지 않으면 TRUE를 반환합니다.
}

void CContronExDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialogEx::OnSysCommand(nID, lParam);
	}
}

// 대화 상자에 최소화 단추를 추가할 경우 아이콘을 그리려면
//  아래 코드가 필요합니다.  문서/뷰 모델을 사용하는 MFC 애플리케이션의 경우에는
//  프레임워크에서 이 작업을 자동으로 수행합니다.

void CContronExDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // 그리기를 위한 디바이스 컨텍스트입니다.

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// 클라이언트 사각형에서 아이콘을 가운데에 맞춥니다.
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// 아이콘을 그립니다.
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		//CDC* p = m_pic.GetWindowDC();
		//CRect rect;
		//CStatic* staticSize = (CStatic*)GetDlgItem(IDC_STATIC_VIEW);
		//staticSize->GetClientRect(&rect);
		//InvalidateRect(rect);
		//CBrush brush1(RGB(255, 0, 0));
		//CBrush* oldBrush = p->SelectObject(&brush1);

		//int a = rect.Width() / 2; // picturebox 가로크기
		//int b = rect.Height() / 2; //picturebox 세로크기

		//int x_ratio = 2 * m_nHorizontal;
		//int y_ratio = 2 * m_nVertical;

		//POINT arPt[4] = { {a - x_ratio ,b - y_ratio},{a + x_ratio,b - y_ratio},{a + x_ratio,b + y_ratio},{a - x_ratio,b + y_ratio} };
		//POINT Dia[4] = { {a,b - y_ratio},{a + x_ratio,b},{a,b + y_ratio},{a - x_ratio,b} };
		//switch (m_nSelObject)
		//{
		//case 0:
		//	p->Ellipse(a - x_ratio, b - y_ratio, a + x_ratio, b + y_ratio);
		//	break;
		//case 1:
		//	p->Polygon(arPt, 4);
		//	break;
		//case 2:
		//	p->Polygon(Dia, 4);
		//	break;
		//default:
		//	break;
		//}
		//CDialogEx::OnPaint();
		//
		CPaintDC dc(this);
		CRect rect;
		CStatic* staticSize = (CStatic*)GetDlgItem(IDC_STATIC_VIEW);
		staticSize->GetClientRect(&rect);

		CBrush brush1(RGB(255, 0, 0));
		CBrush* oldBrush = dc.SelectObject(&brush1);

		int a = rect.Width() / 2; // picturebox 가로크기
		int b = rect.Height() / 2; //picturebox 세로크기

		int x_ratio = 1.5 * m_nHorizontal;
		int y_ratio = 1.5 * m_nVertical;

		POINT arPt[4] = { {a - x_ratio ,b - y_ratio},{a + x_ratio,b - y_ratio},{a + x_ratio,b + y_ratio},{a - x_ratio,b + y_ratio} };
		POINT Dia[4] = { {a,b - y_ratio},{a + x_ratio,b},{a,b + y_ratio},{a - x_ratio,b} };

		CDC* p = m_pic.GetWindowDC();
		dc.SelectObject(p);
		p->SelectObject(brush1);

		switch (m_nSelObject)
		{
		case 0:
			//dc.Ellipse(a - x_ratio, b - y_ratio, a + x_ratio, b + y_ratio);
			p->Ellipse(a - x_ratio, b - y_ratio, a + x_ratio, b + y_ratio);
			break;
		case 1:
			p->Polygon(arPt, 4);
			break;
		case 2:
			p->Polygon(Dia, 4);
			break;
		default:
			break;
		}
		CDialogEx::OnPaint();
	}


}

// 사용자가 최소화된 창을 끄는 동안에 커서가 표시되도록 시스템에서
//  이 함수를 호출합니다.
HCURSOR CContronExDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}



void CContronExDlg::OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar)
{
	// TODO: 여기에 메시지 처리기 코드를 추가 및/또는 기본값을 호출합니다.

	if (pScrollBar == (CScrollBar*)&m_sliderScale)
	{
		int temp = m_sliderScale.GetPos();
		m_pgrsScale.SetPos(temp);
		m_nHorizontal = m_edit1 * temp / 100;
		m_nVertical = m_edit2 * temp / 100;
	}
	else
	{
		m_sliderScale.SetPos(0);
		m_pgrsScale.SetPos(0);

		if (m_checkbutton.GetCheck()) // 정비율 상태
		{
			if (m_nHorizontal != m_sliderHorizontal.GetPos())
			{
				m_edit1 = m_sliderHorizontal.GetPos();
				m_edit2 = m_sliderHorizontal.GetPos();
				m_sliderVertical.SetPos(m_edit2);
				m_nHorizontal = m_nVertical = m_sliderHorizontal.GetPos();

			}
			if (m_nVertical != m_sliderVertical.GetPos())
			{
				m_edit1 = m_sliderVertical.GetPos();
				m_edit2 = m_sliderVertical.GetPos();
				m_sliderHorizontal.SetPos(m_edit2);
				m_nHorizontal = m_nVertical = m_sliderVertical.GetPos();
			}
		}
		else
		{
			m_edit1 = m_sliderHorizontal.GetPos();
			m_edit2 = m_sliderVertical.GetPos();
			m_nHorizontal = m_edit1;
			m_nVertical = m_edit2;
		}
	}

	UpdateData(FALSE);
	HWND PictureControlhWnd;
	CRect ViewRect;
	PictureControlhWnd = GetDlgItem(IDC_STATIC_VIEW)->GetSafeHwnd();
	::GetClientRect(PictureControlhWnd, &ViewRect);
	ViewRect.SetRect(0, 0, SIZE_X, SIZE_Y);
	InvalidateRect(&ViewRect, TRUE);
	CDialogEx::OnHScroll(nSBCode, nPos, pScrollBar);
}


void CContronExDlg::OnEnChangeEditHorizontal()
{
	// TODO:  RICHEDIT 컨트롤인 경우, 이 컨트롤은
	// CDialogEx::OnInitDialog() 함수를 재지정 
	//하고 마스크에 OR 연산하여 설정된 ENM_CHANGE 플래그를 지정하여 CRichEditCtrl().SetEventMask()를 호출하지 않으면
	// 이 알림 메시지를 보내지 않습니다.
	if (UpdateData(TRUE))
		m_sliderHorizontal.SetPos(m_edit1);

	// TODO:  여기에 컨트롤 알림 처리기 코드를 추가합니다.
}


void CContronExDlg::OnEnChangeEditVertical()
{
	// TODO:  RICHEDIT 컨트롤인 경우, 이 컨트롤은
	// CDialogEx::OnInitDialog() 함수를 재지정 
	//하고 마스크에 OR 연산하여 설정된 ENM_CHANGE 플래그를 지정하여 CRichEditCtrl().SetEventMask()를 호출하지 않으면
	// 이 알림 메시지를 보내지 않습니다.
	if (UpdateData(TRUE))
		m_sliderVertical.SetPos(m_edit2);

	// TODO:  여기에 컨트롤 알림 처리기 코드를 추가합니다.
}


void CContronExDlg::OnBnClickedRadioRectangle()
{
	m_nSelObject = 1;

	HWND PictureControlhWnd;
	CRect ViewRect;
	PictureControlhWnd = GetDlgItem(IDC_STATIC_VIEW)->GetSafeHwnd();
	::GetClientRect(PictureControlhWnd, &ViewRect);
	ViewRect.SetRect(0, 0, SIZE_X, SIZE_Y);
	InvalidateRect(&ViewRect, TRUE);
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
}


void CContronExDlg::OnBnClickedRadioCircle()
{
	m_nSelObject = 0;

	HWND PictureControlhWnd;
	CRect ViewRect;
	PictureControlhWnd = GetDlgItem(IDC_STATIC_VIEW)->GetSafeHwnd();
	::GetClientRect(PictureControlhWnd, &ViewRect);
	ViewRect.SetRect(0, 0, SIZE_X, SIZE_Y);
	InvalidateRect(&ViewRect, TRUE);
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
}


void CContronExDlg::OnBnClickedRadioDiamond()
{
	m_nSelObject = 2;

	HWND PictureControlhWnd;
	CRect ViewRect;
	PictureControlhWnd = GetDlgItem(IDC_STATIC_VIEW)->GetSafeHwnd();
	::GetClientRect(PictureControlhWnd, &ViewRect);
	ViewRect.SetRect(0, 0, SIZE_X, SIZE_Y);
	InvalidateRect(&ViewRect, TRUE);
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
}


void CContronExDlg::OnBnClickedCheckSameRatio()
{

	if (m_sliderHorizontal.GetPos() >= m_sliderVertical.GetPos())
	{
		m_edit2 = m_nVertical = m_sliderHorizontal.GetPos();
		m_sliderVertical.SetPos(m_edit2);
	}
	else
	{
		m_edit1 = m_nHorizontal = m_sliderVertical.GetPos();
		m_sliderHorizontal.SetPos(m_edit1);
	}

	HWND PictureControlhWnd;
	CRect ViewRect;
	PictureControlhWnd = GetDlgItem(IDC_STATIC_VIEW)->GetSafeHwnd();
	::GetClientRect(PictureControlhWnd, &ViewRect);
	ViewRect.SetRect(0, 0, SIZE_X, SIZE_Y);
	InvalidateRect(&ViewRect, TRUE);

	UpdateData(FALSE);
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
}


void CContronExDlg::OnBnClickedButtonScale()
{
	m_pgrsScale.SetPos(0);
	m_sliderScale.SetPos(0);
	SetTimer(1, 10, NULL);
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
}


void CContronExDlg::OnTimer(UINT_PTR nIDEvent)
{
	// TODO: 여기에 메시지 처리기 코드를 추가 및/또는 기본값을 호출합니다.
	if (nIDEvent == 1)
	{
		if (m_pgrsScale.GetPos() < 100)
		{
			GetDlgItem(IDC_BUTTON1)->EnableWindow(FALSE);
			m_sliderScale.SetPos(m_sliderScale.GetPos()+1);
			m_pgrsScale.OffsetPos(1);
			m_nHorizontal = m_sliderScale.GetPos() * m_edit1 / 100;
			m_nVertical = m_sliderScale.GetPos() * m_edit2 / 100;
			UpdateData(FALSE);
			HWND PictureControlhWnd;
			CRect ViewRect;
			PictureControlhWnd = GetDlgItem(IDC_STATIC_VIEW)->GetSafeHwnd();
			::GetClientRect(PictureControlhWnd, &ViewRect);
			ViewRect.SetRect(0, 0, SIZE_X, SIZE_Y);
			InvalidateRect(&ViewRect, TRUE);
		}
		else
		{
			KillTimer(1);
			GetDlgItem(IDC_BUTTON1)->EnableWindow(TRUE);
		}
	}
	CDialogEx::OnTimer(nIDEvent);
}

//======================================================================
// Win32 API Template - Douglas Boling 코드
//
//======================================================================
#include <windows.h>                 // 윈도우 관련 정의
#include "TypingPractice.h"          // 프로그램 관련 정의


#define ID_TIMER 1
//----------------------------------------------------------------------
// 전역 데이터
//
static const LPCWSTR szAppName = TEXT("TypingPractice");   // 프로그램 이름
HINSTANCE hInst;								// 프로그램 인스턴스 핸들
HWND hwndMain;									// 메인 윈도우 핸들
TCHAR wordlist[10][15] = { TEXT("Boy"),TEXT("Sister"),TEXT("Girl"),
							TEXT("Lion"),TEXT("Movie"),TEXT("Mother"),
							TEXT("Cat"),TEXT("Monkey"),TEXT("Animal"),
							TEXT("Apple") };

LPSTR wordlist2[10] = { "Boy","Sister","Girl",
							"Lion","Movie","Mother",
							"Cat","Monkey","Animal",
							"Apple" };
typedef struct Mystruct
{
	RECT r;
	LPSTR name;
	CHAR name2[50];
}Mystruct;

RECT rec[50];
static CHAR szMsg[80] = "";
int temp = 0; // 임시변수
int life = 10; // 라이프
int score = 0; // 점수
Mystruct mst[50]; 
int static arr[5] = { 0,1,2,3,4 };
int static y_pos[5] = { 10,-20,-50,-80,-110 };

//======================================================================
// 프로그램 시작점
//
int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
	LPSTR lpCmdLine, int nCmdShow)
{
	MSG msg;
	int rc = 0;
	for (int i = 0; i < 50; i++)
	{
		int temp = rand() % 400;
		int temp2 = rand() % 10;
		RECT r = { temp,0,temp + 140,20 };
		mst[i].r = r;
		mst[i].name = wordlist[temp2];
		strcpy(mst[i].name2, wordlist2[temp2]);
	}

	// 어플리케이션 초기화
	if (!InitApplication(hInstance))
		return 0;

	// 인스턴스 초기화
	if (!InitInstance(hInstance, lpCmdLine, nCmdShow))
		return 0;

	// 메시지 루프
	while (GetMessage(&msg, NULL, 0, 0)) {
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}

	// 인스턴스 소거
	return TermInstance(hInstance, msg.wParam);
}

//----------------------------------------------------------------------
// InitApp - 어플리케이션 초기화
//
BOOL InitApplication(HINSTANCE hInstance)
{
	WNDCLASS wc;

	// 전역 변수에 인스턴스 핸들 보관
	hInst = hInstance;

	// 주 윈도우 클래스 등록
	wc.style = CS_HREDRAW | CS_VREDRAW | CS_DBLCLKS; // 윈도우 스타일
	wc.lpfnWndProc = MainWndProc;             // 윈도우 프로시저
	wc.cbClsExtra = 0;                        // 추가 클래스 데이터
	wc.cbWndExtra = 0;                        // 추가 윈도우 데이터
	wc.hInstance = hInstance;                 // 소유자 핸들
	wc.hIcon = LoadIcon(NULL, IDI_APPLICATION);   // 프로그램 아이콘
	wc.hCursor = LoadCursor(NULL, IDC_ARROW);// 기본 커서
	wc.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
	wc.lpszMenuName = NULL;                  // 메뉴 이름
	wc.lpszClassName = (LPWSTR)szAppName;     // 윈도우 클래스 이름

	if (!RegisterClass(&wc))
		return FALSE;

	return TRUE;
}

//----------------------------------------------------------------------
// InitInstance - 인스턴스 초기화
//
BOOL InitInstance(HINSTANCE hInstance, LPSTR lpCmdLine, int nCmdShow)
{
	// 주 윈도우 생성
	hwndMain = CreateWindowEx(WS_EX_APPWINDOW,
		szAppName,           // 윈도우 클래스
		szAppName,     // 윈도우 타이틀
		// 스타일 플래그
		WS_OVERLAPPEDWINDOW,  //WS_VISIBLE | WS_SYSMENU | WS_CAPTION,
		CW_USEDEFAULT,       // x 좌표
		CW_USEDEFAULT,       // y 좌표
		800,       // 초기 너비
		500,       // 초기 높이
		NULL,                // 부모 윈도우 핸들
		NULL,                // 메뉴 (NULL로 설정)
		hInstance,           // 응용프로그램 인스턴스
		NULL);               // 생성 매개변수 포인터

	if (!IsWindow(hwndMain))
		return FALSE;  // 윈도우 생성 실패시 작동 실패

	// 윈도우 표시 및 갱신
	ShowWindow(hwndMain, nCmdShow);
	UpdateWindow(hwndMain);
	return TRUE;
}

//----------------------------------------------------------------------
// TermInstance - 프로그램 소거
//
int TermInstance(HINSTANCE hInstance, int nDefRC)
{
	return nDefRC;
}

//======================================================================
// 주 윈도우를 위한 메시지 처리 핸들러
//
//----------------------------------------------------------------------
// MainWndProc - 주 윈도우의 콜백 함수
//
LRESULT CALLBACK MainWndProc(HWND hWnd, UINT wMsg, WPARAM wParam, LPARAM lParam)
{
	int i;
	//
	// 메시지 분배 테이블을 검사하여 해당 메시지를 처리할지 확인한 후
	// 해당 메시지 핸들러를 호출
	//
	for (i = 0; i < dim(MainMessages); i++) {
		if (wMsg == MainMessages[i].Code)
			return (*MainMessages[i].Fxn)(hWnd, wMsg, wParam, lParam);
	}
	return DefWindowProc(hWnd, wMsg, wParam, lParam);
}

//----------------------------------------------------------------------
// DoCreateMain - WM_CREATE 메시지 처리
//
LRESULT DoCreateMain(HWND hWnd, UINT wMsg, WPARAM wParam, LPARAM lParam)
{
	SetTimer(hWnd, ID_TIMER, 100, NULL);
	return 0;
}

//----------------------------------------------------------------------
// DoPaintMain - WM_PAINT 메시지 처리
//
LRESULT DoPaintMain(HWND hWnd, UINT wMsg, WPARAM wParam, LPARAM lParam)
{
	PAINTSTRUCT ps;
	RECT rect;
	HDC hdc;
	TCHAR szMsg[80];
	if (life > 0)
	{

		// 클라이언트 영역의 크기 계산
		GetClientRect(hWnd, &rect);
		hdc = BeginPaint(hWnd, &ps);

		wsprintf(szMsg, TEXT("Life = %d / Score = %d :"), life, score);
		DrawText(hdc, szMsg, -1, &rect,
			DT_LEFT | DT_SINGLELINE);

		for (int i = 0; i < 5; i++) // 텍스트 출력
		{
			DrawText(hdc, mst[arr[i]].name, -1, &mst[arr[i]].r, DT_CENTER | DT_SINGLELINE);
		}

		for (int i = 0; i < 5; i++)
		{
			if (y_pos[i % 5] <= rect.bottom - 100)
			{
				mst[arr[i]].r.top = rect.top + 20 + y_pos[arr[i] % 5];
				mst[arr[i]].r.bottom = rect.top + 40 + y_pos[arr[i] % 5];
			}
			else
			{
				life--;
				arr[i] += 5;
				y_pos[i % 5] = 0;
			}
			y_pos[i % 5] += 5;
		}

		HBRUSH brush;
		brush = (HBRUSH)SelectObject(hdc, CreateSolidBrush(RGB(255, 0, 0)));
		Rectangle(hdc, rect.left, rect.bottom - 60, rect.right, rect.bottom - 50);

		EndPaint(hWnd, &ps);
	}
	return 0;
}
//----------------------------------------------------------------------
// DoTimerMain - WM_Timer 메시지 처리
//
LRESULT DoTimerMain(HWND hWnd, UINT wMsg, WPARAM wParam, LPARAM lParam)
{
	UINT timer = wParam;
	if (timer == ID_TIMER)
		InvalidateRect(hWnd, NULL, TRUE);
	return 0;
}
//----------------------------------------------------------------------
// DoKeyChar - WM_CHAR 메시지 처리
//
LRESULT DoKeyChar(HWND hWnd, UINT wMsg, WPARAM wParam, LPARAM lParam)
{
	TCHAR code;
	code = wParam;

	if (code == VK_RETURN)
	{
		for (int i = 0; i < 5; i++)
		{
			if (!strcmp(szMsg, mst[arr[i]].name2))
			{
				y_pos[arr[i] % 5] = 0;
				arr[i] += 5;
				score += strlen(szMsg) * 10;
				break;
			}
		}
		strcpy_s(szMsg, 80, "");
	}
	else
	{
		int len;
		len = strlen(szMsg);
		szMsg[len] = (TCHAR)wParam;
		szMsg[len + 1] = 0;
	}
	return 0;
}
//----------------------------------------------------------------------
// DoDestroyMain - WM_DESTROY 메시지 처리
//

LRESULT DoDestroyMain(HWND hWnd, UINT wMsg, WPARAM wParam, LPARAM lParam)
{
	KillTimer(hWnd, ID_TIMER);
	PostQuitMessage(0);
	return 0;
}

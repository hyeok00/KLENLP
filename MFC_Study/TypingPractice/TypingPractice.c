//======================================================================
// Win32 API Template - Douglas Boling �ڵ�
//
//======================================================================
#include <windows.h>                 // ������ ���� ����
#include "TypingPractice.h"          // ���α׷� ���� ����


#define ID_TIMER 1
//----------------------------------------------------------------------
// ���� ������
//
static const LPCWSTR szAppName = TEXT("TypingPractice");   // ���α׷� �̸�
HINSTANCE hInst;								// ���α׷� �ν��Ͻ� �ڵ�
HWND hwndMain;									// ���� ������ �ڵ�
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
int temp = 0; // �ӽú���
int life = 10; // ������
int score = 0; // ����
Mystruct mst[50]; 
int static arr[5] = { 0,1,2,3,4 };
int static y_pos[5] = { 10,-20,-50,-80,-110 };

//======================================================================
// ���α׷� ������
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

	// ���ø����̼� �ʱ�ȭ
	if (!InitApplication(hInstance))
		return 0;

	// �ν��Ͻ� �ʱ�ȭ
	if (!InitInstance(hInstance, lpCmdLine, nCmdShow))
		return 0;

	// �޽��� ����
	while (GetMessage(&msg, NULL, 0, 0)) {
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}

	// �ν��Ͻ� �Ұ�
	return TermInstance(hInstance, msg.wParam);
}

//----------------------------------------------------------------------
// InitApp - ���ø����̼� �ʱ�ȭ
//
BOOL InitApplication(HINSTANCE hInstance)
{
	WNDCLASS wc;

	// ���� ������ �ν��Ͻ� �ڵ� ����
	hInst = hInstance;

	// �� ������ Ŭ���� ���
	wc.style = CS_HREDRAW | CS_VREDRAW | CS_DBLCLKS; // ������ ��Ÿ��
	wc.lpfnWndProc = MainWndProc;             // ������ ���ν���
	wc.cbClsExtra = 0;                        // �߰� Ŭ���� ������
	wc.cbWndExtra = 0;                        // �߰� ������ ������
	wc.hInstance = hInstance;                 // ������ �ڵ�
	wc.hIcon = LoadIcon(NULL, IDI_APPLICATION);   // ���α׷� ������
	wc.hCursor = LoadCursor(NULL, IDC_ARROW);// �⺻ Ŀ��
	wc.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
	wc.lpszMenuName = NULL;                  // �޴� �̸�
	wc.lpszClassName = (LPWSTR)szAppName;     // ������ Ŭ���� �̸�

	if (!RegisterClass(&wc))
		return FALSE;

	return TRUE;
}

//----------------------------------------------------------------------
// InitInstance - �ν��Ͻ� �ʱ�ȭ
//
BOOL InitInstance(HINSTANCE hInstance, LPSTR lpCmdLine, int nCmdShow)
{
	// �� ������ ����
	hwndMain = CreateWindowEx(WS_EX_APPWINDOW,
		szAppName,           // ������ Ŭ����
		szAppName,     // ������ Ÿ��Ʋ
		// ��Ÿ�� �÷���
		WS_OVERLAPPEDWINDOW,  //WS_VISIBLE | WS_SYSMENU | WS_CAPTION,
		CW_USEDEFAULT,       // x ��ǥ
		CW_USEDEFAULT,       // y ��ǥ
		800,       // �ʱ� �ʺ�
		500,       // �ʱ� ����
		NULL,                // �θ� ������ �ڵ�
		NULL,                // �޴� (NULL�� ����)
		hInstance,           // �������α׷� �ν��Ͻ�
		NULL);               // ���� �Ű����� ������

	if (!IsWindow(hwndMain))
		return FALSE;  // ������ ���� ���н� �۵� ����

	// ������ ǥ�� �� ����
	ShowWindow(hwndMain, nCmdShow);
	UpdateWindow(hwndMain);
	return TRUE;
}

//----------------------------------------------------------------------
// TermInstance - ���α׷� �Ұ�
//
int TermInstance(HINSTANCE hInstance, int nDefRC)
{
	return nDefRC;
}

//======================================================================
// �� �����츦 ���� �޽��� ó�� �ڵ鷯
//
//----------------------------------------------------------------------
// MainWndProc - �� �������� �ݹ� �Լ�
//
LRESULT CALLBACK MainWndProc(HWND hWnd, UINT wMsg, WPARAM wParam, LPARAM lParam)
{
	int i;
	//
	// �޽��� �й� ���̺��� �˻��Ͽ� �ش� �޽����� ó������ Ȯ���� ��
	// �ش� �޽��� �ڵ鷯�� ȣ��
	//
	for (i = 0; i < dim(MainMessages); i++) {
		if (wMsg == MainMessages[i].Code)
			return (*MainMessages[i].Fxn)(hWnd, wMsg, wParam, lParam);
	}
	return DefWindowProc(hWnd, wMsg, wParam, lParam);
}

//----------------------------------------------------------------------
// DoCreateMain - WM_CREATE �޽��� ó��
//
LRESULT DoCreateMain(HWND hWnd, UINT wMsg, WPARAM wParam, LPARAM lParam)
{
	SetTimer(hWnd, ID_TIMER, 100, NULL);
	return 0;
}

//----------------------------------------------------------------------
// DoPaintMain - WM_PAINT �޽��� ó��
//
LRESULT DoPaintMain(HWND hWnd, UINT wMsg, WPARAM wParam, LPARAM lParam)
{
	PAINTSTRUCT ps;
	RECT rect;
	HDC hdc;
	TCHAR szMsg[80];
	if (life > 0)
	{

		// Ŭ���̾�Ʈ ������ ũ�� ���
		GetClientRect(hWnd, &rect);
		hdc = BeginPaint(hWnd, &ps);

		wsprintf(szMsg, TEXT("Life = %d / Score = %d :"), life, score);
		DrawText(hdc, szMsg, -1, &rect,
			DT_LEFT | DT_SINGLELINE);

		for (int i = 0; i < 5; i++) // �ؽ�Ʈ ���
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
// DoTimerMain - WM_Timer �޽��� ó��
//
LRESULT DoTimerMain(HWND hWnd, UINT wMsg, WPARAM wParam, LPARAM lParam)
{
	UINT timer = wParam;
	if (timer == ID_TIMER)
		InvalidateRect(hWnd, NULL, TRUE);
	return 0;
}
//----------------------------------------------------------------------
// DoKeyChar - WM_CHAR �޽��� ó��
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
// DoDestroyMain - WM_DESTROY �޽��� ó��
//

LRESULT DoDestroyMain(HWND hWnd, UINT wMsg, WPARAM wParam, LPARAM lParam)
{
	KillTimer(hWnd, ID_TIMER);
	PostQuitMessage(0);
	return 0;
}

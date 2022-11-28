//======================================================================
// ��� ����
//
#ifndef _TypingPractice_H_
#define _TypingPractice_H_
//================================================================
// ������ ���� ��ȯ
#define dim(x) (sizeof(x) / sizeof(x[0])) 

//----------------------------------------------------------------------
// ������ Ÿ�� ����
//
struct decodeUINT {   // �޽����� �Լ��� ���� ��Ű�� ����ü
    UINT Code;
    LRESULT (*Fxn)(HWND, UINT, WPARAM, LPARAM);
}; 

struct decodeCMD {    // �޴� ID�� �Լ��� ���� ��Ű�� ����ü
    UINT Code;
    LRESULT (*Fxn)(HWND, WORD, HWND, WORD);
};

//----------------------------------------------------------------------
// �Լ� ����
//
BOOL InitApplication (HINSTANCE);
BOOL InitInstance (HINSTANCE, LPSTR, int);
int TermInstance (HINSTANCE, int);

// ������ ���ν���
LRESULT CALLBACK MainWndProc (HWND, UINT, WPARAM, LPARAM);

// �޽��� �ڵ鷯
LRESULT DoCreateMain (HWND, UINT, WPARAM, LPARAM);
LRESULT DoPaintMain (HWND, UINT, WPARAM, LPARAM);
LRESULT DoDestroyMain (HWND, UINT, WPARAM, LPARAM);
LRESULT DoTimerMain(HWND, UINT, WPARAM, LPARAM);
LRESULT DoKeyChar(HWND, UINT, WPARAM, LPARAM);

//----------------------------------------------------------------------
// MainWndProc�� ó�� �޽��� �й� ���̺�
const struct decodeUINT MainMessages[] = {
    WM_CREATE, DoCreateMain,
    WM_PAINT, DoPaintMain,
    WM_DESTROY, DoDestroyMain,
	WM_TIMER, DoTimerMain,
	WM_CHAR, DoKeyChar
};

#endif	/* _TypingPractice_ */
function click_write() {

		if (
'<%=session.getAttribute("id") != null%>'
	) {
			location.href = '/Web_Project/include/iframe/write.jsp?category=1'
		} else {
			alert('로그인 후 이용가능합니다.');
		}
	}
function check(){    
    if('<%=session.getAttribute("id") == null%>'){
    	alert('로그인 후 이용가능합니다.');
    	return false;
    }
    return true;
  }
  
function table(){
	 location.href='/Web_Project/include/iframe/table"<%=bbsCategory%>".jsp'
  }
  
var ccc=document.getElementById('botbtn');
ccc.addEventListener('click',table);
  
var aaa=document.getElementById('botbtn1');
aaa.addEventListener('click',update_click);

function update_click() {
    if('<%=adt.getWriter().equals(session.getAttribute("id"))%>'){
       location.href='/Web_Project/include/iframe/update_write.jsp?no=<%=bbsID%>&category=<%=bbsCategory%>'
		} else {
			alert('작성자가 다릅니다.');
		}
	}
	
var bbb=document.getElementById('botbtn2');
bbb.addEventListener('click',delete_click);

function delete_click() {
    if('<%=adt.getWriter().equals(session.getAttribute("id"))%>'){
    	if(confirm('정말 삭제하시겠습니까?')){
    		location.href='/Web_Project/include/iframe/deleteArticle.jsp?no=<%=bbsID%>&category=<%=bbsCategory%>'
			}
		} else {
			alert('작성자가 다릅니다.');
		}
	}
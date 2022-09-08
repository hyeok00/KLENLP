package alba;

public class user {
	UserDAO dao = new UserDAO();
	
	private String id;
	private String pw;
	private String name;
	private String email;
	private String answer;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String id_search(String email,String answer) {
	      return dao.find_Id(email,answer);
	   }
	
	
}

public class People {
	private String ID;
	private String PWD;

	public People(String id, String pwd) {
		ID = id;
		PWD = pwd;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPWD() {
		return PWD;
	}

	public void setPWD(String pWD) {
		PWD = pWD;
	}
}

class Admin extends People {
	public Admin(String id, String pwd) {
		super(id, pwd);
	}
}

class Teacher extends People {
	public Teacher(String id, String pwd) {
		super(id, pwd);
	}

}

class Student extends People {

	private String Code;

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public Student(String id, String pwd) {
		super(id, pwd);
		Code = "00";
	}

}
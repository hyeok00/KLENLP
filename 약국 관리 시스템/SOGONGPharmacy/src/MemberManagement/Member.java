/**
 * @systemName		SOGONG Pharmacy Management System
 * @subSystemName	MemberManagement
 * @className 		Member
 * @author 			정윤서
 */
package MemberManagement;

public class Member {
	private String memberCode;
	private String memberName;
	private String memberPhone;
	private int concernedStatus;
	private String concernedReason;
	
	public Member() {
		this.memberCode = "";
		this.memberName = "";
		this.memberPhone = "";
		this.concernedStatus = 0;
		this.concernedReason = "";
	}
	public Member(String memberCode, String memberName, String memberPhone, int concernedStatus,
			String concernedReason) {
		super();
		this.memberCode = memberCode;
		this.memberName = memberName;
		this.memberPhone = memberPhone;
		this.concernedStatus = concernedStatus;
		this.concernedReason = concernedReason;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public int getConcernedStatus() {
		return concernedStatus;
	}

	public String getConcernReason() {
		return concernedReason;
	}

	public void setMemberCode(String code) {
		this.memberCode = code;
	}

	public void setMemberName(String name) {
		this.memberName = name;
	}

	public void setMemberPhone(String phone) {
		this.memberPhone = phone;
	}

	public void setConcernedStatus(int status) {
		this.concernedStatus = status;
	}

	public void setConcernedReason(String reason) {
		this.concernedReason = reason;
	}
}

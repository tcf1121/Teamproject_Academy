package Back.Datarelated.student.member;

import java.io.Serializable;

public class StudentMemberVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID;
	private String pw;
	private String name;
	private String phone;
	
	public StudentMemberVo() {
	}
	
	public StudentMemberVo(String ID, String pw, String name, String phone) {
		setSId(ID);
		setSPw(pw);
		setSName(name);
		setSPhone(phone);
	}

	
	public String getSId() {return this.ID;}
	public void setSId(String id) {this.ID = id;}
	
	public String getSPw() {return this.pw;}
	public void setSPw(String pw) {this.pw = pw;}
	
	public String getSName() {return this.name;}
	public void setSName(String name) {this.name = name;}
	
	public String getSPhone() {return this.phone;}
	public void setSPhone(String phone) {this.phone = phone;}
}

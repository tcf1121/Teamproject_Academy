package Back.Datarelated.lecturer.member;

import java.io.Serializable;

public class LecturerMemberVo implements Serializable {
	private String ID;
	private String pw;
	private String name;
	private String phone;
	
	public LecturerMemberVo() {
	}
	
	public LecturerMemberVo(String ID, String pw, String name, String phone) {
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

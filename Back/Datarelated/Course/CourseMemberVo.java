package Back.Datarelated.Course;

import java.io.Serializable;

public class CourseMemberVo implements Serializable {
	  private String s_id;
	   private String c_id;
	   private String grade;
	   private String cName;//강좌명
	   private String lName;//강사명
	   private String room;
	   private String t_id;
	   private String sName;//학생 이름
	   private String sPhone;//학생 전화번호
	   
	   public String getSId() {return this.s_id;}
	   public void setSId(String s_id) {this.s_id = s_id;}
	   
	   public String getCId() {return this.c_id;}
	   public void setCId(String c_id) {this.c_id = c_id;}
	   
	   public String getGrade() {return this.grade;}
	   public void setGrade(String grade) {this.grade = grade;}
	   
	   public String getCName() {return this.cName;}
	   public void setCName(String cName) {this.cName = cName;}
	   
	   public String getLName() {return this.lName;}
	   public void setLName(String lName) {this.lName = lName;}
	   
	   public String getRoom() {return this.room;}
	   public void setRoom(String room) {this.room = room;}
	   
	   public String getTId() {return this.t_id;}
	   public void setTId(String t_id) {this.t_id = t_id;}
	   
	   public String getSName() {return this.sName;}
	   public void setSName(String sName) {this.sName = sName;}
	   
	   public String getSPhone() {return this.sPhone;}
	   public void setSPhone(String sPhone) {this.sPhone = sPhone;}
	   
	}
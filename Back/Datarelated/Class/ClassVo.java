package Back.Datarelated.Class;

import java.io.Serializable;

public class ClassVo implements Serializable  {
	private String id;
	private String cName;
	private String room;
	private String t_id;
	private String l_id;
	private String lName;
	
	public String getCId() {return this.id;}
	public void setCId(String id) {this.id = id;}
	
	public String getCName() {return this.cName;}
	public void setCName(String cName) {this.cName = cName;}
	
	public String getRoom() {return this.room;}
	public void setRoom(String room) {this.room = room;}
	
	public String getTId() {return this.t_id;}
	public void setTId(String t_id) {this.t_id = t_id;}
	
	public String getLId() {return this.l_id;}
	public void setLId(String l_id) {this.l_id = l_id;}
	
	public String getLName() {return this.lName;}
	public void setLName(String lName) {this.lName = lName;}

}

package Back.Datarelated.Chatting;

import java.io.Serializable;
import java.time.LocalTime;

public class ChattingVo implements Serializable {
	private String c_id;
	private int id;
	private String sendId;
	private String sendName;
	private String sendTime;
	private String history;
	
	public ChattingVo(String c_id, String sendId, String sendName) {
	      this.c_id = c_id;
	      this.sendId = sendId;
	      this.sendName = sendName;
	   }
	
	public ChattingVo(String c_id, String sendId, String sendName, String sendTime, String history) {
		this.c_id = c_id;
		this.sendId = sendId;
		this.sendName = sendName;
		this.sendTime = sendTime;
		this.history = history;
	}
	
	public ChattingVo(String c_id, int id, String sendId, String sendName, String sendTime, String history) {
		this.c_id = c_id;
		this.id = id;
		this.sendId = sendId;
		this.sendName = sendName;
		this.sendTime = sendTime;
		this.history = history;
	}
	
	public String getChatRoomId() {return this.c_id;}
	public void setChatRoomId(String c_id) {this.c_id = c_id;}
	
	public int getId() {return this.id;}
	public void setId(int id) {this.id = id;}
	
	public String getSendId() {return this.sendId;}
	public void setSendId(String sendId) {this.sendId = sendId;}
	
	public String getSendName() {return this.sendName;}
	public void setSendName(String sendName) {this.sendName = sendName;}
	
	public String getRTime() {return this.sendTime;}
	public void setRTime(String sendTime) {this.sendTime = sendTime;}
	
	public String getHistory() {return this.history;}
	public void setHistory(String history) {this.history = history;}
}

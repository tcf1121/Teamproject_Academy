package Back.Datarelated;

import java.io.Serializable;

public class Protocol implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String Command;
	Object Object;
	
	public Protocol(String command, Object object)  {
		Command = command;
		Object = object;
	}
	public String getCommand() {
		return Command;
	}
	public Object getObject() {
		return Object;
	}
	public void setCommand(String command) {
		Command = command;
	}
	public void setObject(Object object) {
		Object = object;
	}
}

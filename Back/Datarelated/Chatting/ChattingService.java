package Back.Datarelated.Chatting;

import java.io.IOException;
import java.util.List;
import Back.Server.MySocketClient;

public class ChattingService {

	private MySocketClient Socket;
	private ChattingVo user;
	
	public boolean addChat(ChattingVo chat) {
		try {
			Socket = new MySocketClient();
			if(Socket.addChat(chat)) {
	        	return true;
	        }
	        else 
	        	return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		return false;
   }
	public ChattingVo getChat(String c_id) {
	       ChattingVo chat;
	       try {
	         Socket = new MySocketClient();
	         chat = Socket.getChat(c_id);
	         return chat;
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } catch (ClassNotFoundException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }   
	        return null;
	    }
	
	public List<ChattingVo> getChatting(String c_id) {
    	List<ChattingVo> list;
    	try {
			Socket = new MySocketClient();
			list = Socket.getChatting(c_id);
			return list;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        return null;
    }

}

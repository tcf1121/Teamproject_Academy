package Back.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.util.List;

import Back.Datarelated.Protocol;
import Back.Datarelated.Chatting.ChattingVo;
import Back.Datarelated.Class.ClassVo;
import Back.Datarelated.Course.CourseMemberVo;

public class MySocketClient {
	
	private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    
    public MySocketClient() throws IOException {
        socket = new Socket("172.29.229.169", 8700);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }
    public boolean updateState() throws IOException, ClassNotFoundException {
    	String response = (String) in.readObject();
    	System.out.println(response); // 필요에 따라 응답 처리
    	
        return response.equals("UPDATE_SUCCESS");
    }
    
    public void sendChatMessage(String message) throws IOException, ClassNotFoundException {
        String request = "CHAT#" + message;
        out.writeObject(request);
        // 서버로부터 받은 응답을 처리하는 로직을 추가할 수 있습니다.
    }
    
    public void sendConsultMessage(String message) throws IOException, ClassNotFoundException {
        String request = "Consult#" + message;
        out.writeObject(request);
        // 서버로부터 받은 응답을 처리하는 로직을 추가할 수 있습니다.
    }
    
    
    public boolean isIdOverlap(String id, int authority) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, authority);
        Protocol pr = new Protocol("isIdOverlap", info);
        out.writeObject(pr);
        String response = (String) in.readObject();
        System.out.println(response); // 필요에 따라 응답 처리
        return response.equals("CHEAK_SUCCESS");
    }
    public boolean Join(Object user, int authority) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(Integer.toString(authority), user);
    	Protocol pr = new Protocol("Join", info);
    	out.writeObject(pr);
        String response = (String) in.readObject();
        System.out.println(response);
        return response.equals("JOIN_SUCCESS");
    }
    public void deleteUser(String id, int authority) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, authority);
    	Protocol pr = new Protocol("deleteUser", info);
    	out.writeObject(pr);
    }
    public void retouchUsers(Object user, int authority) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(Integer.toString(authority), user);
    	Protocol pr = new Protocol("retouchUsers", info);
    	out.writeObject(pr);
    }
    public boolean logIn(Object user, int authority) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(Integer.toString(authority), user);
    	Protocol pr = new Protocol("login", info);
    	out.writeObject(pr);
        String response = (String) in.readObject();
        System.out.println(response);
        return response.equals("LOGIN_SUCCESS");
    }
    public Object getUser(String id, int authority) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(id, authority);
    	Protocol pr = new Protocol("getUser", info);
    	out.writeObject(pr);
        Object user = in.readObject();
        return user;
    }
    public List<CourseMemberVo> getCourseList(String id)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("getCourseList", id);
    	out.writeObject(pr);
    	List<CourseMemberVo> list = (List<CourseMemberVo>)in.readObject();
        return list;
    }
  
    public List<ClassVo> getClassList(String id)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("getClassList", id);
    	out.writeObject(pr);
    	List<ClassVo> list = (List<ClassVo>)in.readObject();
        return list;
    }
    public List<CourseMemberVo> getCourseOfStudentList(String id)throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("getCourseOfStudentList", id);
    	out.writeObject(pr);
    	List<CourseMemberVo> list = (List<CourseMemberVo>)in.readObject();
        return list;
    }
    public void Courseinsert(String s_id, String c_id) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(s_id, c_id);
    	Protocol pr = new Protocol("Courseinsert", info);
    	out.writeObject(pr);
    }
    public void Coursedelete(String s_id, String c_id)throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(s_id, c_id);
    	Protocol pr = new Protocol("Coursedelete", info);
    	out.writeObject(pr);
    }
    public String getGrade(String s_id, String c_id) throws IOException, ClassNotFoundException {
    	Protocol info = new Protocol(s_id, c_id);
        Protocol pr = new Protocol("getGrade", info);
        out.writeObject(pr);
        return (String)in.readObject();
     }
    public void Classinsert(ClassVo c) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("Classinsert", c);
    	out.writeObject(pr);
    }
    public void updateGrade(CourseMemberVo Cv) throws IOException, ClassNotFoundException {
    	Protocol pr = new Protocol("updateGrade", Cv);
    	out.writeObject(pr);
    }
    public void endServer() {
    	try {
			if (socket != null) socket.close();
			System.out.println("서버연결종료");
		} catch (IOException e) {
			System.out.println("소켓통신에러");
		}
    }
    public boolean addChat(ChattingVo chat) throws IOException, ClassNotFoundException {
        Protocol pr = new Protocol("addChat", chat);
        out.writeObject(pr);
        String response = (String) in.readObject();
        System.out.println(response);
        return response.equals("ADD_SUCCESS");
     }
     public List<ChattingVo> getChatting(String c_id) throws IOException, ClassNotFoundException {
        Protocol pr = new Protocol("getChatting", c_id);
        out.writeObject(pr);
        List<ChattingVo> list = (List<ChattingVo>) in.readObject();
         System.out.println(list);
         return list;
     }
     public ChattingVo getChat(String c_id) throws IOException, ClassNotFoundException {
        Protocol pr = new Protocol("getChat", c_id);
        out.writeObject(pr);
        ChattingVo chat = (ChattingVo) in.readObject();
         System.out.println(chat);
         return chat;
     }
}

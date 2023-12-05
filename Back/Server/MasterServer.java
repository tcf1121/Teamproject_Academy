package Back.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import Back.Datarelated.*;
import Back.Datarelated.Chatting.ChattingDao;
import Back.Datarelated.Chatting.ChattingVo;
import Back.Datarelated.Class.ClassDao;
import Back.Datarelated.Class.ClassVo;
import Back.Datarelated.Course.CourseMemberDao;
import Back.Datarelated.Course.CourseMemberVo;
import Back.Datarelated.lecturer.member.LecturerMemberDao;
import Back.Datarelated.lecturer.member.LecturerMemberVo;
import Back.Datarelated.student.member.StudentMemberDao;
import Back.Datarelated.student.member.StudentMemberVo;

public class MasterServer extends Thread{
	public final int inport = 8700;
	Vector<ChildServer> Clientlist = new Vector<>();
	ArrayList<String> IdList = new ArrayList<String>(); // 클라이언트의 닉네임을 저장해줍니다.
	ServerSocket masterserver;
	LecturerMemberDao LMDao = new LecturerMemberDao();
	StudentMemberDao SMDao = new StudentMemberDao();
	CourseMemberDao CMDao = new CourseMemberDao();
	ClassDao CLDao = new ClassDao();
	ChattingDao CDao = new ChattingDao();
	ChattingVo CVo;
	public void run() {
		try {
			masterserver = new ServerSocket(inport);
			while (true) {
				Socket cs = masterserver.accept(); //cs: client socket
				ChildServer csvr = new ChildServer(cs);
				csvr.start();
				Clientlist.add(csvr);
			}
		} catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
	}
	

	class ChildServer extends Thread {
		private Socket clientSocket = null;
		private ObjectInputStream in = null;
		private ObjectOutputStream out = null;		
	    String id;
	    String command;
        int location;
        
		public ChildServer(Socket sock) throws ClassNotFoundException, SQLException, IOException {
			clientSocket = sock;
			out = new ObjectOutputStream(clientSocket.getOutputStream());
	        in = new ObjectInputStream(clientSocket.getInputStream());
	        
		}
		@Override
		public void run() {
//			try {
//				UserPacket o1;
//				while (true) { // the state machine, Simple: REQ-REPLY
//					o1 = (UserPacket)in.readObject();
//					//in.close();
//					System.out.println(o1.toString());
//					System.out.println(o1.getUId());
//					out.writeObject("OK");
//					//out.close();
//				}
//			} catch (Exception e) {
//				System.out.println("Exit.");
//			}
			try {
				
	            while (true) {
	            	Protocol pr = (Protocol) in.readObject();
	                command = pr.getCommand();	                
	                Protocol info;
	                int authority;
	                String c_id;
					switch (command) {
//	                    case "CHAT":
//	                        String message = requestData[1];
//	                        System.out.println("메시지 수신: " + message);
//	                        // 채팅 메시지를 다른 클라이언트로 전송하는 로직을 추가합니다.
//	                        // 여기서는 수신한 메시지를 해당 클라이언트에 연결된 다른 클라이언트에게 보내는 방식으로 구현합니다.
//	                        sendChatMessageToOtherClients(message);
//	                        break;
	                    case "Join":
	                    	info = (Protocol)pr.getObject();
	                    	authority = Integer.valueOf(info.getCommand());
	                    	if(authority == 0) {
	                    		SMDao.addStudents((StudentMemberVo)info.getObject());
	                    	}else {
	                    		LMDao.addLecturers((LecturerMemberVo)info.getObject());
	                    	}
	                        out.writeObject("JOIN_SUCCESS");
	                        break;
	                    case "isIdOverlap":
	                    	info = (Protocol)pr.getObject();
	                    	authority = (int)info.getObject();
	                    	boolean isId;
	                    	if(authority == 0) {
	                    		isId = SMDao.isStudentIdOverlap((String)info.getCommand());
	                    	}else {
	                    		isId = LMDao.isLecturerIdOverlap((String)info.getCommand());
	                    	}
	                        out.writeObject(isId ?"CHEAK_SUCCESS": "CHEAK_FAILURE");
	                        break;
	                    case "deleteUser":
	                    	info = (Protocol)pr.getObject();
	                    	authority = (int)info.getObject();
	                    	if(authority == 0) {
	                    		SMDao.deleteStudents(info.getCommand());
	                    	}else {
	                    		LMDao.deleteLecturers(info.getCommand());
	                    	}
	                        break;
	                    case "retouchUsers":
	                    	info = (Protocol)pr.getObject();
	                    	authority = Integer.valueOf(info.getCommand());
	                    	if(authority == 0) {
	                    		SMDao.retouchStudents((StudentMemberVo)info.getObject());
	                    	}else {
	                    		LMDao.retouchLecturers((LecturerMemberVo)info.getObject());
	                    	}
	                        break;
	                    case "login":
	                    	info = (Protocol)pr.getObject();
	                    	authority = Integer.valueOf(info.getCommand());
	                        boolean loginResult;
	                    	if(authority == 0) {
	                    		StudentMemberVo user = (StudentMemberVo)info.getObject();
	                    		loginResult = SMDao.StudentLogin(user.getSId(), user.getSPw());
	                    	}else {
	                    		LecturerMemberVo user = (LecturerMemberVo)info.getObject();
	                    		loginResult = LMDao.LecturerLogin(user.getSId(), user.getSPw());
	                    	}
	                        out.writeObject(loginResult ? "LOGIN_SUCCESS" : "LOGIN_FAILURE");
	                        break;
	                    case "getUser":
	                    	info = (Protocol)pr.getObject();
	                    	authority = (int)info.getObject();
	                    	if(authority == 0) {
	                    		out.writeObject(SMDao.getStudent(info.getCommand()));
	                    	}else {
	                    		out.writeObject(LMDao.getLecturer(info.getCommand()));
	                    	}
	                        break;
	                    case "getCourseList":
	                    	id = (String)pr.getObject();
	                    	out.writeObject(CMDao.getCourseList(id));
	                        break;
	                    case "getClassList":
	                    	id = (String)pr.getObject();
	                    	out.writeObject(CLDao.getClassList(id));
	                        break;
	                    case "getCourseOfStudentList":
	                    	id = (String)pr.getObject();
	                    	out.writeObject(CMDao.getCourseOfStudentList(id));
	                        break;
	                    case "Courseinsert":
	                    	info = (Protocol)pr.getObject();
	                    	String ci_id = (String)info.getObject();
	                    	CMDao.insert(info.getCommand(), ci_id);
	                        break;
	                    case "Coursedelete":
	                    	info = (Protocol)pr.getObject();
	                    	String cd_id = (String)info.getObject();
	                    	CMDao.delete(info.getCommand(), cd_id);
	                        break;
	                    case "Classinsert":
	                    	CLDao.insert((ClassVo)pr.getObject());
	                        break;
	                    case "getGrade":
	                    	info = (Protocol)pr.getObject();
	                        out.writeObject(CMDao.getGrade(info.getCommand(), (String)info.getObject()));
	                        break;
	                    case "updateGrade":
	                    	CMDao.updateGrade((CourseMemberVo)pr.getObject());
	                        break;
	                    case "addChat":
	                          CVo = (ChattingVo)pr.getObject();
	                          CDao.addChat(CVo);
	                          out.writeObject("ADD_SUCCESS");
	                           break;
	                       case "getChatting":
	                          c_id = (String)pr.getObject();
	                          out.writeObject(CDao.ChattingList(c_id));
	                           break;
	                       case "getChat":
	                          c_id = (String)pr.getObject();
	                          out.writeObject(CDao.getChat(c_id));
	                           break;
	                    default:
	                        out.writeObject("INVALID_OPERATION");
	                        break;
	                }
	            }
	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	            System.out.println(id + "님이 퇴장하셨습니다.");
	        } finally {
	            try {
	                clientSocket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
		}
		
		//private ResultPacket doIt(RequestData o) { … return aResultPacket;}

	}
}

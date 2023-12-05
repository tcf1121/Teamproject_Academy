package Back.Datarelated.Chatting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Back.Datarelated.student.member.StudentMemberVo;

public class ChattingDao {
	private Connection conn;
	
	public ChattingDao() {
        try {
        	//JDBC Driver 등록
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            //연결하기
            String connectionUrl = 
            		"jdbc:sqlserver://localhost:1433;"
            				+"database=Academy;"
            				+"encrypt=false;"
            				+"user=tcf12;"
            				+"password=dnlsxjaks!12;";
            conn = DriverManager.getConnection(connectionUrl);
        }catch(Exception e) {
        	e.printStackTrace();
        }
	}
	
	public int getChatId(String c_id) {
		 int num = 0;
	      try {
	          String sql = "select count(*) from Chatting\n"+
	                "where c_id = ?";
	          PreparedStatement pstmt = conn.prepareStatement(sql);
	          pstmt.setString(1, c_id);
	          ResultSet rs = pstmt.executeQuery();
	          
	          if(rs.next()) {
	             num = rs.getInt(1);
	             System.out.println(num);
	          }      
	          rs.close();
	          pstmt.close();
	       }catch(Exception e) {
	          e.printStackTrace();
	       }
	      return num+1;
	}

	//채팅 추가
	public void addChat(ChattingVo chat) {
		int Id = getChatId(chat.getChatRoomId());
		try {
			String sql = "insert into Chatting(c_id, ID, sendId, sendName, rtime, history) values(?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chat.getChatRoomId());
			pstmt.setInt(2, Id);
			pstmt.setString(3, chat.getSendId());
			pstmt.setString(4, chat.getSendName());
			pstmt.setString(5, chat.getRTime());
			pstmt.setString(6, chat.getHistory());
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
   }
	
	//채팅 하나 가져오기
    public ChattingVo getChat(String c_id) {
        ChattingVo chat = null; // 초기화
           try {
               String sql = "SELECT *\r\n"
                       + "FROM Chatting\r\n"
                       + "WHERE c_id = ?\r\n"
                       + "AND ID = (SELECT MAX(ID) FROM Chatting WHERE c_id = ?);\r\n";
               PreparedStatement pstmt = conn.prepareStatement(sql);
               pstmt.setString(1, c_id);
               pstmt.setString(2, c_id);
               ResultSet rs = pstmt.executeQuery();

               // rs.next()로 커서를 다음 레코드로 이동시키고 데이터를 가져옴
               if (rs.next()) {
                   chat = new ChattingVo(
                       rs.getString("c_id"),
                       rs.getInt("ID"),
                       rs.getString("sendId"),
                       rs.getString("sendName"),
                       rs.getString("rtime"),
                       rs.getString("history")
                   );
               }

               rs.close();
               pstmt.close();
           } catch(Exception e) {
               e.printStackTrace();
           }
           return chat;
    }
		
	//채팅 리스트 가져오기
	public List<ChattingVo> ChattingList(String c_id){
		List<ChattingVo> list = new ArrayList<ChattingVo>();
    	try {
    		String sql = "select * \n"
				+ "from Chatting\n"
    				+ "where c_id = ?"
				+ "ORDER BY ID";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, c_id);
    		ResultSet rs = pstmt.executeQuery();  		
    		while(rs.next()) {
    			ChattingVo chat = new ChattingVo(rs.getString("c_id"),rs.getString("sendId"),
    					rs.getString("sendName"), rs.getString("rtime"), rs.getString("history"));
    			list.add(chat);
    		}
    		rs.close();
    		pstmt.close();
    		return list;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return null;
	}

}

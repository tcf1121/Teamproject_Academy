package Back.Datarelated.Course;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CourseMemberDao {
	 private Connection conn;
	 
	public CourseMemberDao() {
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
	//수강 신청
	public void insert(String s_id, String c_id) {
		
		try {
			String sql = "insert into Course(s_id, c_id) values(?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, s_id);
			pstmt.setString(2, c_id);
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	//성적 입력
	public void updateGrade(CourseMemberVo Cv) {
		try {
			String sql = "update Course set grade = ? where s_id = ? and c_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, Cv.getGrade());
			pstmt.setString(2, Cv.getSId());
			pstmt.setString(3, Cv.getCId());
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	/*public List<CourseMemberVo> getList(String name) throws SQLException, ClassNotFoundException{
		
		String sql = "select C.ID, cName, L.name, room_number, timetable_id, num from Class C, Lecturer L where C.l_id = L.ID and cName like ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		
		ResultSet rs = pstmt.executeQuery();
		
		List<CourseMemberVo> list = new ArrayList<CourseMemberVo>();
		while(rs.next())
		{
			CourseMemberVo c = new CourseMemberVo();
			
			c.setClassId(rs.getString("ID"));
			c.setCname(rs.getString("cname"));
			c.setLname(rs.getString("name"));
			c.setRoom_number(rs.getString("room_number"));
			c.setTimetable_id(rs.getString("timetable_id"));
			c.setNum(rs.getInt("num"));
			
			list.add(c);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}*/
	
	//수강 리스트 
	public List<CourseMemberVo> getCourseList(String id) {
		List<CourseMemberVo> list = new ArrayList<CourseMemberVo>();
		String sql;
		PreparedStatement pstmt;
		try {
			if(id.equals("ALL")) {
				sql="select Class.ID, cName, Lecturer.name, room, t_id, grade from Class, Course, Lecturer where l_id = Lecturer.ID and c_id = Class.ID";
				pstmt = conn.prepareStatement(sql);
			}
			else {
				sql = "select Class.ID, cName, Lecturer.name, room, t_id, grade from Class, Course, Lecturer where l_id = Lecturer.ID and c_id = Class.ID and s_id = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next())
			{
				CourseMemberVo c = new CourseMemberVo();
				
				c.setCId(rs.getString("ID"));
				c.setCName(rs.getString("cName"));
				c.setLName(rs.getString("name"));
				c.setRoom(rs.getString("room"));
				c.setTId(rs.getString("t_id"));
				c.setGrade(rs.getString("grade"));
				list.add(c);
			}
    		rs.close();
    		pstmt.close();
    		return list;
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return null;
	}
	
	//수강 취소
	public void delete(String s_id, String c_id) {
		try {
			String sql = "delete Course where s_id=? and c_id=?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, s_id);
			pstmt.setString(2, c_id);
			
			pstmt.executeUpdate();
			
			pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	// 수강 중인 학생 목록
	public List<CourseMemberVo> getCourseOfStudentList(String id) {
	      List<CourseMemberVo> list = new ArrayList<CourseMemberVo>();
	      String sql;
	      PreparedStatement pstmt;
	      try {
	         sql = "select cName, Student.name, Student.phone, grade, Student.ID from Course, Class, Student where s_id = Student.ID and c_id = Class.ID and c_id = ?";
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, id);
	         
	         ResultSet rs = pstmt.executeQuery();
	         
	         
	         while(rs.next())
	         {
	            CourseMemberVo c = new CourseMemberVo();
	            
	            c.setCName(rs.getString("cName"));
	            c.setSName(rs.getString("name"));
	            c.setSPhone(rs.getString("phone"));
	            c.setGrade(rs.getString("grade"));
	            c.setSId(rs.getString("ID"));
	            list.add(c);
	         }
	          rs.close();
	          pstmt.close();
	          return list;
	       }catch(Exception e) {
	          e.printStackTrace();
	       }
	       return null;
	   }
	public String getGrade(String s_id, String c_id) {
		String grade = null;
	      try {
	        String sql = "select * from Course where s_id = ? and c_id = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, s_id);
	        pstmt.setString(2, c_id);
	        ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
			grade = rs.getString("grade");
			} 
			rs.close();
	        pstmt.close();
	       }catch(Exception e) {
	          e.printStackTrace();
	       }
	      return grade;
	   }
}

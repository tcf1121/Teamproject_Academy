package Back.Datarelated.lecturer.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LecturerMemberDao {
	private Connection conn;
	public LecturerMemberDao() {
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
	//강사 회원 가입
	public void addLecturers(LecturerMemberVo lecturer) {
		try {
			String sql = "insert into Lecturer(ID, pw, name, phone) values(?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, lecturer.getSId());
			pstmt.setString(2, lecturer.getSPw());
			pstmt.setString(3, lecturer.getSName());
			pstmt.setString(4, lecturer.getSPhone());
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteLecturers(String id) {
		try {
			String sql = "delete from Lecturer where ID=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void retouchLecturers(LecturerMemberVo user) {
		String id = user.getSId();
		String phone = user.getSPhone();
		String pw = user.getSPw();
    	try {
		String sql = "update Lecturer\n"+
				" set pw = ?, phone = ?\n"
				+ "where ID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, pw);
		pstmt.setString(2, phone);
		pstmt.setString(3, id);
		pstmt.executeUpdate();
		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
    public boolean isLecturerIdOverlap(String id) {
    	String sql = "select ID from Lecturer";
    	try {
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			if(rs.getString("ID").equals(id)) {
    				rs.close();
    				pstmt.close();
    				return true;
    			}
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
	public LecturerMemberVo getLecturer(String id) {
		LecturerMemberVo lecturer = new LecturerMemberVo();
    	try {
    		String sql = ""+
				"select * from Lecturer where ID = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			lecturer.setSId(rs.getString("ID"));
    			lecturer.setSPw(rs.getString("pw"));
    			lecturer.setSName(rs.getString("name"));
    			lecturer.setSPhone(rs.getString("phone"));
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		return lecturer;
	}
	public boolean LecturerLogin(String id, String pw) {
		try {
    		String sql = "select * from Lecturer\n"+
    				 "where ID = ? and pw = ? ";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		pstmt.setString(2, pw);
    		ResultSet rs = pstmt.executeQuery();
    		System.out.println(id);
    		System.out.println(pw);
            //rs.next();
            if (rs.next()) {
            	// Success
            	return true;
            	} 
        	}catch(Exception e) {
        		e.printStackTrace();
        	} 
		return false;
	}
	public List<LecturerMemberVo> LecturerList(){
		List<LecturerMemberVo> list = new ArrayList<LecturerMemberVo>();
    	try {
    		String sql = "select * \n"
				+ "from Lecturer\n";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		ResultSet rs = pstmt.executeQuery();  		
    		while(rs.next()) {
    			LecturerMemberVo lecturer = new LecturerMemberVo(rs.getString("ID"),
    					rs.getString("pw"), rs.getString("name"), rs.getString("phone"));
    			list.add(lecturer);
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

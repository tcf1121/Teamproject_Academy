package Back.Datarelated.student.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentMemberDao {
	private Connection conn;
	public StudentMemberDao() {
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

	//학생 회원가입
	public void addStudents(StudentMemberVo student) {
		try {
			String sql = "insert into Student(ID, pw, name, phone) values(?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.getSId());
			pstmt.setString(2, student.getSPw());
			pstmt.setString(3, student.getSName());
			pstmt.setString(4, student.getSPhone());
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
   }

	public void deleteStudents(String id) {
		try {
			String sql = "delete from Student where ID=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void retouchStudents(StudentMemberVo user) {
		String id = user.getSId();
		String phone = user.getSPhone();
		String pw = user.getSPw();
		try {
		String sql = "update Student\n"+
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
	
    public boolean isStudentIdOverlap(String id) {
    	String sql = "select ID from Student";
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
	public StudentMemberVo getStudent(String id) {
		StudentMemberVo student = new StudentMemberVo();
    	try {
    		String sql = ""+
				"select * from Student where ID = ?";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1, id);
    		ResultSet rs = pstmt.executeQuery();
    		
    		while(rs.next()) {
    			student.setSId(rs.getString("ID"));
    			student.setSPw(rs.getString("pw"));
    			student.setSName(rs.getString("name"));
    			student.setSPhone(rs.getString("phone"));
    		}
    		rs.close();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		return student;
	}
	public boolean StudentLogin(String id, String pw) {
		try {
    		String sql = "select * from Student\n"+
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
	public List<StudentMemberVo> StudentList(){
		List<StudentMemberVo> list = new ArrayList<StudentMemberVo>();
    	try {
    		String sql = "select * \n"
				+ "from Student\n";
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		ResultSet rs = pstmt.executeQuery();  		
    		while(rs.next()) {
    			StudentMemberVo student = new StudentMemberVo(rs.getString("ID"),
    					rs.getString("pw"), rs.getString("name"), rs.getString("phone"));
    			list.add(student);
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

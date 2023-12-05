package Back.Datarelated.Class;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClassDao {
	private Connection conn;
	
	public ClassDao() {
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
    public boolean CheckID(String id) {
    	String sql = "select ID from Class";
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
    
	public String makeRandClassID() {
		String id = "";
		do {
			try {
	    		String sql = "	declare @code char(5) \n"
	    				+ "	set @code = (\n"
	    				+ "	select c1 as [text()]\n"
	    				+ "	FROM (  select top(5) c1 \n"
	    				+ "	FROM (\r\n"
	    				+ "	VALUES    ('A'), ('B'), ('C'), ('D'), ('E'), ('F'), ('G'),\n"
	    				+ "	('H'), ('I'), ('J'),   ('K'), ('L'), ('M'), ('N'), ('O'),\n"
	    				+ "	('P'), ('Q'), ('R'), ('S'), ('T'),   ('U'), ('V'), ('W'),\n"
	    				+ "	('X'), ('Y'), ('Z'), ('0'), ('1'), ('2'), ('3'),   ('4'),\n"
	    				+ "	('5'), ('6'), ('7'), ('8'), ('9')  ) AS T1(c1)\n"
	    				+ "	ORDER BY ABS(CHECKSUM(NEWID()))\n"
	    				+ "	) AS T2 FOR XML PATH('')\n"
	    				+ "	);\r\n"
	    				+ "	select @code";
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		ResultSet rs = pstmt.executeQuery();
	    		
	    		if(rs.next()) {
	    			id = rs.getString(1);
	    		}		
	    		rs.close();
	    		pstmt.close();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		} while (CheckID(id));
		return id;
	}
	
	public void insert(ClassVo c) {
		String cid = makeRandClassID();
		try {
			String sql = "insert into Class(ID, cName, t_id, l_id) values(?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, cid);
			pstmt.setString(2, c.getCName());
			pstmt.setString(3, c.getTId());
			pstmt.setString(4, c.getLId());
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	
	public void update(ClassVo c) {
		try {
			String sql = "update Course set cName = ?, room = ?, t_id = ?, l_id = ? where ID = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, c.getCName());
			pstmt.setString(2, c.getRoom());
			pstmt.setString(3, c.getTId());
			pstmt.setString(4, c.getLId());
			pstmt.setString(6, c.getCId());
    		pstmt.executeUpdate();
    		pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	public List<ClassVo> getClassList(String id) {
		List<ClassVo> list = new ArrayList<ClassVo>();
		String sql;
		PreparedStatement pstmt;
		try {
			if(id.equals("ALL")) {
				sql="select Class.ID, cName, Lecturer.name, room, t_id from Class, Lecturer where l_id = Lecturer.ID";
				pstmt = conn.prepareStatement(sql);
			}
			else {
				sql = "select Class.ID, cName, Lecturer.name, room, t_id from Class, Lecturer where l_id = Lecturer.ID and l_id = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next())
			{
				ClassVo c = new ClassVo();
				
				c.setCId(rs.getString("ID"));
				c.setCName(rs.getString("cName"));
				c.setLName(rs.getString("name"));
				c.setRoom(rs.getString("room"));
				c.setTId(rs.getString("t_id"));
				
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
	
	public void delete(String id) {
		try {
			String sql = "delete Class where ID=?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			pstmt.executeUpdate();
			
			pstmt.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
}

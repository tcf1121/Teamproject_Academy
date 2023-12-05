package GUI.Lecturer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Back.Datarelated.Chatting.ChattingVo;
import Back.Datarelated.Class.ClassVo;
import Back.Datarelated.Course.CourseMemberVo;
import Back.Server.MySocketClient;
import GUI.Chat.ChatRoom;
import GUI.Lecturer.Popup.gradeUpdatePopup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
 
public class LecturerCourseForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    JScrollPane scrollPane;
    ImageIcon icon;
    private JButton btnBack;
	private JButton btngrade;
	private JButton btnchat;
    private JLabel lblui;
    private JLabel cNameLbl;
    private JScrollPane tablescroll;
    private JTable taClasslist;
    private DefaultTableModel DTmodel;
    private String header[]= {"순번","이름", "전화번호","성적", "학생아이디"};
    private TableRowSorter<DefaultTableModel> sorter;
    private LCourseForm owner;
    private MySocketClient Socket;
    private List<CourseMemberVo> list;
    private String c_id;
    private ClassVo CMvo;
    public LecturerCourseForm(LCourseForm owner, ClassVo CMVo) throws ClassNotFoundException, IOException {
    	this.owner = owner;
        try {
			Socket = new MySocketClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        
        //배경 Panel 생성후 컨텐츠페인으로 지정      
        JPanel bg = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        this.CMvo = CMVo;
        this.c_id = CMVo.getCId();
        list = Socket.getCourseOfStudentList(CMVo.getCId());
        bg.setLayout(null);
        init();
        bg.add(cNameLbl);
        bg.add(btnBack);
        bg.add(btngrade);
        bg.add(btnchat);
        tablescroll.setViewportView(taClasslist);
        bg.add(tablescroll);
        bg.add(lblui);
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        cNameLbl.setText(CMVo.getCName());
        
        addListeners();
        showFrame();
    }
    public void init() {
    	Font font = new Font("SansSerif", 1, 20);
		cNameLbl = new JLabel("안녕");
		cNameLbl.setForeground(new Color(255, 255, 255));
		cNameLbl.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 40));
		cNameLbl.setBounds(393, 27, 342, 70);
    	
    	
        btnBack = new JButton();
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
        btnBack.setBounds(24, 30, 68, 60);
        
        lblui = new JLabel(new ImageIcon(LecturerCourseForm.class.getResource("/Image/Background/LectureCoursebg.png")));
        lblui.setBounds(0, 0, 781, 558);
        
		btngrade = new JButton();
		btngrade.setBounds(667, 221, 83, 85);
		btngrade.setBorderPainted(false);
		btngrade.setContentAreaFilled(false);
		
		btnchat = new JButton();
		btnchat.setBounds(667, 388, 83, 85);
		btnchat.setBorderPainted(false);
		btnchat.setContentAreaFilled(false);
		
        DTmodel = (new DefaultTableModel(header, 0) {
        	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
        	      //all cells false
        	      return false;
        	    }
        	});
        for(int i=0; i<list.size(); i++) {
        	Object[] data = {i+1, list.get(i).getSName(), list.get(i).getSPhone(),
        			list.get(i).getGrade(),list.get(i).getSId()};
        	DTmodel.addRow(data);
        }
        taClasslist = new JTable(DTmodel);
        taClasslist.setFont(font);
        taClasslist.getColumn(header[0]).setPreferredWidth(90);
        taClasslist.getColumn(header[1]).setPreferredWidth(178);
        taClasslist.getColumn(header[2]).setPreferredWidth(219);
        taClasslist.getColumn(header[3]).setPreferredWidth(220);
        taClasslist.getColumn(header[4]).setWidth(0);
        taClasslist.getColumn(header[4]).setMinWidth(0);
        taClasslist.getColumn(header[4]).setMaxWidth(0);
        taClasslist.setRowHeight(23);
        sorter = new TableRowSorter<>(DTmodel);
        taClasslist.setRowSorter(sorter);
        
        tablescroll = new JScrollPane();
        tablescroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tablescroll.setBounds(10, 136, 633, 392);
    }
    public void showFrame() {
        setTitle("Hello, 코딩");
        setSize(800,600);
        setLocation(owner.getLocation());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);//창 크기 고정
        setVisible(true);
    }

    public void addListeners() {
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	owner.setVisible(true);
            }
        });
        btnchat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println(CMvo.getLId());
            	ChattingVo Chatv = new ChattingVo(CMvo.getCId(), CMvo.getLId(), CMvo.getLName());
            	ChatRoom CR = new ChatRoom(Chatv);
            	CR.start();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		LecturerCourseForm.this,
                        "종료하시겠습니까?",
                        "종료",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
        btngrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CourseMemberVo Cv = new CourseMemberVo();
            	Cv.setCId(c_id);
            	Cv.setSId((String)taClasslist.getValueAt(taClasslist.getSelectedRow(), 4));
            	Cv.setGrade((String)taClasslist.getValueAt(taClasslist.getSelectedRow(), 3));
            	Cv.setSPhone((String)taClasslist.getValueAt(taClasslist.getSelectedRow(), 2));
            	Cv.setSName((String)taClasslist.getValueAt(taClasslist.getSelectedRow(), 1));
            	new gradeUpdatePopup(LecturerCourseForm.this, Cv);
            }
        });

    }
   
    public String getSId(){
    	Object value;
    	value = taClasslist.getValueAt(taClasslist.getSelectedRow(), 4);
    	return value.toString();
    }
    
    
    public DefaultTableModel getDTmodel() {
    	return this.DTmodel;
    }
    public JTable getJtable() {
    	return this.taClasslist;
    }
}

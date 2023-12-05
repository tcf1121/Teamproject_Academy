package GUI.Student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Back.Datarelated.Chatting.ChattingVo;
import Back.Datarelated.Course.CourseMemberVo;
import Back.Server.MySocketClient;
import GUI.Chat.ChatRoom;
import GUI.Main.MainForm;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class MyCourseForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MySocketClient Socket;
	private CourseMemberVo course;
    private JLabel lblui;
	private JPanel contentPane;
	private JLabel cNameLbl;
	private JLabel gradeLbl;
	private JButton deleteBtn;
	private JButton chatBtn;
	private JButton backBtn;
	private SCourseForm owner;
	private String s_id;
	private String grade;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public MyCourseForm(SCourseForm owner, CourseMemberVo CMVo) throws ClassNotFoundException, IOException{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		this.owner = owner;
		this.s_id = CMVo.getSId();
		this.course = CMVo;
        try {
			Socket = new MySocketClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        grade = Socket.getGrade(s_id, course.getCId());
        
		init();
		if(grade == null) {
			gradeLbl.setText("x");
		}else {
			gradeLbl.setText(grade);
		}
		
		
		cNameLbl.setText(CMVo.getCName());
		showFrame();
		addListeners();
	}
	
	public void init() {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cNameLbl = new JLabel("안녕");
		cNameLbl.setForeground(new Color(255, 255, 255));
		cNameLbl.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 40));
		cNameLbl.setBounds(393, 27, 342, 70);
		getContentPane().add(cNameLbl);
		
		gradeLbl = new JLabel("A");
		gradeLbl.setForeground(new Color(255, 255, 255));
		gradeLbl.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 90));
		gradeLbl.setBounds(281, 267, 206, 183);
		gradeLbl.setHorizontalAlignment(JLabel.CENTER);
		getContentPane().add(gradeLbl);
		
		deleteBtn = new JButton();
		deleteBtn.setBounds(670, 223, 83, 85);
		deleteBtn.setBorderPainted(false);
		deleteBtn.setContentAreaFilled(false);
		getContentPane().add(deleteBtn);
		
		chatBtn = new JButton();
		chatBtn.setBounds(670, 388, 83, 85);
		chatBtn.setBorderPainted(false);
		chatBtn.setContentAreaFilled(false);
		getContentPane().add(chatBtn);
		
		backBtn = new JButton();
		backBtn.setBounds(23, 27, 83, 70);
		backBtn.setBorderPainted(false);
		backBtn.setContentAreaFilled(false);
		getContentPane().add(backBtn);
		
        lblui = new JLabel(new ImageIcon(MainForm.class.getResource("/Image/Background/mycoursebg.png")));
        lblui.setBounds(0, 0, 789, 565);
        getContentPane().add(lblui);
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

    	backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	owner.setVisible(true);
            }
        });
    	chatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ChattingVo Chatv = new ChattingVo(course.getCId(), course.getSId(), course.getSName());
            	ChatRoom CR = new ChatRoom(Chatv);
            	CR.start();
            }
        });
       	deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	Socket.Coursedelete(s_id, course.getCId());
    			} catch (ClassNotFoundException | IOException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
                JOptionPane.showMessageDialog(
                		MyCourseForm.this,               		
                		"해당 강의의 수강을 취소하였습니다.");
            	dispose();
            	owner.setVisible(true);
            }
        });
       	
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		MyCourseForm.this,
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
    }
}

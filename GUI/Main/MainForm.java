package GUI.Main;

import javax.swing.*;

import Back.Server.MySocketClient;
import GUI.Lecturer.CourseapForm;
import GUI.Lecturer.LCourseForm;
import GUI.Main.Popup.UserUpdatePopup;
import GUI.Student.EnrolmentForm;
import GUI.Student.SCourseForm;
import Back.Datarelated.lecturer.member.LecturerMemberVo;
import Back.Datarelated.student.member.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
 
public class MainForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    JScrollPane scrollPane;
    ImageIcon icon;
    private JButton btnWithdrawal;
    private JButton btnLogout;
    private JButton btnAp;
    private JButton btnCourse;
    private JButton btnretouch;
    private JLabel lblapname;
    private JLabel lblName;
    private JLabel lblui;
    private MySocketClient Socket;
    private Object user;
    private String name;
    private String ap;
    private LoginForm owner;
    private String id;
    private int authority;
    
    public MainForm(LoginForm owner, int authority) throws ClassNotFoundException, IOException {
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
        user = Socket.getUser(owner.getTfId(), authority);
        this.id = owner.getTfId();
        this.authority = authority;
        if(authority==0) {
        	StudentMemberVo sv = (StudentMemberVo)user;
        	name = sv.getSName() + " 학생";
        	ap = "수강";
        }else {
          	LecturerMemberVo sv = (LecturerMemberVo)user;
        	name = sv.getSName() + " 강사";
        	ap = "강좌";
        }
        bg.setLayout(null);
        init();
        
        scrollPane = new JScrollPane(bg);


        setContentPane(scrollPane);
        bg.add(btnLogout);
        bg.add(btnretouch);
        bg.add(btnCourse);
        bg.add(btnAp);
        bg.add(btnWithdrawal);
        bg.add(lblapname);
        bg.add(lblName);
        bg.add(lblui);

        addListeners();
        showFrame();
    }
    public void init() {
        lblui = new JLabel(new ImageIcon(MainForm.class.getResource("/Image/Background/mainbg.png")));
        lblui.setBounds(0, 0, 781, 558);
		//rbManager.addItemListener(this);
		
        btnWithdrawal = new JButton();       
        btnWithdrawal.setBorderPainted(false);
        btnWithdrawal.setContentAreaFilled(false);
        btnWithdrawal.setBounds(632, 52, 127, 36);
        
        btnLogout = new JButton(); 
        btnLogout.setBorderPainted(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setBounds(236, 52, 127, 36);
        
        btnretouch = new JButton();
        btnretouch.setContentAreaFilled(false);
        btnretouch.setBorderPainted(false);
        btnretouch.setBounds(416, 52, 165, 36);
        
        btnCourse = new JButton();
        btnCourse.setContentAreaFilled(false);
        btnCourse.setBorderPainted(false);
        btnCourse.setBounds(21, 177, 141, 141);
               
        btnAp = new JButton();
        btnAp.setContentAreaFilled(false);
        btnAp.setBorderPainted(false);
        btnAp.setBounds(20, 357, 142, 140);
               
        lblapname = new JLabel();
        lblapname.setFont(new Font("나눔고딕", Font.BOLD, 20));
        lblapname.setForeground(new Color(255, 255, 255));
        lblapname.setText(ap);
        lblapname.setBounds(45, 458, 51, 31);
            
        lblName = new JLabel();
        lblName.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 30));
        lblName.setForeground(new Color(255, 255, 255));
        lblName.setText(name);
        lblName.setBounds(10, 35, 213, 67);
    }
    
    private void showFrame() {
        setTitle("Hello, 코딩");//타이틀
        setSize(800,600);//프레임의 크기
        setLocation(owner.getLocation());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }

    public void addListeners() {

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		MainForm.this,
                        "로그아웃하시겠습니까?",
                        "로그아웃",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                    dispose();
                    owner.setVisible(true);
                }
            }
        });
        btnretouch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new UserUpdatePopup(MainForm.this, user, authority);
            }
        });
        btnWithdrawal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		MainForm.this,
                        "계정을 삭제하시겠습니까?",
                        "경고",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.OK_OPTION) {
                    try {
						Socket.deleteUser(id, authority);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    JOptionPane.showMessageDialog(
                    		MainForm.this,
                            "계정이 삭제되었습니다.\n"
                            + "프로그램이 종료됩니다."
                    );
                    System.exit(0);
                }
            }
        });

        btnCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(authority ==0) {
            		try {
            			setVisible(false);
						new SCourseForm(MainForm.this, (StudentMemberVo)user);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}else {
            		try {
            			setVisible(false);
						new LCourseForm(MainForm.this, (LecturerMemberVo)user);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}
            }
        });
        btnAp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(authority ==0) {
                	try {
                		setVisible(false);
    					new EnrolmentForm(MainForm.this, (StudentMemberVo)user);
    				} catch (ClassNotFoundException | IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
            	}else {
            		try {
            			setVisible(false);
						new CourseapForm(MainForm.this, (LecturerMemberVo)user);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}

            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		MainForm.this,
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
    
    
    /*
    public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange() == ItemEvent.SELECTED) {
			if(rbStudent.isSelected()) {

			}
			else if(rbLecturer.isSelected()) {			

			}
			else if(rbManager.isSelected()) {

			}
		}
	}*/
}

package GUI.Main;

import javax.swing.*;

import Back.Server.MySocketClient;
import Back.Datarelated.lecturer.member.LecturerMemberVo;
import Back.Datarelated.student.member.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
 
public class LoginForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    JScrollPane scrollPane;
    ImageIcon icon;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JButton btnLogin;
    private JButton btnJoin;
    private JLabel lblui;
	private JRadioButton rbStudent;
	private JRadioButton rbLecturer;
	private JRadioButton rbManager;
	private ButtonGroup bgRBG;
    private MySocketClient Socket;
    
    public LoginForm() {

        icon = new ImageIcon(LoginForm.class.getResource("/Image/Background/loginbg.png"));
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
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };

        bg.setLayout(null);
        init();
        
        scrollPane = new JScrollPane(bg);
        
        bg.add(tfId);
        bg.add(tfPw);


        setContentPane(scrollPane);
        bg.add(rbStudent);
        bg.add(rbLecturer);
        bg.add(rbManager);
        bg.add(btnJoin);
        bg.add(btnLogin);
        
        bg.add(lblui);

        addListeners();
        showFrame();
    }
    public void init() {
    	Font font = new Font("나눔고딕 ExtraBold", Font.BOLD, 15);
        
        lblui = new JLabel(new ImageIcon(LoginForm.class.getResource("/Image/Background/loginbg.png")));
        
        lblui.setBounds(0, 0, 781, 558);
        
        tfId = new JTextField();
        tfId.setFont(font);
        tfId.setBounds(365, 377, 173, 21);
        
        tfPw = new JPasswordField();        
        tfPw.setFont(font);
        tfPw.setBounds(365, 417, 173, 21);
        
		rbStudent = new JRadioButton();
		rbStudent.setBounds(449, 346, 25, 25);
		rbStudent.setBorderPainted(false);
		rbStudent.setContentAreaFilled(false);
		//rbStudent.addItemListener(this);
		
		rbLecturer = new JRadioButton();
		rbLecturer.setBounds(343, 345, 25, 25);
		rbLecturer.setBorderPainted(false);
		rbLecturer.setContentAreaFilled(false);
		//rbLecturer.addItemListener(this);
		
		rbManager = new JRadioButton();
		rbManager.setBounds(237, 344, 25, 25);
		rbManager.setBorderPainted(false);
		rbManager.setContentAreaFilled(false);
		//rbManager.addItemListener(this);
		
		bgRBG = new ButtonGroup();
		bgRBG.add(rbStudent);
		bgRBG.add(rbLecturer);
		bgRBG.add(rbManager);
		
        btnLogin = new JButton();       
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBounds(236, 458, 156, 36);
        
        btnJoin = new JButton(); 
        btnJoin.setBorderPainted(false);
        btnJoin.setContentAreaFilled(false);
        btnJoin.setBounds(402, 458, 156, 36);
		
    }
    public void showFrame() {
        setTitle("Hello, 코딩");
        setSize(800,600);
        setLocationRelativeTo(null);//화면을 가운데
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);//창 크기 고정
    }

    public void addListeners() {

        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new JoinForm(LoginForm.this);
                tfId.setText("");
                tfPw.setText("");
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 아이디 입력 x
                if (tfId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "아이디를 입력하시오.",
                            "아이디 입력",
                            JOptionPane.WARNING_MESSAGE);

                    // 회원가입된 아이디 입력 o
                } else if(rbManager.isSelected()) {
            		if (tfId.getText().equals("12341234")) {
					    // 비밀번호 입력 x
					    if(String.valueOf(tfPw.getPassword()).isEmpty()) {
					        JOptionPane.showMessageDialog(
					        		LoginForm.this,
					                "비밀번호를 입력하시오.",
					                "비밀번호 입력",
					                JOptionPane.WARNING_MESSAGE);
					     
					    }else if (String.valueOf(tfPw.getPassword()).equals("12341234")) {// 모두 잘 입력됨
					    	setVisible(false);
					        tfId.setText("");
					        tfPw.setText("");
					     // 비밀번호 틀림 
					    } else {
					    	JOptionPane.showMessageDialog(
					        		LoginForm.this,
					                "비밀번호를 잘못 입력하셨습니다.");
					    }
					    // 회원 가입 되지 않은 아이디 입력
					} else {
					    JOptionPane.showMessageDialog(
					    		LoginForm.this,
					            "관리자 아이디가 아닙니다."
					         
					    );
					
					}
            	}else {
					try {
						int authority;
		            	if(rbStudent.isSelected() || rbLecturer.isSelected()) {
		            		Object loginuser;
		            		if(rbStudent.isSelected()) {
		            			authority = 0;
								loginuser = new StudentMemberVo(tfId.getText(),
										String.valueOf(tfPw.getPassword()), "", "");
		            		}
		            		else {
			            		authority = 1;
								loginuser = new LecturerMemberVo(tfId.getText(),
										String.valueOf(tfPw.getPassword()), "", "");
		            		}
		            		if (Socket.isIdOverlap(tfId.getText(), authority)) {
							    // 비밀번호 입력 x
							    if(String.valueOf(tfPw.getPassword()).isEmpty()) {
							        JOptionPane.showMessageDialog(
							        		LoginForm.this,
							                "비밀번호를 입력하시오.",
							                "비밀번호 입력",
							                JOptionPane.WARNING_MESSAGE);
							     
							    }else if (Socket.logIn(loginuser, authority)) {// 모두 잘 입력됨
							    	setVisible(false);
							        new MainForm(LoginForm.this, authority);
							        tfId.setText("");
							        tfPw.setText("");
							     // 비밀번호 틀림 
							    } else {
							    	JOptionPane.showMessageDialog(
							        		LoginForm.this,
							                "비밀번호를 잘못 입력하셨습니다.");
							    }
							    // 회원 가입 되지 않은 아이디 입력
							} else {
							    JOptionPane.showMessageDialog(
							    		LoginForm.this,
							            "없는 아이디 입니다."
							         
							    );
							
							}
		            	}
	
							
						} catch (HeadlessException | ClassNotFoundException |IOException e1) {
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
                		LoginForm.this,
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
    
    public String getTfId() {
		return tfId.getText();
	}
    
    public static void main(String[] args) {
    	LoginForm frame = new LoginForm();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
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

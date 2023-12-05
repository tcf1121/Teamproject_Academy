package GUI.Main;

import javax.swing.*;

import Back.Datarelated.lecturer.member.LecturerMemberVo;
import Back.Datarelated.student.member.StudentMemberVo;
import Back.Server.MySocketClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


public class JoinForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
    private LoginForm owner;
    private JTextField tfId;
    private JPasswordField tfPw;
    private JTextField tfName;
    private JButton btnJoin;
    private JButton btnduplication;
    private JComboBox cbauthority;
    private MySocketClient Socket;
    private JTextField tfPhonenum;
    private int idcheck = 0;
    
    public JoinForm(LoginForm owner) {
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
        bg.setLayout(null);
        init();
        bg.add(tfId);
        bg.add(tfPw);
        bg.add(tfName);
        bg.add(btnJoin);
        bg.add(btnduplication);
        scrollPane = new JScrollPane(bg);
        

        bg.add(cbauthority);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon(JoinForm.class.getResource("/Image/Background/joinbg.png")));
        lblNewLabel.setBounds(0, 0, 786, 563);
        bg.add(lblNewLabel);
        
        tfPhonenum = new JTextField();
        tfPhonenum.setBounds(318, 302, 165, 23);
        bg.add(tfPhonenum);
        tfPhonenum.setColumns(10);
        setContentPane(scrollPane);
        
        addListeners();
        showFrame();
    }
    private void init() {
        // 크기 고정

    	Font font = new Font("나눔고딕 ExtraBold", Font.BOLD, 15);

        tfId = new JTextField();
        tfId.setFont(font);
        tfPw = new JPasswordField();
        tfPw.setFont(font);
        tfName = new JTextField();
        tfName.setFont(font);
        
        btnJoin = new JButton();
        btnJoin.setBorderPainted(false);
        btnJoin.setContentAreaFilled(false);
        
        btnduplication = new JButton();
        btnduplication.setBorderPainted(false);
        btnduplication.setContentAreaFilled(false);
        
        tfId.setBounds(318, 157, 165, 23);
        tfPw.setBounds(318, 210, 165, 23);
        tfName.setBounds(318, 256, 165, 23);
        btnJoin.setBounds(297, 391, 171, 42);
        btnduplication.setBounds(510, 153, 94, 30);
        
        cbauthority = new JComboBox();
        cbauthority.setFont(font);
        cbauthority.setModel(new DefaultComboBoxModel(new String[] {"학생", "강사"}));
        cbauthority.setBounds(318, 350, 165, 22);
    }
    private void addListeners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
                owner.setVisible(true);
            }
        });
        btnduplication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	try {
					if(Socket.isIdOverlap(tfId.getText(), 0)&&
							Socket.isIdOverlap(tfId.getText(), 1)) {
					    JOptionPane.showMessageDialog(
					            JoinForm.this,
					            "이미 존재하는 Id입니다."
					    );
					    tfId.requestFocus();
					}
					else {
					    JOptionPane.showMessageDialog(
					            JoinForm.this,
					            "사용할 수 있는 Id입니다."
					    );
					    idcheck = 1;
					}
				} catch (HeadlessException | ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        btnJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // 정보 하나라도 비어있으면
                if(isBlank()) {
                    JOptionPane.showMessageDialog(
                            JoinForm.this,
                            "모든 정보를 입력해주세요."
                    );
                    // 모두 입력했을 때
                } else {
                    // Id 중복일 때
                    try {
						if(idcheck == 0) {
						    JOptionPane.showMessageDialog(
						            JoinForm.this,
						            "아이디 중복 확인을 해주세요."
						    );
						    tfId.requestFocus();

						    // Pw와 Re가 일치하지 않았을 때
						} else {
							Object newUser;
							int authority;
							if(cbauthority.getSelectedItem().toString().equals("학생")) {
								newUser = new StudentMemberVo(tfId.getText(), 
										String.valueOf(tfPw.getPassword()), 
										tfName.getText(), tfPhonenum.getText());
								authority = 0;
							}else {
								newUser = new LecturerMemberVo(tfId.getText(), 
										String.valueOf(tfPw.getPassword()), 
										tfName.getText(), tfPhonenum.getText());
								authority = 1;
							}
							Socket.Join(newUser, authority);
						    JOptionPane.showMessageDialog(
						            JoinForm.this,
						            "회원가입을 완료했습니다!"
						    );
						    dispose();
						    owner.setVisible(true);
						}
					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int choice = JOptionPane.showConfirmDialog(
                		JoinForm.this,
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
    public boolean isBlank() {
        boolean result = false;
        if(tfId.getText().isEmpty()) {
            tfId.requestFocus();
            return true;
        }
        if(String.valueOf(tfPw.getPassword()).isEmpty()) {
            tfPw.requestFocus();
            return true;
        }
        if(tfName.getText().isEmpty()) {
            tfName.requestFocus();
            return true;
        }
        if(tfPhonenum.getText().isEmpty()) {
            tfPhonenum.requestFocus();
            return true;
        }
        return result;
    }
    

    private void showFrame() {
        setTitle("Hello, 코딩");//타이틀
        setSize(800,600);//프레임의 크기
        setLocation(owner.getLocation());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
    }
}
package GUI.Student;

import javax.swing.*;

import Back.Server.MySocketClient;
import GUI.Main.MainForm;
import Back.Datarelated.Course.CourseMemberVo;
import Back.Datarelated.student.member.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
 
public class SCourseForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    JScrollPane scrollPane;
    ImageIcon icon;
    private JButton btnBack;
    private JButton btnMove;
    private JLabel lblui;
    private MySocketClient Socket;
    private StudentMemberVo user;
    private MainForm owner;
    private JComboBox cbCourse;
    private List<CourseMemberVo> listcourse;
    
    public SCourseForm(MainForm owner, StudentMemberVo user) throws ClassNotFoundException, IOException {
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
        this.user = user; 
        listcourse = Socket.getCourseList(user.getSId());
        bg.setLayout(null);
        init();
        
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        bg.add(btnBack);
        bg.add(btnMove);
        bg.add(cbCourse);
        bg.add(lblui);
        
        addListeners();
        showFrame();
    }
    public void init() {
        lblui = new JLabel(new ImageIcon(MainForm.class.getResource("/Image/Background/Coursebg.png")));
        lblui.setBounds(0, 0, 781, 558);
        
    	String[] coursename = new  String[listcourse.size()];
        for(int i=0;i<listcourse.size();i++) {
        	coursename[i] = listcourse.get(i).getCName();
        }
        btnBack = new JButton(); 
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setBounds(21, 28, 81, 60);
        
        btnMove = new JButton();
        btnMove.setContentAreaFilled(false);
        btnMove.setBorderPainted(false);
        btnMove.setBounds(317, 463, 148, 53);
        
        cbCourse = new JComboBox();
        cbCourse.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 21));
        cbCourse.setModel(new DefaultComboBoxModel(coursename));
        cbCourse.setBounds(181, 277, 407, 75);
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

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	owner.setVisible(true);
            }
        });
        btnMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
                try {
                	setVisible(false);
                	listcourse.get(cbCourse.getSelectedIndex()).setSId(user.getSId());
                	listcourse.get(cbCourse.getSelectedIndex()).setSName(user.getSName());
					new MyCourseForm(SCourseForm.this, listcourse.get(cbCourse.getSelectedIndex()));
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		SCourseForm.this,
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

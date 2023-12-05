package GUI.Lecturer;

import javax.swing.*;

import Back.Server.MySocketClient;
import GUI.Main.MainForm;
import Back.Datarelated.Class.ClassVo;
import Back.Datarelated.lecturer.member.LecturerMemberVo;
import Back.Datarelated.student.member.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
 
public class CourseapForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    JScrollPane scrollPane;
    ImageIcon icon;
    private JButton btnBack;
    private JButton btnAp;
    private JLabel lblui;
    private MySocketClient Socket;
    private LecturerMemberVo user;
    private MainForm owner;
    private String id;
    private JComboBox cbTime;
    private JTextField tfCname;
    
    public CourseapForm(MainForm owner, LecturerMemberVo user) throws ClassNotFoundException, IOException {
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
        this.id = user.getSId();
        bg.setLayout(null);
        init();
        
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        bg.add(btnBack);
        bg.add(btnAp);
        bg.add(cbTime);
        bg.add(lblui);
        
        tfCname = new JTextField();
        tfCname.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 21));
        tfCname.setBounds(369, 211, 196, 53);
        bg.add(tfCname);
        tfCname.setColumns(10);
        
        addListeners();
        showFrame();
    }
    public void init() {
        lblui = new JLabel(new ImageIcon(MainForm.class.getResource("/Image/Background/CourseApbg.png")));
        lblui.setBounds(0, 0, 781, 558);
        
    	String[] timename = {"A", "B", "C", "D", "E", "F"};
        btnBack = new JButton(); 
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setBounds(21, 28, 81, 60);
        
        btnAp = new JButton();
        btnAp.setContentAreaFilled(false);
        btnAp.setBorderPainted(false);
        btnAp.setBounds(297, 482, 185, 53);
        
        cbTime = new JComboBox();
        cbTime.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 21));
        cbTime.setModel(new DefaultComboBoxModel(timename));
        cbTime.setBounds(369, 323, 196, 53);
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
        btnAp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ClassVo CV = new ClassVo();
            	CV.setCName(tfCname.getText());
            	CV.setTId((String)cbTime.getSelectedItem());
            	CV.setLId(id);
            	try {
					Socket.Classinsert(CV);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                JOptionPane.showMessageDialog(
                		CourseapForm.this,
                		"강의를 등록하였습니다."
                );
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		CourseapForm.this,
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

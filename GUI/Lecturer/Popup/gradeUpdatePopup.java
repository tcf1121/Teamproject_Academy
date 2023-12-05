package GUI.Lecturer.Popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Back.Datarelated.Course.CourseMemberVo;
import Back.Server.MySocketClient;
import GUI.Lecturer.LecturerCourseForm;

import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class gradeUpdatePopup extends JDialog{
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
    ImageIcon icon;
	private JButton btnX;
	private JButton btnupdate;

	
	private LecturerCourseForm owner;
	private JTextField tfSname;
	private JTextField tfSPhone;
	private JTextField tfGrade;
	private String name;
	private String phone;
	private String grade;
	private CourseMemberVo CV;
    private MySocketClient Socket;
    
	public gradeUpdatePopup(LecturerCourseForm owner, CourseMemberVo CV) {
		this.owner = owner;
        try {
			Socket = new MySocketClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	    icon = new ImageIcon(gradeUpdatePopup.class.getResource("/Image/Background/gradeupdatebg.png"));
        //배경 Panel 생성후 컨텐츠페인으로 지정      
        JPanel bg = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
                g.drawImage(null, 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        this.name = CV.getSName();
        this.phone = CV.getSPhone();
        this.grade = CV.getGrade();
        this.CV = CV;
        System.out.println(CV.getCId());
        System.out.println(CV.getSId());
        System.out.println(CV.getGrade());
	    bg.setLayout(null);
	    init();

	    scrollPane = new JScrollPane(bg);
	    
	    JLabel lblNewLabel = new JLabel("New label");
	    lblNewLabel.setIcon(icon);
	    lblNewLabel.setBounds(0, 0, 377, 289);
	    
	    bg.add(tfSname);
	    bg.add(tfSPhone);
	    bg.add(tfGrade);
	    bg.add(btnupdate);
	    bg.add(btnX);
	    bg.add(lblNewLabel);
	    setContentPane(scrollPane);
	    
	    addListeners();
	    showFrame();
	}
	private void init() {
		Font font = new Font("SansSerif", 1, 20);
		
	    btnupdate = new JButton();
	    btnupdate.setBorderPainted(false);
	    btnupdate.setContentAreaFilled(false);
	    btnupdate.setBounds(145, 226, 91, 54);
	    
	    tfSname = new JTextField();
	    tfSname.setEditable(false);
	    tfSname.setBackground(new Color(255, 255, 255));
	    tfSname.setBounds(134, 87, 190, 27);
	    tfSname.setFont(font);
	    tfSname.setBorder(null);
	    tfSname.setText(name);
	    
	    tfSPhone = new JTextField();
	    tfSname.setEditable(false);
	    tfSPhone.setBounds(134, 135, 190, 27);	    
	    tfSPhone.setFont(font);
	    tfSPhone.setBorder(null);
	    tfSPhone.setText(phone);
	    
	    tfGrade = new JTextField();
	    tfGrade.setBounds(134, 181, 190, 27);	    
	    tfGrade.setFont(font);
	    tfGrade.setBorder(null);
	    tfGrade.setText(grade);
 
        btnX = new JButton();
        btnX.setBorderPainted(false);
        btnX.setContentAreaFilled(false);
        btnX.setBounds(338, 10, 33, 33);
	}
	
    private void showFrame() {
        setSize(379,292);//프레임의 크기
        setUndecorated(true);
        Point p = new Point();
        p.setLocation(owner.getLocation().getX()+
        		(owner.getSize().getWidth()-getSize().getWidth())/2,
        		owner.getLocation().getY()+
        		(owner.getSize().getHeight()-getSize().getHeight())/2);
        setLocation(p);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);//창의 크기를 변경하지 못하게
        setVisible(true);
        setBackground(new Color(0,0,0,100));
    }
	
    private void addListeners() {
        btnupdate.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                if (tfGrade.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(gradeUpdatePopup.this,
                            "성적을 입력해주세요.",
                            "오류",
                            JOptionPane.WARNING_MESSAGE);
                }
                else {
                	CV.setGrade(tfGrade.getText());
                	try {
						Socket.updateGrade(CV);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    JOptionPane.showMessageDialog(gradeUpdatePopup.this,
                            "성적을 입력했습니다.",
                            "성공",
                            JOptionPane.WARNING_MESSAGE);
            		owner.getDTmodel().setValueAt(tfGrade.getText(), owner.getJtable().convertRowIndexToModel(owner.getJtable().getSelectedRow()), 3);
                	dispose();
                	dispose();
                }
        	}
    	});
    	
        btnX.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            dispose();
        	}
    	});
    }
}

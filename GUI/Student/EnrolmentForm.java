package GUI.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Back.Datarelated.Class.ClassVo;
import Back.Datarelated.student.member.StudentMemberVo;
import Back.Server.MySocketClient;
import GUI.Main.MainForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
 
public class EnrolmentForm extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StudentMemberVo users;
    JScrollPane scrollPane;
    ImageIcon icon;
    private JButton btnAp;
    private JButton btnBack;
    private JLabel lblui;
    private JScrollPane tablescroll;
    private JTable taClasslist;
    private DefaultTableModel DTmodel;
    private String header[]= {"강의실","강사명", "강의명","시간", "강의ID"};
    private TableRowSorter<DefaultTableModel> sorter;
    private MainForm owner;
    private MySocketClient Socket;
    private List<ClassVo> list;
    public EnrolmentForm(MainForm owner, StudentMemberVo user) throws ClassNotFoundException, IOException {
    	this.owner = owner;
    	users = user;
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
        list = Socket.getClassList("ALL");
        bg.setLayout(null);
        init();
        bg.add(btnAp);
        bg.add(btnBack);
        tablescroll.setViewportView(taClasslist);
        bg.add(tablescroll);
        bg.add(lblui);
        scrollPane = new JScrollPane(bg);
        setContentPane(scrollPane);
        
        
        addListeners();
        showFrame();
    }
    public void init() {
    	Font font = new Font("SansSerif", 1, 20);
        
        btnAp = new JButton();
		btnAp.setBorderPainted(false);
		btnAp.setContentAreaFilled(false);
        btnAp.setBounds(313, 512, 156, 40);
        
        btnBack = new JButton();
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
        btnBack.setBounds(24, 30, 68, 60);
        
        lblui = new JLabel(new ImageIcon(MainForm.class.getResource("/Image/Background/EnrolmentForm.png")));
        lblui.setBounds(0, 0, 781, 558);
        
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
        	Object[] data = {list.get(i).getRoom(), list.get(i).getLName(),
        			list.get(i).getCName(), list.get(i).getTId(), list.get(i).getCId()};
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
        tablescroll.setBounds(39, 140, 699, 364);
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

        btnAp.addActionListener(new ActionListener() {
            @Override           
            public void actionPerformed(ActionEvent e) {
            	try {
					Socket.Courseinsert(users.getSId(), getCId());
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                JOptionPane.showMessageDialog(
                		EnrolmentForm.this,
                		"수강 신청이 완료되었습니다."
                );
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	owner.setVisible(true);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                		EnrolmentForm.this,
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
   
    public String getCId(){
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

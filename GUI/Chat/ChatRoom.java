package GUI.Chat;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import Back.Datarelated.Chatting.ChattingService;
import Back.Datarelated.Chatting.ChattingVo;
import Back.Server.MySocketClient;
import GUI.Lecturer.LecturerCourseForm;
import GUI.Main.MainForm;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class ChatRoom extends Thread {
	private ChattingService cs;
	private ChattingVo cv;
	private List<ChattingVo> chatList;
	private JFrame JF;
	private JPanel contentPane;
	private JTextField textField;
	private JTextPane textPane;
	private JTextArea textArea;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private StyledDocument doc;
	private int nTime;
	private int chatnum = 0;
	private ChattingVo chat;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */

	public ChatRoom(ChattingVo chat) {
		cs = new ChattingService();
		chatList = cs.getChatting(chat.getChatRoomId());
		System.out.println(chat.getSendId());
		if(chatList != null) {
			chatnum = chatList.size();
		}
		JF = new JFrame();
		this.chat = chat;
		JF.setTitle("채팅방");
		JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JF.setBounds(100, 100, 365, 473);
		JF.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(236, 241, 248));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		JF.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		init();
		for(int i =0; i<chatList.size();i++) {
			displayComment(chatList.get(i));
		}
		addListeners();
	}
	
	public void init() {
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("채팅방");
		textField.setBackground(new Color(142, 180, 227));
		textField.setBounds(0, 0, 349, 46);
		contentPane.add(textField);
		textField.setColumns(10);
		textArea = new JTextArea();
		textArea.setBounds(10, 364, 220, 60);
		contentPane.add(textArea);
		
		btnNewButton = new JButton("전송");
		btnNewButton.setBackground(new Color(142, 180, 227));
		btnNewButton.setBounds(240, 364, 97, 60);
		contentPane.add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 44, 349, 310);
		contentPane.add(scrollPane);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setBackground(new Color(236, 241, 248));
	}
	
	 public void addListeners() {

		 //보내기
		 btnNewButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	LocalTime now = LocalTime.now();
	            	String formatedNow = now.format(DateTimeFormatter.ofPattern("aHH:mm"));
	            	cv = new ChattingVo(chat.getChatRoomId(), chat.getSendId(), chat.getSendName()
	            			,formatedNow, textArea.getText());
	            	cs.addChat(cv);
	            	textArea.setText(null);
	            }
	        });
	 }
	
	 public void displayComment(ChattingVo message) {
	      // 오른쪽으로 출력.
		 if(message.getSendId().equals(chat.getSendId())) {
             textPrint(message.getRTime()
	                    + "  <" + message.getSendName() + ">", "right");
	           textPrint(message.getHistory(), "right");
		 }
		 else {
	           // 왼쪽으로 출력.    
	           //!chatName.panelName.equals(message.getReceiveFriendName()) -> 나와의 채팅은 왼쪽 출력되면 안되니까.
	           textPrint(message.getRTime()
	                    + "  <" + message.getSendName() + ">", "left");
	           textPrint(message.getHistory(),"left");
		 }
	}
	
	private void textPrint(String message, String center) {
	    try {
	    	doc = textPane.getStyledDocument();
			SimpleAttributeSet sortMethod = new SimpleAttributeSet();
		      
		    if(center.equals("right")) {
		       StyleConstants.setAlignment(sortMethod, StyleConstants.ALIGN_RIGHT);   
		    }else if (center.equals("left")) {
		       StyleConstants.setAlignment(sortMethod, StyleConstants.ALIGN_LEFT);  
		    }
		    doc.setParagraphAttributes(doc.getLength(), doc.getLength() + 1, sortMethod, true);
			doc.insertString(doc.getLength(), message + "\n", sortMethod);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	@Override
	public void run(){

		while (true) {
			try {
				Thread.sleep(2000);
				ChattingVo ChatV = cs.getChat(chat.getChatRoomId());
				if(ChatV != null) {
					int recentnum = ChatV.getId();
					if(recentnum > chatnum) {
						chatnum++;
						displayComment(ChatV);
					}
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}

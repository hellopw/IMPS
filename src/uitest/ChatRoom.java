package uitest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import socket.Client;
import socket.Client.Recv;
import socket.Client.Send;
import socket.Constants;
import socket.ServerHandler;
import Encryption.AEScrypt;
import Encryption.Hash;
import Encryption.Pkconsult;
import bean.Message;
import bean.Msg;
import bean.User;
import bean.UserInfo;

public class ChatRoom extends JFrame{
	private static final int DEFAULT_WIDTH= 350;
	private static final int DEFAULT_HEIGHT=500;
	User user1;
	User user2;
    UserInfo ui;
    String w;
    public static JTextArea showmessage = new JTextArea();           //消息的显示框   对话框
    final JTextField editmessage = new JTextField();                 //消息编辑框 
    
           public ChatRoom(final User user1, User user2,final UserInfo ui){                       //User user
             this.user1=user1;
             this.user2=user2;
             this.ui=ui;
             w=user2.getNickName();
             
             init();
           }
          public ChatRoom(User user1, String name){
        	  this.user1=user1;
        	  this.w=name;
          }
          public static int getStates(User u1, String s){
        	  ChatRoom ch=new ChatRoom(u1,s);
        	  if(ch.isVisible()==false){
        		  return 0;
        	  }else return 1;
          }
           public void init(){
        	   setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        	   setLayout(null);
        	   Panel panel1 = new Panel();
        	   Panel panel2 = new Panel();
        	   Panel panel3 = new Panel();
        	   Panel panel4 = new Panel();
        	   
        	JLabel  label =new JLabel(new ImageIcon("E:\\BaiduNetdiskDownload\\FEIQQ\\FEIQQ\\src\\feiqq\\resource\\image\\avatar.png"));
       		JLabel  label1 =new JLabel(user2.getNickName());
       		JLabel  label2 =new JLabel(""+user2.getAccount());
       		JLabel  label3 =new JLabel(ui.getUserAddress());
       		//panel1.setPreferredSize(new Dimension(300,50));
       		panel1.setLayout(new GridLayout(1,4));
       		panel1.add(label);
       		panel1.add(label1);
       		panel1.add(label2);
       		panel1.add(label3);
       		panel1.setBounds(0,5,300,65);
       	    add(panel1);
        	   
       	    
       	 //JTextArea ta = new JTextArea();	
       	//JTextArea tb = new JTextArea();
       	 
       	 //final JTextArea showmessage = new JTextArea();
       	 //showmessage.addActionListener();
       	 showmessage.setEditable(false);
       	//showmessage.setForeground(Color.blue);
       	showmessage.setFont(new Font("宋体",Font.BOLD,15));
       	 showmessage.append("小明： "+"   你好\n");
 		JScrollPane scrollPane2 = new JScrollPane(showmessage);
 		scrollPane2.setOpaque(true);               //透明度
 		scrollPane2.setForeground(Color.black);          //前景色
 		scrollPane2.setPreferredSize(new Dimension(300,290));           //相对大小
 		panel2.add(scrollPane2);
 		panel2.setBounds(10,75,300,260); 	   
 		//panel2.setBackground(Color.blue);
 		panel2.setForeground(Color.blue);
 		add(panel2);  
 		
 		//final JTextField editmessage = new JTextField();
 		editmessage.setEditable(true);
 		//editmessage.setBounds(0,320,250,60);
 		JScrollPane scrollPane3 = new JScrollPane(editmessage);
 		scrollPane3.setBackground(Color.black);
 		scrollPane3.setPreferredSize(new Dimension(230,60));
 		panel3.add(scrollPane3);
 	    panel3.setBounds(10,350,250,70);
 	    add(panel3);
 		
 		
 		JButton send=new JButton("发送");
 		send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {               //有问题
				 if(editmessage.getText().equals("")){   
					 JOptionPane.showMessageDialog(null, "发送内容不能为空", "alter", JOptionPane.ERROR_MESSAGE); 
				 }else{				
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");        //设置日期格式
					Message mess = new Message(user1.getNickName(),editmessage.getText(),df.format(new Date()));    //包装消息内容
					Msg m=new Msg("chat",mess);                   //封装消息
					String send = ServerHandler.bianma(m);             //编码
					
					 Send r=new Send(ui.getUserAddress(),ui.getUserPort(),send);                            //发送消息
      	             Thread t=new Thread(r);                                                          
      	             t.start();
      	             
      	            editmessage.setText("");
					showmessage.append(mess.getSenderName()+" "+mess.getDate()+"\n"+"   "+mess.getContent()+"\n");//显示信息
				 }
				
			} 
 		   
 		});
 		JButton close=new JButton("关闭 ");
 		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();		 //关闭窗口	
				 //mainWindow m=new mainWindow();                 //打开主窗口
			    // m.setVisible(true);
			}  
 		   
 		});
 		//panel4.setLayout();		
 	    panel4.add(send,BorderLayout.NORTH);
 	    panel4.add(close,BorderLayout.SOUTH);
 	    panel4.setBounds(260,350,40,70);
 		add(panel4);	
 		
 		 {
 			ImageIcon img = new ImageIcon("E:/BaiduNetdiskDownload/FEIQQ/FEIQQ/src/feiqq/resource/image/back4.jpg");  	      
 	      JLabel imgLabel = new JLabel(img);  	   
 	      this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));  	    
 	      imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight()); 
 	      Container contain = this.getContentPane();  
 	      ((JPanel) contain).setOpaque(false); 
 		}
 		
 		Recv1 recv=new Recv1(showmessage);		
 		Thread t=new Thread(recv);
 		t.start();
 		
 			//ActionPerformed();		
       
           }
        
       
        public JTextArea getArea(){
        	return showmessage;
        }
           public static void main(String args[]){ 
        	   User user1=new User("网",12345,"123456","qq邮箱");
        	   User user2=new User("斌",13245,"123456","qq邮箱");
        	   UserInfo ui=new UserInfo(user2,"10.122.227.121",8876);
        	   JFrame f=new ChatRoom(user2,user1,ui);  
               f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
               f.setVisible(true);  
           } 
}

class Recv1 implements Runnable { 
    public User user;
    public JTextArea ta;
    public static String mess;
    ServerSocket serverSocket;
    
	public Recv1(JTextArea t){
		ta=t;
	}
	public void run() {
		InetAddress ip;
		try {
			 //ip =  InetAddress.getByName("10.21.194.116");
			ip = InetAddress.getLocalHost();
			//System.out.println(ip);
			try {
				serverSocket = new ServerSocket(Constants.PORT,100,ip);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		} catch (UnknownHostException e1) {
			
			e1.printStackTrace();
		}       		
		while(true){			
			try {       		         
	                  Socket socket = serverSocket.accept();  	                  
	                  try {   
	                      DataInputStream input = new DataInputStream(socket.getInputStream());  
	                      String sss= input.readUTF();
	                      String[] ss=sss.split(",");
	                      String encryptResultStr=ss[0];	                      
	                      byte[] decryptFrom = AEScrypt.parseHexStr2Byte(encryptResultStr);        //解密
	              		  byte[] decryptResult = AEScrypt.decrypt(decryptFrom,Constants.KEY);  	  	              		   
	                      //byte[] decryptResult = AEScrypt.decrypt(decryptFrom,Con_Pk.pk.KEY);	              		  
	              		  mess=new String(decryptResult, "utf-8");
	              		  if(!ss[1].equals(Hash.hex_sha1(mess))){
	              			  mess="哈希验证没通过";
	              			  break;
	              		  }	              		  
	                      input.close();
	                  } catch (Exception e) {    
	                      System.out.println("服务器 run 异常: " + e.getMessage());    
	                  }finally {    
	                      if (socket != null) {    
	                          try {    
	                              socket.close();    
	                          } catch (Exception e) {    
	                              socket = null;    
	                              System.out.println("服务端 finally 异常:" + e.getMessage());    
	                          }    
	                      }    
	                  }	      
	          } catch (Exception e) {    
	              System.out.println("服务器异常: " + e.getMessage());    
	          } 
	        	int n=Check.check(mess);    	              	        
	        	//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        	Message mess2 =Check.getMessage();
	        	ta.append(mess2.getSenderName()+" "+mess2.getDate()+"\n"+"   "+mess2.getContent()+"\n");
		}	
	}
}

package uitest;

import java.awt.Container;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import socket.Constants;
import socket.Client;
import socket.ServerHandler;
import socket.Client.Cs;
import Encryption.Pkconsult;
import Encryption.XmlImpl;
import bean.Friends;
import bean.Msg;
import bean.User;


public class Register extends JFrame implements ActionListener {               //login������Jframe
	public String IP=Constants.SERVER_IP  ;              //������IP
	public int PORT=Constants.PORT;
	
	 String userName;
	 String password;
	 String account;
	 public static String randomcaptcha;
	  
	 public JLabel logoLabel, userNameLabel, passwordLabel, accountLabel,mailLabel;
	 public JTextField userNameInput, accountInput,mailInput;
	 public JPasswordField passwordInput;
	 public JButton cancel, submit;
	 public Panel panel;
	  
	 public Register() {                             //login������Jframe��
	  setTitle("register");
	  setSize(350, 350);                          //��ʾ��Ĵ�С
	  setLocationRelativeTo(null);
	  init();
	  setVisible(true);
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  setResizable(false);
	 }
	  
	 public void init() {
	  setLayout(null);
	 /* logoLabel= new JLabel();
	   logoLabel.setIcon(new ImageIcon("E:\\eclipse\\student3\\welcome.gif"));
	  logoLabel = new JLabel(new ImageIcon("welcome.gif"));
	  logoLabel.setBounds(125, 10, 120, 70);
	  add(logoLabel);*/
	  
	  userNameLabel = new JLabel("�û���:");                 //userNameLabel������Jlabel�࣬��һ����ǩ��Jlabel���к���setbounds����
	  userNameLabel.setBounds(80, 60, 60, 40);              //setbounds�����������꣬�����꣬���ȣ���ȣ�
	  add(userNameLabel);
	  userNameInput = new JTextField();
	  userNameInput.setBounds(140, 70, 120, 20);
	  add(userNameInput);
	  
	  accountLabel = new JLabel("��    ��:");
	  accountLabel.setBounds(80, 90, 60, 40);                         
	  add(accountLabel);
	  accountInput = new JPasswordField();
	  accountInput.setBounds(140, 100, 120, 20);
	  add(accountInput);
	  
	  passwordLabel = new JLabel("��    ��:");            
	  passwordLabel.setBounds(80, 120, 60, 40);
	  add(passwordLabel);
	  passwordInput = new JPasswordField();
	  passwordInput.setBounds(140, 130, 120, 20);
	  add(passwordInput);
	  
	  mailLabel = new JLabel("��    ��:");           
	  mailLabel.setBounds(80, 150, 60, 40);
	  add(mailLabel);
	  mailInput = new JTextField();
	  mailInput.setBounds(140, 160, 120, 20);
	  add(mailInput);
	  
	  submit = new JButton("submit", new ImageIcon("login.gif"));
	  submit.setBounds(50, 220, 120, 30);
	  submit.setMnemonic(KeyEvent.VK_L);
	  add(submit);
	  
	  cancel = new JButton("cancel", new ImageIcon("exit.gif"));
	  cancel.setBounds(190, 220, 120, 30);
	  cancel.setMnemonic(KeyEvent.VK_X);
	  add(cancel);
	  
	  userNameInput.addActionListener(this);
	  passwordInput.addActionListener(this);
	  accountInput.addActionListener(this);
	  mailInput.addActionListener(this);
	  
	  submit.addActionListener(this);
	  cancel.addActionListener(this);
	  //change.addActionListener(this);
	  
	   { ImageIcon img = new ImageIcon("E:/BaiduNetdiskDownload/FEIQQ/FEIQQ/src/feiqq/resource/image/back8.jpg");  	      
	      JLabel imgLabel = new JLabel(img);  	   
	      this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));  	    
	      imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight()); 
	      Container contain = this.getContentPane();  
	      ((JPanel) contain).setOpaque(false); 
		}
	  
	 }
	 
	 public static void main(String[] args) {
	  new Register();
	 }

	    public static String readFileByLines(String fileName) {
	        File file = new File(fileName);
	        String s = "";
	        BufferedReader reader = null;
	        try {
	            System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            int line = 1;
	            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
	            
	            while ((tempString = reader.readLine()) != null) {
	                // ��ʾ�к�
	            	s =s+tempString;
	                System.out.println("line " + line + ": " + tempString);
	                line++;
	            }
	            //System.out.println(s);
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	        return s;
	    }

	 
	public void actionPerformed(ActionEvent e) {
		String name = userNameInput.getText();
		String account = accountInput.getText();
		String password = passwordInput.getText();
		String mail = mailInput.getText();
		
		if(e.getSource() == cancel){
			   JOptionPane.showMessageDialog(this, "�˳�ע��");
			   dispose();                            //�ص�����
		}else if(e.getSource()==submit){
			
			int ran = (int)(Math.random() * 9000) + 1000;
			Constants.prk=ran;
			String prk=ran+"";                                 //����˽Կ
			Constants.setPRK(prk);			                        
			String puk=Pkconsult.qume(ran);                        //���ɹ�Կ
			Constants.setPUK(puk);
			String filename="E:/����֤��.xml";                         //����֤��
			XmlImpl.createXml_2(filename,name,puk);
			String digital_book=readFileByLines(filename);               
			
			
			 User user=new User(name,Integer.valueOf(account),password,mail);	
			 user.setPublickey(puk);
			 user.setPrivatekey(prk);
			 user.setDigitalbook(digital_book);
			 Msg msg=new Msg("register",user);
				 
				String send=ServerHandler.bianma(msg);
				
				//Client c=new Client();
				Cs cs=new Cs(IP,PORT,send);
				String mess=cs.run();
			  
				int n=Check.check(mess);
				
			 if(n>0){
				 JOptionPane.showMessageDialog(this, "ע��ɹ����뷵�ص�½�����½!");
				    dispose();
				    Login l=new Login();
				    l.setVisible(true);
			 }else{
				 JOptionPane.showMessageDialog(this, "ע��ʧ��");
				    panel.repaint();
			 }
		}
		
	}
	}
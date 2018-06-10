package uitest;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import socket.Client;
import socket.Client.Cs;
import socket.ServerHandler;

import com.google.gson.Gson;

import socket.Constants;
import bean.Friends;
import bean.Msg;
import bean.User;
  
public class Login extends JFrame implements ActionListener {               //login对象是Jframe类
	
	public String IP=Constants.SERVER_IP;                //服务器IP
	public int PORT=Constants.PORT;
	
	User user=new User();
	ArrayList<Friends> list=new ArrayList<Friends>();
	
 String userName;
 String password;
 String captcha;
 public static String randomcaptcha;
  
 public JLabel logoLabel, userNameLabel, passwordLabel, captchaLabel;
 public JTextField userNameInput, captchaInput;
 public JPasswordField passwordInput;
 public JButton login, logout,change,register;
 public Panel panel;
  
 public Login() {                             //login对象是Jframe类
  setTitle("login");
  setSize(400, 300);                          //显示框的大小
  setLocationRelativeTo(null);
  this.init();
  setVisible(true);
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setResizable(false);
 }
  
 public void init() {
  setLayout(null);
  // logoLabel= new JLabel();
  // logoLabel.setIcon(new ImageIcon("E:\\eclipse\\student3\\welcome.gif"));
  logoLabel = new JLabel(new ImageIcon("welcome.gif"));
  logoLabel.setBounds(125, 10, 120, 70);
  add(logoLabel);
  
  userNameLabel = new JLabel("用户名:");                 //userNameLabel对象是Jlabel类，是一个标签。Jlabel类中含有setbounds方法
  userNameLabel.setBounds(60, 90, 60, 40);              //setbounds方法（横坐标，纵坐标，长度，宽度）
  add(userNameLabel);
  userNameInput = new JTextField();
  userNameInput.setBounds(120, 100, 120, 20);
  add(userNameInput);
  
  passwordLabel = new JLabel("密码:");
  passwordLabel.setBounds(60, 120, 60, 40);                         //（横坐标，纵坐标，长度，宽度）
  add(passwordLabel);
  passwordInput = new JPasswordField();
  passwordInput.setBounds(120, 130, 120, 20);
  add(passwordInput);
  
  captchaLabel = new JLabel("验证码:");            //输入验证码的框
  captchaLabel.setBounds(60, 150, 60, 40);
  add(captchaLabel);
  captchaInput = new JTextField();
  captchaInput.setBounds(120, 160, 50, 20);
  add(captchaInput);
  
  panel = new PanelDemo();                      //验证码框
  panel.setBounds(180, 160, 80, 20);
  add(panel);
    
    
  change = new JButton("change");
  change.setBounds(270, 160, 80, 20);
  change.setContentAreaFilled(true);
  change.setBorderPainted(true);
  add(change);
  
  login = new JButton("login", new ImageIcon("login.gif"));
  login.setBounds(40, 200, 80, 30);
  login.setMnemonic(KeyEvent.VK_L);
  add(login);
  logout = new JButton("logout", new ImageIcon("exit.gif"));
  logout.setBounds(150, 200, 80, 30);
  logout.setMnemonic(KeyEvent.VK_X);
  add(logout);
  register = new JButton("register", new ImageIcon("exit.gif"));
  register.setBounds(270, 200, 80, 30);
  register.setMnemonic(KeyEvent.VK_X);
  add(register);
  
  userNameInput.addActionListener(this);
  passwordInput.addActionListener(this);
  captchaInput.addActionListener(this);
  
  login.addActionListener(this);
  logout.addActionListener(this);
  change.addActionListener(this);
  register.addActionListener(this);
  
  {
		ImageIcon img = new ImageIcon("E:/BaiduNetdiskDownload/FEIQQ/FEIQQ/src/feiqq/resource/image/back8.jpg");  	      
      JLabel imgLabel = new JLabel(img);  	   
      this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));  	    
      imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight()); 
      Container contain = this.getContentPane();  
      ((JPanel) contain).setOpaque(false); 
	}
  
 }
 

  
 public void actionPerformed(ActionEvent e)  {
	 
	 
		
  userName = userNameInput.getText();
  password = new String(passwordInput.getPassword());
  captcha = captchaInput.getText();
    
  if (e.getSource() == change) {
   panel.repaint();
  }
  if (e.getSource() == login) {
	  if(captcha.equals(randomcaptcha)){
		  
		Msg msg= new Msg("login",Integer.valueOf(userName),password);
		String send=ServerHandler.bianma(msg);
		
		//Client c=new Client();
		Cs cs=new Cs(IP,PORT,send);
		String mess=cs.run();
		
		//String mess=cs.getMessage();
		int n=Check.check(mess);
		//System.out.println("login"+number+user.getNickName()+user.getAccount()+" 2 "+list.get(0).getOwner()+list.get(1).getFriend());
		  if(n==1){                //登陆成功
			  mainWindow m=new mainWindow(Check.getUser(),Check.getFriends());           //user friend
				  m.setVisible(true);
				  dispose();
		  }else{
			  JOptionPane.showMessageDialog(this, 0);
			    panel.repaint();
		  }
	  }else{
		  JOptionPane.showMessageDialog(this, "验证码错误!");
		     panel.repaint();
	  }
	  		
  }
  if (e.getSource() == logout) {
	  JOptionPane.showMessageDialog(this, "注销成功");
	     dispose();                        //关掉窗口
   
   
  }
  if(e.getSource() == register){
	  Register r=new Register();
	  r.setVisible(true);
	  dispose();
  }
 }
 public static void main(String[] args) {
  new Login();
 }
 
}
class PanelDemo extends Panel {
 public void paint(Graphics g) {
  int width = 80;
  int height = 20;
  g.setColor(Color.LIGHT_GRAY);
  g.fillRect(0, 0, width, height);
  g.setColor(Color.BLACK);
  g.drawRect(0, 0, width, height);
  Random rd = new Random();
  for (int i = 0; i < 100; i++) {
   int x = rd.nextInt(width) - 2;
   int y = rd.nextInt(height) - 2;
   g.setColor(Color.RED);
   g.drawOval(x, y, 2, 2);
  }
  g.setFont(new Font("待定", Font.BOLD, 20));
  g.setColor(Color.BLUE);
  char[] c = "0123456789".toCharArray();
  StringBuffer sb = new StringBuffer();
  for (int i = 0; i < 4; i++) {
   int index = rd.nextInt(c.length);
   sb.append(c[index] + " ");
  }
  g.drawString(sb.toString(), 8, 18);
  
  String str = sb.toString().replaceAll(" ", "");
  Login.randomcaptcha = str;
 }
}
 






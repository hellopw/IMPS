package socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import socket.Client.Cs;
import socket.Client.Send;
import bean.Friends;
import bean.Msg;
import bean.User;
import bean.UserInfo;

import com.google.gson.Gson;

import database.BaseDao;

public class ServerHandler{	
	
	
	public static  int login_check(int i, String pass) throws SQLException {       //n>0 ��¼�ɹ�                            ����������
		String sql = "select * from �û�  where account = "+i+"and password ='"+pass+"'";
		ResultSet r = BaseDao.select(sql);
		if(r.next() == true)
		return 1;
		else
		return 0;		
	}
	
	public static  User getUser(int acc,String pass){                  //�����û���Ϣ����¼	
		User user=new User();
		String sql="select * from �û�  where account = "+
				acc+" and password = '"+pass+"'";
		ResultSet result = BaseDao.select(sql);		
		try {
			if (null != result && result.next()) {                   //��û������
				String name = result.getString("name");
				int account = result.getInt("account");
				String password = result.getString("password");
				String signature = result.getString("signature");
				user= new User(name, account, password, signature);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	public static  User getUser_byacc(int acc){                  //�����û�	
		User user=new User();
		String sql="select * from �û�  where account = "+	acc;
		ResultSet result = BaseDao.select(sql);		
		try {
			if (null != result && result.next()) {                   //��û������
				String name = result.getString("name");
				int account = result.getInt("account");
				String password = result.getString("password");
				String signature = result.getString("signature");
				user= new User(name, account, password, signature);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	public static  User getUser_byname(String na){                  //����Ҫ���жԻ����û�
		User user=new User();
		String sql="select * from �û�  where name = '"+na+"'";
		ResultSet result = BaseDao.select(sql);		
		try {
			if (null != result && result.next()) {                   //��û������
				String name = result.getString("name");
				int account = result.getInt("account");
				String password = result.getString("password");
				String signature = result.getString("signature");
				user= new User(name, account, password, signature);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	/*public static int getPuKey(User u){            //�����û���Կ
		String sql="select public_key from �û�    where account = "+u.getAccount();
		ResultSet result = BaseDao.select(sql);
		int k = 0;
		try {
			k = result.getInt("PuKey");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return k;
	}*/
	public static int register_check(User u) throws SQLException{      //n>0˵���û��˺Ŵ���      ����������
		String sql="select * from �û�  where account= "+u.getAccount();		
		ResultSet r = BaseDao.select(sql);		
			if(r.next() == true)
			return 1;
			else
			return 0;	
	}
	public static int state_check(User u) throws SQLException{             //n>0˵������
		String sql="select * from ����  where friend_name= '"+u.getNickName()+"' and state="+1;
		ResultSet re= BaseDao.select(sql);		
			if(re.next() == true)
			return 1;
			else return 0;
		
	}
	public static int register(User u){                   //n>0ע��ɹ�
		String sql = "insert into �û� (name,account,password,email,public_key,digital_book) " 
                +"VALUES ('"+u.getNickName()+"',"+u.getAccount()+",'"+u.getPassWord()+"','"+u.getEmail()+"','"+u.getPublickey()+"','"+u.getDigitalbook()+"')";
		int n=BaseDao.operate(sql);
		return n;
	}
	public static int delete(User a,User b){           //n>0ɾ���ɹ�
		String sql="delete from ����  where owner_name= '"+a.getNickName()+"'and friend_name = '"+b.getPassWord()+"'";   
		int n= BaseDao.operate(sql);
		return n;
	}
	public static int addfriend(User a,User b,String group){       //n>0��ʾ��ӳɹ�
		String sql = "insert into ���� (owner_name,group_name,friend_name,state) " 
                +"VALUES ('"+a.getNickName()+"','"+group+"','"+b.getNickName()+"',"+1+")";
		int n=BaseDao.operate(sql);
		return n;
	}
	/*public static int update_signature(User u,String s){
		String sql="update �û� set user_signature="+u.getSignature()+"where user_name="+u.getAccount(); 
		int n= BaseDao.operate(sql);
		return n;
	}*/
	
	public static int update_state1(User u,String ip,int state){          //����״̬
		String sql="update ����   set state ="+state+"where friend_name='"+u.getNickName()+"'"; 
		int n= BaseDao.operate(sql);
		return n;
	}
	/*public static int update_state2(User u,String ip,int state){          //����״̬
		String sql="update �û���Ϣ   set state ="+state+"and IP ='"+ip+"' where (account="+u.getAccount()+"or name='"+u.getNickName()+"')"; 
		int n= BaseDao.operate(sql);
		return n;
	}*/
	
	/*public static int update_onui(User u,String ip,int port,int state){
		String sql = "insert into �û���Ϣ ( IP,port,state,account,name) " 
                +"VALUES ('" +ip+ "',"+port+","+state+","+u.getAccount()+",'"+u.getNickName()+"')";
		int n=BaseDao.operate(sql);
		return n;
	}*/
	
	public static int delete_ui(User u){                     //�����û���Ϣ    IP��״̬
		String sql = "delete from �û���Ϣ  where name= '"+u.getNickName()+"'";
		int n=BaseDao.operate(sql);
		return n;
	}
	public static int insert_ui(int account,String name,String ip,int port,int state){
		String sql = "insert into �û���Ϣ(IP,port,state,account,name) " 
                +"VALUES ('" +ip+ "',"+port+","+state+","+account+",'"+name+"')";
		int n=BaseDao.operate(sql);
		return n;
	}
	
	public static ArrayList<Friends> getFriends(User u){               //��¼ʱ����
		ArrayList<Friends> friend_list = new ArrayList<Friends>();	
		String sql="select * from ����  where owner_name = '"+u.getNickName()+"'";
		ResultSet result = BaseDao.select(sql);		
		try {
			while (null != result && result.next()) {                   
				String owner_name = result.getString("owner_name");
				String group_name = result.getString("group_name");
				String friend_name = result.getString("friend_name");
				String state = result.getString("state");
				Friends f= new Friends(owner_name,group_name,friend_name,state);
				friend_list.add(f);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friend_list;
	}
	public static UserInfo getUserInfo(User u){
		UserInfo ui=new UserInfo();
		String sql="select * from �û���Ϣ  where name = '"+u.getNickName()+"'";
		ResultSet result = BaseDao.select(sql);		
		try {
			if (null != result && result.next()) {                   
				String ip = result.getString("IP");
				int port = result.getInt("port");
				String state = result.getString("state");
				//int account = result.getInt("account");
				//String name = result.getString("name");
				//String state = result.getString("state");
				//User u=new (account,name);
				 ui= new UserInfo(u,ip,port,state);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ui;
	}
	public static UserInfo getUserInfo2(User u){
		UserInfo ui=new UserInfo();
		String sql="select * from �û���Ϣ  where account = "+u.getAccount();
		ResultSet result = BaseDao.select(sql);		
		try {
			if (null != result && result.next()) {                   
				String ip = result.getString("IP");
				int port = result.getInt("port");
				String state = result.getString("state");
				//int account = result.getInt("account");
				//String name = result.getString("name");
				//String state = result.getString("state");
				//User u=new (account,name);
				 ui= new UserInfo(u,ip,port,state);		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ui;
	}
	
	
	static Gson gson = new Gson();
	public static Msg jiema(String a){
		Msg o = gson.fromJson(a, Msg.class);;
		return o;		
	}
	public static String bianma(Msg o){
		String s=gson.toJson(o);
		return s;	
	}
	public static void handle(Msg m,DataOutputStream out,String ip,int port) throws SQLException{  
		if(m.getType().equals("login")){
			int n=ServerHandler.login_check(m.getAccount(),m.getPassword());
			if(n>0){
				System.out.println("��¼�ɹ�\n");				
				
			User user=ServerHandler.getUser(m.getAccount(),m.getPassword());
			ArrayList<Friends> friends=ServerHandler.getFriends(user);
			
			insert_ui(m.getAccount(),user.getNickName(),ip,port,1);           //���ݿ����
			update_state1(user,ip,1);
			
			Msg msg=new Msg("login_ok",user,friends);
			String send=bianma(msg);
			try {
				out.writeUTF(send);
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}	
			}else{
				Msg msg=new Msg("login_fail",0);
				String send=bianma(msg);
				System.out.println("��¼ʧ��\n");
				try {
					out.writeUTF(send);
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
		}else if(m.getType().equals("register")){
			int n=ServerHandler.register_check(m.getUser());
			if(n>0){
				Msg msg=new Msg("register_fail",0);
				String send=bianma(msg);
				System.out.println("ע��ʧ��\n");
				try {
					//out.writeUTF("ע��ʧ�ܣ��˻����Ѵ���");
				     out.writeUTF(send);
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}else{
				int k=ServerHandler.register(m.getUser());
				if(k>0){				
					System.out.println("ע��ɹ�\n");
					Msg msg=new Msg("register_ok",1);
					String send=bianma(msg);
					try {
						//out.writeUTF("ע��ɹ�");
						out.writeUTF(send);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					Msg msg=new Msg("register_fail",0);
					String send=bianma(msg);
					System.out.println("ע��ʧ��\n");
					try {
						//out.writeUTF("ע��ʧ�ܣ������� ����");
						out.writeUTF(send);
					} catch (IOException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				}
			}		
		}else if(m.getType().equals("get")){	
			int n=ServerHandler.state_check(m.getUser());
		if(n>0){
		    User user=ServerHandler.getUser_byname(m.getUser().getNickName());
			UserInfo ui=ServerHandler.getUserInfo(user);
			Msg msg=new Msg("get_ok",user,ui);
			String send=bianma(msg);
			try {
				out.writeUTF(send);
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}	
		}else{
			Msg msg=new Msg("get_fail",0);
			String send=bianma(msg);
			try {
				out.writeUTF(send);
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}	
			}
		}else if(m.getType().equals("delete")){
			int n=delete(m.getUsers()[0],m.getUsers()[1]);
			if(n>0){
				Msg msg=new Msg("delete_ok",1);
				String send=bianma(msg);
				try {
					out.writeUTF(send);
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}	
			}else{
				Msg msg=new Msg("delete_fail",0);
				String send=bianma(msg);
				try {
					out.writeUTF(send);
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}	
			}
		}else if(m.getType().equals("add_agree")){	
			User u=getUser_byacc(m.getUsers()[1].getAccount());
			int n=addfriend(m.getUsers()[0],u,m.getCata()); 
			int n1=addfriend(u,m.getUsers()[0],m.getCata());
			UserInfo ui=getUserInfo(m.getUsers()[0]);
			if(n>0){                                                  //���ڣ����				
				Msg msg=new Msg("add_ok",1);
				String send=bianma(msg);
				
				Send r=new Send(ui.getUserAddress(),ui.getUserPort(),send);                            //������Ϣ
 	             Thread t=new Thread(r);                                                          
 	             t.start();	
				
 	            try {
					out.writeUTF(send);
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}	
 	             
			}else{
				Msg msg=new Msg("add_fail0",0);          //�������ʧ��
				String send=bianma(msg);
				
				Send r=new Send(ui.getUserAddress(),ui.getUserPort(),send);                            //������Ϣ
	             Thread t=new Thread(r);                                                          
	             t.start();	
	             try {
						out.writeUTF(send);
					} catch (IOException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}	
			}
		}else if(m.getType().equals("add_disagree")){      
			UserInfo ui=getUserInfo(m.getUsers()[0]);
			Msg msg=new Msg("add_fail1",0);                       //���ܾ�
			String send=bianma(msg);
			
			try {
				out.writeUTF(send);
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}	
			/*Send r=new Send(ui.getUserAddress(),ui.getUserPort(),send);                            //������Ϣ
             Thread t=new Thread(r);                                                          
             t.start();*/			
		}else if(m.getType().equals("add_req_server")){
						
			int k=ServerHandler.register_check(m.getUsers()[1]);          //����˺��Ƿ����
			
			if(k>0){                                               //����
				UserInfo ui=getUserInfo2(m.getUsers()[1]);
				Msg msg=new Msg("add_req_user",m.getUsers(),m.getCata());
				String send=bianma(msg);
								
				//Cs cs = new Cs(ui.getUserAddress(), ui.getUserPort(), send);
				//String mess = cs.run();
				
				 Send r=new Send(ui.getUserAddress(),ui.getUserPort(),send);                     //������Ϣ
	             Thread t=new Thread(r);                                                          
	             t.start();	
	             //System.out.println(ui.getUserAddress()+ui.getUserPort());
	             Msg msg1=new Msg("add_wait",0);
					String send1=bianma(msg1);
	             try {
						out.writeUTF(send1);
					} catch (IOException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}	
	             
			}else{                   //������
				UserInfo ui=getUserInfo(m.getUsers()[0]);
				Msg msg=new Msg("add_req_fail",0);
				String send=bianma(msg);
				
				try {
					out.writeUTF(send);
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}	
				//Cs cs = new Cs(ui.getUserAddress(), ui.getUserPort(), send);
				//String mess = cs.run();
				
				//Send r=new Send(ui.getUserAddress(),ui.getUserPort(),send);                            //������Ϣ
	           // Thread t=new Thread(r);                                                          
	            // t.start();					
			}
		}else if(m.getType().equals("consult")){
			
		}else if(m.getType().equals("outline")){			
			delete_ui(m.getUser());
			update_state1(m.getUser(),ip,0);
			
		}else{
			
		}
		/*else if(type==set){
			if(handle==add){
				int n=ServerHandler.addFriend(a,b);
				if(n>0){
					send(socket,"��ӳɹ�");
				}else{
					send(socket,"���ʧ��");
				}
			}else if(handle==delete){
				
			}else(handle==update){
				
			}
		}*/
	}
	
}
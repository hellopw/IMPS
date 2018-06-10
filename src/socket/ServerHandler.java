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
	
	
	public static  int login_check(int i, String pass) throws SQLException {       //n>0 登录成功                            可能有问题
		String sql = "select * from 用户  where account = "+i+"and password ='"+pass+"'";
		ResultSet r = BaseDao.select(sql);
		if(r.next() == true)
		return 1;
		else
		return 0;		
	}
	
	public static  User getUser(int acc,String pass){                  //返回用户信息做登录	
		User user=new User();
		String sql="select * from 用户  where account = "+
				acc+" and password = '"+pass+"'";
		ResultSet result = BaseDao.select(sql);		
		try {
			if (null != result && result.next()) {                   //还没有做完
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
	public static  User getUser_byacc(int acc){                  //返回用户	
		User user=new User();
		String sql="select * from 用户  where account = "+	acc;
		ResultSet result = BaseDao.select(sql);		
		try {
			if (null != result && result.next()) {                   //还没有做完
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
	public static  User getUser_byname(String na){                  //返回要进行对话的用户
		User user=new User();
		String sql="select * from 用户  where name = '"+na+"'";
		ResultSet result = BaseDao.select(sql);		
		try {
			if (null != result && result.next()) {                   //还没有做完
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
	/*public static int getPuKey(User u){            //返回用户公钥
		String sql="select public_key from 用户    where account = "+u.getAccount();
		ResultSet result = BaseDao.select(sql);
		int k = 0;
		try {
			k = result.getInt("PuKey");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return k;
	}*/
	public static int register_check(User u) throws SQLException{      //n>0说明用户账号纯在      可能有问题
		String sql="select * from 用户  where account= "+u.getAccount();		
		ResultSet r = BaseDao.select(sql);		
			if(r.next() == true)
			return 1;
			else
			return 0;	
	}
	public static int state_check(User u) throws SQLException{             //n>0说明在线
		String sql="select * from 好友  where friend_name= '"+u.getNickName()+"' and state="+1;
		ResultSet re= BaseDao.select(sql);		
			if(re.next() == true)
			return 1;
			else return 0;
		
	}
	public static int register(User u){                   //n>0注册成功
		String sql = "insert into 用户 (name,account,password,email,public_key,digital_book) " 
                +"VALUES ('"+u.getNickName()+"',"+u.getAccount()+",'"+u.getPassWord()+"','"+u.getEmail()+"','"+u.getPublickey()+"','"+u.getDigitalbook()+"')";
		int n=BaseDao.operate(sql);
		return n;
	}
	public static int delete(User a,User b){           //n>0删除成功
		String sql="delete from 好友  where owner_name= '"+a.getNickName()+"'and friend_name = '"+b.getPassWord()+"'";   
		int n= BaseDao.operate(sql);
		return n;
	}
	public static int addfriend(User a,User b,String group){       //n>0表示添加成功
		String sql = "insert into 好友 (owner_name,group_name,friend_name,state) " 
                +"VALUES ('"+a.getNickName()+"','"+group+"','"+b.getNickName()+"',"+1+")";
		int n=BaseDao.operate(sql);
		return n;
	}
	/*public static int update_signature(User u,String s){
		String sql="update 用户 set user_signature="+u.getSignature()+"where user_name="+u.getAccount(); 
		int n= BaseDao.operate(sql);
		return n;
	}*/
	
	public static int update_state1(User u,String ip,int state){          //更新状态
		String sql="update 好友   set state ="+state+"where friend_name='"+u.getNickName()+"'"; 
		int n= BaseDao.operate(sql);
		return n;
	}
	/*public static int update_state2(User u,String ip,int state){          //更新状态
		String sql="update 用户信息   set state ="+state+"and IP ='"+ip+"' where (account="+u.getAccount()+"or name='"+u.getNickName()+"')"; 
		int n= BaseDao.operate(sql);
		return n;
	}*/
	
	/*public static int update_onui(User u,String ip,int port,int state){
		String sql = "insert into 用户信息 ( IP,port,state,account,name) " 
                +"VALUES ('" +ip+ "',"+port+","+state+","+u.getAccount()+",'"+u.getNickName()+"')";
		int n=BaseDao.operate(sql);
		return n;
	}*/
	
	public static int delete_ui(User u){                     //更新用户信息    IP，状态
		String sql = "delete from 用户信息  where name= '"+u.getNickName()+"'";
		int n=BaseDao.operate(sql);
		return n;
	}
	public static int insert_ui(int account,String name,String ip,int port,int state){
		String sql = "insert into 用户信息(IP,port,state,account,name) " 
                +"VALUES ('" +ip+ "',"+port+","+state+","+account+",'"+name+"')";
		int n=BaseDao.operate(sql);
		return n;
	}
	
	public static ArrayList<Friends> getFriends(User u){               //登录时发送
		ArrayList<Friends> friend_list = new ArrayList<Friends>();	
		String sql="select * from 好友  where owner_name = '"+u.getNickName()+"'";
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
		String sql="select * from 用户信息  where name = '"+u.getNickName()+"'";
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
		String sql="select * from 用户信息  where account = "+u.getAccount();
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
				System.out.println("登录成功\n");				
				
			User user=ServerHandler.getUser(m.getAccount(),m.getPassword());
			ArrayList<Friends> friends=ServerHandler.getFriends(user);
			
			insert_ui(m.getAccount(),user.getNickName(),ip,port,1);           //数据库更新
			update_state1(user,ip,1);
			
			Msg msg=new Msg("login_ok",user,friends);
			String send=bianma(msg);
			try {
				out.writeUTF(send);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}	
			}else{
				Msg msg=new Msg("login_fail",0);
				String send=bianma(msg);
				System.out.println("登录失败\n");
				try {
					out.writeUTF(send);
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}else if(m.getType().equals("register")){
			int n=ServerHandler.register_check(m.getUser());
			if(n>0){
				Msg msg=new Msg("register_fail",0);
				String send=bianma(msg);
				System.out.println("注册失败\n");
				try {
					//out.writeUTF("注册失败，账户名已存在");
				     out.writeUTF(send);
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}else{
				int k=ServerHandler.register(m.getUser());
				if(k>0){				
					System.out.println("注册成功\n");
					Msg msg=new Msg("register_ok",1);
					String send=bianma(msg);
					try {
						//out.writeUTF("注册成功");
						out.writeUTF(send);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					Msg msg=new Msg("register_fail",0);
					String send=bianma(msg);
					System.out.println("注册失败\n");
					try {
						//out.writeUTF("注册失败，服务器 故障");
						out.writeUTF(send);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
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
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}	
		}else{
			Msg msg=new Msg("get_fail",0);
			String send=bianma(msg);
			try {
				out.writeUTF(send);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
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
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}	
			}else{
				Msg msg=new Msg("delete_fail",0);
				String send=bianma(msg);
				try {
					out.writeUTF(send);
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}	
			}
		}else if(m.getType().equals("add_agree")){	
			User u=getUser_byacc(m.getUsers()[1].getAccount());
			int n=addfriend(m.getUsers()[0],u,m.getCata()); 
			int n1=addfriend(u,m.getUsers()[0],m.getCata());
			UserInfo ui=getUserInfo(m.getUsers()[0]);
			if(n>0){                                                  //存在，添加				
				Msg msg=new Msg("add_ok",1);
				String send=bianma(msg);
				
				Send r=new Send(ui.getUserAddress(),ui.getUserPort(),send);                            //发送消息
 	             Thread t=new Thread(r);                                                          
 	             t.start();	
				
 	            try {
					out.writeUTF(send);
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}	
 	             
			}else{
				Msg msg=new Msg("add_fail0",0);          //故障添加失败
				String send=bianma(msg);
				
				Send r=new Send(ui.getUserAddress(),ui.getUserPort(),send);                            //发送消息
	             Thread t=new Thread(r);                                                          
	             t.start();	
	             try {
						out.writeUTF(send);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}	
			}
		}else if(m.getType().equals("add_disagree")){      
			UserInfo ui=getUserInfo(m.getUsers()[0]);
			Msg msg=new Msg("add_fail1",0);                       //被拒绝
			String send=bianma(msg);
			
			try {
				out.writeUTF(send);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}	
			/*Send r=new Send(ui.getUserAddress(),ui.getUserPort(),send);                            //发送消息
             Thread t=new Thread(r);                                                          
             t.start();*/			
		}else if(m.getType().equals("add_req_server")){
						
			int k=ServerHandler.register_check(m.getUsers()[1]);          //检测账号是否存在
			
			if(k>0){                                               //存在
				UserInfo ui=getUserInfo2(m.getUsers()[1]);
				Msg msg=new Msg("add_req_user",m.getUsers(),m.getCata());
				String send=bianma(msg);
								
				//Cs cs = new Cs(ui.getUserAddress(), ui.getUserPort(), send);
				//String mess = cs.run();
				
				 Send r=new Send(ui.getUserAddress(),ui.getUserPort(),send);                     //发送消息
	             Thread t=new Thread(r);                                                          
	             t.start();	
	             //System.out.println(ui.getUserAddress()+ui.getUserPort());
	             Msg msg1=new Msg("add_wait",0);
					String send1=bianma(msg1);
	             try {
						out.writeUTF(send1);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}	
	             
			}else{                   //不存在
				UserInfo ui=getUserInfo(m.getUsers()[0]);
				Msg msg=new Msg("add_req_fail",0);
				String send=bianma(msg);
				
				try {
					out.writeUTF(send);
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}	
				//Cs cs = new Cs(ui.getUserAddress(), ui.getUserPort(), send);
				//String mess = cs.run();
				
				//Send r=new Send(ui.getUserAddress(),ui.getUserPort(),send);                            //发送消息
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
					send(socket,"添加成功");
				}else{
					send(socket,"添加失败");
				}
			}else if(handle==delete){
				
			}else(handle==update){
				
			}
		}*/
	}
	
}
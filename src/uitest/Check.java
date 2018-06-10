package uitest;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import socket.Constants;
import socket.ServerHandler;
import socket.Client.Cs;
import socket.Client.Send;
import Encryption.Pkconsult;
import bean.Friends;
import bean.Message;
import bean.Msg;
import bean.User;
import bean.UserInfo;

public class Check {
     private static User user;
     private static ArrayList<Friends> list;
     private static UserInfo ui;
     private static Message mess;
     static int add_sign=0;
     
    /* public static Pk pk;
     public static void setPk(Pk p){
    	 pk=p;
     }
     public static Pk getPk(){
    	 return pk;
     }*/
     public static void setMessage(Message m){
    	 mess=m;
     }
     public static Message getMessage(){
    	 return mess;
     }
	public static void setFriends(ArrayList<Friends> li){          //登录成功	
		list=li;
	}
	public static void setUser(User u){
		user=u;
	}
     public static User getUser(){
    	 return user;
     }
     public static ArrayList<Friends> getFriends(){
    	 return list;
     }
     public static UserInfo getUI(){
    	 return ui;
     }
     public static void setUI(UserInfo u){
    	 ui=u;
     }
	public static int check(String s){
		int number =1;               //成功是1，不成功是0 
	 	Msg m=ServerHandler.jiema(s);
	 	if(m.getType().equals("login_ok")){ 		
	 		//User user=m.getUser();
	 		//ArrayList<Friends> list=m.getFriends();  	 		
	 		setUser(m.getUser());
            setFriends(m.getFriends());
	 	}else if(m.getType().equals("login_fail")){
	 		number=0;
	 	}
	 	if(m.getType().equals("register_ok")){
	 		number=1;
	 	}else if(m.getType().equals("register_fail")){
	 		number=0;
	 	}
	 	if(m.getType().equals("get_ok")){ 	
	 		setUI(m.getUI());
	 		setUser(m.getUser());
	 		
	 		Msg msg=new Msg("consult",0,Constants.PUK);                   //封装消息
			String send = ServerHandler.bianma(msg);             //编码
			
	 		Send r=new Send(ui.getUserAddress(),ui.getUserPort(),send);                            //发送消息
	        Thread t=new Thread(r);                                                          
	        t.start();
	 		
	 	}else if(m.getType().equals("get_fail")){
	 		number=0;
	 	}
	 	if(m.getType().equals("chat")){
	 		setMessage(m.getMessage());
	 	}
	 	if(m.getType().equals("add_req_user")){
	 		int i = JOptionPane.showConfirmDialog(null, "对方请求添加腻味好友", "add",JOptionPane.YES_NO_OPTION);
	 		if(i>0){
	 		    Msg msg=new Msg("add_agree",m.getUsers(),m.getCata());      	 //要填加一个触发器  
        	    String send=ServerHandler.bianma(msg);	
        	    
        	    add_sign=1;
        	    
        	    Send r=new Send(Constants.SERVER_IP,Constants.PORT,send);                            //发送消息
                Thread t=new Thread(r);                                                          
                t.start();		
	 		}else{
	 		    Msg msg=new Msg("add_disagree",m.getUsers(),m.getCata());   //要填加一个触发器   	 
      	      String send=ServerHandler.bianma(msg);
      	            	   
			Send r=new Send(Constants.SERVER_IP,Constants.PORT,send);                            //发送消息
            Thread t=new Thread(r);                                                          
            t.start();		
	 	   }
	 	}else if(m.getType().equals("add_ok")){
	 		number=1;
	 		add_sign=1;
	 	}else if(m.getType().equals("add_fail")){
	 		number=0;
	 	}
	 	if(m.getType().equals("consult")){
	 		String a=Pkconsult.qume2(Constants.prk,m.getPassword());
	 		Constants.setKEY(a);
	 	}
	 	return number;
	 }
}

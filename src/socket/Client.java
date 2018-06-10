package socket;

import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;  
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import Encryption.AEScrypt;
import Encryption.Hash;
import bean.Friends;
import bean.Msg;
import bean.User;
import bean.UserInfo;
import socket.ServerHandler;

public class Client {  
    /*public static  int PORT = 8872;//监听的端口号     
    public static  String IP_ADDR = "10.21.194.116";//自己的网络IP
	public static  String IP_HADDR = "10.122.227.121";//对方的网络IP地址  */
	
    /*public static void main(String[] args) {
    	  	
        System.out.println("客户端启动...\n");        
        Recv r2 = new Recv(IP_ADDR,PORT);           
         System.out.println(r2.getMess());       
        while(true){  	
          Scanner in=new Scanner(System.in);
          String s=in.next();
          if(s!=null){
          Send r = new Send(IP_HADDR,PORT,s);       
          Thread t = new Thread(r);      
          t.start(); //线程开启  
          }
        }
        
        
        
        /*Client c=new Client();
        Cs r3=c.new Cs(IP_ADDR,PORT,"jdiki");
        Thread t3=new  Thread(r3);
        t3.start();    */   
        
 // }
    
    
    
    public static class Recv implements Runnable {                   //接受消息线程  客户端
  	  private int PORT;
  	  private String IP;                 //自己IP
  	  public static  String content;
  	  public Recv(String ip,int port){         
  		  IP=ip;
  		  PORT=port;
  	  }
  	  public Recv(){
  		  
  	  }
      	public void run(){
          try {       	
          	//ServerSocket serverSocket = new ServerSocket(PORT);
          	
          	InetAddress ip = InetAddress.getByName(IP);//这里使用自己的网络IP，IP_ADDR
              ServerSocket serverSocket = new ServerSocket(PORT,100,ip);  
              
              while (true) {    
                  // 一旦有堵塞, 则表示服务器与客户端获得了连接    
                  Socket socket = serverSocket.accept();                  
                  // 处理这次连接    
                  try {   
                      // 读取客户端数据    
                      DataInputStream input = new DataInputStream(socket.getInputStream());  
                      content = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException    
                      
                      System.out.println("1"+content);
                      
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
     
              }    
          } catch (Exception e) {    
              System.out.println("服务器异常: " + e.getMessage());    
          }    
      }      	      	
      	public static String getMess(){
      		return content;
      	}
      	
  }     

    public static class Send implements Runnable{           //发送消息线程    到客户端
   	   private int PORT;//监听的端口号     	    
   		private  String IP_HADDR;//对方的网络IP地址  
   		private String text;
   		
   		/*public Send(String ip,int port){      //别人，端口
   			IP_HADDR=ip;
   			PORT=port;
   		}
   		 public void run(){
   			 while (true) {    
   		            Socket socket = null;  
   		            try { 	            	
   		                //创建一个流套接字并将其连接到指定主机上的指定端口号  
   		                socket = new Socket(IP_HADDR, PORT);//这里使用对方的网络IP，IP_HADDR
   		                //向服务器端发送数据    
   		                DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
   		                System.out.print("请输入: \t"); 
   		                String str = new BufferedReader(new InputStreamReader(System.in)).readLine(); 
   		                
   		                out.writeUTF(str);                       
   		                out.close();   
   		            } catch (Exception e) {  
   		                System.out.println("客户端异常:" + e.getMessage());   
   		            } finally {  
   		                if (socket != null) {  
   		                    try {  
   		                        socket.close();  
   		                    } catch (IOException e) {  
   		                        socket = null;   
   		                        System.out.println("客户端 finally 异常:" + e.getMessage());   
   		                    }  
   		                }  
   		            }  
   		 } 	       
   	    } */
   		 
   		
   		public Send(String ip,int port,String t){
   			IP_HADDR=ip;
   			PORT=port;
   			text=t;
   		}
   		public void run(){
  			       
  		            Socket socket = null; 		          
  		            try { 	            	
  		                //创建一个流套接字并将其连接到指定主机上的指定端口号  
  		                socket = new Socket(IP_HADDR, PORT);//这里使用对方的网络IP，IP_HADDR
  		                //向服务器端发送数据    
  		                DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
  		                
  		              byte[] encryptResult = AEScrypt.encrypt(text, Constants.KEY);    //加密
  		                String a=AEScrypt.parseByte2HexStr(encryptResult);  
  		                String b=Hash.hex_sha1(text);                           //哈希
  		                String c=a+","+b;
  		                
  		                out.writeUTF(c);
  		                out.close();   
  		            } catch (Exception e) {  
  		                System.out.println("客户端异常:" + e.getMessage());   
  		            } finally {  
  		                if (socket != null) {  
  		                    try {  
  		                        socket.close();  
  		                    } catch (IOException e) {  
  		                        socket = null;   
  		                        System.out.println("客户端 finally 异常:" + e.getMessage());   
  		                    }  
  		                }  
  		            } 
  		 	       
  	    } 
   }

 /* public  class Cs implements Runnable{           //和服务器数据交换
  	   private int PORT;            //监听的端口号     	   
  		private  String IP_HADDR;             //服务器IP地址  
  		public String text;
  		public String mess;
  		public Cs(String ip2,int port,String s){      //服务器ip，端口	
  			IP_HADDR=ip2;
  			PORT=port;
  			text=s;
  		}
  		 public void run(){ 
  		            Socket socket =  null;
  		            try {                	
  		                socket =  new Socket(IP_HADDR, PORT);     //这里使用服务器网络IP，IP_HADDR
  		                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
  		                //String a=msg_send();
  		                out.writeUTF(text); 

  		                DataInputStream input = new DataInputStream(socket.getInputStream());  
  	                   // mess = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException 
  		                setMessage(input.readUTF());
  		                System.out.println("12"+mess);
  		                
  	                    input.close();
  	                    out.close();	                
  		            } catch (Exception e) {  
  		                System.out.println("客户端异常:" + e.getMessage());   
  		            }finally {  
  		                if (socket != null) {  
  		                    try {  
  		                        socket.close();  
  		                    } catch (IOException e) {  
  		                        socket = null;   
  		                        System.out.println("客户端 finally 异常:" + e.getMessage());   
  		                    }  
  		                }  
  		            } 
  		      }
  		 
  	       public String getMessage(){                //接受的消息
  	    	   return mess;
  	       }
  	       public void setText(String s){              //要发送的消息
  	    	   text=s;
  	       }
  	       public void setMessage(String a){
  	    	   mess = a;
  	       }
  	     public  String msg_send(){
  	    	System.out.print("请输入: \t");     
  	         Scanner in = new Scanner(System.in);
  	 		System.out.println("请选择消息类型：\n");
  	 		String string=in.next();
  	 		Msg msg = null;
  	        if(string.equals("login")){           //请求登录
  	        	System.out.println("请输入账号：\n");
  	     		int a=in.nextInt();
  	     		System.out.println("请输入密码：\n");
  	     		String b=in.next();
  	     		//a,b是登录 界面得到的4个值
  	     		 msg = new Msg(string,a,b);
  	 		}else if(string.equals("register")){        //请求注册
  	 			System.out.println("请输入昵称：\n");
  	     		String a=in.next();
  	     		System.out.println("请输入账号：\n");
  	     		int b=in.nextInt();
  	 			System.out.println("请输入密码：\n");
  	     		String c=in.next();
  	     		System.out.println("请输入邮箱：\n");
  	     		String d=in.next();
  	 			//a,b,c,d是注册界面得到的4个值
  	     		User u=new User(a,b,c,d);
  	     	    msg = new Msg(string,u);
  	 		}else if(string.equals("get")){	          //请求公钥
  	 			System.out.println("请输入账号");
  	 			int a=in.nextInt();
  	 			//a是主界面双击好友得到的好友的账号
  	 			msg = new Msg(string,a);
  	 		}
  	 		String str=ServerHandler.bianma(msg);
  	 		return str;
  	    }
  }*/
    
  public static class Cs {           //和服务器数据交换
 	    public int PORT;            //监听的端口号     	   
 		public String IP_HADDR;             //服务器IP地址  
 		public String text;
 		public String mess;
 		
 		public Cs(String ip2,int port,String s){      //服务器ip，端口	
 			IP_HADDR=ip2;
 			PORT=port;
 			text=s;
 		}
 		
 		 public String  run(){ 
 		            Socket socket =  null;
 		            try {                	
 		                socket =  new Socket(IP_HADDR, PORT);     //这里使用服务器网络IP，IP_HADDR
 		                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
 		                //String a=msg_send();
 		                out.writeUTF(text); 
 		                
 		                DataInputStream input = new DataInputStream(socket.getInputStream());  
 	                    mess = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException           
 		                System.out.println("12"+mess);
       
 	                    input.close();
 	                    out.close();	                
 		            } catch (Exception e) {  
 		                System.out.println("客户端异常:" + e.getMessage());   
 		            }/*finally {  
 		                if (socket != null) {  
 		                    try {  
 		                        socket.close();  
 		                    } catch (IOException e) {  
 		                        socket = null;   
 		                        System.out.println("客户端 finally 异常:" + e.getMessage());   
 		                    }  
 		                }  
 		            } */
 		            return mess;
 		      }
         }
  
}    







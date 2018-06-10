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
    /*public static  int PORT = 8872;//�����Ķ˿ں�     
    public static  String IP_ADDR = "10.21.194.116";//�Լ�������IP
	public static  String IP_HADDR = "10.122.227.121";//�Է�������IP��ַ  */
	
    /*public static void main(String[] args) {
    	  	
        System.out.println("�ͻ�������...\n");        
        Recv r2 = new Recv(IP_ADDR,PORT);           
         System.out.println(r2.getMess());       
        while(true){  	
          Scanner in=new Scanner(System.in);
          String s=in.next();
          if(s!=null){
          Send r = new Send(IP_HADDR,PORT,s);       
          Thread t = new Thread(r);      
          t.start(); //�߳̿���  
          }
        }
        
        
        
        /*Client c=new Client();
        Cs r3=c.new Cs(IP_ADDR,PORT,"jdiki");
        Thread t3=new  Thread(r3);
        t3.start();    */   
        
 // }
    
    
    
    public static class Recv implements Runnable {                   //������Ϣ�߳�  �ͻ���
  	  private int PORT;
  	  private String IP;                 //�Լ�IP
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
          	
          	InetAddress ip = InetAddress.getByName(IP);//����ʹ���Լ�������IP��IP_ADDR
              ServerSocket serverSocket = new ServerSocket(PORT,100,ip);  
              
              while (true) {    
                  // һ���ж���, ���ʾ��������ͻ��˻��������    
                  Socket socket = serverSocket.accept();                  
                  // �����������    
                  try {   
                      // ��ȡ�ͻ�������    
                      DataInputStream input = new DataInputStream(socket.getInputStream());  
                      content = input.readUTF();//����Ҫע��Ϳͻ����������д������Ӧ,������� EOFException    
                      
                      System.out.println("1"+content);
                      
                      input.close();
                  } catch (Exception e) {    
                      System.out.println("������ run �쳣: " + e.getMessage());    
                  }finally {    
                      if (socket != null) {    
                          try {    
                              socket.close();    
                          } catch (Exception e) {    
                              socket = null;    
                              System.out.println("����� finally �쳣:" + e.getMessage());    
                          }    
                      }    
                  }
     
              }    
          } catch (Exception e) {    
              System.out.println("�������쳣: " + e.getMessage());    
          }    
      }      	      	
      	public static String getMess(){
      		return content;
      	}
      	
  }     

    public static class Send implements Runnable{           //������Ϣ�߳�    ���ͻ���
   	   private int PORT;//�����Ķ˿ں�     	    
   		private  String IP_HADDR;//�Է�������IP��ַ  
   		private String text;
   		
   		/*public Send(String ip,int port){      //���ˣ��˿�
   			IP_HADDR=ip;
   			PORT=port;
   		}
   		 public void run(){
   			 while (true) {    
   		            Socket socket = null;  
   		            try { 	            	
   		                //����һ�����׽��ֲ��������ӵ�ָ�������ϵ�ָ���˿ں�  
   		                socket = new Socket(IP_HADDR, PORT);//����ʹ�öԷ�������IP��IP_HADDR
   		                //��������˷�������    
   		                DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
   		                System.out.print("������: \t"); 
   		                String str = new BufferedReader(new InputStreamReader(System.in)).readLine(); 
   		                
   		                out.writeUTF(str);                       
   		                out.close();   
   		            } catch (Exception e) {  
   		                System.out.println("�ͻ����쳣:" + e.getMessage());   
   		            } finally {  
   		                if (socket != null) {  
   		                    try {  
   		                        socket.close();  
   		                    } catch (IOException e) {  
   		                        socket = null;   
   		                        System.out.println("�ͻ��� finally �쳣:" + e.getMessage());   
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
  		                //����һ�����׽��ֲ��������ӵ�ָ�������ϵ�ָ���˿ں�  
  		                socket = new Socket(IP_HADDR, PORT);//����ʹ�öԷ�������IP��IP_HADDR
  		                //��������˷�������    
  		                DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
  		                
  		              byte[] encryptResult = AEScrypt.encrypt(text, Constants.KEY);    //����
  		                String a=AEScrypt.parseByte2HexStr(encryptResult);  
  		                String b=Hash.hex_sha1(text);                           //��ϣ
  		                String c=a+","+b;
  		                
  		                out.writeUTF(c);
  		                out.close();   
  		            } catch (Exception e) {  
  		                System.out.println("�ͻ����쳣:" + e.getMessage());   
  		            } finally {  
  		                if (socket != null) {  
  		                    try {  
  		                        socket.close();  
  		                    } catch (IOException e) {  
  		                        socket = null;   
  		                        System.out.println("�ͻ��� finally �쳣:" + e.getMessage());   
  		                    }  
  		                }  
  		            } 
  		 	       
  	    } 
   }

 /* public  class Cs implements Runnable{           //�ͷ��������ݽ���
  	   private int PORT;            //�����Ķ˿ں�     	   
  		private  String IP_HADDR;             //������IP��ַ  
  		public String text;
  		public String mess;
  		public Cs(String ip2,int port,String s){      //������ip���˿�	
  			IP_HADDR=ip2;
  			PORT=port;
  			text=s;
  		}
  		 public void run(){ 
  		            Socket socket =  null;
  		            try {                	
  		                socket =  new Socket(IP_HADDR, PORT);     //����ʹ�÷���������IP��IP_HADDR
  		                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
  		                //String a=msg_send();
  		                out.writeUTF(text); 

  		                DataInputStream input = new DataInputStream(socket.getInputStream());  
  	                   // mess = input.readUTF();//����Ҫע��Ϳͻ����������д������Ӧ,������� EOFException 
  		                setMessage(input.readUTF());
  		                System.out.println("12"+mess);
  		                
  	                    input.close();
  	                    out.close();	                
  		            } catch (Exception e) {  
  		                System.out.println("�ͻ����쳣:" + e.getMessage());   
  		            }finally {  
  		                if (socket != null) {  
  		                    try {  
  		                        socket.close();  
  		                    } catch (IOException e) {  
  		                        socket = null;   
  		                        System.out.println("�ͻ��� finally �쳣:" + e.getMessage());   
  		                    }  
  		                }  
  		            } 
  		      }
  		 
  	       public String getMessage(){                //���ܵ���Ϣ
  	    	   return mess;
  	       }
  	       public void setText(String s){              //Ҫ���͵���Ϣ
  	    	   text=s;
  	       }
  	       public void setMessage(String a){
  	    	   mess = a;
  	       }
  	     public  String msg_send(){
  	    	System.out.print("������: \t");     
  	         Scanner in = new Scanner(System.in);
  	 		System.out.println("��ѡ����Ϣ���ͣ�\n");
  	 		String string=in.next();
  	 		Msg msg = null;
  	        if(string.equals("login")){           //�����¼
  	        	System.out.println("�������˺ţ�\n");
  	     		int a=in.nextInt();
  	     		System.out.println("���������룺\n");
  	     		String b=in.next();
  	     		//a,b�ǵ�¼ ����õ���4��ֵ
  	     		 msg = new Msg(string,a,b);
  	 		}else if(string.equals("register")){        //����ע��
  	 			System.out.println("�������ǳƣ�\n");
  	     		String a=in.next();
  	     		System.out.println("�������˺ţ�\n");
  	     		int b=in.nextInt();
  	 			System.out.println("���������룺\n");
  	     		String c=in.next();
  	     		System.out.println("���������䣺\n");
  	     		String d=in.next();
  	 			//a,b,c,d��ע�����õ���4��ֵ
  	     		User u=new User(a,b,c,d);
  	     	    msg = new Msg(string,u);
  	 		}else if(string.equals("get")){	          //����Կ
  	 			System.out.println("�������˺�");
  	 			int a=in.nextInt();
  	 			//a��������˫�����ѵõ��ĺ��ѵ��˺�
  	 			msg = new Msg(string,a);
  	 		}
  	 		String str=ServerHandler.bianma(msg);
  	 		return str;
  	    }
  }*/
    
  public static class Cs {           //�ͷ��������ݽ���
 	    public int PORT;            //�����Ķ˿ں�     	   
 		public String IP_HADDR;             //������IP��ַ  
 		public String text;
 		public String mess;
 		
 		public Cs(String ip2,int port,String s){      //������ip���˿�	
 			IP_HADDR=ip2;
 			PORT=port;
 			text=s;
 		}
 		
 		 public String  run(){ 
 		            Socket socket =  null;
 		            try {                	
 		                socket =  new Socket(IP_HADDR, PORT);     //����ʹ�÷���������IP��IP_HADDR
 		                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
 		                //String a=msg_send();
 		                out.writeUTF(text); 
 		                
 		                DataInputStream input = new DataInputStream(socket.getInputStream());  
 	                    mess = input.readUTF();//����Ҫע��Ϳͻ����������д������Ӧ,������� EOFException           
 		                System.out.println("12"+mess);
       
 	                    input.close();
 	                    out.close();	                
 		            } catch (Exception e) {  
 		                System.out.println("�ͻ����쳣:" + e.getMessage());   
 		            }/*finally {  
 		                if (socket != null) {  
 		                    try {  
 		                        socket.close();  
 		                    } catch (IOException e) {  
 		                        socket = null;   
 		                        System.out.println("�ͻ��� finally �쳣:" + e.getMessage());   
 		                    }  
 		                }  
 		            } */
 		            return mess;
 		      }
         }
  
}    







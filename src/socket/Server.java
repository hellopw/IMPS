package socket;

import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.IOException;
import java.io.InputStreamReader;  
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;  
import java.net.Socket;  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;

//import database.DataConnect;
import database.DataConnection;
import Encryption.AEScrypt;
import Encryption.Hash;
import bean.Friends;
import bean.Msg;
import bean.User;
  
public class Server {  
    public static final int PORT = Constants.PORT;//监听的端口号     
      
    public static void main(String[] args) {    
        System.out.println("服务器启动...\n");    
        Server server = new Server();    
        server.init();    
    }    
    
    public void init() {    
        try { 
        	InetAddress ip = InetAddress.getByName(Constants.SERVER_IP);
            ServerSocket serverSocket = new ServerSocket(PORT,100,ip);  
            
            while (true) {    
                // 一旦有堵塞, 则表示服务器与客户端获得了连接    
                Socket client = serverSocket.accept(); 
               
                // 处理这次连接    
                new HandlerThread(client);    
            }    
        } catch (Exception e) {    
            System.out.println("服务器异常: " + e.getMessage());    
        }    
    }    
    
    private class HandlerThread implements Runnable {    
        private Socket socket;    
        public HandlerThread(Socket client) {    
            socket = client;    
            new Thread(this).start();    
        }        
        public void run() {  
        	    	
            try {    
                  
                DataInputStream input = new DataInputStream(socket.getInputStream());  
                
                	
        		 String u = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException          
                
                
                
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());  
                
                                                              
                Msg m=ServerHandler.jiema(u);                    //服务器端的操作
                ServerHandler.handle(m,out,socket.getInetAddress().getHostAddress(),socket.getLocalPort());
                  
                out.close();    
                input.close();    
            } catch (Exception e) {    
                System.out.println("服务器 run 异常: " + e.getMessage());    
            } /*finally {    
                if (socket != null) {    
                    try {    
                        socket.close();       //断开连接
                    } catch (Exception e) {    
                        socket = null;    
                        System.out.println("服务端 finally 异常:" + e.getMessage());    
                    }    
                }    
            }  */ 
        } 
    }
  
}

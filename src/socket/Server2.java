package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import bean.Msg;
//import socket.Server.HandlerThread;

public class Server2 {
	public static final int PORT = Constants.PORT;//�����Ķ˿ں�     
    
    public static void main(String[] args) {    
        System.out.println("����������...\n");    
        Server server = new Server();    
        server.init();    
    }   
    
    public void init() {    
        try { 
        	InetAddress ip = InetAddress.getByName(Constants.SERVER_IP);
            ServerSocket serverSocket = new ServerSocket(PORT,100,ip);  
            
            while (true) {    
                // һ���ж���, ���ʾ��������ͻ��˻��������    
                Socket client = serverSocket.accept();              
                // �����������     ����һ��cs�߳�
                new S_C(client);    
            }    
        } catch (Exception e) {    
            System.out.println("�������쳣: " + e.getMessage());    
        }    
    }    
    
    private class S_C implements Runnable {    
        private Socket socket;    
        public S_C(Socket client) {    
            socket = client;    
            new Thread(this).start();    
        }        
        public void run() {        	    	
            try {    
                  
                DataInputStream input = new DataInputStream(socket.getInputStream());   	
        		String u = input.readUTF();//����Ҫע��Ϳͻ����������д������Ӧ,������� EOFException          

        		DataOutputStream out = new DataOutputStream(socket.getOutputStream());  
                                                                        
                Msg m=ServerHandler.jiema(u);                    //�������˵Ĳ���
                ServerHandler.handle(m,out,socket.getInetAddress().getHostAddress(),socket.getLocalPort());
                  
                out.close();        //���ر��൱��socket�Ѿ��ر�
                input.close();    
            } catch (Exception e) {    
                System.out.println("������ run �쳣: " + e.getMessage());    
            } finally {    
                if (socket != null) {    
                    try {    
                        socket.close();       //�Ͽ�����
                    } catch (Exception e) {    
                        socket = null;    
                        System.out.println("����� finally �쳣:" + e.getMessage());    
                    }    
                }    
            }   
        }  
    }
    
}

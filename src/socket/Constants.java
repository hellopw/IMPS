package socket;

public class Constants {
          public static String SERVER_IP = "10.8.215.92";
          public static int PORT = 10004;  
          public static int client_PORT = 19990;     //��ʱ�����ǽ��ͻ��˵Ķ˿�ֱ�Ӷ����ʼ���ˣ���ʵ����˿��ǿ��Ը��ĵ� 
          public static int prk=12345;
          public static String PRK="sjifk"; 
          public static String PUK="jdikf";
          public static String KEY="ABC";
          public static void setPUK(String s){
        	  PUK=s;
          }
          public static void setPRK(String s){
        	  PRK=s;
          }
          public static String getPRK(){
        	  return PRK;
          }
          public static String getPUK(){
        	  return PUK;
          }
          public static String getKEY(){
        	  return KEY;
          }
          public static void setKEY(String s){
        	  KEY=s;
          }
}


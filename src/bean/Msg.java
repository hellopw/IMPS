package bean;

import java.util.ArrayList;

public class Msg{                 //������ı���Ϣ
	private String type;              //��Ϣ����
	private int account;
	private String password;
	private User user;
	private String ip;
	public int port;
	public int puk;	
	private ArrayList<Friends> friends;          
	private String handle; 
	
	private Message message;                  //����Ϣ  �ͻ��˵���
	private UserInfo ui;
	
	private User[] users;                    //   ���ɾ������
	private String cata;
	
	public Msg(String t,User u,UserInfo a){
		type=t;
		user=u;
		ui=a;
	}
	public Msg(){
		
	}
	public Msg(String t,User[] us1,String c){
		type=t;
		users=us1;
		cata=c;
	}
	public Msg(String t,Message m){
		type=t;
		message=m;
	}
	public Msg(String t,int account){            //����Կ
		type=t;
		this.account=account;
	}
	public Msg(String t,String ip,int port,int puk){       //���ع�Կ��IP���˿�
		type=t;
		this.ip=ip;
		this.port=port;
		this.puk=puk;
	}
	public Msg(String t,int account,String pass){         //��¼           ���촰��Ҳ�����������
		type=t;
		this.account=account;
		password=pass;
	}
	public Msg(String t,User u){              //ע�ᣬҲ������Ӻ���
		type=t;           //type="register" or "add"
		user=u;
	}
	public Msg(String t,User u,String h){         //���û����в���
		type=t;
		user=u;
		handle=h;
	}
	public Msg(String t,User u,ArrayList<Friends> fri){      //���ؿͻ���������
		type=t;
		user=u;
		friends=fri;
	}
	/*public Msg(String t,User u,list<> message){      //���ؿͻ���������
		
	}*/
	public User[] getUsers(){
		return users;
	}
	public void setUsers(User[] us){
		 users = us;
	}
	public String getCata(){
		return cata;
	}
	public void setCata(String s){
		cata=s;
	}
	public UserInfo getUI(){
		return ui;
	}
	public void setUI(UserInfo u){
		ui=u;
	}
	public Message getMessage(){
		return message;
	}
	public String getType(){
		return type;
	}
	public void setType(String s){
		this.type = s;
	}
	public String getPassword(){
		return password;
	}
	public void setPassword(String s){
		this.password=s;
	}
	public int getAccount(){
		return account;
	}
	public void setAccount(int s){
		this.account=s;
	}
	public String getHandle(){
		return handle;
	}
	public void setHandle(String s){
		this.handle=s;
	}
	public User getUser(){
		return user;
	}
	public void setUser(User s){
		this.user=s;
	}
	public ArrayList<Friends> getFriends(){
		return friends;
	}
	public void setFriends(ArrayList<Friends> f){
		this.friends=f;
	}
	public String getIP(){
		return ip;
	}
	public void setIP(String ip){
		this.ip=ip;
	}
	public int getPort(){
		return port;
	}
	public void setPort(int port){
		this.port=port;
	}
	public int getPuk(){
		return puk;
	}
	public void setPuk(int key){
		this.puk=key;
	}
}
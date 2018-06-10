package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Message {              //传输的信息
	 
	private int senderAccount;    //发送者账号
	
	private String senderName;
	
	private String senderAddress;

	private String senderPort;

	
	private int receiverAccount;         //接收者
	
	private String receiverName;
	
	private String receiverAddress;
	
	private String receiverPort;
	
	private String content;
	private String  date;

	public Message() {
		
	}
	
    public Message(String sendname,String text,String date){       //服务器存储，客户端显示 
    	this.senderName=sendname;
    	this.content=text;
    	this.date=date;
    }

	public int getSenderAccount() {
		return senderAccount;
	}

	public void setSenderAccount(int senderId) {
		this.senderAccount = senderId;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getSenderPort() {
		return senderPort;
	}

	public void setSenderPort(String senderPort) {
		this.senderPort = senderPort;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public int getReceiverAccount() {
		return receiverAccount;
	}

	public void setReceiverAccount(int receiverId) {
		this.receiverAccount = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverPort() {
		return receiverPort;
	}

	public void setReceiverPort(String receiverPort) {
		this.receiverPort = receiverPort;
	}
    public String getContent(){
    	return content;
    }
    public void setContent(String s){
    	content=s;
    }
    public String getDate(){
    	return date;
    }
    public void setDate(String s){
    	date=s;
    }
}

package bean;


public class User {

	/** 用户账号 */
	private int Account;
	/** 用户密码*/	
	private String PassWord;
	/** 用户昵称 */
	private String Name;

	/** 用户邮箱 */
	private String email;
	/** 用户数字证书 */
	private String signature;
	/** 用户公钥 */
	private String Public_key;
	/** 用户私钥*/
	private String Private_key; 
	
	private String Digital_book;
	
	public User() {
		this.Account = 0;
		this.PassWord = "";
		this.Name = "";
		this.email = null;
	}
	
	public User(int account, String password) {
		this.Account = account;
		this.PassWord = password;
	}

	public User(String nickname,int account, String password, String e_mail) {
		this.Name = nickname;
		this.Account = account;
		this.PassWord = password;		
		this.email = e_mail;	
	}
	
    public User(int account,String pbk,String prk,String dib){
    	this.Account=account;
    	this.Digital_book=dib;
    	this.Public_key=pbk;
    	this.Private_key=prk;
    }
	
	public int getAccount() {
		return this.Account;
	}

	public void setAccount(int account) {
		this.Account = account;
	}

	public String getNickName() {
		return Name;
	}

	public void setNickName(String nickname) {
		this.Name = nickname;
	}

	public String getPassWord() {
		return this.PassWord;
	}

	public void setPassWord(String password) {
		this.PassWord = password;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String e_mail){
		this.email = e_mail; 
	}

	public String getSignature() {
		return this.signature;
	}

	public void setCFSignature(String cfsignature) {
		this.signature = cfsignature;
	}
	
	public String getPublickey(){
		return this.Public_key;
	}
	
	public void setPublickey(String publickey){
		this.Public_key = publickey;
	}
	
	public String getPrivatekey(){
		return this.Private_key;
	}
	
	public void setPrivatekey(String privatekey){
		this.Private_key = privatekey;
	}
    public String getDigitalbook(){
    	return this.Digital_book;
    }
    public void setDigitalbook(String digital){
    	this.Digital_book=digital;
    }
}

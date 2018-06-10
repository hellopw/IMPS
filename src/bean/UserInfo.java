package bean;

/**
 * Description: �û�IP��״̬��Ϣ <br/>
 * Date: 2014��11��23�� ����1:41:19 <br/>
 * 
 * @author SongFei
 * @version
 * @since JDK 1.7
 * @see
 */
public class UserInfo {

	/** �û����� */
    User user;
	/** �û�IP */
	private String userAddress;
	/** �û��˿� */
	private int userPort;
	/** չʾ״̬ */
	private String userState;

    public UserInfo(){
    	this.user = new User();
        this.userAddress = "";
        this.userPort = 0;
        this.userState = "";
    }
	/*public UserInfo(User u, String ip,String port,String status) {
      this.user = u;
      this.userAddress = ip;
      this.userPort = port;
      this.userStatus = status;
	}*/
	
	public UserInfo(User user, String useraddress, int userport) {
		this.user = user;
		this.userAddress = useraddress;
		this.userPort = userport;
	}
	
	public UserInfo(User user, String useraddress, int userport, String userstate) {
		this.user = user;
		this.userAddress = useraddress;
		this.userPort = userport;
		this.userState = userstate;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String getUserAddress() {
		return this.userAddress;
	}

	public void setUserAddress(String useraddress) {
		this.userAddress = useraddress;
	}

	public int getUserPort() {
		return this.userPort;
	}

	public void setUserPort(int userport) {
		this.userPort = userport;
	}

	public String getUserState() {
		return this.userState;
	}

	public void setUserState(String userstate) {
		this.userState = userstate;
	}

}

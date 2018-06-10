package bean;

/**
 * Description: 用户IP、状态信息 <br/>
 * Date: 2014年11月23日 下午1:41:19 <br/>
 * 
 * @author SongFei
 * @version
 * @since JDK 1.7
 * @see
 */
public class UserInfo {

	/** 用户对象 */
    User user;
	/** 用户IP */
	private String userAddress;
	/** 用户端口 */
	private int userPort;
	/** 展示状态 */
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

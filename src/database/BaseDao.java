package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.Friends;

public class BaseDao {

	private static Connection connect;
	private static Statement statement;
	private static ResultSet resultSet;
	
	/*public static void main(String args[]){
		String sql="select * from 好友  where owner_name = '小张'";
		ResultSet result = BaseDao.select(sql);		
		try {
			while (null != result && result.next()) {                   
				String owner_name = result.getString("owner_name");
				String group_name = result.getString("group_name");
				String friend_name = result.getString("friend_name");
				String state = result.getString("state");
				Friends f= new Friends(owner_name,group_name,friend_name,state);
				//friend_list.add(f);	
				System.out.println(owner_name+" "+group_name+friend_name+"\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}*/

	/** 查询 */
	public static ResultSet select(String sql) {
		try {
			System.out.println(sql);
			connect = DataConnect.getConnect();
			statement = connect.createStatement();
			resultSet = statement.executeQuery(sql);
			return resultSet;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnect(connect, statement, resultSet);
		}
		
		return null;
	}

	/** 操作（新增、修改、删除） */
	public static int operate(String sql) {
		int number = 0;
		try {
			System.out.println(sql);
			connect = DataConnect.getConnect();
			statement = connect.createStatement();
			number = statement.executeUpdate(sql);
			// 设置事务为手动，方便回滚
			connect.setAutoCommit(false);
			connect.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connect.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			closeConnect(connect, statement, resultSet);
		}
		return number;
	}

	/** 关闭连接 */
	public static void closeConnect(Connection connect, Statement statement,
			ResultSet resultSet) {
		try {
//			if (null != resultSet) {
//				resultSet.close();
//			}
//			if (null != statement) {
//				statement.close();
//			}
//			if (null != connect) {
//				connect.close();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
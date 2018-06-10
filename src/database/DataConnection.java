package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class DataConnection {
	/*public static Connection getConnection() throws ClassNotFoundException, SQLException  {
		Connection conn = null;
		
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433;databaseName=test";              
			String user = "Peter wang";
			String pass = "wb123456";
			conn = DriverManager.getConnection(url, user, pass);
		
			return conn;
}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException  {
		
		Map<String, String> k = new HashMap<String, String>();
		
		Connection conn = getConnection();
		String sql = "select * from fqq_user";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData data = rs.getMetaData();
		System.out.println("id"+"\t"+"nick_name"+"\t"+"user_name"+"\t"+"user_password"+"\t"+"user_signature"+"\n");
		while(rs.next()){
			String a=rs.getString("id");
			String b=rs.getString("nick_name");
			String c=rs.getString("user_name");
			String d=rs.getString("user_password");
			String e=rs.getString("user_signature");
			System.out.println(a+"\t"+b+"\t"+c+"\t"+d+"\t"+e+"\n");
			
			k.put(c,d);
			
		}
		if(k.containsKey("123")){
			 if(k.get("123").equals("123")){
				 System.out.println("登录成功");
			 }
		}	
	}*/
	public static void main(String[] args) throws SQLException, ClassNotFoundException  {
		String sql="select * from fqq_user";                //选择查询
		String sql1 = "INSERT INTO fqq_user (id,nick_name,user_name,user_password,user_signature) " 
                +"VALUES (123456,'蜡笔小新',1,'神奇宝贝','铁甲小宝')";                      //插入
		String sql2="update fqq_user set user_signature='海贼王' where user_name='987'";          //更新
		String sql3="delete from fqq_user where user_name='456'";            //删除
		String sql4="";
		int n=BaseDao.operate(sql);
		int n1=BaseDao.operate(sql1);
		int n2=BaseDao.operate(sql2);
		int n3=BaseDao.operate(sql3);
		System.out.println(n2);
	}
	
}
		
 class BaseDao1 {

	private static Connection connect;
	private static Statement statement;
	private static ResultSet resultSet;

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
				// TODO Auto-generated catch block
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

 class DataConnect1 {
		private static Connection conn;
		private DataConnect1() {
		}
		public static Connection getConnect() {
			try {
				if (null == conn) {
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String url = "jdbc:sqlserver://localhost:1433;databaseName=test";              
					String user = "peter wang";
					String pass = "wb123456";
					conn = DriverManager.getConnection(url, user, pass);				
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return conn;
		}	
		
}
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataConnect {
	private static Connection conn;
	private DataConnect() {
	}
	public static Connection getConnect() {
		try {
			if (null == conn) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://localhost:1433;databaseName=Chat_Test";              
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
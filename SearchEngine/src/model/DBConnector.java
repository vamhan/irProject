package model;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class DBConnector {
	
	static String host = "192.168.56.101";
	static String port = "1433";
	static String username = "sa";
	static String password = "1234";
	static String database = "userProfile";
	
	static Connection conn;

	public static void connect() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://" + host + ":" + port + ";user=" + username + ";password=" + password + ";database=" + database);
			System.out.println("test");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet query(String sql) {
		ResultSet rs;
		try {
			Statement sta = conn.createStatement();
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			rs = null;
			e.printStackTrace();
		}
		return rs;
	}
	
	public static int update(String sql) {
		int rs;
		try {
			Statement sta = conn.createStatement();
			rs = sta.executeUpdate(sql);
			System.out.print(sql);
		} catch (SQLException e) {
			rs = 0;
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

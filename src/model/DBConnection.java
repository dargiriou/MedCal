package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class DBConnection {

	static String filename = "MedCalDB.db";
	public static String fileurl = "jdbc:sqlite:" + filename;
	
	public static Connection getConnection()
	{
		
		try {
			Class.forName("org.sqlite.JDBC");
			SQLiteConfig config = new SQLiteConfig();  
		    config.enforceForeignKeys(true);
			return DriverManager.getConnection(fileurl);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("fuckmesideways 23 dbconnection"+e);
		}
		
		return null;
	}
}
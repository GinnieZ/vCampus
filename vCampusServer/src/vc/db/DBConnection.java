package vc.db;

import java.beans.Statement;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	private static Connection con = null;
	private static String DBurl = "jdbc:Access:///db/vCampus.mdb";

	public static Connection getConnection() {
		if (con == null) {
			try {
//        Class.forName("com.hxtt.sql.access.AccessDriver");
//        System.out.println("Driver loaded");
//        
//        con = DriverManager.getConnection(DBurl);
//        System.out.println("Database connected");
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				String url = "jdbc:ucanaccess://db/vCampus.mdb";
				con = DriverManager.getConnection(url, "", "");
//        Class.forName("com.hxtt.sql.access.AccessDriver");
//        con = DriverManager.getConnection(DBurl);
				System.out.println("Database connected");

			} catch (ClassNotFoundException e) {
				System.out.println("Fail to load driver");
			} catch (SQLException e) {
				System.out.println("Fail to connect database");
			}
		}
		return con;
	}
}

package com.chinasofti.utils;


import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {


	static Connection connection;

	static{

		 String	driver ="com.mysql.jdbc.Driver";
		 String	url = "jdbc:mysql://localhost:3306/message_cloud?useUnicode=true&characterEncoding=utf8";
		 String	username = "root";
		 String	password = "";

		try {
			Class.forName(driver);
			connection	 = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	public static Connection getConnection(){
		return connection;
	}

}
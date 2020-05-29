package com.batch.jdbcContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class SqlConnection {
	
	private static final Logger logger = Logger.getGlobal();
	
	private SqlConnection() {}
	
	private static class LazyHolder {
		public static final SqlConnection INSTANCE = new SqlConnection();
	}
	
	public static SqlConnection getInstance() {
		return LazyHolder.INSTANCE;
	}
	
	private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String URL = "jdbc:h2:tcp://localhost/~/test";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "";
	
	private Connection conn;
	
	public Connection getConnection() {
		try {
			if(this.conn != null && !this.conn.isClosed()) {
				logger.info("Obtained JDBC Connection[Transaction : " + this.conn.hashCode() + "]");
				return conn;
			}
			
			Class.forName(JDBC_DRIVER);
			this.conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			logger.info("Connecting Success[" + this.conn.hashCode() + "]");
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return this.conn;
	}
	
	public void closeConnection() {
		if(this.conn != null) {
			try {
				if(this.conn.getAutoCommit()) {
					this.conn.close();
					logger.info("Connection Close");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
}

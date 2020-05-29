package com.batch.jdbcContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class SqlTransactionManager {
	
	public static final Logger logger = Logger.getGlobal();
	
	public static final String COMMIT = "COMMIT"; 
	public static final String ROLLBACK = "ROLLBACK"; 
	
	private SqlConnection sqlConnection;
	
	private Connection connection;
	
	public SqlTransactionManager(SqlConnection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}

	public void begin() {
		logger.info("Trancation Begin");
		this.connection = this.sqlConnection.getConnection();
		try {
			this.connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void end(String arg) {
		logger.info("Trancation End : " + arg);
		try {
			if(COMMIT.equals(arg)) {
				this.connection.commit();
			} else if(ROLLBACK.equals(arg)) {
				this.connection.rollback();
			}
			this.connection.close();
			logger.info("Trancation Connection Close");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}

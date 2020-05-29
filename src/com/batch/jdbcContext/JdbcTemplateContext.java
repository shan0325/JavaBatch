package com.batch.jdbcContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplateContext implements JdbcContext {

	private SqlConnection sqlConnection;
	
	public JdbcTemplateContext(SqlConnection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}

	@Override
	public <T> List<T> selectList(StatementStrategy stmtStrategy, RowMapper<T> rowMapper) {
		Connection conn = sqlConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<T> list = new ArrayList<T>();
		try {
			pstmt = stmtStrategy.makePreparedStatement(conn);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rowMapper.mapRow(rs));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			sqlConnection.closeConnection();
			closePreparedStatement(pstmt);
			closeResultSet(rs);
		}
	}
	
	@Override
	public <T> T selectOne(StatementStrategy stmtStrategy, RowMapper<T> rowMapper) {
		Connection conn = sqlConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = stmtStrategy.makePreparedStatement(conn);
			rs = pstmt.executeQuery();
			rs.next();
			return rowMapper.mapRow(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			sqlConnection.closeConnection();
			closePreparedStatement(pstmt);
			closeResultSet(rs);
		}
	}
	
	@Override
	public int execSql(StatementStrategy stmtStrategy) {
		Connection conn = sqlConnection.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = stmtStrategy.makePreparedStatement(conn);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			sqlConnection.closeConnection();
			closePreparedStatement(pstmt);
		}
	}

	public void closePreparedStatement(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}

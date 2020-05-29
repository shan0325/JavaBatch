package com.batch.jdbcContext;

import java.util.List;

public interface JdbcContext {
	
	public <T> List<T> selectList(StatementStrategy stmtStrategy, RowMapper<T> rowMapper);
	
	public <T> T selectOne(StatementStrategy stmtStrategy, RowMapper<T> rowMapper);
	
	public int execSql(StatementStrategy stmtStrategy);

}

package com.batch.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.batch.domain.Member;
import com.batch.jdbcContext.JdbcContext;
import com.batch.jdbcContext.RowMapper;
import com.batch.jdbcContext.StatementStrategy;

public class MemberDAO {
	
	private JdbcContext jdbcContext;
	
	public MemberDAO(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}
	
	private RowMapper<Member> memberMapper = new RowMapper<Member>() {
		@Override
		public Member mapRow(ResultSet rs) throws SQLException {
			String id = rs.getString("ID");
			String password = rs.getString("PASSWORD");
			String name = rs.getString("NAME");
			String email = rs.getString("EMAIL");
			Date regDate = rs.getDate("REG_DATE");
			
			Member member = new Member();
			member.setId(id);
			member.setPassword(password);
			member.setName(name);
			member.setEmail(email);
			member.setRegDate(regDate);
			return member;
		}
	};
	
	public List<Member> findMembers() {
		return jdbcContext.selectList(new StatementStrategy() {
			@Override
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement pstmt = c.prepareStatement("SELECT * FROM MEMBER");
				return pstmt;
			}
		}, this.memberMapper);
	}

	public Member findMember(String pId) {
		return jdbcContext.selectOne(conn -> {
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE ID = ?");
			pstmt.setString(1, pId);
			return pstmt;
		}, this.memberMapper);
	}

	public int addMember(Member member) {
		return jdbcContext.execSql(conn -> {
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO MEMBER(ID, PASSWORD, NAME, REG_DATE, EMAIL, TEL, MTEL) VALUES(?, ?, ?, SYSDATE, ?, ?, ?)");
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getTel());
			pstmt.setString(6, member.getMtel());
			return pstmt;
		});
	}

	public int modMember(Member member) {
		return jdbcContext.execSql(conn -> {
			PreparedStatement pstmt = conn.prepareStatement("UPDATE MEMBER SET PASSWORD = ?, NAME = ?, EMAIL = ?, TEL = ?, MTEL = ? WHERE ID = ?");
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getTel());
			pstmt.setString(5, member.getMtel());
			pstmt.setString(6, member.getId());
			return pstmt;
		});
	}
}

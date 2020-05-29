package com.batch.service;

import java.util.List;

import com.batch.domain.Member;
import com.batch.jdbcContext.SqlTransactionManager;
import com.batch.repository.MemberDAO;

public class MemberService {
	
	private SqlTransactionManager sqlTransactionManager;
	
	private MemberDAO memberDAO;
	
	public MemberService(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setSqlSessionManager(SqlTransactionManager sqlSessionManager) {
		this.sqlTransactionManager = sqlSessionManager;
	}
	
	public List<Member> findMembers() {
		return memberDAO.findMembers();
	}

	public Member findMember() {
		return memberDAO.findMember("test");
	}
	
	public void addMember(Member member) {
		memberDAO.addMember(member);
	}
	
	public void modMember(Member member) {
		// 트랜잭션 시작
		sqlTransactionManager.begin();
		try {
			int count = memberDAO.modMember(member);
			System.out.println(count);
			
			// throw new Exception("트랜잭션 롤백 테스트 익셉션");
			
			// 트랜잭션 유지 되는지 확인
			findMembers();
			findMember();
			
			// 트랜잭션 종료
			sqlTransactionManager.end(SqlTransactionManager.COMMIT);
		} catch(Exception e) {
			e.printStackTrace();
			// 트랜잭션 종료
			sqlTransactionManager.end(SqlTransactionManager.ROLLBACK);
		}
	}

}

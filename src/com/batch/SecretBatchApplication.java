package com.batch;

import java.util.List;

import com.batch.domain.Member;
import com.batch.jdbcContext.JdbcContext;
import com.batch.jdbcContext.JdbcTemplateContext;
import com.batch.jdbcContext.SqlConnection;
import com.batch.jdbcContext.SqlTransactionManager;
import com.batch.repository.MemberDAO;
import com.batch.service.MemberService;

public class SecretBatchApplication {

	public static void main(String[] args) {
		SqlConnection sqlConnection = SqlConnection.getInstance(); // DB Ŀ�ؼ�
		JdbcContext jdbcContext = new JdbcTemplateContext(sqlConnection); // JDBC ���ø�
		SqlTransactionManager sqlTransactionManager = new SqlTransactionManager(sqlConnection); // DB Ʈ�����
		
		MemberDAO memberDAO = new MemberDAO(jdbcContext);
		MemberService memberService = new MemberService(memberDAO);
		memberService.setSqlSessionManager(sqlTransactionManager);
		
		// ��� ���
//		Member addMember = new Member();
//		addMember.setId("test9");
//		addMember.setPassword("1111");
//		addMember.setName("�׽�Ʈ6");
//		addMember.setEmail("test6@naver.com");
//		addMember.setMtel("010-1111-2222");
//		memberService.addMember(addMember);
		
		// ��� ����
		Member modMember = new Member();
		modMember.setId("test5");
		modMember.setPassword("1111");
		modMember.setName("�׽�Ʈ5");
		modMember.setEmail("test5@naver.com");
		modMember.setMtel("010-1111-2222");
		memberService.modMember(modMember);
		
		// ��� ����Ʈ
		List<Member> members = memberService.findMembers();
		members.forEach(System.out::println);
		
		// ��� �Ѱ�
		Member member = memberService.findMember();
		System.out.println(member);
		
	}
}

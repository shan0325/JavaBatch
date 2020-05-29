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
		SqlConnection sqlConnection = SqlConnection.getInstance(); // DB Ä¿³Ø¼Ç
		JdbcContext jdbcContext = new JdbcTemplateContext(sqlConnection); // JDBC ÅÛÇÃ¸´
		SqlTransactionManager sqlTransactionManager = new SqlTransactionManager(sqlConnection); // DB Æ®·£Àè¼Ç
		
		MemberDAO memberDAO = new MemberDAO(jdbcContext);
		MemberService memberService = new MemberService(memberDAO);
		memberService.setSqlSessionManager(sqlTransactionManager);
		
		// ¸â¹ö µî·Ï
//		Member addMember = new Member();
//		addMember.setId("test9");
//		addMember.setPassword("1111");
//		addMember.setName("Å×½ºÆ®6");
//		addMember.setEmail("test6@naver.com");
//		addMember.setMtel("010-1111-2222");
//		memberService.addMember(addMember);
		
		// ¸â¹ö ¼öÁ¤
		Member modMember = new Member();
		modMember.setId("test5");
		modMember.setPassword("1111");
		modMember.setName("Å×½ºÆ®5");
		modMember.setEmail("test5@naver.com");
		modMember.setMtel("010-1111-2222");
		memberService.modMember(modMember);
		
		// ¸â¹ö ¸®½ºÆ®
		List<Member> members = memberService.findMembers();
		members.forEach(System.out::println);
		
		// ¸â¹ö ÇÑ°Ç
		Member member = memberService.findMember();
		System.out.println(member);
		
	}
}

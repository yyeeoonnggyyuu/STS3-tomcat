package com.company.hello.member;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class MemberDao {
	private Map<String, MemberVo> memberDB = new HashMap<String, MemberVo>(); //임시DB

	public void insertMember(MemberVo memberVo) {
		System.out.println("[MemerDao] insertMember()");
		
		System.out.println("m_id: " + memberVo.getM_id());
		System.out.println("m_pw: " + memberVo.getM_pw());
		System.out.println("m_mail: " + memberVo.getM_mail());
		System.out.println("m_phone: " + memberVo.getM_id());

		memberDB.put(memberVo.getM_id(), memberVo);
		printAllMember();
	}
	
	private void printAllMember() {
		System.out.println("[MemberDao] printAllMember()");

		Set<String> keys = memberDB.keySet();
		Iterator<String> iterator = keys.iterator();
		
		while(iterator.hasNext()) {
			String key = iterator.next();
			MemberVo memberVo = memberDB.get(key);
			
			System.out.println("m_id: " + memberVo.getM_id());
			System.out.println("m_pw: " + memberVo.getM_pw());
			System.out.println("m_mail: " + memberVo.getM_mail());
			System.out.println("m_phone: " + memberVo.getM_id());

		}
		
	}
	

}


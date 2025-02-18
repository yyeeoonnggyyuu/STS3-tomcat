package com.office.library.admin.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMemberService {
	
	final static public int ADMIN_ACCOUNT_ALREADY_EXIST = 0;
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;
	
	@Autowired
	AdminMemberDao adminMemberDao;

	public int createAccountConfirm(AdminMemberVo adminMemberVo) { 
		System.out.println("[AdminMemberService] createAccountConfirm()" );
		
		boolean isMember = adminMemberDao.isAdminMember(adminMemberVo.getA_m_id());
		
		if(!isMember) {
			int result = adminMemberDao.insertAdminAccount(adminMemberVo);
			
			if(result >0)
				return ADMIN_ACCOUNT_CREATE_SUCCESS; /*AdminMemberDao가 반환해준 result =1 을 받으면 이걸 controller에 넘겨줌*/
			else
				return ADMIN_ACCOUNT_CREATE_FAIL;
		} else {
			return ADMIN_ACCOUNT_ALREADY_EXIST;
		}
	}
	
	public AdminMemberVo loginConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberService] loginConfirm()" );
		
		AdminMemberVo loginedAdminMemberVo = adminMemberDao.selectAdmin(adminMemberVo);
		
		if(loginedAdminMemberVo != null)
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN SUCCESS!!");
		else
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN FAIL!!");
		
		return loginedAdminMemberVo;
	}

	//관리자 목록 리스트업
	public List<AdminMemberVo> listupAdmin() {
		System.out.println("[AdminMemberService] listupAdmin()" );
		
		return adminMemberDao.selectAdmins();
	}

	//관리자목록 일반관리자 승인
	public void setAdminApproval(int a_m_no) {
		System.out.println("[AdminMemberService] setAdminApproval()" );
		
		int result = adminMemberDao.updateAdminAccount(a_m_no);
		
	}
}

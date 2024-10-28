package com.office.library.user.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMemberService {
	
	final static public int USER_ACCOUNT_ALREADY_EXIST = 0;
	final static public int USER_ACCOUNT_CREATE_SUCCESS= 1;
	final static public int USER_ACCOUNT_CREATE_FAIL = -1;

	@Autowired
	UserMemberDao userMemberDao;
	
	public int createAccountConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberService] createAccountConfirm()" );
		
		
		boolean isMember = userMemberDao.isUserMember(userMemberVo.getU_m_id());

		if(!isMember) {
			int result = userMemberDao.insertUserAccount(userMemberVo);
			
			if(result > 0)
				return USER_ACCOUNT_CREATE_SUCCESS;
			else
				return USER_ACCOUNT_CREATE_FAIL;
		}
		return USER_ACCOUNT_ALREADY_EXIST;
	}

	public UserMemberVo loginConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberService] loginConfirm()" );
		
		UserMemberVo logenedUserMemberVo = userMemberDao.selectUser(userMemberVo);
		
		if(logenedUserMemberVo != null) {
			System.out.println("[UserMemberService] USER MEMBER LOGIN SUCCESS!!");
		} else {
			System.out.println("[UserMemberService] USER MEMBER LOGIN FAIL!!");
		}
		return logenedUserMemberVo;
	}
	
	//회원 정보 수정 확인
	public int modifyAccountConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberService] modifyAccountConfirm()" );
		
		return userMemberDao.updateUserAccount(userMemberVo);
	}

	public UserMemberVo getLoginedUserMemberVo(int u_m_no) {
		System.out.println("[UserMemberService] getLoginedUserMemberVo()" );
		
		return userMemberDao.selectUser(u_m_no);
	}

}

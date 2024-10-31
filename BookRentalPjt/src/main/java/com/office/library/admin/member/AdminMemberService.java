package com.office.library.admin.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.office.library.book.RentalBookVo;

@Service
public class AdminMemberService {

	// 회원가입 결과를 나타내는 상수 정의
	final static public int ADMIN_ACCOUNT_ALREADY_EXIST = 0;
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;

	// Dao 자동객체생성
	@Autowired
	AdminMemberDao adminMemberDao;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;

	// 회원가입 컨펌
	public int createAccountConfirm(AdminMemberVo adminMemberVo) {

		System.out.println("[AdminMemberService] createAccountConfirm()");
		// 중복아이디 확인
		boolean isMember = adminMemberDao.isAdminMember(adminMemberVo.getA_m_id());
		// 회원 데이터베이스 인설트 및 실패 중복 환인
		if (!isMember) {
			int result = adminMemberDao.insertAdminAccount(adminMemberVo);

			if (result > 0)
				return ADMIN_ACCOUNT_CREATE_SUCCESS;
			else
				return ADMIN_ACCOUNT_CREATE_FAIL;
		} else {
			return ADMIN_ACCOUNT_ALREADY_EXIST;
		}
	}

	// 로그인 확인
	public AdminMemberVo loginConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberService] loginConfirm()");

		AdminMemberVo loginedAdminMemberVo = adminMemberDao.selectAdmin(adminMemberVo);

		if (loginedAdminMemberVo != null)
			System.out.println("[AdminMemberService] ADMIN_ACCOUNT_CREATE_SUCCESS!!");
		else
			System.out.println("[AdminMemberService] ADMIN_ACCOUNT_CREATE_FAIL");

		return loginedAdminMemberVo;
	}

	// 관리자 승인 기능
	public List<AdminMemberVo> listupAdmin() {
		System.out.println("[AdminMemberService] listupAdmin()");

		return adminMemberDao.selectAdmins();
	}

	// 일반관리자 승인
	public void setAdminApproval(int a_m_no) {
		System.out.println("[AdminMemberService] setAdminApproval()");

		int result = adminMemberDao.updateAdminAccount(a_m_no);

	}

//	계정수정 컨펌
	public int modifyAccountConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberService] modifyAccountConfirm()");

		return adminMemberDao.updateAdminAccount(adminMemberVo);
	}

	public AdminMemberVo getLoginedAdminMemberVo(int a_m_no) {
		System.out.println("[AdminMemberService] getLoginedAdminMemberVo()");

		return adminMemberDao.selectAdmin(a_m_no);
	}

	// 비밀번호 재설정 메일 컨펌
	public int findPasswordConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberService] findPasswordConfirm()");

		AdminMemberVo selectedAdminMemberVo = adminMemberDao.selectAdmin(adminMemberVo.getA_m_id(),
				adminMemberVo.getA_m_name(), adminMemberVo.getA_m_mail());

		int result = 0;

		if (selectedAdminMemberVo != null) {
			String newPassword = createNewPassword();
			result = adminMemberDao.updatePassword(adminMemberVo.getA_m_id(), newPassword);
			
			if(result > 0)
				sendNewPasswordByMail(adminMemberVo.getA_m_mail(), newPassword);
		}

		return result;
	}
	
	//비밀버호 재설정
	private String createNewPassword() {
		System.out.println("[AdminMemberService] createNewPassword()");

		char[] chars = new char[] { 
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
				'u', 'v', 'w', 'x', 'y', 'z' };

		StringBuffer stringBuffer = new StringBuffer();
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(new Date().getTime());

		int index = 0;
		int length = chars.length;
		for (int i = 0; i < 8; i++) {
			index = secureRandom.nextInt(length);

			if (index % 2 == 0)
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			else
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());

		}
		System.out.println("[AdminMemberService] NEW PASSWORD: " + stringBuffer.toString());

		return stringBuffer.toString();
	}
	
	//새 비밀번호 메일 전송 service
		private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
		    System.out.println("[AdminMemberService] sendNewPasswordByMail()");
		    final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {

		        @Override
		        public void prepare(MimeMessage mimeMessage) throws Exception {
		            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		            mimeMessageHelper.setTo(toMailAddr);
		            mimeMessageHelper.setSubject("[한국도서관] 새 비밀번호 안내입니다.");
		            mimeMessageHelper.setText("새비밀번호 : " + newPassword, true);
		        }
		    };

		    javaMailSenderImpl.send(mimeMessagePreparator);
		}
		


}

package com.office.library.user.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/member")
public class UserMemberController {
	
	@Autowired
	UserMemberService userMemberService;

//	회원가입 폼 
	@GetMapping("/createAccountForm")
	public String createAccountForm() { /*value와 여기 매개변수값을 맞춰주는게 좋음*/
		System.out.println("[UserMemberController] createAccountForm()" );
		
		String nextPage = "user/member/create_account_form";
		
		return nextPage;
	}
	
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberController] createAccountConfirm()" );
		
		String nextPage ="/user/member/create_account_ok";
		
		int result = userMemberService.createAccountConfirm(userMemberVo);
		
		if (result <= 0)
			nextPage ="/user/member/create_account_ng";
		
		return nextPage;
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[UserMemberController] loginForm()" );
		
		String nextPage = "user/member/login_form";
		
		return nextPage;
	}
	
	@PostMapping("/loginConfirm")
	public String loginConfirm(UserMemberVo userMemberVo, HttpSession session) {
		System.out.println("[UserMemberController] loginConfirm()" );
		
		String nextPage = "user/member/login_ok";
		
		UserMemberVo loginedUserMemberVo = userMemberService.loginConfirm(userMemberVo);
		
		if(loginedUserMemberVo == null) {
			nextPage ="user/member/login_ng";
		} else {
			session.setAttribute("loginedUserMemberVo", loginedUserMemberVo);
			session.setMaxInactiveInterval(60 * 30);
		}				
		return nextPage;
	}
	
	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {
		System.out.println("[UserMemberController] modifyAccountForm()" );
		
		String nextPage ="user/member/modify_account_form";
		
		UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
		
		if(loginedUserMemberVo == null)
			nextPage = "redirect:/user/member/loginForm";
		
		return nextPage;
	}
	
	//회원 정보 수정 확인
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfirm(UserMemberVo userMemberVo, HttpSession session) {
		System.out.println("[UserMemberController] modifyAccountConfirm()" );
		

		String nextPage="user/member/modify_account_ok";
		
		int result = userMemberService.modifyAccountConfirm(userMemberVo);
		
		if(result > 0) {
			UserMemberVo loginedUserMemberVo = userMemberService.getLoginedUserMemberVo(userMemberVo.getU_m_no());
			
			session.setAttribute("loginedUserMemberVo", loginedUserMemberVo);
			session.setMaxInactiveInterval(60 * 30);
		} else {
			nextPage ="user/member/modify_account_ng";
		}
		
		return nextPage;
	}
	
	@GetMapping("logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		System.out.println("[UserMemberController] logoutConfirm()" );
		
		String nextPage ="redirect:/";
		session.invalidate();
				
		return nextPage;
	}
	
}

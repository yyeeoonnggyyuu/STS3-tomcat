package com.office.library.admin.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {

	@Autowired
	AdminMemberService adminMemberService;
	
	//회원가입 첫화면
	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		System.out.println("[AdminMemberController] createAccountForm()");
		
		String nextPage = "admin/member/create_account_form";
		
		return nextPage;
	}
	
	//회원가입 확인
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberController] createAccountConfirm()");
		
		String nextPage = "admin/member/create_account_ok";
		
		int result = adminMemberService.createAccountConfirm(adminMemberVo);
		
		if(result <= 0)
			nextPage = "admin/member/create_account_ng";
		
		return nextPage;
	}
	
	//로그인 화면 연결
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[AdminMemberController] loginForm()");
		
		String nextPage ="/admin/member/login_form";
		
		return nextPage;
	}
	
	//로그인 확인
	@PostMapping("/loginConfirm")
	public String loginConfirm(AdminMemberVo adminMemberVo, HttpSession session) {
		System.out.println("[AdminMemberController] loginConfirm()");
		
		String nextPage = "admin/member/login_ok";
		
		AdminMemberVo loginedAdminMemberVo = adminMemberService.loginConfirm(adminMemberVo);
		
		if(loginedAdminMemberVo == null) {
			nextPage ="admin/member/login_ng";
		} else {
			session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo);
			session.setMaxInactiveInterval(60 * 30);
		}
			
		
		return nextPage;
	}
	
	//관리자 로그아웃 컨펌
	@GetMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		System.out.println("[AdminMemberController] logoutConfirm()");
		
		String nextPage ="redirect:/admin";
		session.invalidate();
		return nextPage;
	}
	
	
	//관리자 승인 기능
	@GetMapping("/listupAdmin")
	public String listupAdmin(Model model) {
		System.out.println("[AdminMemberController] listupAdmin()");
		
		String nextPage ="admin/member/listup_admins";
		
		List<AdminMemberVo> adminMemberVos = adminMemberService.listupAdmin(); 
		
		model.addAttribute("adminMemberVos", adminMemberVos);
		
		return nextPage;
	}
	
	//일반관리자 승인
	@GetMapping("/setAdminApproval")
	public String setAdminApproval(@RequestParam("a_m_no") int a_m_no) {
		System.out.println("[AdminMemberController] setAdminApproval()");
		
		 String nextPage = "redirect:/admin/member/listupAdmin";
		 
		 adminMemberService.setAdminApproval(a_m_no);
		
		return nextPage;
	}
	
	//계정수정 폼 연결
		@GetMapping("/modifyAccountForm")
		public String modifyAccountForm(HttpSession session) {
			System.out.println("[AdminMemberController] modifyAccountForm()");
			
			String nextPage = "admin/member/modify_account_form";
			
			AdminMemberVo loginedAdminMemberVo = (AdminMemberVo) session.getAttribute("loginedAdminMemberVo");
			
			if(loginedAdminMemberVo == null	)
				nextPage = "admin/member/login_form";
			
			return nextPage;
		}
		
	
//	계정수정 컨펌
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfirm(AdminMemberVo adminMemberVo, HttpSession session) {
		System.out.println("[AdminMemberController] modifyAccountConfirm()");
		
		String nextPage = "admin/member/modify_account_ok";
		
		int result = adminMemberService.modifyAccountConfirm(adminMemberVo);
		
		if(result > 0) {
			AdminMemberVo loginedAdminMemberVo = adminMemberService.getLoginedAdminMemberVo(adminMemberVo.getA_m_no());
			
			session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo);
			session.setMaxInactiveInterval(60 * 30);
		} else {
			nextPage = "admin/member/modify_account_ng";
		}
		return nextPage;
	}
	
	//비밀번호 재설정 메일 폼
	@GetMapping("/findPasswordForm")
	public String findPasswordForm() {
		System.out.println("[AdminMemberController] findPasswordForm()");
		
		String nextPage = "admin/member/find_password_form";
		
		return nextPage;
	}
	

	//비밀번호 재설정 메일 컨펌
	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberController] findPasswordConfirm()");
		
		String nextPage ="admin/member/find_password_ok";
		
		int result = adminMemberService.findPasswordConfirm(adminMemberVo);
		
		if(result <= 0)
			nextPage ="admin/member/find_password_ng";
		
		return nextPage;
	}
}

package com.office.library.admin.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin/member") /*member 뒤에 / 붙이지않아야함*/
public class AdminMemberController {
	
	@Autowired
	AdminMemberService adminMemberService;

	
//	@RequestMapping(value = "/createAccountForm", method = RequestMethod.GET)
	@GetMapping("/createAccountForm")
	public String createAccountForm() { /*value와 여기 매개변수값을 맞춰주는게 좋음*/
		System.out.println("[AdminHomeController] createAccountForm()" );
		
		String nextPage = "admin/member/create_account_form";
		
		return nextPage;
	}

//	@RequestMapping(value = "/createAccountConfirm", method = RequestMethod.POST)
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(AdminMemberVo adminMemberVo) { /*(AdminMemberVo adminMemberVo) adminmemberVOdptj */
		
		System.out.println("[AdminHomeController] createAccountConfirm()" );
		
		String nextPage = "admin/member/create_account_ok";
		/*여기서 service클래스에서 반환된 result = 1 이면 ok 0 또는 -1 이 반환되면 아래 if문 통해 ng 반환*/
		
		int result = adminMemberService.createAccountConfirm(adminMemberVo);
		
		if(result <= 0)
			nextPage = "admin/member/create_account_ng";
		
		return nextPage;
	}
	
	
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[AdminMemberController] loginForm()");
		
		String nextPage = "admin/member/login_form";
		
		return nextPage;
	}
	
	
	//로그인컨펌 요청  Vo 의 아이디와 비밀번호를 받기때문에 Vo를 파라미터로 받음
	@PostMapping("/loginConfirm")
	public String loginConfirm(AdminMemberVo adminMemberVo, HttpSession session) {
		System.out.println("[AdminHomeController] loginConfirm()");
		
		String nextPage = "admin/member/login_ok";
		
		AdminMemberVo loginedAdminMemberVo = 
				adminMemberService.loginConfirm(adminMemberVo);
		
		if (loginedAdminMemberVo == null) {
			nextPage = "admin/member/login_ng";
		} else {
			session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo);
			session.setMaxInactiveInterval(60 * 30);
		}
		
		return nextPage;
	}
	
	//관리자 로그아웃
	
	@RequestMapping(value = "/logoutConfirm", method = RequestMethod.GET)
	public String logoutConfirm(HttpSession session) {
		System.out.println("[AdminHomeController] logoutConfirm()");
		
		String nextpage = "redirect:/admin";
		
		session.invalidate();
	
		return nextpage;
	}
	
	@GetMapping("/listupAdmin")
	public String listupAdmin(Model model) {
		System.out.println("[AdminHomeController] listupAdmin()");
		
		String nextPage = "/admin/member/listup_admins";
		
		List<AdminMemberVo> adminMemberVos = adminMemberService.listupAdmin();
		
		model.addAttribute("adminMemberVos" , adminMemberVos);
		
		return nextPage; 
	}
	
	//관리자목록 일반관리자 승인
	@GetMapping("/setAdminApproval")
	public String setAdminApproval(@RequestParam("a_m_no") int a_m_no) {
		System.out.println("[AdminHomeController] setAdminApproval()");
		
		
		String nextPage = "redirect:/admin/member/listupAdmin";
		
		adminMemberService.setAdminApproval(a_m_no);
		
		return nextPage;
	}
	
	//관리자 정보 수정화면 연결
	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {
		System.out.println("[AdminHomeController] modifyAccountForm()");
		
		String nextPage = "admin/member/modify_account_form";
		
		AdminMemberVo loginedAdminMemberVo = (AdminMemberVo) session.getAttribute("loginedAdminMemberVo");
		if(loginedAdminMemberVo == null)
			nextPage = "redirect:/admin/member/loginForm";
		
		return nextPage;
	}
	
	//관리자 정보 수정
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfirm(AdminMemberVo adminMemberVo, HttpSession session) {
		System.out.println("[AdminHomeController] modifyAccountConfirm()");
		
		String nextPage = "admin/memeber/modify_account_ok";
		
		int result = adminMemberService.modifyAccountConfirm(adminMemberVo);
		
		if(result >0) {
			AdminMemberVo loginedAdminMemberVo = adminMemberService.getLoginedAdminMemberVo(adminMemberVo.getA_m_no());
		
			session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo);
			session.setMaxInactiveInterval(60 * 30);
		} else {
			nextPage = "admin/memeber/modify_account_ng";
		}
		return nextPage;
		
	}
	
	//비밀번호찾기 폼
	@GetMapping("/findPasswordForm")
	public String findPasswordForm() {
		System.out.println("[AdminHomeController] findPasswordForm()");
		
		String nextPage ="admin/member/find_password_form";
		
		return nextPage;
	}
	
	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminHomeController] findPasswordConfirm()");
		
		String nextPage= "admin/member/find_password_ok";
		
		int result = AdminMemberService.findPasswordConfirm(adminMemberVo);
		
		if(result <= 0)
			nextPage = "admin/memeber/find_password_ng";
		return nextPage;
		
	}
	
}

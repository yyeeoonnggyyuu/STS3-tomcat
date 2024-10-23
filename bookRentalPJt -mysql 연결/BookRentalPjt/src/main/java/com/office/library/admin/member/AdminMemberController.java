package com.office.library.admin.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		System.out.println("[AdminHomeController] loginForm()");
		
		String nextPage = "admin/member/login_form";
		
		return nextPage;
	}
	
	@PostMapping("/loginConfirm")
	public String loginConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminHomeController] loginConfirm()");
		
		String nextPage = "admin/member/login_ok";
		
		AdminMemberVo loginedAdminMemberVo =adminMemberService.loginConfirm(adminMemberVo);
		
		if(loginedAdminMemberVo == null) {
			nextPage = "admin/member/login_ng";
		}
		
		return nextPage;
		
		
	}
	
}

package com.company.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.hello.member.MemberService;
import com.company.hello.member.MemberVo;
import com.company.hello.member.MemberDao;



@Controller
public class MemberController {
	
//	MemberService memberService = new MemberService();
	
	@Autowired
	private MemberService memberService;

	@RequestMapping("/signUp")
	public String signUp() {
		return "sign_up";
	}


	@RequestMapping("/signUpConfirm")
	public String signUpConfirm(MemberVo memberVo){
		System.out.println("[MemberController] signUpConfirm()");
		
		System.out.println("m_id: " + memberVo.getM_id());
		System.out.println("m_pw: " + memberVo.getM_pw());
		System.out.println("m_mail: " + memberVo.getM_mail());
		System.out.println("m_phone: " + memberVo.getM_id());
		
		memberService.signUpConfirm(memberVo);
		
		return null;

	}

	@RequestMapping("/signIn")
	public String signIn() {
		return "sign_in";
	}

}

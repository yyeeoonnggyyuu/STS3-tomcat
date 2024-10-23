package com.company.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.hello.member.MemberService;
import com.company.hello.member.MemberVo;




@Controller
public class MemberController {
	
//	MemberService memberService = new MemberService();
	
	@Autowired
	MemberService memberService;

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
		
		return "sign_up_ok";
		
	}
	
	@RequestMapping("/signIn")
	public String signIn() {
		return "sign_in";
	}
	

	@RequestMapping("/signInConfirm")
	public String signInConfirm(MemberVo memberVo) {
		System.out.println("[MemberController] signInConfirm()");
		
		MemberVo signInedMember = memberService.signInConfirm(memberVo);
		
		if(signInedMember != null)
			return "sign_in_ok";
		else
			return "sign_in_ng";
		
	}

}

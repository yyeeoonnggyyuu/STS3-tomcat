package com.company.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@GetMapping({"","/"})
	public String home() {
		System.out.println("[MemberController] home()");
		
		String nextPage = "member/home";
		
		return nextPage;
	
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println();
		
		String nextPage = "member/login_form";
		
		return nextPage;
	}
	
	@PostMapping("/loginConfirm")
	public String loginConfirm(
				@RequestParam("m_id") String m_id,
				@RequestParam("m_pw") String m_pw,
				HttpServletResponse response) {
		System.out.println("[MemberController] loginConfirm()");
		

		String nextPage = "/member/login_ok";
		
		if(m_id.equals("user") && m_pw.equals("1234")) {
			Cookie cookie = new Cookie("loginMember", m_id);
			cookie.setMaxAge(60 * 30);
			response.addCookie(cookie);
		} else {
			nextPage = "member/login_ng";
		}
		
		
		return nextPage;
	}
	
	@GetMapping("/logoutForm")
	public String logoutForm(@CookieValue(value ="loginMember", required = false) String loginMember, HttpServletResponse response) {
		System.out.println("[MemberController] logoutForm()");
		
		String nextPage = "redirect:/member/";
		Cookie cookie = new Cookie("loginMember", loginMember);
		cookie.setMaxAge(0);
		return nextPage;
	}
}

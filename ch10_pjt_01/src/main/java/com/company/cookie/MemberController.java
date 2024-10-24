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
				HttpServletResponse response) {/*리턴시킬 때 쿠키인 response를 같이 보냄*/
		System.out.println("[MemberController] loginConfirm()");
		

		String nextPage = "/member/login_ok";
		
		if(m_id.equals("user") && m_pw.equals("1234")) {
			Cookie cookie = new Cookie("loginMember", m_id);
			cookie.setMaxAge(60 * 30);/*활용 시칸 60분중 30분 이란 뜻*/
			response.addCookie(cookie);/*위에서 새로 생성된 객체에서 m_id가 포함된 쿠키를 보냄*/
		} else {
			nextPage = "member/login_ng";
		}
		
		
		return nextPage;
	}

	@GetMapping("/logoutForm")						/*loginMember 밸류 값을 받아옴*/    /*받아온 밸류값을 스트링 loginMember객체에 넣음*/
	public String logoutForm(@CookieValue(value ="loginMember", required = false) String loginMember, HttpServletResponse response) {
		System.out.println("[MemberController] logoutForm()");
		
		String nextPage = "redirect:/member/";
		Cookie cookie = new Cookie("loginMember", loginMember);
		cookie.setMaxAge(0); /*쿠키 종료*/
		return nextPage;
	}
}

package com.office.library.user.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/member/")
public class UserMemberController {
	
	@Autowired
	UserMemberService userMemberService;
	
	//유저 회원가입 폼
    @GetMapping({"/createAccountForm"})
    public String createAccountForm() {
        System.out.println("[UserMemberController] createAccountForm()");
        String nextPage = "user/member/create_account_form";
        return nextPage;
    }
    
    //유저 회원가입 컨펌
    @PostMapping({"/createAccountConfirm"})
    public String createAccountConfirm(UserMemberVo userMemberVo) {
        System.out.println("[UserMemberController] createAccountConfirm()");
        String nextPage = "user/member/create_account_ok";
        int result = userMemberService.createAccountConfirm(userMemberVo);
        if (result <= 0) {
            nextPage = "user/member/create_account_ng";
        }

        return nextPage;
    }
    
    //유저 로그인 폼
    @GetMapping({"/loginForm"})
    public String loginForm() {
        System.out.println("[UserMemberController] loginForm()");
        String nextPage = "user/member/login_form";
        return nextPage;
    }

  //유저 로그인 컨펌
    @PostMapping({"/loginConfirm"})
    public String loginConfirm(UserMemberVo userMemberVo, HttpSession session) {
        System.out.println("[UserMemberController] loginConfirm()");
        String nextPage = "user/member/login_ok";
        UserMemberVo loginedUserMemberVo = userMemberService.loginConfirm(userMemberVo);
        if (loginedUserMemberVo == null) {
            nextPage = "user/member/login_ng";
        } else {
            session.setAttribute("loginedUserMemberVo", loginedUserMemberVo);
            session.setMaxInactiveInterval(60 *30);
        }

        return nextPage;
    }

  //유저 계정수정 폼
    @GetMapping({"/modifyAccountForm"})
    public String modifyAccountForm(HttpSession session) {
        System.out.println("[UserMemberController] modifyAccountForm()");
        String nextPage = "user/member/modify_account_form";
        
//        UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
//        if(loginedUserMemberVo == null)
//        	nextPage = "redorect:/user/member/login_form";
        return nextPage;
    }

    //유저 계정수정 컨펌
    @PostMapping({"/modifyAccountConfirm"})
    public String modifyAccountConfirm(UserMemberVo userMemberVo, HttpSession session) {
        System.out.println("[UserMemberController] modifyAccountConfirm()");
        String nextPage = "user/member/modify_account_ok";
        int result = userMemberService.modifyAccountConfirm(userMemberVo);
        if (result > 0) {
            UserMemberVo loginedUserMemberVo = userMemberService.getLoginedUserMemberVo(userMemberVo.getU_m_no());
            session.setAttribute("loginedUserMemberVo", loginedUserMemberVo);
            session.setMaxInactiveInterval(60 * 30);
        } else {
            nextPage = "user/member/modify_account_ng";
        }

        return nextPage;
    }

    //유저 로그아웃 컨펌
    @GetMapping({"/logoutConfirm"})
    public String logoutConfirm(HttpSession session) {
        System.out.println("[UserMemberController] logoutConfirm()");
        String nextPage = "redirect:/";
        session.invalidate();
        return nextPage;
    }

    //유저 패스워드 재설정 폼
    @GetMapping({"/findPasswordForm"})
    public String findPasswordForm() {
        System.out.println("[UserMemberController] findPasswordForm()");
        String nextPage = "user/member/find_password_form";
        return nextPage;
    }

  //유저 패스워드 재설정 컨펌
    @PostMapping({"/findPasswordConfirm"})
    public String findPasswordConfirm(UserMemberVo userMemberVo) {
        System.out.println("[UserMemberController] findPasswordConfirm()");
        String nextPage = "user/member/find_password_ok";
        int result = userMemberService.findPasswordConfirm(userMemberVo);
        if (result <= 0) {
            nextPage = "user/member/find_password_ng";
        }

        return nextPage;
    }
}

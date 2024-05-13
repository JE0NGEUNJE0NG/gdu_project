package com.cu.gdu.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cu.gdu.dto.MemberDto;
import com.cu.gdu.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService memberService;
	private final BCryptPasswordEncoder bcryptPwdEncoder;
	
	// * 로그인 관련 ----------------------------------------
	@PostMapping("/login.do")
	public void login(MemberDto m, HttpServletRequest request
					 , HttpSession session
					 , RedirectAttributes redirectAttributes
					 , HttpServletResponse response) throws IOException {
		
		MemberDto loginUser = memberService.selectMember(m);
		
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		
		//if(loginUser != null && m.getMemPwd().equals(loginUser.getMemPwd()) ) 	{  // 암호화 전
		if(loginUser != null && bcryptPwdEncoder.matches(m.getMemPwd(), loginUser.getMemPwd()) ) 	{ 
			request.getSession().setAttribute("loginUser", loginUser);
			out.println("alert('" + loginUser.getMemName() + "님 환영합니다~');");
			out.println("location.href = '" + request.getContextPath() + "/member/mainpage';"); // mainpage.jsp로 이동
		
		}else {
			out.println("alert('로그인 실패하였습니다. 아이디 및 비밀번호를 다시 확인해주세요.')");
			out.println("history.back();");
		}
		out.println("</script>");
		
	}
	
	 @GetMapping("/mainpage")
	    public String showMainPage() {
	        return "member/mainpage"; 
	    }
	
	// * 마이페이지 관련 -------------------------
	@GetMapping("/mypage.page")
	public String mypage() {
		return "/member/mypage";
	}
	
	// * 비밀번호 변경 관련 -------------------------
	@PostMapping("modifyPwd.do")
	public String updatePassword(String memPwd, String updatePwd
								, HttpSession session
								, RedirectAttributes redirectAttributes) {
		
		System.out.println(memPwd);
		System.out.println(updatePwd);
		
		MemberDto loginUser = (MemberDto)session.getAttribute("loginUser");
		
		//if(memPwd.equals(loginUser.getMemPwd())) { // 암호화 전 
		if(bcryptPwdEncoder.matches(memPwd, loginUser.getMemPwd())) {
			loginUser.setMemPwd( bcryptPwdEncoder.encode(updatePwd) );
			memberService.updatePassword(loginUser);
			session.setAttribute("loginUser", memberService.selectMember(loginUser));
			redirectAttributes.addFlashAttribute("alertMsg", "비밀번호 변경 성공했습니다.");
			
		}else {
			redirectAttributes.addFlashAttribute("alertMsg", "비밀번호가 틀렸습니다. 다시 입력해주세요");
			
		}
		
		return "redirect:/member/mypage.page";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

package com.dev.devfolio.controller;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.devfolio.dto.Member;
import com.dev.devfolio.service.MemberService;
import com.dev.devfolio.util.Util;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("usr/member/join")
	public String showJoinPage(HttpSession session) {
		return "usr/member/join";
	}
	
	@RequestMapping("usr/member/loginIdCheck")
	@ResponseBody
	public int loginIdCheck(@RequestParam Map<String, Object> param) {
		String loginId = (String) param.get("loginId");
		int result = memberService.loginIdCheck(loginId);
		return result;
	}	
	
	@RequestMapping("usr/member/nicknameCheck")
	@ResponseBody
	public int nicknameCheck(@RequestParam Map<String, Object> param) {
		String nickname = (String) param.get("nickname");
		int result = memberService.nicknameCheck(nickname);
		return result;
	}	
	
	@RequestMapping("usr/member/doJoin")
	@ResponseBody
	public String doJoin(@RequestParam Map<String, Object> param, HttpSession session) {
		String msg = String.format("%s님 환영합니다.", param.get("nickname"));
		String email = (String) param.get("emailId") + "@" + (String) param.get("emailDomain");
		String redirectUrl = Util.ifEmpty((String) param.get("redirectUrl"), "/usr/member/input");
		String loginPwReal = (String) param.get("loginPwReal");
		
		param.put("loginPwReal", loginPwReal);
		param.put("email", email);

		int id = memberService.join(param);

		session.setAttribute("tempInput", id);
		session.removeAttribute("isTrue");
		session.removeAttribute("nickname");
		session.removeAttribute("email");
		session.removeAttribute("emailDomain");
		
		return Util.msgAndReplace(msg,redirectUrl);
	}
	
	@RequestMapping("usr/member/input")
	public String showInputPage() {
		return "usr/member/inputDetail";
	}		

	@RequestMapping("usr/member/doInput")
	@ResponseBody
	public String doInput(@RequestParam Map<String, Object> param, HttpSession session) {
		int id = (int) session.getAttribute("tempInput");
		
		String msg = String.format("가입이 완료되었습니다.");
		String redirectUrl = Util.ifEmpty((String) param.get("redirectUrl"), "/usr/home/main");
		
		String preferredRegion = (String) param.get("region_high") + " / " + (String) param.get("region_middle")+ " / " + (String) param.get("region_low");
		param.put("preferredRegion", preferredRegion);
		param.put("id", id);
		
		memberService.input(param);
		
		return Util.msgAndReplace(msg,redirectUrl);
	}	
	
	@RequestMapping("usr/member/login")
	public String showLoginPage() {
		return "usr/member/login";
	}	
	
	@RequestMapping("usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPwReal, String redirectUrl, HttpSession session) {
		Member member = memberService.getMemberByLoginId(loginId);
		
		if (member.getPreferredRegion().isEmpty() | member.getSkill().isEmpty()) {
			session.setAttribute("tempInput", member.getId());
		}
		
		if(member.getAuthKey() == null) {
			return Util.msgAndBack("존재하지 않는 아이디입니다.");
		} else if (member.getLoginPw().equals(loginPwReal) == false) {
			return Util.msgAndBack("비밀번호가 일치하지 않습니다.");
		}
		
		session.setAttribute("loginedMemberId", member.getId());
		
		String msg = String.format("%s님 환영합니다.", member.getNickname());

		redirectUrl = Util.ifEmpty(redirectUrl, "/usr/home/main");

		return Util.msgAndReplace(msg, redirectUrl);
	}
	
	@RequestMapping("usr/member/doLogout")
	@ResponseBody
	public String doLogout(HttpSession session) {
		session.removeAttribute("loginedMemberId");

		return Util.msgAndReplace("로그아웃 되었습니다.", "../home/main");
	}	
}

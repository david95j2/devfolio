package com.dev.devfolio.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.devfolio.service.MemberService;
import com.dev.devfolio.util.Util;

@Controller
public class UsrMemberController {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("usr/member/join")
	public String showJoinPage() {
		return "usr/member/join";
	}
	
	@RequestMapping("usr/member/doJoin")
	@ResponseBody
	public String doJoin(@RequestParam Map<String, Object> param) {
		String msg = String.format("%s님 환영합니다.", param.get("nickname"));
		String email = (String) param.get("emailId") + "@" + (String) param.get("emailDomain");
		param.put("email", email);
		String redirectUrl = Util.ifEmpty((String) param.get("redirectUrl"), "/usr/home/main");
		System.out.println("결과는? : "+redirectUrl+" 입니다.");
		memberService.join(param);
		
		return Util.msgAndReplace(msg,redirectUrl);
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
}

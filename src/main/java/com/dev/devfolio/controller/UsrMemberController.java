package com.dev.devfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrMemberController {
	
	@RequestMapping("usr/member/join")
	@ResponseBody
	public String showJoinPage() {
		return "회원가입페이지";
	}
}

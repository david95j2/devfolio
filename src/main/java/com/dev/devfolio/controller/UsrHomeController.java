package com.dev.devfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsrHomeController {
	
	@RequestMapping("usr/home/main")
	@ResponseBody
	public String showMainPage() {
		return "메인페이지";
	}
}

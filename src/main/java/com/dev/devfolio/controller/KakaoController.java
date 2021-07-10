package com.dev.devfolio.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.devfolio.service.KakaoService;
import com.dev.devfolio.util.Util;

@Controller
public class KakaoController {
	
	@Value("${kakao.loginApi.restApi}")
	private String kakao_restApi_key;
	
	@Autowired
	private KakaoService kakaoService;
		
	// 카카오 로그인 테스트
	@RequestMapping("usr/kakao/doLogin")
	public String doLogin(HttpSession session, @RequestParam(value = "code", required = false) String code, HttpServletRequest req)
			throws Exception {
		// 1.인증코드 받기
		System.out.println("### 인증코드 ### : " + code);

		// 2.인증된 코드로 사용자토큰 받기
		String access_Token = kakaoService.getAccessToken(code,kakao_restApi_key);
		
		if (access_Token == null) {
			return Util.pure(req, "<script> alert('인증에 실패하였습니다.'); self.close(); </script>");
		}
		
		// 3.사용자토큰으로 로그인한 유저의 정보 받아오기
		HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);

		/* (나에게 메시지 보내기 기능 추가) */ 
		// 4.사용자토큰으로 로그인한 유저(본인)에게 카카오 메시지 보내기
		boolean isSendMessage = kakaoService.isSendMessage(access_Token);
		String result = "카카오톡 나에게 메시지 보내기에 실패하였습니다.";
		if (isSendMessage) {
			result = "카카오톡 나에게 메시지 보내기에 성공하였습니다.";
		}
		
		boolean isTrue = false;
		if (userInfo.get("access_Token") != null) {
			isTrue = true;
		}
		
		// 5.session 값 셋팅
		session.setAttribute("isTrue", isTrue);
		session.setAttribute("nickname", userInfo.get("nickname"));
		session.setAttribute("email", userInfo.get("email"));
		session.setAttribute("emailDomain", userInfo.get("emailDomain"));
		
		return Util.pure(req, "<script> window.onload = function() { opener.parent.location='../member/join';"
				+ " window.close();} </script>");
	}

	// 앱에서 로그아웃(정보제공 여부 유지)
	@RequestMapping("usr/kakao/logout")
	public String logout(HttpSession session) {
		kakaoService.kakaoLogout((String) session.getAttribute("access_Token"));
		session.removeAttribute("access_Token");
		session.removeAttribute("nickname");
		session.removeAttribute("result");
		session.removeAttribute("email");
		session.removeAttribute("profile_image");
		session.removeAttribute("thumbnail_image");

		return "usr/member/kakaoLogout";
	}

	// 앱과 연결된 카카오계정 연결해제(정보제공 여부도 전부 초기화)
	@RequestMapping("usr/kakao/unlink")
	public String unlink(HttpSession session) {
		kakaoService.kakaoUnlink((String) session.getAttribute("access_Token"));
		session.removeAttribute("access_Token");
		session.removeAttribute("nickname");
		session.removeAttribute("result");
		session.removeAttribute("email");
		session.removeAttribute("profile_image");
		session.removeAttribute("thumbnail_image");

		return "usr/member/kakaoLogout";
	}

}

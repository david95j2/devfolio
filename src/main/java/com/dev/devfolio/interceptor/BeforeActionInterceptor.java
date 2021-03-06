package com.dev.devfolio.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.dev.devfolio.dto.Member;
import com.dev.devfolio.service.MemberService;
import com.dev.devfolio.util.Util;

import lombok.extern.slf4j.Slf4j;

@Component("beforeActionInterceptor") // 컴포넌트 이름 설정
@Slf4j
public class BeforeActionInterceptor implements HandlerInterceptor {
	@Autowired
	private MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 기타 유용한 정보를 request에 담는다.
		Map<String, Object> param = Util.getParamMap(request);
		String paramJson = Util.toJsonStr(param);

		String requestUrl = request.getRequestURI();
		String queryString = request.getQueryString();

		if (queryString != null && queryString.length() > 0) {
			requestUrl += "?" + queryString;
		}

		String[] pathBits = request.getRequestURI().split("/");

		String controllerTypeCode = "usr";
		String controllerSubject = "home";
		String controllerActName = "main";

		if (pathBits.length > 1) {
			controllerTypeCode = pathBits[1];
		}

		if (pathBits.length > 2) {
			controllerSubject = pathBits[2];
		}

		if (pathBits.length > 3) {
			controllerActName = pathBits[3];
		}

		request.setAttribute("controllerTypeCode", controllerTypeCode);
		request.setAttribute("controllerSubject", controllerSubject);
		request.setAttribute("controllerActName", controllerActName);

		log.debug("controllerTypeCode : " + controllerTypeCode);
		log.debug("controllerSubject : " + controllerSubject);
		log.debug("controllerActName : " + controllerActName);

		boolean isAjax = false;
		
		String isAjaxParameter = request.getParameter("isAjax");
		
		if ( isAjaxParameter == null  ) {
			if ( controllerActName.startsWith("get") ) {
				isAjax = true;
			}
			else if ( controllerTypeCode.equals("usr") ) {
				isAjax = true;
			}
		}
		else if ( isAjaxParameter.equals("Y") ) {
			isAjax = true;
		}

		if (isAjax == false && request.getParameter("isAjax") != null && request.getParameter("isAjax").equals("Y")) {
			isAjax = true;
		}

		request.setAttribute("isAjax", isAjax);
		
		log.debug("isAjax : " + isAjax);

		String encodedRequestUrl = Util.getUrlEncoded(requestUrl);

		request.setAttribute("requestUrl", requestUrl);
		request.setAttribute("encodedRequestUrl", encodedRequestUrl);

		request.setAttribute("afterLoginUrl", requestUrl);
		request.setAttribute("encodedAfterLoginUrl", encodedRequestUrl);

		request.setAttribute("paramMap", param);
		request.setAttribute("paramJson", paramJson);

		int loginedMemberId = 0;
		Member loginedMember = null;

		String authKey = request.getParameter("authKey");

		if (authKey != null && authKey.length() > 0) {
			loginedMember = memberService.getMemberByAuthKey(authKey);

			if (loginedMember == null) {
				request.setAttribute("authKeyStatus", "invalid");
			} else {
				request.setAttribute("authKeyStatus", "valid");
				loginedMemberId = loginedMember.getId();
			}
		} else {
			HttpSession session = request.getSession();
			request.setAttribute("authKeyStatus", "none");

			if (session.getAttribute("loginedMemberId") != null) {
				loginedMemberId = (int) session.getAttribute("loginedMemberId");
				loginedMember = memberService.getMemberById(loginedMemberId);
			}
		}

		// 로그인 여부에 관련된 정보를 request에 담는다.
		boolean isLogined = false;
		boolean isAdmin = false;

		if (loginedMember != null) {
			isLogined = true;
			isAdmin = memberService.isAdmin(loginedMember);
		}

		request.setAttribute("loginedMemberId", loginedMemberId);
		request.setAttribute("isLogined", isLogined);
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("loginedMember", loginedMember);

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}

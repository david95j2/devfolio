package com.dev.devfolio.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.devfolio.dao.MemberDao;
import com.dev.devfolio.dto.Member;
import com.dev.devfolio.util.Util;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	public void join(Map<String, Object> param) {
		memberDao.join(param);
	}
	
	public int loginIdCheck(String loginId) {	
		Member result = memberDao.getMemberByLoginId(loginId);

		if (result == null) {
			return 0;
		} else {
			return 1;
		}
	}

	public int nicknameCheck(String nickname) {
		Member result = memberDao.getMemberByNickname(nickname);

		if (result == null) {
			return 0;
		} else {
			return 1;
		}
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}
	
	public Member getMemberByAuthKey(String authKey) {
		return memberDao.getMemberByAuthKey(authKey);
	}

	public Member getMemberById(int id) {
		return memberDao.getMemberById(id);
	}
	
	public boolean isAdmin(Member actor) {
		return actor.getAuthLevel() == 7;
	}

	public Object input(Map<String, Object> param) {
		return memberDao.input(param);
	}	
}

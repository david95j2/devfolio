package com.dev.devfolio.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dev.devfolio.dto.Member;

@Mapper
public interface MemberDao {
	int join(Map<String,Object> param);

	Member getMemberByLoginId(@Param("loginId") String loginId);

	Member getMemberByNickname(@Param("nickname") String nickname);

	Member getMemberByAuthKey(@Param("authKey") String authKey);

	Member getMemberById(@Param("id") int id);

	void input(Map<String, Object> param);
}

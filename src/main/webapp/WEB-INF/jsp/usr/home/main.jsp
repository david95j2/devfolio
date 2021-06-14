<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="../part/head.jspf"%>

<h1>메인페이지</h1>
<h2>추후 구현</h2>

<br />


<c:if test="${ isLogined == false }">
	<a href="../member/join">회원가입</a>
	<br />
	<a href="../member/login">로그인</a>
</c:if>

<c:if test="${ isLogined == true }">
	<br />
	<h2>${loginedMember.getNickname() }님 환영합니다.</h2>
	<br />
	<a href="../member/doLogout">로그아웃</a>
</c:if>

<%@ include file="../part/foot.jspf"%>

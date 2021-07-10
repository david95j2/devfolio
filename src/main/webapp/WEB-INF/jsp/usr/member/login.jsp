<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ include file="../part/head.jspf"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js" integrity="sha512-szJ5FSo9hEmXXe7b5AUVtn/WnL8a5VofnFeYC2i2z03uS2LhAch7ewNLbl5flsEmTTimMN0enBZg/3sQ+YOSzQ==" crossorigin="anonymous"></script>
<spring:eval expression="@environment.getProperty('kakao.loginApi.javaScript')" var="key" />

<div class="lg:flex">
  <div class="lg:w-1/2 xl:max-w-screen-sm">
    <div class="py-12 bg-white lg:bg-white flex justify-center lg:justify-start lg:px-12">
      <div class="cursor-pointer flex items-center">
        <a href="https://codepen.io/ucong2222/pen/xxRmVQZ" class="lg:hidden block logo h-full w-66">
          <div class="text-7xl text-color-1 font-semibold">DevFolio</div>
        </a>
        <div class="lg:block hidden text-xl tracking-wide ml-2 font-semibold">
          <div>개발자들을 위한</div>
          <div>개발자들에 의한</div>
          <a class="text-6xl text-color-1">DevFolio</a>
        </div>
      </div>
    </div>
    <div class="mt-10 px-12 sm:px-24 md:px-48 lg:px-12 lg:mt-16 xl:px-24 xl:max-w-2xl">
      <h2 class="text-center text-3xl text-color-1 font-display font-semibold lg:text-left xl:text-3xl xl:text-bold">LOGIN</h2>
      <div class="mt-12">
        <form action="doLogin" method="POST" onsubmit="DoLoginForm__submit(this); return false;">
          <input type="hidden" name="loginPwReal" />
		  <input type="hidden" name="redirectUrl" value="${param.redirectUrl}" />
          <div>
            <div class="text-sm font-bold text-gray-700 tracking-wide">ID</div>
            <input class="w-full text-lg py-2 border-b border-gray-300 focus:outline-none focus:border-blue-900" type="text" placeholder="Enter your ID" autofocus="autofocus" name="loginId">
            <div class="text-sm font-bold text-gray-700 tracking-wide" id="id_check"></div>
          </div>
          <div class="mt-8">
            <div class="flex justify-between items-center">
              <div class="text-sm font-bold text-gray-700 tracking-wide">Password</div>
              <div>
                <a class="text-xs font-display font-semibold text-main hover:text-blue-800 cursor-pointer">Forgot Password?</a>
              </div>
            </div>
            <input class="w-full text-lg py-2 border-b border-gray-300 focus:outline-none focus:border-blue-900" type="password" placeholder="Enter your password" autofocus="autofocus" name="loginPw">
          	<div class="text-sm font-bold text-gray-700 tracking-wide" id="pw_check"></div>
          </div>
          <div class="mt-10">
            <button class="login-btn bg-main text-gray-100 p-4 w-full rounded-full tracking-wide font-semibold font-display focus:outline-none focus:shadow-outline shadow-lg">LOGIN</button>
          </div>
        </form>  

        <div class="mt-12 text-sm font-display font-semibold text-gray-700 text-center">
          SNS로 계정으로 간편하게 시작하세요.
          <div class="icon-block flex justify-center mt-8 text-4xl">
            <div class="mr-8">
            	<a href="#" class="hover:bg-opacity-80 bg-yellow-200 w-20 h-20 block rounded-full flex justify-center items-center" onclick="openPop()">
            		<i class="fas fa-comment"></i>
            	</a>
            </div>
            <div>
            	<a href="#" class="hover:bg-opacity-80 bg-green-600 text-white w-20 h-20 block rounded-full flex justify-center items-center">
            		<span>N</span>
            	</a>
            </div>
          </div>
        </div>

        <div class="mt-12 text-sm font-display font-semibold text-gray-700 text-center">
          Don't have an account ?
          <a href="../member/join" class="cursor-pointer text-main hover:text-bleu-900">Sign up</a>
        </div>
      </div>
    </div>
  </div>

  <div class="hidden lg:flex items-center justify-center bg-blue-100  flex-1 h-screen sticky top-0 left-0">
    <div class="max-w-md transform duration-200 hover:scale-110 cursor-pointer">
      <div class="">
        <img class="" src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FzteFU%2Fbtq1C0Z4ssZ%2FvuQHfBIUbpQEJMQJDWkZ51%2Fimg.png" alt="">
      </div>
    </div>
  </div>
</div>

<script>
	function openPop(){
	    var popup = window.open('https://kauth.kakao.com/oauth/authorize?client_id=${key}&redirect_uri=http://localhost:8090/usr/kakao/doLogin&response_type=code', '카카오로 로그인', 'width=450px,height=600px,scrollbars=no');
	}

	let DoLoginForm__submited = false;
	
	function DoLoginForm__submit(form) {
		if ( DoLoginForm__submited ) {
			alert('처리중입니다.');
			return;
		}
	
		form.loginId.value = form.loginId.value.trim();
	
		if ( form.loginId.value.length == 0 ) {
			$('#id_check').text("로그인 아이디를 입력해주세요.");
			$('#id_check').css("color","red");
			form.loginId.focus();
			
			return;
		}
		
		form.loginPw.value = form.loginPw.value.trim();
	
		if ( form.loginPw.value.length == 0 ) {
			$('#pw_check').text("비밀번호를 입력해주세요.");
			$('#pw_check').css("color","red");
			form.loginPw.focus();
			
			return;
		}
		
		form.loginPwReal.value = sha256(form.loginPw.value);
		
		form.loginPw.value = "";
		
		form.submit();
		DoLoginForm__submited = true;		
	}
</script>

<%@ include file="../part/foot.jspf"%>

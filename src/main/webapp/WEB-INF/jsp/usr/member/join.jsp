<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ include file="../part/head.jspf"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>    
<spring:eval expression="@environment.getProperty('kakao.loginApi.javaScript')" var="key" />      

<div class="lg:flex">

  <div class="lg:w-1/2 xl:max-w-screen-sm">
    <div class="mt-8 px-12 sm:px-24 md:px-48 lg:px-12 lg:mt-8 xl:px-24 xl:max-w-2xl">
      <h2 class="text-center text-3xl text-color-1 font-display font-semibold lg:text-left xl:text-3xl
                    xl:text-bold">JOIN</h2>
      <div class="mt-12">
        <form class="join" action="doJoin" method="POST" onsubmit="JoinForm__checkAndSubmit(this); return false;">
          <input type="hidden" name="loginPwReal"/>
          <div>
            <div class="text-sm font-bold text-gray-700 tracking-wide">ID</div>
            <input class="w-full text-lg py-2 border-b border-gray-300 focus:outline-none focus:border-blue-900" type="text" placeholder="Enter your ID" id="user_id" autofocus="autofocus" name="loginId" value="${email }">
            <div class="text-sm font-bold text-gray-700" id="id_check"></div>      
          </div>
          <div class="mt-8">
            <div class="text-sm font-bold text-gray-700 tracking-wide">password</div>
            <input class="w-full text-lg py-2 border-b border-gray-300 focus:outline-none focus:border-blue-900" type="password" placeholder="Enter your password" id="user_pw" autofocus="autofocus">
            <div class="text-sm font-bold text-gray-700" id="pw_check"></div>
          </div>
          <div class="mt-8">
            <div class="text-sm font-bold text-gray-700 tracking-wide">passwordConfirm</div>
            <input class="w-full text-lg py-2 border-b border-gray-300 focus:outline-none focus:border-blue-900" type="password" placeholder="Enter your passwordConfirm" id="user_pwReal" autofocus="autofocus" name="loginPw">
          	<div class="text-sm font-bold text-gray-700" id="pwReal_check"></div>
          </div>
          <div class="mt-8">
            <div class="text-sm font-bold text-gray-700 tracking-wide">name</div>
            <input class="w-full text-lg py-2 border-b border-gray-300 focus:outline-none focus:border-blue-900" type="text" placeholder="Enter your name" id="user_name" autofocus="autofocus" name="name" value="${nickname }">
          	<div class="text-sm font-bold text-gray-700" id="name_check"></div>
          </div>
          <div class="mt-8">
            <div class="text-sm font-bold text-gray-700 tracking-wide">nickname</div>
            <input class="w-full text-lg py-2 border-b border-gray-300 focus:outline-none focus:border-blue-900" type="text" placeholder="Enter your nickname" id="user_nickname" autofocus="autofocus" name="nickname">
          	<div class="text-sm font-bold text-gray-700" id="nickname_check"></div>
          </div>
          <div class="mt-8">
            <div class="text-sm font-bold text-gray-700 tracking-wide">Email Address</div>
            <div class="flex justify-center items-center">
            	<input class="w-full text-lg py-2 border-b border-gray-300 focus:outline-none focus:border-blue-900" type="text" placeholder="Email ID" id="user_email" autofocus="autofocus" name="emailId" value="${email }">@
            	<input class="w-full text-center text-lg py-2 border-b border-gray-300 focus:outline-none focus:border-blue-900" type="text" placeholder="Email Domain" id="user_emailDomain" value="${emailDomain }" autofocus="autofocus" name="emailDomain">
            	<select class="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" id="email">
            		<option value="0" >Select</option>
					<option value="1">????????????</option>
					<option value="naver.com">naver.com</option>
					<option value="gmail.com">gmail.com</option>
					<option value="daum.net">daum.net</option> 
            	</select>
            </div>
            <div class="text-sm font-bold text-gray-700" id="email_check"></div>
          </div>
          <div class="mt-8">
            <div class="text-sm font-bold text-gray-700 tracking-wide">cellphoneNo</div>
            <input class="w-full text-lg py-2 border-b border-gray-300 focus:outline-none focus:border-blue-900" type="tel" placeholder="Enter your cellphoneNo" id="user_phoneNum" autofocus="autofocus" name="cellphoneNo"
             pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}" maxlength="13">
          	<div class="text-sm font-bold text-gray-700" id="phoneNum_check"></div>
          </div>

          <div class="mt-10">
            <button class="login-btn bg-main text-gray-100 p-4 w-full rounded-full tracking-wide
                           font-semibold font-display focus:outline-none focus:shadow-outline
                           shadow-lg" type="submit">join</button>
          </div>
        </form>

        <div class="mt-12 text-sm font-display font-semibold text-gray-700 text-center">
          SNS??? ???????????? ???????????? ???????????????..
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

<script>
	function openPop(){
	    var popup = window.open('https://kauth.kakao.com/oauth/authorize?client_id=${key}&redirect_uri=http://localhost:8090/usr/kakao/doLogin&response_type=code', '???????????? ?????????', 'width=450px,height=600px,scrollbars=no');
	}

	let JoinForm__checkAndSubmitDone = false;

	// id ??????
	const idRule = /^[a-zA-Z0-9\\@.]+$/;
	var idRuleResult = false;

	// ???????????? ??????
	const pwRule = /^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,20}$/;
	var pwRuleResult = false;
	var pwRule2Result = false;
	
	// ?????? ??????
	const nameRule = /^[???-???]{2,6}$/;
	var nameRuleResult = false;
	
	// ????????? ??????
	const nicknameRule = /^[???-???a-zA-Z0-9]{1,12}$/;
	var nicknameRuleResult = false;
	
	// ????????? ??????
	const emailRule1 = /^[a-zA-Z0-9]{1,}$/;
	var emailRuleResult = false;
	
	const emailRule2 = /^[a-zA-Z.]{4,}$/;
	var emailDomainRuleResult = false;
	
	// ???????????? ??????
	const cellphoneNoRule = /^[0-9-]{13}$/;
	var cellphoneNoRuleResult = false;
	
	// ????????? ????????? ?????? ??????
	$("#user_id").blur(function() {
		const loginId = $('#user_id').val();
		idRuleResult = false;
		
		if(loginId == ""){
			$('#id_check').text('???????????? ??????????????????.');
			$('#id_check').css('color', 'red');
			return;
		} else if(idRule.test(loginId) == false){
			$('#id_check').text("??????????????? ????????? ??? ????????????.");
			$('#id_check').css('color', 'red');
			return;
		} 
		
		$.ajax({
			url : 'loginIdCheck',
			type : 'post',
			data : {
				loginId
			},
			success : function(data) {
				if (data == 1) {
					$("#id_check").text("???????????? ??????????????????.");
					$('#id_check').css('color', 'red');
				} else {
					$('#id_check').text('??????????????? ?????????');
					$('#id_check').css('color', 'green');
					idRuleResult = true;
				}	
			}, error : function() {
					alert("Error");
			}
		});
	});	

	// ???????????? ????????? ?????? ??????
	$("#user_pw").blur(function() {
		const loginPw = $('#user_pw').val();
		const loginPwReal = $('#user_pw').val();
		
		if (loginPwReal != loginPw) {
			const loginPwReal = $('#user_pwReal').val("");
			$('#pwReal_check').text("");
		}
		
		if (loginPw.length == 0) {
			$('#pw_check').text('??????????????? ??????????????????.');
			$('#pw_check').css('color', 'red');
		} else if (loginPw.length < 8 || loginPw.length > 20) {
			$('#pw_check').text('??????????????? 8~20????????? ??????????????????.');
			$('#pw_check').css('color', 'red');
		} else if (pwRule.test(loginPw) == false) {
			$('#pw_check').text('??????????????? "?????? + ?????? 1?????? (?????? + ????????????)"??? ??????????????????.');
			$('#pw_check').css('color', 'red');
		} else {
			$('#pw_check').text('????????? ????????????');
			$('#pw_check').css('color', 'green');
			pwRuleResult = true;
		}
	});
	
	// ???????????? ?????? ??????	
	$("#user_pwReal").blur(function() {
		const loginPw = $('#user_pw').val();
		const loginPwReal = $('#user_pwReal').val();
		pwRuleResult = false;
		
		if (loginPwReal.length == 0 ) {
			$('#pwReal_check').text('??????????????? ??????????????????.');
			$('#pwReal_check').css('color', 'red');
		} else if (loginPw != loginPwReal) {
			$('#pwReal_check').text('??????????????? ???????????? ????????????.');
			$('#pwReal_check').css('color', 'red');
		} else {
			$('#pwReal_check').text('???????????? ??????');
			$('#pwReal_check').css('color', 'green');
			pwRule2Result = true;
		}
	});	
	
	// ?????? ????????? ?????? ??????	
	$("#user_name").blur(function() {
		const name = $('#user_name').val();
		nameRuleResult = false;
		
		if (name.length < 2) {
			$('#name_check').text('????????? ??????????????????.');
			$('#name_check').css('color', 'red');
		} else if (nameRule.test(name) == false) {
			$('#name_check').text('????????? ????????? ???????????????.');
			$('#name_check').css('color', 'red');
		} else {
			$('#name_check').text("");
			nameRuleResult = true;
		}
	});
	
	// ????????? ????????? ?????? ??????	
	$("#user_nickname").blur(function() {
		const nickname = $('#user_nickname').val();
		nicknameRuleResult = false;
		
		if (nickname.length > 12 ) {
			$('#nickname_check').text("???????????? 12?????????????????????.");
			$('#nickname_check').css('color', 'red');
			return;
		} else if(nickname == ""){
			$('#nickname_check').text('???????????? ??????????????????.');
			$('#nickname_check').css('color', 'red');
			return;
		} else if(nicknameRule.test(nickname) == false){
			$('#nickname_check').text("??????????????? ????????? ??? ????????????.");
			$('#nickname_check').css('color', 'red');
			return;
		} 
		
		$.ajax({
			url : 'nicknameCheck',
			type : 'post',
			data : {
				nickname
			},
			success : function(data) {
				if (data == 1) {
					$("#nickname_check").text("???????????? ??????????????????.");
					$('#nickname_check').css('color', 'red');
				} else {
					$('#nickname_check').text('??????????????? ?????????');
					$('#nickname_check').css('color', 'green');
					nicknameRuleResult = true;
				}	
			}, error : function() {
					alert("Error");
			}
		});
	});

	// ????????? ???????????? ?????????	
	$('#email').on('change',function() {
		const value = $(this).val();
		  
		if(value == 1 || value == 0) {
		  $('#user_emailDomain').val("");
		  $('#user_emailDomain').prop("readonly",false);
		  if (value == 1) {
			$('#user_emailDomain').focus();
			return;
		  }
		  return;
		}
		$('#user_emailDomain').prop("readonly",true);
		$('#user_emailDomain').val(value);
	})

	// ????????? ????????? ?????? ????????? ??????	
	$("#user_email").blur(function() {
		const email = $('#user_email').val();
		emailRuleResult = false;

		if (email.length == 0 ) {
		  $('#email_check').text('????????? ???????????? ??????????????????.');
		  $('#email_check').css('color', 'red');
		  return;
		} else if (emailRule1.test(email) == false) {
			$('#email_check').text('?????? ????????? ?????????.');
			$('#email_check').css('color', 'red');
		} else {
			$('#email_check').text("");
			emailRuleResult = true;
		}
	});		
	
	// ????????? ????????? ?????? ????????? ??????	
	$("#user_emailDomain").blur(function() {
		const email = $('#user_emailDomain').val();
		emailDomainRuleResult = false;

		if (email.length == 0 ) {
		  $('#email_check').text('???????????? ??????????????????.');
		  $('#email_check').css('color', 'red');
		  return;
		} else if (emailRule2.test(email) == false || email.includes('.') == false) {
			$('#email_check').text('?????? ????????? ?????????.');
			$('#email_check').css('color', 'red');
		} else {
			$('#email_check').text("");
			emailDomainRuleResult = true;
		}
	});		
	
	// ?????? ?????????(-) ??????	
	function autoHypenTel(str) {
		str = str.replace(/[^0-9]/g, '');
		var tmp = '';

		if (str.substring(0, 2) == 02) {
		// ?????? ??????????????? ?????? 10??????????????? ???????????? ??? ????????? ???????????? ????????????
		  if (str.length < 3) {
		    return str;
		  } else if (str.length < 6) {
		  	  tmp += str.substr(0, 2);
		      tmp += '-';
		      tmp += str.substr(2);
		      return tmp;
	      } else if (str.length < 10) {
	      	  tmp += str.substr(0, 2);
		      tmp += '-';
		      tmp += str.substr(2, 3);
		      tmp += '-';
	          tmp += str.substr(5);
	          return tmp;
		  } else {
		      tmp += str.substr(0, 2);
		      tmp += '-';
		      tmp += str.substr(2, 4);
		      tmp += '-';
		      tmp += str.substr(6, 4);
		      return tmp;
		  }
		} else {
		    // ????????? ??? ?????? ?????? ???????????? ??? ??????
		    if (str.length < 4) {
		      return str;
		    } else if (str.length < 7) {
		        tmp += str.substr(0, 3);
		        tmp += '-';
		        tmp += str.substr(3);
		        return tmp;
		    } else if (str.length < 11) {
		        tmp += str.substr(0, 3);
		        tmp += '-';
		        tmp += str.substr(3, 3);
		        tmp += '-';
		        tmp += str.substr(6);
		        return tmp;
		    } else {
		        tmp += str.substr(0, 3);
		        tmp += '-';
		        tmp += str.substr(3, 4);
		        tmp += '-';
		        tmp += str.substr(7);
		        return tmp;
		    }
		}
		
		return str;
	}

	// ???????????? ?????? ????????? ??????	
	$('#user_phoneNum').keyup(function (event) {
		event = event || window.event;
		cellphoneNoRuleResult = false;
		var _val = this.value.trim();
		this.value = autoHypenTel(_val);
		
	});
	
	// ???????????? ????????? ?????? ????????? ??????	
	$("#user_phoneNum").blur(function() {
		const phoneNum = $('#user_phoneNum').val();
		cellphoneNoRuleResult = false;

		if (phoneNum.length == 0 ) {
		  $('#phoneNum_check').text('??????????????? ??????????????????.');
		  $('#phoneNum_check').css('color', 'red');
		  return;
		} else if (cellphoneNoRule.test(phoneNum) == false) {
			$('#phoneNum_check').text('?????? ????????? ?????????.');
			$('#phoneNum_check').css('color', 'red');
		} else {
			$('#phoneNum_check').text("");
			cellphoneNoRuleResult = true;
		}
	});	
	
	function JoinForm__checkAndSubmit(form) {	
		if (idRuleResult == false) return $('#user_id').focus();
		else if (pwRuleResult == false) return $('#user_pw').focus();
		else if (pwRule2Result == false) return $('#user_pwReal').focus();
		else if (nameRuleResult == false) return $('#user_name').focus();
		else if (nicknameRuleResult == false) return $('#user_nickname').focus();
		else if (emailRuleResult == false) return $('#user_email').focus();
		else if (emailDomainRuleResult == false) return $('#user_emailDomain').focus();
		else if (cellphoneNoRuleResult == false) return $('#user_phoneNum').focus();
		
		form.loginPwReal.value = sha256(form.loginPw.value);
		form.loginPw.value = "";
		
		if (idRuleResult == true && pwRuleResult == true && nameRuleResult == true && nicknameRuleResult == true && emailRuleResult == true && emailDomailRuleResult == true && cellphoneNoRuleResult == true) {
			form.submit();
			JoinForm__checkAndSubmitDone = true;
			return;
		} 
	}
</script>   

      </div>
    </div>
  </div>
  <div class="hidden lg:flex items-center justify-center bg-blue-100 flex-1 h-screen sticky top-0 left-0">
    <div class="max-w-md transform duration-200 hover:scale-110 cursor-pointer">
      <div class="">
        <img class="" src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FSo8Ix%2Fbtq1FA0n54L%2FeKnOyBlr436oPK3p8w9fs0%2Fimg.png" alt="">
      </div>
    </div>
  </div>
</div>

<%@ include file="../part/foot.jspf"%>

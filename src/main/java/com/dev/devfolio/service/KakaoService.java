package com.dev.devfolio.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class KakaoService {
	
	private String afterUrl = "http://localhost:8090/usr/kakao/doLogin";

	public String getAccessToken(String authorize_code, String kakao_restApi_key) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		try {
			URL url = new URL(reqURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id="+kakao_restApi_key); // 본인이 발급받은 key
			sb.append("&redirect_uri="+afterUrl); // 본인이 설정해 놓은 경로
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			// 이 로직 실행을 위해서 pom.xml에 Gson 라이브러리를 추가한 것입니다.
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return access_Token;
	}

	public HashMap<String, Object> getUserInfo(String access_Token) {
		HashMap<String, Object> userInfo = new HashMap<>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");

			// 요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			// properties는 프로필 정보(닉네임/프로필 사진) (필수동의항목)
			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			// kakao_account는 이메일, 성별, 연령대 등의 정보 (선택동의항목)
			JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
			
			userInfo.put("age_range", kakao_account.get("age_range"));
			
			// 정보제공에 동의 X '"email_needs_agreement":true'
			// 정보제공에 동의 O '"email_needs_agreement":false'
			boolean email_needs_agreement = kakao_account.getAsJsonObject().get("email_needs_agreement").getAsBoolean();

			String email = "이메일 동의 항목에 사용자 동의 필요";
			if (email_needs_agreement == false) {
				email = kakao_account.getAsJsonObject().get("email").getAsString();
			}
			
			String emailDomain = email.split("\\@")[1];
			email = email.split("\\@")[0];
			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
			String thumbnail_image = properties.getAsJsonObject().get("thumbnail_image").getAsString();

			userInfo.put("nickname", nickname);
			userInfo.put("email", email);
			userInfo.put("emailDomain", emailDomain);
			userInfo.put("profile_image", profile_image);
			userInfo.put("thumbnail_image", thumbnail_image);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return userInfo;
	}

	// 카카오계정 로그아웃
	public void kakaoLogout(String access_Token) {
		// 우선 session에 저장되어있는 access_Token값을 가져와 access_Token값을 통해 로그인되어있는 사용자를 확인합니다.
		String reqURL = "https://kapi.kakao.com/v1/user/logout";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String result = "";
			String line = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println(result);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 카카오계정 연결 해제
	public void kakaoUnlink(String access_Token) {
		// 우선 session에 저장되어있는 access_Token값을 가져와 access_Token값을 통해 로그인되어있는 사용자를 확인합니다.
		String reqURL = "https://kapi.kakao.com/v1/user/unlink";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String result = "";
			String line = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 나에게 메시지 보내기
	public boolean isSendMessage(String access_Token) {
		// 마찬가지로 access_Token값을 가져와 access_Token값을 통해 로그인되어있는 사용자를 확인합니다.
//		String reqURL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
		
		// custom Message
		String reqURL = "https://kapi.kakao.com/v2/api/talk/memo/send";
		try {
			URL url = new URL(reqURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true); // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();

			sb.append("template_id=" + "55278");

			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			// response body : 0이면 성공
			System.out.println("response body : " + result);	

			bw.close();
			br.close();

			if (responseCode == 200) {
				return true;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

}

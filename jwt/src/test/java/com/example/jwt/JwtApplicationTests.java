package com.example.jwt;

import com.example.jwt.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;

@SpringBootTest
class JwtApplicationTests {

	@Autowired
	private JwtService jwtService;
	@Test
	void contextLoads() {
	}

	@Test
	void tokenCreate(){
		var claims=new HashMap<String,Object>();//claims=유저 정보
		claims.put("user_id",923);//key & value
		var expiredAt= LocalDateTime.now().plusMinutes(10);//10분동안만 유효한 토큰

		var jwtToken=jwtService.create(claims,expiredAt);
		System.out.println(jwtToken);//eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo5MjMsImV4cCI6MTcxMTk4NDUzOH0.gt0iUhXBofWU4eKYg4LZvpyrqzAUhWpihn-PEqcqEFo
		//위 토큰 값 jwt 사이으 Encoded에 입력
	}

	@Test
	void tokenValidation(){
		var token="eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo5MjMsImV4cCI6MTcxMTk4NTkzOX0.Cz9HxCLuojzSXWYEDUBWB7az5f4tokZMW7g9yjGRUDo";
		jwtService.validation(token);
	}
}

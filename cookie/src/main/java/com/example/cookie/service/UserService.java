package com.example.cookie.service;

import com.example.cookie.db.UserRepository;
import com.example.cookie.model.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    //login logic
    public String  login(LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        var id=loginRequest.getId();//이름
        var pw=loginRequest.getPassword();//비밀번호

        var optionalUser=userRepository.findByName(id);//login 시, user의 id(이름), pw 입력했기에 id(이름)를 인자로 함

        if(optionalUser.isPresent()){
            var userDto=optionalUser.get();
            if(userDto.getPassword().equals(pw)){
                return userDto.getId();//로그인 성공 시, 사용자의 id 리턴

                /*//로그인 성공 시, cookie에 해당 정보 유저 정보 저장
                var cookie=new Cookie("authorization-cookie",userDto.getId());
                cookie.setDomain("localhost");//ex) naver.com
                cookie.setPath("/");//모든 경로 root에서  사용 가능
                cookie.setHttpOnly(true);//쿠키 접근 막음: 자바스크립트에서 해당 값(쿠키)를 읽을 수 없도록 보안 처리함 즉, http 통신할 때만 사용할 수 있음
                cookie.setSecure(true);//http 에서만 사용 가능 즉, 서버가 http로 사용해야만 클라이언트가 사용할 수 있음, API 통신이 HTTP가 아닌 HTTPS에서만 해당 쿠키 전송하도록 설정
                cookie.setMaxAge(-1);//session처럼 연결된 동안에만 사용 가능
                httpServletResponse.addCookie(cookie);//컨트롤러에서 넘어온 httpresponse에 해당 쿠기를 추가*/
            }
        }else{
            throw new RuntimeException("User Not Found");
        }
        return null;
    }
}

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
    public void login(LoginRequest loginRequest, HttpServletResponse httpServletResponse){
        var id=loginRequest.getId();
        var pw=loginRequest.getPassword();

        var optionalUser=userRepository.findByName(id);//login 시, user의 id, pw 입력했기에 id를 인자로 함

        if(optionalUser.isPresent()){
            var userDto=optionalUser.get();
            if(userDto.getPassword().equals(pw)){
                //cookie에 해당 정보 유저 정보 저장
                var cookie=new Cookie("authorization-cookie",userDto.getId());
                cookie.setDomain("localhost");//ex) naver.com
                cookie.setPath("/");//모든 경로 root에서  사용 가능
                cookie.setMaxAge(-1);//session처럼 연결된 동안에만 사용 가능
                httpServletResponse.addCookie(cookie);//컨트롤러에서 넘어온 httpresponse에 해당 쿠기를 추가
            }
        }else{
            throw new RuntimeException("User Not Found");
        }

    }
}

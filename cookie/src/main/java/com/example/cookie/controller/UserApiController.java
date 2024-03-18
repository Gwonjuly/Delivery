package com.example.cookie.controller;


import com.example.cookie.db.UserRepository;
import com.example.cookie.model.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserApiController {

    private final UserRepository userRepository;//controller <- repository..? service 안거침

    @GetMapping("/me")//pathvariable (x)
    public UserDto me(HttpServletRequest httpServletRequest,
                      @CookieValue(name = "authorization-cookie",required = false) String authorizationCookie//default=true, false로 하여금 쿠기가 없을 때 에러 발생하도록 함
    ){//쿠기 값 확인(받음)
        log.info("authorizationCooke:{}",authorizationCookie);//쿠키 값(value) 확인 -> 491a56ce-2df7-45f2-95bf-5ec5775f31fb
        var optionalUserDto=userRepository.findById(authorizationCookie);
        return optionalUserDto.get();//-> {"id":"491a56ce-2df7-45f2-95bf-5ec5775f31fb","name":"홍길동","password":"1234"}
        /*var cookies=httpServletRequest.getCookies();
        if(cookies !=null){
            for(Cookie cookie:cookies){
                log.info("key:{}, value:{}",cookie.getName(), cookie.getValue());//쿠키는 key:value 한 쌍, key=authorization-cookie
            }
        }*/
    }

    @GetMapping("/me2")//talend api로 진행
    public UserDto me2( //header에 "authorization"이라는 header가 있으면 findById에서 그 header 값을 가지고 리턴
            @RequestHeader(name = "authorization",required = false) String authorizationHeader
    ){
        log.info("authorizationHeader:{}",authorizationHeader);
        var optionalUserDto=userRepository.findById(authorizationHeader);
        return optionalUserDto.get();
    }
}

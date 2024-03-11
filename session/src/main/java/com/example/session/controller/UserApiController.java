package com.example.session.controller;

import com.example.session.model.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @GetMapping("/me")//자신의 정보 불러오기
    public UserDto me(HttpSession httpSession){//세션에 연결된 사용자
        var userObject=httpSession.getAttribute("USER");
        if(userObject==null)
            return null;
        else{
            var userDto=(UserDto)userObject;
            return userDto;
        }
    }
}

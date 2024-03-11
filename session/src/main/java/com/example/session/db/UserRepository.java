package com.example.session.db;

import com.example.session.model.UserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRepository {

    private List<UserDto> userList=new ArrayList<>();

    @PostConstruct//service 빈이 초기화됐을 때, 해당 메서드 호춢
    public void init(){
        userList.add(new UserDto("유저","1234"));
        userList.add(new UserDto("유저2","1234"));
        userList.add(new UserDto("유저3","1234"));
    }

    public Optional<UserDto> findByName(String name){
        /*return userList.stream()
                .filter(it->{
                    return it.getName().equals(name);
                }).findFirst();*/
        return userList.stream()
                .filter(it->it.getName().equals(name)).findFirst();//lamda

    }
}

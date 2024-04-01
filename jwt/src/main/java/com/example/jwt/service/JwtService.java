package com.example.jwt.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private static String secretKey="";

    /*
    jwt token: Header, Payload, Signature 세 부분으로 구성
    토큰: 스트링, key: jwt 토큰을 signWith 시 사용
     */
    public String create(Map<String,Object> clames, LocalDateTime expireAt){
        var key= Keys.hmacShaKeyFor(secretKey.getBytes());//커스텀 방식으로 키 생성

        //local date time -> date 형식으로 변환: setExpiration의 인자가 data 형식임
        var _expireAt= Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.ES256)
                .setClaims(clames)
                .setExpiration(_expireAt)
                .compact();//string 타입 리턴
    }

    public void validation(){}
}

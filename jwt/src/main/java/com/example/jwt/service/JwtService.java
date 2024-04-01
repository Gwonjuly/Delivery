package com.example.jwt.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class JwtService {

    private static String secretKey="java11SpringBootJWTTokenIssueMethod";

    /*
    jwt token: Header, Payload, Signature 세 부분으로 구성
    토큰: 스트링, key: jwt 토큰을 signWith 시 사용
    header-jwt header, claims-jwt claims(body)
     */
    public String create(Map<String,Object> claims, LocalDateTime expireAt){
        var key= Keys.hmacShaKeyFor(secretKey.getBytes());//커스텀 방식(secret)으로 키 생성

        //local date time -> date 형식으로 변환: setExpiration의 인자가 data 형식임
        var _expireAt= Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)//key
                .setClaims(claims)//유저 정보
                .setExpiration(_expireAt)//토큰 유효시간
                .compact();//string 타입 리턴
    }

    //토큰 검증
    public void validation(String token){//클라이언트에서 넘어온 토큰
        var key=Keys.hmacShaKeyFor(secretKey.getBytes());

        var parser=Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        try{
            var result=parser.parseClaimsJws(token);//토큰 파싱
            result.getBody().entrySet().forEach(value->{
                log.info("key:{}, value:{}",value.getKey(),value.getValue());
            });
        }catch (Exception e){
            if(e instanceof SignatureException){
                throw new RuntimeException("JWT Token Not Valid Exception");
            }else if(e instanceof ExpiredJwtException){
                throw new RuntimeException("JWT Token Expired Exception");
            }else
                throw new RuntimeException("JWT Token Validation Exception");
        }

    }
}

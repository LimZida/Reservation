package service;

import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/***
*
* <p> Title : JwtTokenProviderService
*
*
* <p> Description : createToken(String Email) => 해당 이메일에 대한 토큰을 만드는 함수
*					validateToken(String jwtToken) => 토큰의 유효기간을 검사하는 함수
*					getInformation(String token) =>	암호화된 토큰의 정보를 해독하는 함수
*
*
* <p> Last Update Date : 2022.11.14
*
*
* @author LHY
*
*/

@Component
public class JwtTokenProviderService {
    //암복호화에 사용되는 키 값
    private static String secretKey = Base64.getEncoder().encodeToString("암호화키".getBytes());

    public String createToken(Integer userSeqPK) {
        Claims claims = Jwts.claims();
        claims.put("userSeqPK", userSeqPK);
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims) // 데이터를 넣어 줍니다
                .setIssuedAt(now)   // 토큰 발행 일자
                .setExpiration(new Date(now.getTime() + (1000L * 60 * 60 * 24 * 30))) // 유효 기간 30일
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘과 암복호화에 사용할 키
                .compact(); // Token 생성
    }

 // Jwt Token의 유효성 및 만료 기간 검사
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // Jwt Token에서 데이터를 전달
    public Claims getInformation(String token) {
        Claims claims =Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims;
    }
}
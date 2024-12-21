package com.codeit.todo.common.config;

import com.codeit.todo.common.exception.jwt.JwtException;
import com.codeit.todo.common.exception.payload.ErrorStatus;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final int UNAUTHORIZED = 401;
    private static final long TOKEN_VALID_MILLI_SECONDS =1000L*60*60; //1시간
    private static final int COOKIE_VALID_SECONDS = 60*60*24; //24시간

    

    @Value("${jwtpassword.source}")
    private String secretKeySource;
    private String secretKey;

    @PostConstruct
    public void setUp(){
        secretKey = Base64.getEncoder()
                .encodeToString(secretKeySource.getBytes());
    }

    private final UserDetailsService userDetailsService;

    public String createToken(String email){
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+ TOKEN_VALID_MILLI_SECONDS))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public String resolveToken(HttpServletRequest request){
        String token = request.getHeader("token");
        return token;
    }

    public boolean validToken(String jwtToken){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken)
                    .getBody();
            Date now = new Date();
            return claims.getExpiration().after(now);
        } catch (ExpiredJwtException e) {
            throw new JwtException(ErrorStatus.toErrorStatus("JWT 토큰이 만료되었습니다.", UNAUTHORIZED));
        } catch (SignatureException e) {
            throw new JwtException(ErrorStatus.toErrorStatus("JWT 토큰의 서명이 유효하지 않습니다. 시크릿 키가 변경되었을 수 있습니다.", UNAUTHORIZED));
        } catch (MalformedJwtException e) {
            throw new JwtException(ErrorStatus.toErrorStatus("잘못된 JWT 형식입니다.", UNAUTHORIZED));
        } catch (UnsupportedJwtException e) {
            throw new JwtException(ErrorStatus.toErrorStatus("지원되지 않는 JWT 타입입니다.", UNAUTHORIZED));
        } catch (Exception e) {
            throw new JwtException(ErrorStatus.toErrorStatus("JWT 토큰이 유효하지 않습니다.", UNAUTHORIZED));
        }
    }

    public Authentication getAuthentication(String jwtToken){
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserEmail(jwtToken));
        return new UsernamePasswordAuthenticationToken(userDetails, " ", userDetails.getAuthorities());
    }

    public String getUserEmail(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }


    public ResponseCookie createResponseCookie(String token) {
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(COOKIE_VALID_SECONDS)
                .sameSite("None")
                .domain(".solidtodo.shop")
                .build();
        return cookie;
    }
}

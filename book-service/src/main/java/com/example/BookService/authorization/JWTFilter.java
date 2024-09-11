package com.example.bookservice.authorization;

import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTFilter extends GenericFilterBean {

    private final JWTProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = getTokenFromRequest(httpRequest);

        log.info("Processing request: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());
        log.info("Extracted token: {}", token);

        if (token != null && jwtProvider.validateToken(token)) {
            Claims claims = jwtProvider.getClaims(token);
            log.info("Token is valid. Claims: {}", claims);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(), null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.warn("Token is invalid or missing.");
        }
        chain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(JWTConstant.HEADER_STRING);
        if (StringUtils.hasText(bearer) && bearer.startsWith(JWTConstant.TOKEN_PREFIX)) {
            return bearer.substring(JWTConstant.TOKEN_PREFIX.length());
        }
        return null;
    }
}
package com.cybersoft.osahaneat.security;

import com.cybersoft.osahaneat.utils.JwtUtilsHeplers;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class CustomFilterJwt extends OncePerRequestFilter {

    @Autowired
    JwtUtilsHeplers jwtUtilsHeplers;
    private Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            boolean isSuccess = jwtUtilsHeplers.verifyToken(jwt);
            if (isSuccess) {
                String data = jwtUtilsHeplers.getDataFromToken(jwt);

                // tạo chứng thực để truy cập link
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken("", "", new ArrayList<>());

                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(token);
                System.out.println("hello: " + data);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        filterChain.doFilter(request, response);
//        System.out.println("hello");
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}

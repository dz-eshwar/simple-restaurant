package com.britr.simpleRestaurant.config;

import com.britr.simpleRestaurant.constants.SimpleRestaurantConstants;
import com.britr.simpleRestaurant.service.UserService;
import com.britr.simpleRestaurant.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    public UserService userService;

    @Autowired
    public JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("filter is working");
        String header = fetchHeader(request);
        System.out.println(header);
        if(StringUtils.isBlank(header)||!header.startsWith(SimpleRestaurantConstants.BEARER_STRING)){
            filterChain.doFilter(request,response);
            return;
        }
        else{
            String token = fetchToken(header);
            System.out.println(token);
            if(StringUtils.isNotBlank(token)){
                String userName = fetchUserName(token);
                System.out.println(userName);
                if(StringUtils.isNotBlank(userName) &&
                        SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = userService.loadUserByUsername(userName);

                    if(jwtUtil.validateToken(token,userDetails)){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }

        }
        filterChain.doFilter(request,response);
    }

    public String fetchHeader(HttpServletRequest request){
        System.out.println(request.getHeader(SimpleRestaurantConstants.HEADER_STRING));
        if(StringUtils.isNotBlank(request.getHeader(SimpleRestaurantConstants.HEADER_STRING))){
            return request.getHeader(SimpleRestaurantConstants.HEADER_STRING);
        }
        else{
            return "";
        }
    }

    public String fetchToken(String header){
        if(header.startsWith(SimpleRestaurantConstants.BEARER_STRING)){
            return header.replace(SimpleRestaurantConstants.BEARER_STRING,"");
        }
        else{
            return "";
        }
    }

    public String fetchUserName(String token){
        return jwtUtil.getUsernameFromToken(token);
    }
}

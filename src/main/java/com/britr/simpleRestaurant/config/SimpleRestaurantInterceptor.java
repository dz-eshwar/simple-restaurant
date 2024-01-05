package com.britr.simpleRestaurant.config;

import com.britr.simpleRestaurant.model.UserDetail;
import com.britr.simpleRestaurant.repository.UserDetailRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class SimpleRestaurantInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    public UserDetailRepository userDetailRepository;

    @Autowired
    public SecurityFilter filter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = filter.fetchHeader(request);
        if(StringUtils.isNotBlank(header)){
            String token = filter.fetchToken(header);
            if(StringUtils.isNotBlank(token)){
                String userName = filter.fetchUserName(token);
                if(StringUtils.isNotBlank(userName)){
                    UserDetail userDetail = userDetailRepository.findByUsername(userName);
                    request.setAttribute("$",userDetail.getId());
                    return true;
                }
            }
        }
       return false;
    }
}

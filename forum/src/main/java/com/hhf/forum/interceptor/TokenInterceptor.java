package com.hhf.forum.interceptor;

import com.hhf.forum.entity.User;
import com.hhf.forum.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    UserMapper userMapper;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

       String token = request.getParameter("token");
        System.out.println("aaaaa="+token);
       User user=userMapper.whoami(token);

       if(user==null){
           throw  new RuntimeException("token无效");
       }

       UserContext.setUser(user);

        return true;
    }
}

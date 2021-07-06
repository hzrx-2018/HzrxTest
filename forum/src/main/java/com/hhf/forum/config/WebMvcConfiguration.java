package com.hhf.forum.config;

import com.hhf.forum.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    TokenInterceptor tokenInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration =registry.addInterceptor(tokenInterceptor);

        interceptorRegistration.addPathPatterns("/api/**")
                .excludePathPatterns("/error",
                        "/api/user/registe",
                        "/api/user/login",
                        "/api/post/posts",
                        "/api/post/find",
                        "/api/image/upload",
                        "/api/image/download",
                        "/api/user/recentUser",
                        "/api/user/admLogin");
    }
}

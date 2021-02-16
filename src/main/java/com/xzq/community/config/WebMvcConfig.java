package com.xzq.community.config;

import com.xzq.community.controller.interceotor.LoginRequiredInterceptor;
import com.xzq.community.controller.interceotor.LoginTicketInterceptor;
import com.xzq.community.entity.LoginTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;
    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginTicketInterceptor)
        .excludePathPatterns("/**/*.css","/**/*.js","/**/*.png","/**/*.png","/**/*.jpeg");

        registry.addInterceptor(loginRequiredInterceptor).excludePathPatterns("/**/*.css","/**/*.js","/**/*.png","/**/*.png","/**/*.jpeg");
    }

}

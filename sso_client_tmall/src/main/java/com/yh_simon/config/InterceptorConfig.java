package com.yh_simon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.yh_simon.interceptor.TMallInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] patterns={"/tmall"};
        registry.addInterceptor(new TMallInterceptor()).addPathPatterns(patterns);
    }
}

package com.yh_simon.config;

import com.yh_simon.interceptor.TaobaoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] patterns={"/taobao"};
        registry.addInterceptor(new TaobaoInterceptor()).addPathPatterns(patterns);
    }
}

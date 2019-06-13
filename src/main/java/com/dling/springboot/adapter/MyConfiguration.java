package com.dling.springboot.adapter;


import com.dling.springboot.config.AppProperties;
import com.dling.springboot.interceptor.DataInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfiguration extends WebMvcConfigurerAdapter {

//    @Autowired
//    private AppProperties appProperties;

    @Bean
    public HandlerInterceptor getDataInterceptor(){
        return new DataInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        super.addInterceptors(registry);
//        registry.addInterceptor(new DataInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(new DataInterceptor(appProperties)).addPathPatterns("/**");
//        registry.addInterceptor(getDataInterceptor()).addPathPatterns("/**");
    }
}

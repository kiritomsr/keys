package com.msr.keys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer(){
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {

                registry.addViewController("/personal").setViewName("person");
                registry.addViewController("/error").setViewName("404");
            }
        };
        return webMvcConfigurer;
    }


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginHandlerInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("../**","../static/*",
//                        "/","/userlogin","/login","/sign",
//                        "/register.html","/userreg","/toReg",
//                        "/usercheckUsername","/usercheckEmail",
//                        "/mainnull",
//                        "/item", "/item.html","/itemLoad","/itemDetail","/itemselect/**",
//                        "/css/**","/js/**","/img/**","/norda/**",
//                        "/test/**"
//                );
//    }
}

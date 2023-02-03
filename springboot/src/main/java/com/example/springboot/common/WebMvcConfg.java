package com.example.springboot.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: yy
 * @Date: 2020/1/7 10:23
 * @Version 1.0
 * 过去使用 SpringMVC 时，如果访问一个页面，必须要写相应的 Controller 类。
 * 而 SpringBoot 要实现这个需求只需要直接在 MVC 配置中重写 addViewControllers 方法配置映射关系即可，不需要在写相应的 Controller 类。
 *
 */
@Configuration
public class WebMvcConfg implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/loginNew").setViewName("loginNew");
        registry.addViewController("/registerNew").setViewName("registerNew");

        registry.addViewController("/sessionResult").setViewName("sessionResult");
    }
}

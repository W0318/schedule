package com.example.springboot.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2

/**
 * @Author: yy
 * @Date: 2019/12/31 16:37
 * @Version 1.0
 */
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // 只有在类上使用@Api注解标注并且在方法上使用@ApiOperation注解才会暴露给swagger，这种方式没有包名的限制，可以将需要暴露的接口分散到各个包里
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build();
    }
}

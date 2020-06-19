package com.joseph.ipmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    public static final String IP_TAG = "IP Endpoints";

    @Bean
    public Docket employeeApi(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.joseph.ipmanager.controllers"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaInfo())
                .tags(new Tag(IP_TAG,"IP Endpoints"));
    }

    private ApiInfo metaInfo(){
        List<VendorExtension> vendorExtensions = new ArrayList<>();

        ApiInfo apiInfo = new ApiInfo("IP Management",
                "An API for managing IP Address and IP Pools",
                "1.0",
                "Terms of Service",
                new Contact("Joseph Bonnici", ""
                        ,"joebonnici93@gmail.com"),
                "", "",vendorExtensions);
        return apiInfo;
    }

}


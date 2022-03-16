package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration// ioc등록
public class WebMvcConfig implements WebMvcConfigurer { //web설정파일

    @Value("${file.path}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry
                .addResourceHandler("/upload/**") //jps에서 /upload/**패턴이 나오면
                .addResourceLocations("file:///"+uploadFolder)//이코드 발동
                .setCachePeriod(60*10*6) //1시간동안 이미지 캐싱
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}

package com.zz.crow.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //跳转的地址
        String urlPath = "/auth/member/register/page";

        //绑定的试图名
        String viewName = "member-reg";

        registry.addViewController(urlPath).setViewName(viewName);
        registry.addViewController("/auth/member/center/page").setViewName("member-center");
        registry.addViewController("/auth/member/my/crowd").setViewName("member-crowd");
        registry.addViewController("/auth/member/my/crowd").setViewName("member-crowd");
    }


}

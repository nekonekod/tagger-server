package com.nekonekod.tagger.taggerserver;

import com.nekonekod.tagger.taggerserver.web.controller.ProcessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author duwenjun
 * @date 2018/1/11
 */
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ProcessInterceptor()).addPathPatterns("/**");
    }

}

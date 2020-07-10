package com.vivid.common.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//import com.vivid.common.interceptor.LoggerInterceptor;



@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	//@Autowired
    //private LoggerInterceptor loggerInterceptor;

    //@Override
    //public void addInterceptors(InterceptorRegistry registry){
    //    registry.addInterceptor(loggerInterceptor);
    //}
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule(); 
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(0,jackson2HttpMessageConverter);
    }

}

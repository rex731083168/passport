package cn.ce.passport.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.ce.passport.common.filter.LoginFilter;

/**
 * Created by fuqy on 2016/1/14.
 */
@Configuration
public class FilterConfig {

//    @Autowired
//    private RedisService codisClient;
//2017.1.2 fuqy

    @Bean
    FilterRegistrationBean loginFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        LoginFilter loginFilter = new LoginFilter();
 //       loginFilter.setCodisClient(codisClient);
        filterRegistrationBean.setFilter(loginFilter);
        Set<String> urlPatterns = new HashSet<>();
        urlPatterns.add("/*");
        filterRegistrationBean.addUrlPatterns();
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
  
}
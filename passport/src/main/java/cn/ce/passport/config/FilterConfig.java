package cn.ce.passport.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.ce.passport.common.filter.CorsFilter;

/**
 * Created by fuqy
 */
@Configuration
public class FilterConfig {

	@Bean
	FilterRegistrationBean loginFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		CorsFilter corsFilter = new CorsFilter();

		filterRegistrationBean.setFilter(corsFilter);
		Set<String> urlPatterns = new HashSet<>();
		urlPatterns.add("/*");
		filterRegistrationBean.addUrlPatterns();
		filterRegistrationBean.setUrlPatterns(urlPatterns);
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}

}

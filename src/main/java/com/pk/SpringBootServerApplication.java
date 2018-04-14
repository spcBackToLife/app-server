package com.pk;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.pk.config.SimpleCORSFilter;


@SpringBootApplication
@MapperScan("com.pk.mapper")
@EnableAutoConfiguration
// @EnableWebMvc 这个注释不能要！！！！
public class SpringBootServerApplication {
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    SimpleCORSFilter simpleCORSFilter = new SimpleCORSFilter();
    registrationBean.setFilter(simpleCORSFilter);
    List<String> urlPatterns = new ArrayList<>();
    urlPatterns.add("/*");
    registrationBean.setUrlPatterns(urlPatterns);
    return registrationBean;
  }


  public static void main(String[] args) {
    SpringApplication.run(SpringBootServerApplication.class, args);
  }
}

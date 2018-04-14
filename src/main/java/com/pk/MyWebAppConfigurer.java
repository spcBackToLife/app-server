package com.pk;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

  // http://localhost:8084/dynamic_image/Comimages/gJSj9mKbda7+dB+jOvIP+g==/image_201707062314080.jpg

  // private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/META-INF/resources/",
  // "classpath:/resources/", "classpath:/EngramFile/", "classpath:/public/"};
  //
  // @Override
  // public void addResourceHandlers(ResourceHandlerRegistry registry) {
  // registry.addResourceHandler("/EngramFile/**")
  // .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
  // }

  // @Override
  // public void addResourceHandlers(ResourceHandlerRegistry registry) {
  //
  // registry.addResourceHandler("/templates/**")
  // .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
  // registry.addResourceHandler("/static/**")
  // .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
  // registry.addResourceHandler("/uploadtt/**").addResourceLocations("file:///D:/WebFiles/upload/");
  // super.addResourceHandlers(registry);
  // }


}

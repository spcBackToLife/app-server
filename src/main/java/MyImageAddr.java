

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 自定义的图片路径
 *
 * @author pzr
 *
 */
@Configuration
public class MyImageAddr extends WebMvcConfigurerAdapter {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // addResourceHandler中的是访问路径，可以修改为其他的字符串
    // addResourceLocations中的是实际路径
    registry.addResourceHandler("/EngramFile/**").addResourceLocations("classpath:/EngramFile/");
    super.addResourceHandlers(registry);
  }
}

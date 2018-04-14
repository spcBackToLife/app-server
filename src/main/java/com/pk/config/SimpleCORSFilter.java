package com.pk.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangxiang on 17-3-15.
 */
public class SimpleCORSFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(SimpleCORSFilter.class);

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
	  System.out.println("进入filter");
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {}

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    logger.info("Initializing CORS filter...");
  }
}

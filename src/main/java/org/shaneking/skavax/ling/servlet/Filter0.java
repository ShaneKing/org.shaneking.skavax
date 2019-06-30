package org.shaneking.skavax.ling.servlet;

import org.shaneking.skavax.ling.servlet.http.HttpServletRequestWrapper0;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class Filter0 implements Filter {
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    if (servletRequest instanceof HttpServletRequest) {
      filterChain.doFilter(new HttpServletRequestWrapper0((HttpServletRequest) servletRequest), servletResponse);
    } else {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }
}

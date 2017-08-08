package org.zp.notes.spring.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 打印访问请求过滤器
 * @author Zhang Peng
 */
@WebFilter(filterName = "accessFilter", urlPatterns = "/*")
public class AccessFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        long startTime = System.currentTimeMillis();
        String requestURI = httpServletRequest.getQueryString() == null ? httpServletRequest.getRequestURI()
                : (httpServletRequest.getRequestURI() + "?" + httpServletRequest.getQueryString());

        // 提交给 Servlet 或者下一个 Filter
        chain.doFilter(httpServletRequest, httpServletResponse);

        long endTime = System.currentTimeMillis();

        logger.debug("{} 访问了 {}，总用时 {} 毫秒", httpServletRequest.getRemoteAddr(), requestURI, (endTime - startTime));
    }

    public void destroy() {

    }
}

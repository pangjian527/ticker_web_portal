package com.tl.ticker.web.config.filter;

import com.tl.ticker.web.common.Constant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pangjian on 16-12-1.
 */
@WebFilter(displayName = "securityFilter",urlPatterns = "*/portal/**")
public class SecurityFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addHeader("P3P: CP", "\"CAO PSA OUR\"");

        String home = request.getContextPath();
        String uri = request.getRequestURI();//当前访问的地址

        /* 静态资源不拦截 */
        if(uri.indexOf("/js/")>=0 ||uri.indexOf("/images/")>=0 ||uri.indexOf(".css")>=0 ||uri.indexOf(".ico")>=0){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (request.getSession().getAttribute(Constant.SESSION_USER) != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String indexUrl = home + "/portal";
        if (uri.equals(indexUrl)|| "/portal/login/submit".equals(uri) ||"/portal/register/submit".equals(uri) || "/portal/login".equals(uri)||"/portal/register".equals(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        System.out.println(indexUrl);
        response.sendRedirect(indexUrl);
    }

    @Override
    public void destroy() {

    }
}

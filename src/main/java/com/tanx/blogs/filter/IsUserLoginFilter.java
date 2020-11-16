package com.tanx.blogs.filter;

import com.tanx.blogs.pojo.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tanxiang
 * @version 1.0
 * @date 2020/10/20 下午7:50
 */
@WebFilter(filterName = "isUserLogin", urlPatterns = {"/home","/home.html","/"})
public class IsUserLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession().getAttribute("user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(user == null && !"anonymousUser".equals(authentication.getName())){
            response.sendRedirect("/logout");
            return;
        }

        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}

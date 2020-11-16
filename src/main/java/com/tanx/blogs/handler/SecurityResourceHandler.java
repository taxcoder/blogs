package com.tanx.blogs.handler;

import com.tanx.blogs.pojo.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * @author tanxiang
 * @version 1.0
 * @date 2020/10/20 下午6:46
 */

@Configuration("securityResourceHandler")
public class SecurityResourceHandler {
    public boolean hasRole(Authentication authentication,HttpServletRequest request,String role) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if(user == null || !user.getLoginName().equals(authentication.getName())){
            return false;
        }

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        for(GrantedAuthority authoritie: authorities){
            if(authoritie.getAuthority().contains(role)){
                return true;
            }
        }
        return true;
    }

    public boolean hasAuthority(Authentication authentication,HttpServletRequest request,String authority) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if(user == null || !user.getLoginName().equals(authentication.getName())){
            return false;
        }

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        for(GrantedAuthority authoritie: authorities){
            if(authoritie.getAuthority().contains(authority)){
                return true;
            }
        }
        return true;
    }
}

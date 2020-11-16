//package com.tanx.blogs.service.authorize;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import java.security.Principal;
//import java.util.Set;
//
///**
// * @author tanxiang
// * @version 1.0
// * @date 2020/10/19 下午9:04
// */
//@Service("authorizeService")
//public class AuthorizeService {
//
//    public boolean check(Authentication authentication, HttpServletRequest request){
//
//        System.out.println(authentication.getPrincipal());
//        System.out.println(request.getSession().getAttribute("user"));
////        System.out.println("principal:"+principal);
////        if(principal instanceof UserDetails){
////            System.out.println(((UserDetails)principal).getUsername());
////            System.out.println(((UserDetails)principal).getPassword());
////        }
////
////        if(principal instanceof Principal){
////            System.out.println(((Principal) principal).getName());
////        }
////
////        System.out.println(principal.);
//
////        if (principal != null && principal instanceof UserDetails) {
////            UserDetails user = (UserDetails) principal;
////            System.out.println("user:" + user);
////            //获取认证用户里的url列表
////            Set<SimpleGrantedAuthority> authorities = (Set<SimpleGrantedAuthority>) user.getAuthorities();
////            System.out.println("authorities:" + authorities);
////            //判断url列表里是否包含request请求的url
////            boolean contains = authorities.stream()
////                    .map(SimpleGrantedAuthority::getAuthority)
////                    .anyMatch(request.getRequestURI()::equals);
////            return contains;
////        }
//            return true;
//    }
//}

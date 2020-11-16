package com.tanx.blogs.handler;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.druid.support.json.JSONUtils;
import com.tanx.blogs.pojo.model.User;
import com.tanx.blogs.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


/**
 * 登录成功
 * @author tanxiang
 */
@Component
public class SuccessAuthenticationHandler implements AuthenticationSuccessHandler {


    private UserServiceImpl userService;

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public static SuccessAuthenticationHandler successAuthenticationHandler;

    @PostConstruct
    public void init(){
        successAuthenticationHandler = this;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {

        HttpSession session = httpServletRequest.getSession();
        User user = successAuthenticationHandler.userService.queryLoginName(authentication.getName());
        session.setAttribute("user",user);

        PrintWriter writer = httpServletResponse.getWriter();
        Map<String, Object> map = new HashMap<>(4);
        map.put("state", 1);
        map.put("message", "登录成功!");
        writer.write(JSONUtils.toJSONString(map));
    }
}

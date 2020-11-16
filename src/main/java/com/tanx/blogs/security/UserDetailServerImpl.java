package com.tanx.blogs.security;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import com.tanx.blogs.utils.VariableUtils;
import com.tanx.blogs.service.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author tan
 */
@Component
public class UserDetailServerImpl implements UserDetailsService {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        VariableUtils.loginName = loginName;

        com.tanx.blogs.pojo.model.User user = this.userService.queryLoginName(loginName);

        if (user == null) {
            throw new UsernameNotFoundException("用户名不能为空！");
        }

        VariableUtils.active = user.getActive();

        if (!user.getActive()) {
            user.setPassword("");
        }

        Collection<GrantedAuthority> authorities = authorities(user.getRank());
        return new User(loginName, this.passwordEncoder.encode(user.getPassword()), authorities);
    }

    private Collection<GrantedAuthority> authorities(boolean rank) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 判断当前的权限
        if (rank) {
            // 管理员权限赋予AUTHORITY_SIGN 和 ROLE_SUPER
            authorities.add(new SimpleGrantedAuthority("AUTHORITY_SIGN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_SUPER"));
        } else {
            // 非管理员权限赋予AUTHORITY_SIGN 和 ROLE_PLAIN
            authorities.add(new SimpleGrantedAuthority("AUTHORITY_SIGN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_PLAIN"));
        }
        return authorities;
    }
}

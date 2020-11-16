package com.tanx.blogs.config;

import org.springframework.context.annotation.Bean;
import com.tanx.blogs.handler.FailureAuthenticationHandler;
import com.tanx.blogs.handler.SuccessAuthenticationHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * @author tan
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                .antMatchers( "/", "/home", "/home.html", "/informal", "/footmark", "/journal", "/archives").permitAll()
                .antMatchers("/super/administrator/**" ).access("@securityResourceHandler.hasRole(authentication,request,'ROLE_SUPER')")
                .antMatchers("/super/operate/**" ).access("@securityResourceHandler.hasAuthority(authentication,request,'AUTHORITY_SIGN')")
                .antMatchers("/super/images/**" ).access("@securityResourceHandler.hasAuthority(authentication,request,'AUTHORITY_SIGN')")
                .antMatchers("/super/simple/**" ).access("@securityResourceHandler.hasRole(authentication,request,'ROLE_PLAIN')")
                .and()
                .formLogin()
                .loginPage("/home")
                .successHandler(new SuccessAuthenticationHandler())
                .failureHandler(new FailureAuthenticationHandler())
                .loginProcessingUrl("/login")
                .usernameParameter("loginName")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutSuccessUrl("/home.html")
                .invalidateHttpSession(true);
        http.csrf().disable();
    }

    @Bean
    HttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedPeriod(true);
        return firewall;
    }
}
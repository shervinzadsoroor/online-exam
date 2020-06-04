package com.shervin.maktabfinalproject.securityconfigs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //    @Autowired
//    UserDetailsService userDetailsService;

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    final MyUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //redirect
    @Autowired
    public void WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    //for authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    //for authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/static/css/header.css").permitAll()
                .antMatchers("/", "/account/login", "/account/signUp")
                .permitAll()
                .antMatchers("/account/**", "/manager/**", "/logout").hasRole("MANAGER")
                .antMatchers("/instructor/**", "/exam/**", "/logout").hasRole("INSTRUCTOR")
                .antMatchers("/collegian/**", "/logout").hasRole("COLLEGIAN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/account/login")
                .successHandler(authenticationSuccessHandler)
                .failureUrl("/login.html?error=true")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/account/login");

    }
}

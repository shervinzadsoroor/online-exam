package com.shervin.maktabfinalproject.securityconfigs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //    @Autowired
//    UserDetailsService userDetailsService;

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    MyUserDetailsService userDetailsService;

    //for authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    //redirect
    @Autowired
    public void WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }


    //for authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/account/register").hasAnyRole( "COLLEGIAN", "INSTRUCTOR")
//                .antMatchers("/", "/account/signUp", "/account/login").permitAll()
                .antMatchers("/", "/account/login", "/account/signUp")
                .permitAll()
                .antMatchers("/account/**", "/manager/**").hasRole("MANAGER")
                .antMatchers("/instructor/**", "/exam/**").hasRole("INSTRUCTOR")
                .antMatchers("/collegian/**").hasRole("COLLEGIAN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/account/login")
                .successHandler(authenticationSuccessHandler)
                .failureUrl("/login.html?error=true")
                .permitAll();

    }
}

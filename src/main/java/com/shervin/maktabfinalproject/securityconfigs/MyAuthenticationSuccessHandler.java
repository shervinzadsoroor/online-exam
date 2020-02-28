package com.shervin.maktabfinalproject.securityconfigs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String userName = authentication.getName();
        if (roles.contains("ROLE_MANAGER")) {
            httpServletResponse.sendRedirect("/manager/dashboard/" + userName);
        } else if (roles.contains("ROLE_INSTRUCTOR")) {
            httpServletResponse.sendRedirect("/instructor/dashboard/" + userName);
        } else if (roles.contains("ROLE_COLLEGIAN")) {
            httpServletResponse.sendRedirect("/collegian/dashboard/" + userName);
        }
    }

}
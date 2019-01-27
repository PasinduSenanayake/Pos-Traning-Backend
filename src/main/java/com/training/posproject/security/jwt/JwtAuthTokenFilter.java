package com.training.posproject.security.jwt;

import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.training.posproject.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger loggerObj = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        try {

            String jwt = getJwt(request);
            if (jwt != null && !jwt.equals("") && (tokenProvider.validateJwtToken(jwt))) {
                    String username = tokenProvider.getUserNameFromJwtToken(jwt);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            loggerObj.error("Can NOT set user authentication -> message: {}", e);
            response.setStatus(500);
        }


    }


    private String getJwt(HttpServletRequest request) {
        String authHeader = null;

        if(request.getCookies()!=null && request.getCookies().length > 0) {
                authHeader = Arrays.stream(request.getCookies())
                        .filter(c -> c.getName().equals("posAuthCookie"))
                        .findFirst()
                        .map(Cookie::getValue)
                        .orElse(null);
            }



        return authHeader;
    }
}
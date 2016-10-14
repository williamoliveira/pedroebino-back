package com.pin2.pedrobino.configurations.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiOriginsFilter extends OncePerRequestFilter {

    private static final String MAX_PRE_FLIGHT_CACHE_SECONDS = String.valueOf(60 * 60 * 24);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("called");

        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Access-Control-Allow-Origin", "*");

        if (request.getHeader("Access-Control-Request-Method") != null && request.getMethod().equals("OPTIONS")) {
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE, PUT, PATCH, HEAD");
            response.addHeader("Access-Control-Allow-Headers", "Origin, Content-Type, api_key, Accept, Authorization, X-Requested-With");
            response.addHeader("Access-Control-Max-Age", MAX_PRE_FLIGHT_CACHE_SECONDS);

            response.setStatus(HttpServletResponse.SC_OK);
        }
        else{
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {}
}
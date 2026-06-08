package com.bancocuscatlan.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKey extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ApiKey.class);
    private final String API_KEY = "BANCO-CUSCATLAN-2026-SECRET";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        long startTime = System.currentTimeMillis();

        if (request.getRequestURI().contains("/swagger") || request.getRequestURI().contains("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestKey = request.getHeader("X-API-KEY");

        if (API_KEY.equals(requestKey)) {
            filterChain.doFilter(request, response);
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Response: {} - Duration: {}ms", response.getStatus(), duration);
        } else {
            logger.warn("Acceso denegado: API Key inválida o ausente desde {}", request.getRemoteAddr());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Acceso denegado: API Key invalida o ausente");
        }
    }
}
package ru.zed.app.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RedirectAuthenticatedUserFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String requestURI = request.getRequestURI();

            if ("/LinkWorld/auth/login".equals(requestURI) || "/LinkWorld/auth/register".equals(requestURI)) {
                response.sendRedirect("/LinkWorld/user/account");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}

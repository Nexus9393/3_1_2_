package ru.kata.spring.boot_security.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(SuccessUserHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        logger.info("User authenticated: {}, roles: {}", authentication.getName(), roles);

        if (roles.contains("ADMIN")) {
            logger.info("Redirecting to /admin");
            response.sendRedirect("/admin");
        } else if (roles.contains("USER")) {
            logger.info("Redirecting to /user");
            response.sendRedirect("/user");
        } else {
            logger.warn("No recognized roles, redirecting to /login");
            response.sendRedirect("/login");
        }
    }
}
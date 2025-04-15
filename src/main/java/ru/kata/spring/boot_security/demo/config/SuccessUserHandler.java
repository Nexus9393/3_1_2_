package ru.kata.spring.boot_security.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import static org.springframework.security.core.authority.AuthorityUtils.authorityListToSet;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = Logger.getLogger(SuccessUserHandler.class.getName());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Set<String> roles = authorityListToSet(authentication.getAuthorities());
        logger.info("User authenticated: " + authentication.getName() + ", roles: " + roles);
        if (roles.contains("ADMIN")) {
            logger.info("Redirecting to /admin");
            response.sendRedirect("/admin");
        } else if (roles.contains("USER")) {
            logger.info("Redirecting to /user");
            response.sendRedirect("/user");
        } else {
            logger.warning("No recognized roles, redirecting to /login");
            response.sendRedirect("/login");
        }
    }
}
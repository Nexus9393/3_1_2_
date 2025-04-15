package ru.kata.spring.boot_security.demo.security;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.logging.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());

    private final UserRepository userRepo;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Attempting to load user by email: " + email);
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warning("User not found: " + email);
                    return new UsernameNotFoundException("User not found: " + email);
                });
        Hibernate.initialize(user.getRoles());
        logger.info("User found: " + email + ", roles: " + user.getRoles() + ", password hash: " + user.getPassword());
        return user;
    }
}

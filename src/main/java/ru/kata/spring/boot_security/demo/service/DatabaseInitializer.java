package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class.getName());

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public DatabaseInitializer(UserService userService, RoleRepository roleRepository, UserRepository userRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        // Очистка существующих данных
        logger.info("Clearing existing users and roles...");
        userRepository.deleteAll();
        roleRepository.deleteAll();

        // Инициализация ролей
        logger.info("Initializing roles...");
        Role adminRole = initializeRole("ADMIN");
        Role userRole = initializeRole("USER");

        // Инициализация пользователя admin
        logger.info("Initializing admin user...");
        initializeUser(
                "Admin",
                "admin@example.com",
                "admin123",
                Set.of(adminRole)
        );

        // Инициализация пользователя user
        logger.info("Initializing user...");
        initializeUser(
                "User",
                "user@example.com",
                "user123",
                Set.of(userRole)
        );
    }

    private Role initializeRole(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            logger.info("Creating role: " + roleName);
            role = new Role(roleName);
            roleRepository.save(role);
        } else {
            logger.info("Role already exists: " + roleName);
        }
        return role;
    }

    private void initializeUser(String name, String email, String password, Set<Role> roles) {
        if (userRepository.findByEmail(email).isEmpty()) {
            logger.info("Creating user: " + email);
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setRoles(new HashSet<>(roles));
            userService.addUser(user);
            logger.info("User created: " + email + " with roles: " + roles);
        } else {
            logger.info("User already exists: " + email);
        }
    }
}
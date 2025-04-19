package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import ru.kata.spring.boot_security.demo.model.Role;


public interface RoleService {
    List<Role> getAllRoles();
    Role findByName(String name);
}

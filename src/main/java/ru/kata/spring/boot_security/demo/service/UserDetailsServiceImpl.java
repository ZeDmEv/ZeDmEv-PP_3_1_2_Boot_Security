package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class.getName());

    private final PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        UserDetailsImpl user = new UserDetailsImpl(userOptional.get());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    public User findUserById(Long id) {
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElse(new User());
    }

    public User findUserByUsername(String username) {
        Optional<User> userFromDb = userRepository.findByUsername(username);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return new ArrayList<User>(userRepository.findAll());
    }

    public boolean saveUser(User user) {
        logger.info("save begins...");
        Optional<User> userByName = userRepository.findByUsername(user.getUsername());
        if (userByName.isPresent()) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public void editUser(User user) {
        Optional<User> userById = userRepository.findById(user.getId());
        if (userById.isPresent() && user.getPassword().equals(userById.get().getPassword())) {
            logger.info("user.getPassword() = " + user.getPassword());
            userRepository.save(user);
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    public void setRole(User user, String role) {
        if (role.equalsIgnoreCase("ADMIN")) {
            Set<Role> tmp = new HashSet<>();
            tmp.add(roleRepository.findByName("ROLE_ADMIN"));
            tmp.add(roleRepository.findByName("ROLE_USER"));
            user.setRoles(tmp);
            logger.info("Role set to: " + user.getRoles());
            logger.info("Role set to: " + user.getRolesToString());
        }
        if (role.equalsIgnoreCase("USER")) {
            user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));
            logger.info("Role set to: " + user.getRoles());
            logger.info("Role set to: " + user.getRolesToString());
        }
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_ADMIN"));
        roles.add(roleRepository.findByName("ROLE_USER"));
        return roles;
    }
}

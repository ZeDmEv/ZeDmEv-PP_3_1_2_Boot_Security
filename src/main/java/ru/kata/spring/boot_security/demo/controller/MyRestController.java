package ru.kata.spring.boot_security.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class MyRestController {
    private final Logger logger = Logger.getLogger(MyController.class.getName());
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private void setUserService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/user")
    public ResponseEntity<User> test(Authentication authentication){
        return new ResponseEntity<User>(userDetailsService.findUserByUsername(authentication.getName()),HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> usersList(){
        return new ResponseEntity<List<User>>(userDetailsService.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/getuser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return new ResponseEntity<User>(userDetailsService.findUserById(id),HttpStatus.OK);
    }

    @GetMapping("/getroles")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<List<Role>>(userDetailsService.getAllRoles(),HttpStatus.OK);
    }
    @PutMapping ("/edit")
    public ResponseEntity<HttpStatus> editUser(@RequestBody User user) {
        logger.info("Сработал PUT метод");
        logger.info(user.toString());
        userDetailsService.editUser(user);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
    @DeleteMapping ("/delete")
    public ResponseEntity<HttpStatus> deleteUser(@RequestBody User user) {
        logger.info("Сработал Delete метод");
        logger.info(user.toString());
        userDetailsService.deleteUser(user);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    @PutMapping ("/add")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        userDetailsService.saveUser(user);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
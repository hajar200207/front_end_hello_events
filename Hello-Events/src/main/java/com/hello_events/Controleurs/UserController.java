package com.hello_events.Controleurs;

import com.hello_events.DTO.UserDTO;
import com.hello_events.Entites.User;
import com.hello_events.Repositories.UserRepository;
import com.hello_events.Security.JwtAuth;
import com.hello_events.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtAuth jwtAuth;
@Autowired
private UserRepository  userRepository;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        user.setRole(User.Role.valueOf("USER"));
//        if (user.getRole() == User.Role.ADMIN && userService.adminExists()) {
//            return ResponseEntity.badRequest().body("An admin account already exists. Only one admin is allowed.");
//        }
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User foundUser = userService.findByUsername(user.getUsername());
        if (foundUser != null && new BCryptPasswordEncoder().matches(user.getPassword(), foundUser.getPassword())) {
            List<String> roles = Collections.singletonList(foundUser.getRole().name());
            String token = jwtAuth.generateToken(foundUser.getUsername(), roles);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", foundUser.getRole().name());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", user.getUsername());
            userInfo.put("role", user.getRole().name());
            userInfo.put("email", user.getEmail());

            return ResponseEntity.ok(userInfo);
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.status(404).body("User not found");
    }


//    @GetMapping("/all")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userService.findAllUsers();
//        return ResponseEntity.ok(users);
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")// nfss l7aja bnissba l USER
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }
}

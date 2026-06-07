package com.example.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthControl {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?>  signUp(@RequestBody User user) {

        if(userRepository.findByEmail(user.getEmail()) != null){
            return ResponseEntity.badRequest().body("Email already exists");
        }

        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){

        User existingUser = userRepository.findByEmail(user.getEmail());

        if(existingUser == null){
            return ResponseEntity.badRequest().body("Users not found");
        }

        if(!existingUser.getPassword().equals(user.getPassword())){
            return ResponseEntity.badRequest().body("Wrong password");
        }

        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/users")
    public Object getAllUser() {
        return userRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "Deleted";
    }
}

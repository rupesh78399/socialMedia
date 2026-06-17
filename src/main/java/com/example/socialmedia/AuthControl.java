package com.example.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

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

    @PutMapping("/profile-image/{email}")
    public ResponseEntity<?> updateProfileImage(@PathVariable String email , @RequestBody User request){

        User user = userRepository.findByEmail(email);

        if(user == null){
            return ResponseEntity.badRequest().body("User Not Found");
        }

        user.setImage(request.getImage());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email){

        User user = userRepository.findByEmail(email);

        if(user == null){
            return ResponseEntity.badRequest().body("User Not Found");
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/online")
    public void setOnline(@RequestParam String email){

        User user = userRepository.findByEmail(email);
        user.setOnline(true);
        userRepository.save(user);

    }

    @PostMapping("/offline")
    public ResponseEntity<?>  setOffline(@RequestParam String email){

        User user = userRepository.findByEmail(email);
        if(user == null){
            return ResponseEntity.badRequest().body("User not found");
        }

        try {
            user.setOnline(false);
            user.setLastSeen(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));

            userRepository.save(user);
            System.out.println("Saving Time = " + LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
            return ResponseEntity.ok("Offline updated");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public User getStatus(@RequestParam String email){

        return userRepository.findByEmail(email);
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

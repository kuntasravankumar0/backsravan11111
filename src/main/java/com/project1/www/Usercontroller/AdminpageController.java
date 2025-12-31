package com.project1.www.Usercontroller;

import com.project1.www.UserModel.Adminpagemodel;
import com.project1.www.UserRepository.Adminpagerepo;
import com.project1.www.dto.LoginRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Adminaprovel")
public class AdminpageController {

    @Autowired
    private Adminpagerepo userRepo;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Adminpagemodel user) {

        if (userRepo.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(400).body("Email already exists");
        }

        user.setStatus("PENDING");

        user.setCustomerId("ADM" + System.currentTimeMillis());

        userRepo.save(user);
        return ResponseEntity.ok("Registration successful - Wait for approval");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        Adminpagemodel user = userRepo.findByEmail(req.getEmail());

        if (user == null) return ResponseEntity.status(404).body("User not found");
        if (!user.getPassword().equals(req.getPassword())) return ResponseEntity.status(401).body("Invalid password");
        if (user.getStatus().equals("PENDING")) return ResponseEntity.status(403).body("Waiting for approval");
        if (user.getStatus().equals("REJECTED")) return ResponseEntity.status(403).body("Your account was rejected");

        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/pending")
    public List<Adminpagemodel> getPendingUsers() {
        return userRepo.findByStatus("PENDING");
    }

    @GetMapping("/all")
    public List<Adminpagemodel> getAllUsers() {
        return userRepo.findAll();
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveUser(@PathVariable Long id) {
        Adminpagemodel user = userRepo.findById(id).orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        user.setStatus("APPROVED");
        userRepo.save(user);
        return ResponseEntity.ok("User Approved");
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectUser(@PathVariable Long id) {
        Adminpagemodel user = userRepo.findById(id).orElse(null);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        user.setStatus("REJECTED");
        userRepo.save(user);
        return ResponseEntity.ok("User Rejected");
    }

    @GetMapping("/get/{customerId}")
    public ResponseEntity<?> getUser(@PathVariable String customerId) {
        Adminpagemodel user = userRepo.findByCustomerId(customerId);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        return ResponseEntity.ok(user);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<?> updateUser(
            @PathVariable String customerId,
            @RequestBody Adminpagemodel updatedData) {

        Adminpagemodel user = userRepo.findByCustomerId(customerId);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        user.setAdminname(updatedData.getAdminname());
        user.setEmail(updatedData.getEmail());
        user.setPassword(updatedData.getPassword());

        userRepo.save(user);

        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<?> deleteUser(@PathVariable String customerId) {
        Adminpagemodel user = userRepo.findByCustomerId(customerId);
        if (user == null) return ResponseEntity.status(404).body("User not found");

        userRepo.deleteByCustomerId(customerId);
        return ResponseEntity.ok("User deleted successfully");
    }
}

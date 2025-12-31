package com.project1.www.Usercontroller;

import com.project1.www.UserModel.usermodel;
import com.project1.www.UserRepository.userrepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class usercontroller {

    @Autowired
    private userrepository userRepo;

    @PostMapping("/create")
    public Object createUser(@Valid @RequestBody usermodel user) {

        // Check email exists
        if (userRepo.existsByUseremail(user.getUseremail())) {
            return Map.of(
                    "status", false,
                    "message", "User already exists (email)");
        }

        // Check number exists
        if (userRepo.existsByNumber(user.getNumber())) {
            return Map.of(
                    "status", false,
                    "message", "User already exists (number)");
        }

        // Generate unique random 12-digit customerId
        user.setCustomerId(generateUniqueCustomerId());

        // Save user
        usermodel saved = userRepo.save(user);

        return Map.of(
                "status", true,
                "message", "User created successfully",
                "customerId", saved.getCustomerId(),
                "data", saved);
    }

    @PostMapping("/create-simple")
    public Object createSimpleUser(@RequestBody Map<String, Object> body) {
        try {
            // Check if user already exists
            String email = body.get("useremail").toString();
            Long number = Long.parseLong(body.get("number").toString());

            if (userRepo.existsByUseremail(email)) {
                return Map.of(
                        "status", false,
                        "message", "User already exists with this email");
            }

            if (userRepo.existsByNumber(number)) {
                return Map.of(
                        "status", false,
                        "message", "User already exists with this number");
            }

            usermodel user = new usermodel();
            user.setUsername(body.get("username").toString());
            user.setUseremail(email);
            user.setNumber(number);
            user.setCustomerId(generateUniqueCustomerId());

            // Set default values for additional fields that might exist in DB
            user.setAccountNonLocked(true);
            user.setEmail(email); // Sync both email fields
            user.setStatus("ONLINE");
            if (body.get("latitude") != null)
                user.setLatitude(Double.parseDouble(body.get("latitude").toString()));
            if (body.get("longitude") != null)
                user.setLongitude(Double.parseDouble(body.get("longitude").toString()));
            user.setAuthProvider(body.get("authProvider") != null ? body.get("authProvider").toString() : "MANUAL");

            usermodel saved = userRepo.save(user);

            return Map.of(
                    "status", true,
                    "message", "User created successfully",
                    "data", saved);
        } catch (Exception e) {
            e.printStackTrace(); // Log the full error
            return Map.of(
                    "status", false,
                    "message", "Error creating user: " + e.getMessage());
        }
    }

    @GetMapping("/check")
    public Object checkUser(@RequestParam("email") String useremail,
            @RequestParam("number") Long number) {

        // 1️⃣ BOTH match same user
        Optional<usermodel> bothMatch = userRepo.findByUseremailAndNumber(useremail, number);
        if (bothMatch.isPresent()) {
            return Map.of(
                    "status", true,
                    "type", "both",
                    "message", "Email and number match");
        }

        // 2️⃣ Number exists (but email doesn't match)
        Optional<usermodel> numberOnly = userRepo.findByNumber(number);
        if (numberOnly.isPresent()) {
            return Map.of(
                    "status", false,
                    "type", "number",
                    "message", "Mobile number exists but email does NOT match.");
        }

        // 3️⃣ Email exists (but number doesn't match)
        Optional<usermodel> emailOnly = userRepo.findByUseremail(useremail);
        if (emailOnly.isPresent()) {
            return Map.of(
                    "status", false,
                    "type", "email",
                    "message", "Email exists but mobile number does NOT match.");
        }

        // 4️⃣ Neither exists
        return Map.of(
                "status", true,
                "type", "none",
                "message", "User not found. You can create a new user.");
    }

    // -------------------------------------------------------------
    // DELETE USER BASED ONLY ON NUMBER (DELETE)
    // -------------------------------------------------------------
    @DeleteMapping("/delete/{number}")
    public Object deleteUser(@PathVariable Long number) {

        Optional<usermodel> userCheck = userRepo.findByNumber(number);

        if (userCheck.isEmpty()) {
            return Map.of(
                    "status", false,
                    "message", "User not found with number: " + number);
        }

        userRepo.delete(userCheck.get());

        return Map.of(
                "status", true,
                "message", "User deleted successfully");
    }

    // -------------------------------------------------------------
    // Get All Users
    // -------------------------------------------------------------
    @GetMapping("/all")
    public List<usermodel> getAllUsers() {
        return userRepo.findAll();
    }

    // -------------------------------------------------------------
    // UPDATE USER BASED ONLY ON NUMBER (PUT)
    // -------------------------------------------------------------
    @PutMapping("/update/{oldNumber}")
    public Object updateByNumber(
            @PathVariable Long oldNumber,
            @Valid @RequestBody Map<String, Object> body) {

        Optional<usermodel> existingUserOptional = userRepo.findByNumber(oldNumber);

        if (existingUserOptional.isEmpty()) {
            return Map.of(
                    "status", false,
                    "message", "User not found with number: " + oldNumber);
        }

        usermodel user = existingUserOptional.get();

        // Extract fields from body
        String newUsername = body.get("username").toString();
        String newEmail = body.get("useremail").toString();
        Long newNumber = Long.parseLong(body.get("number").toString());

        // ---------- Email check ----------
        if (!user.getUseremail().equals(newEmail) &&
                userRepo.existsByUseremail(newEmail)) {

            return Map.of(
                    "status", false,
                    "message", "Update failed: Email already exists!");
        }

        // ---------- Number check ----------
        if (!user.getNumber().equals(newNumber) &&
                userRepo.existsByNumber(newNumber)) {

            return Map.of(
                    "status", false,
                    "message", "Update failed: Number already exists!");
        }

        // ---------- Update ----------
        user.setUsername(newUsername);
        user.setUseremail(newEmail);
        user.setNumber(newNumber);
        if (body.get("status") != null)
            user.setStatus(body.get("status").toString());
        if (body.get("latitude") != null)
            user.setLatitude(Double.parseDouble(body.get("latitude").toString()));
        if (body.get("longitude") != null)
            user.setLongitude(Double.parseDouble(body.get("longitude").toString()));

        userRepo.save(user);

        return Map.of(
                "status", true,
                "message", "User updated successfully",
                "data", user);
    }

    @GetMapping("/getbynumber/{number}")
    public Object getByNumber(@PathVariable Long number) {

        Optional<usermodel> userOptional = userRepo.findByNumber(number);

        if (userOptional.isEmpty()) {
            return Map.of(
                    "status", false,
                    "message", "User not found with number: " + number);
        }

        return Map.of(
                "status", true,
                "message", "User fetched successfully",
                "data", userOptional.get());
    }

    private String generateUniqueCustomerId() {
        String id;
        do {
            id = String.format("%012d", (long) (Math.random() * 1_000_000_000_000L));
        } while (userRepo.existsByCustomerId(id));
        return id;
    }

}

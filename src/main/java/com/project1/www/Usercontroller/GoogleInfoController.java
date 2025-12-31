package com.project1.www.Usercontroller;

import com.project1.www.UserModel.GoogleInfo;
import com.project1.www.UserRepository.GoogleInfoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/googleinfo")
@CrossOrigin(origins = "*")
public class GoogleInfoController {

    @Autowired
    private GoogleInfoRepository googleInfoRepository;

    /**
     * Create or update Google user information
     */
    @PostMapping
    public ResponseEntity<Object> saveGoogleInfo(@Valid @RequestBody GoogleInfo googleInfo) {
        try {
            // Validate required fields
            if (googleInfo.getGoogleId() == null || googleInfo.getGoogleId().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", false,
                        "message", "Google ID is required",
                        "error", "VALIDATION_ERROR"));
            }

            if (googleInfo.getEmail() == null || googleInfo.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", false,
                        "message", "Email is required",
                        "error", "VALIDATION_ERROR"));
            }

            // Check if Google ID already exists
            Optional<GoogleInfo> existingInfo = googleInfoRepository.findByGoogleId(googleInfo.getGoogleId());

            GoogleInfo savedInfo;
            String message;

            if (existingInfo.isPresent()) {
                // Update existing record
                GoogleInfo existing = existingInfo.get();
                existing.setName(googleInfo.getName());
                existing.setEmail(googleInfo.getEmail());
                existing.setEmailVerified(googleInfo.getEmailVerified());
                existing.setPicture(googleInfo.getPicture());
                existing.setLocale(googleInfo.getLocale());
                existing.setAuthProvider(googleInfo.getAuthProvider());
                existing.setStatus(googleInfo.getStatus() != null ? googleInfo.getStatus() : "ONLINE");
                existing.setLatitude(googleInfo.getLatitude());
                existing.setLongitude(googleInfo.getLongitude());

                savedInfo = googleInfoRepository.save(existing);
                message = "Google user information updated successfully";
            } else {
                // Create new record
                savedInfo = googleInfoRepository.save(googleInfo);
                message = "Google user information saved successfully";
            }

            return ResponseEntity.ok(Map.of(
                    "status", true,
                    "message", message,
                    "data", savedInfo,
                    "timestamp", System.currentTimeMillis()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "Failed to save Google user information",
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()));
        }
    }

    /**
     * Get Google user by Google ID
     */
    @GetMapping("/{googleId}")
    public ResponseEntity<Object> getGoogleInfoByGoogleId(@PathVariable String googleId) {
        try {
            if (googleId == null || googleId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", false,
                        "message", "Google ID is required"));
            }

            Optional<GoogleInfo> googleInfo = googleInfoRepository.findByGoogleId(googleId);

            if (googleInfo.isPresent()) {
                return ResponseEntity.ok(Map.of(
                        "status", true,
                        "message", "Google user found",
                        "data", googleInfo.get(),
                        "timestamp", System.currentTimeMillis()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", false,
                        "message", "Google user not found with ID: " + googleId,
                        "timestamp", System.currentTimeMillis()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "Error retrieving Google user information",
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()));
        }
    }

    /**
     * Get Google user by email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Object> getGoogleInfoByEmail(@PathVariable String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", false,
                        "message", "Email is required"));
            }

            Optional<GoogleInfo> googleInfo = googleInfoRepository.findByEmail(email);

            if (googleInfo.isPresent()) {
                return ResponseEntity.ok(Map.of(
                        "status", true,
                        "message", "Google user found",
                        "data", googleInfo.get(),
                        "timestamp", System.currentTimeMillis()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", false,
                        "message", "Google user not found with email: " + email,
                        "timestamp", System.currentTimeMillis()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "Error retrieving Google user information",
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()));
        }
    }

    /**
     * Get all Google users with pagination support
     */
    @GetMapping
    public ResponseEntity<Object> getAllGoogleInfo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        try {
            List<GoogleInfo> allGoogleInfo = googleInfoRepository.findAll();

            // Simple pagination
            int start = page * size;
            int end = Math.min(start + size, allGoogleInfo.size());
            List<GoogleInfo> paginatedList = allGoogleInfo.subList(start, end);

            return ResponseEntity.ok(Map.of(
                    "status", true,
                    "message", "Google users retrieved successfully",
                    "data", paginatedList,
                    "pagination", Map.of(
                            "page", page,
                            "size", size,
                            "total", allGoogleInfo.size(),
                            "totalPages", (int) Math.ceil((double) allGoogleInfo.size() / size)),
                    "timestamp", System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "Error retrieving Google users",
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()));
        }
    }

    /**
     * Delete Google user by Google ID
     */
    @DeleteMapping("/{googleId}")
    public ResponseEntity<Object> deleteGoogleInfo(@PathVariable String googleId) {
        try {
            if (googleId == null || googleId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", false,
                        "message", "Google ID is required"));
            }

            Optional<GoogleInfo> googleInfo = googleInfoRepository.findByGoogleId(googleId);

            if (googleInfo.isPresent()) {
                googleInfoRepository.delete(googleInfo.get());
                return ResponseEntity.ok(Map.of(
                        "status", true,
                        "message", "Google user deleted successfully",
                        "deletedId", googleId,
                        "timestamp", System.currentTimeMillis()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", false,
                        "message", "Google user not found with ID: " + googleId,
                        "timestamp", System.currentTimeMillis()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "Error deleting Google user",
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()));
        }
    }

    /**
     * Get statistics about Google users
     */
    @GetMapping("/stats")
    public ResponseEntity<Object> getGoogleUserStats() {
        try {
            List<GoogleInfo> allUsers = googleInfoRepository.findAll();
            long verifiedCount = allUsers.stream().filter(u -> u.getEmailVerified() != null && u.getEmailVerified())
                    .count();

            return ResponseEntity.ok(Map.of(
                    "status", true,
                    "message", "Statistics retrieved successfully",
                    "data", Map.of(
                            "totalUsers", allUsers.size(),
                            "verifiedUsers", verifiedCount,
                            "unverifiedUsers", allUsers.size() - verifiedCount,
                            "verificationRate",
                            allUsers.size() > 0 ? (double) verifiedCount / allUsers.size() * 100 : 0),
                    "timestamp", System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", false,
                    "message", "Error retrieving statistics",
                    "error", e.getMessage(),
                    "timestamp", System.currentTimeMillis()));
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", true,
                "message", "Google Info API is healthy",
                "service", "GoogleInfoController",
                "timestamp", System.currentTimeMillis()));
    }
}
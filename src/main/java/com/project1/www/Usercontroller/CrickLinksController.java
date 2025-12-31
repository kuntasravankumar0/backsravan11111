package com.project1.www.Usercontroller;

import com.project1.www.UserModel.CrickLinks;
import com.project1.www.UserRepository.CrickLinksRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cricklinks")

public class CrickLinksController {

    private final CrickLinksRepository repository;

    public CrickLinksController(CrickLinksRepository repository) {
        this.repository = repository;
    }

    /* ================= CREATE ================= */
@PostMapping
public ResponseEntity<?> create(@RequestBody CrickLinks data) {

    // 1. If customerId is missing → auto-generate
    if (data.getCustomerId() == null || data.getCustomerId().isBlank()) {
        data.setCustomerId(generateUniqueCustomerId());
    }
    // 2. If customerId is provided → must be unique
    else if (!repository.findByCustomerId(data.getCustomerId()).isEmpty()) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("CustomerId already exists: " + data.getCustomerId());
    }

    CrickLinks saved = repository.save(data);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
}
private String generateUniqueCustomerId() {
    String customerId;
    do {
        customerId = "ADM" + System.currentTimeMillis();
    } while (!repository.findByCustomerId(customerId).isEmpty());
    return customerId;
}

    /* ================= READ ================= */

    @GetMapping
    public ResponseEntity<List<CrickLinks>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return repository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Record not found with id: " + id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getByCustomerId(@PathVariable String customerId) {
        List<CrickLinks> list = repository.findByCustomerId(customerId);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No record found for customerId: " + customerId);
        }
        return ResponseEntity.ok(list); // unique customerId → one record
    }

    /* ================= UPDATE (PARTIAL SAFE UPDATE) ================= */

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody CrickLinks data) {

        Optional<CrickLinks> optional = repository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cannot update. ID not found: " + id);
        }

        CrickLinks existing = optional.get();

        if (data.getSofternames() != null)
            existing.setSofternames(data.getSofternames());

        if (data.getLinkparagrafe() != null)
            existing.setLinkparagrafe(data.getLinkparagrafe());

        if (data.getImageUrl() != null)
            existing.setImageUrl(data.getImageUrl());

        if (data.getCmdPrompt() != null)
            existing.setCmdPrompt(data.getCmdPrompt());

        if (data.getVedioSource() != null)
            existing.setVedioSource(data.getVedioSource());

        if (data.getAboutSoftware() != null)
            existing.setAboutSoftware(data.getAboutSoftware());

        CrickLinks updated = repository.save(existing);
        return ResponseEntity.ok(updated);
    }

    /* ================= DELETE ================= */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cannot delete. ID not found: " + id);
        }
        repository.deleteById(id);
        return ResponseEntity.ok("Deleted record with id: " + id);
    }

    @DeleteMapping("/customer/{customerId}")
    @Transactional
    public ResponseEntity<?> deleteByCustomerId(@PathVariable String customerId) {
        List<CrickLinks> list = repository.findByCustomerId(customerId);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No record found for customerId: " + customerId);
        }
        repository.deleteByCustomerId(customerId);
        return ResponseEntity.ok("Deleted record for customerId: " + customerId);
    }

    /* ================= EXTRA FEATURE: SEARCH ================= */

    @GetMapping("/search")
    public ResponseEntity<List<CrickLinks>> searchBySoftwareName(
            @RequestParam String q) {

        List<CrickLinks> result = repository.findAll()
                .stream()
                .filter(c ->
                        c.getSofternames() != null &&
                        c.getSofternames().toLowerCase().contains(q.toLowerCase()))
                .toList();

        return ResponseEntity.ok(result);
    }
}

package com.project1.www.Usercontroller;

import com.project1.www.UserModel.LinksModel;
import com.project1.www.UserRepository.LinksRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/links")
public class LinksController {

    @Autowired
    private LinksRepository linksRepository;

    // Generate random 8-digit linknumber
    private String generateLinkNumber() {
        return String.valueOf((long) (Math.random() * 90000000L) + 10000000L);
    }

    // ------------------ CREATE (POST) ------------------
    @PostMapping("/create")
    public LinksModel createLink(@RequestBody LinksModel model) {

        // Generate unique linknumber
        String ln;
        do {
            ln = generateLinkNumber();
        } while (linksRepository.existsByLinknumber(ln));

        model.setLinknumber(ln);

        return linksRepository.save(model);
    }

    // ------------------ GET BY LINKNUMBER ------------------
    @GetMapping("/getby/{linknumber}")
    public LinksModel getByLinkNumber(@PathVariable String linknumber) {
        return linksRepository.findByLinknumber(linknumber)
                .orElseThrow(() -> new RuntimeException("Link not found"));
    }

    // ------------------ GET ALL ------------------
    @GetMapping("/all")
    public List<LinksModel> getAll() {
        return linksRepository.findAll();
    }

    // ------------------ UPDATE BY LINKNUMBER ------------------
    @PutMapping("/update/{linknumber}")
    public LinksModel updateLink(@PathVariable String linknumber, @RequestBody LinksModel updated) {

        LinksModel old = linksRepository.findByLinknumber(linknumber)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        old.setLinkname(updated.getLinkname());
        old.setCategary(updated.getCategary());
        old.setUsenote(updated.getUsenote());
        old.setLinks(updated.getLinks());

        return linksRepository.save(old);
    }

    // ------------------ DELETE BY LINKNUMBER ------------------
  @DeleteMapping("/delete/{linknumber}")
public String deleteLink(@PathVariable String linknumber) {

    LinksModel record = linksRepository.findByLinknumber(linknumber)
            .orElse(null);

    if (record == null) {
        return "No record found!";
    }

    linksRepository.delete(record); // <-- SAFE DELETE (NO TRANSACTION NEEDED)
    return "Deleted successfully!";
}

}

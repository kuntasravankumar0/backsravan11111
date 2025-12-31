package com.project1.www.UserModel;

import jakarta.persistence.*;

@Entity
@Table(name = "links_table")
public class LinksModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String linkname;
    private String categary;
    private String usenote;
    private String links;

    @Column(name = "linknumber", unique = true)
    private String linknumber;   // This will be auto-generated

    // ---------------- GETTERS & SETTERS ----------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinkname() {
        return linkname;
    }

    public void setLinkname(String linkname) {
        this.linkname = linkname;
    }

    public String getCategary() {
        return categary;
    }

    public void setCategary(String categary) {
        this.categary = categary;
    }

    public String getUsenote() {
        return usenote;
    }

    public void setUsenote(String usenote) {
        this.usenote = usenote;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getLinknumber() {
        return linknumber;
    }

    public void setLinknumber(String linknumber) {
        this.linknumber = linknumber;
    }
}

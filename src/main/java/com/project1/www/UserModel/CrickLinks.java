package com.project1.www.UserModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "crick_links")
public class CrickLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---------- crickLinks ----------
    @JsonProperty("crickLinks")
    @Column(name = "softernames")
    private String softernames;

    // ---------- customerId ----------
    @JsonProperty("customerId")
    @Column(name = "customer_id", unique = true)
    private String customerId;

    // ---------- linkTest ----------
    @JsonProperty("linkTest")
    @Column(name = "linkparagrafe")
    private String linkparagrafe;

    // ---------- imageUrl ----------
    @JsonProperty("imageUrl")
    @Column(name = "image_url")
    private String imageUrl;

    // ---------- cmdCommandSetup ----------
    @JsonProperty("cmdCommandSetup")
    @Column(name = "cmd_prompt", columnDefinition = "TEXT")
    private String cmdPrompt;

    // ---------- videoLink ----------
    @JsonProperty("videoLink")
    @Column(name = "vedio_source")
    private String vedioSource;

    // ---------- aboutSoftware ----------
    @JsonProperty("aboutSoftware")
    @Column(name = "about_software", columnDefinition = "TEXT")
    private String aboutSoftware;

    /* ================= GETTERS ================= */

    public Long getId() {
        return id;
    }

    public String getSofternames() {
        return softernames;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getLinkparagrafe() {
        return linkparagrafe;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCmdPrompt() {
        return cmdPrompt;
    }

    public String getVedioSource() {
        return vedioSource;
    }

    public String getAboutSoftware() {
        return aboutSoftware;
    }

    /* ================= SETTERS ================= */

    public void setId(Long id) {
        this.id = id;
    }

    public void setSofternames(String softernames) {
        this.softernames = softernames;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setLinkparagrafe(String linkparagrafe) {
        this.linkparagrafe = linkparagrafe;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCmdPrompt(String cmdPrompt) {
        this.cmdPrompt = cmdPrompt;
    }

    public void setVedioSource(String vedioSource) {
        this.vedioSource = vedioSource;
    }

    public void setAboutSoftware(String aboutSoftware) {
        this.aboutSoftware = aboutSoftware;
    }
}

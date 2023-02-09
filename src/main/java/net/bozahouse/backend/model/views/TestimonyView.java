package net.bozahouse.backend.model.views;

import lombok.Data;

import java.util.Date;

@Data
public class TestimonyView {
    private long id;
    private Date createdAt;
    private Date updatedAt;
    private String message;
    private String authorLastname;
    private String authorFirstname;
    private String authorUsername;
    private int currentPage;
    private int totalPages;
    private int pageSize;
}

package net.bozahouse.backend.model.views;

import lombok.Data;

import java.util.Date;


@Data
public class NewsletterView {
    private long id;
    private String subject;
    private String frenchContent;
    private String englishContent;
    private Date sendingDate;
    private Date createdAt;
    private Date updatedAt;
    private String username;
    private int currentPage;
    private int totalPages;
    private int pageSize;
}

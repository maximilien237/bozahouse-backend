package net.bozahouse.backend.model.views;

import lombok.Data;

import java.util.Date;


@Data
public class AppUserDatesView {
    private long id;
    private Date lastConnexion;
    private String username;

    private int currentPage;
    private int totalPages;
    private int pageSize;
    private int realSize;
}

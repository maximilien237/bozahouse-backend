package net.bozahouse.backend.model.views;

import lombok.Data;

import java.util.Date;

@Data
public class TalentView {
    private String id;
    private String type;
    private String title;
    private String domain;
    private String experience;
    private String skills;
    private String sex;
    private String salary;
    private String salaryChoice;
    private String tel;
    private String whatsAppNumber;
    private String countryName;
    private String cityName;
    private String level;
    private String countryCode;
    private String address;
    private String workMode;
    private String reference;
    private Date publishedAt;
    private Date updatedAt;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String contract;
    private String linkedin;
    private String github;

    private int currentPage;
    private int totalPages;
    private int pageSize;

}

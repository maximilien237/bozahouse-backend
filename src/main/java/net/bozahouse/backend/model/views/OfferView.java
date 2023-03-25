package net.bozahouse.backend.model.views;

import lombok.Data;

import java.util.Date;

@Data
public class OfferView {
    private String id;
    private String type;
    private String title;
    private String mission;
    private String skills;
    private int needPeople;
    private String domain;
    private String address;
    private String tel;
    private String whatsAppNumber;
    private String experience;
    private String email;
    private String salary;
    private String salaryChoice;
    private Date endOffer;
    private String workMode;
    private String profile;
    private int duration;
    private String reference;
    private Date publishedAt;
    private Date updatedAt;
    private String username;
    private String firstname;
    private String lastname;
    private String sex;
    private String name;
    private String fcb;
    private String linkedin;
    private String web;
    private String contract;
    private String countryCode;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private int sizeActivated;
    private int sizeDisabled;

}

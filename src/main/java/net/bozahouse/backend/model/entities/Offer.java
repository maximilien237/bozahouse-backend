package net.bozahouse.backend.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offers")
public class Offer {

    @Id
    private String id;
    private String type;
    private String title;
    @Column(name = "mission", columnDefinition = "LONGTEXT")
    private String mission;
    @Column(name = "skills", columnDefinition = "LONGTEXT")
    private String skills;
    private int needPeople;
    private String domain;
    private String address;
    private String tel;
    private String countryCode;
    private String whatsAppNumber;
    private String experience;
    private String email;
    private String salary;
    private String salaryChoice;
    private Date endOffer;
    private boolean valid;
    private String workMode;
    @Column(name = "profile", columnDefinition = "LONGTEXT")
    private String profile;
    private long duration;
    private String reference;
    private Date publishedAt;
    private Date updatedAt;
    private String name;
    private String fcb;
    private String web;
    private String linkedin;
    private String contract;
    @NotNull
    @ManyToOne
    private AppUser user;
}

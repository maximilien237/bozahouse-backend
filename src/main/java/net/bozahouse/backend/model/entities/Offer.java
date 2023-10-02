package net.bozahouse.backend.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offers")
public class Offer extends AbstractEntity{

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
    private String reference;
    private String name;
    private String fcb;
    private String web;
    private String linkedin;
    private String contract;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user")
    private AppUser user;
}

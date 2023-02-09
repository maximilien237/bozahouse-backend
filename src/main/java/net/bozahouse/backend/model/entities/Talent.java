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
@Table(name = "talents")
public class Talent {
    @Id
    private String id;
    private String type;
    private String title;
    private String domain;
    private String experience;
    @Column(name = "skills", columnDefinition = "LONGTEXT")
    private String skills;
    private String salary;
    private String salaryChoice;
    private String countryCode;
    private String tel;
    private String whatsAppNumber;
    private boolean valid;

    private String address;
    private String workMode;
    private String reference;
    private Date publishedAt;
    private Date updatedAt;

    private String email;
    private String contract;
    private String linkedin;
    private String github;
    private String level;


    @NotNull
    @ManyToOne
    private AppUser user;
}

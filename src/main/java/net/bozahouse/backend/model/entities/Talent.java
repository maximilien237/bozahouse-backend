package net.bozahouse.backend.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;



@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "talents")
public class Talent extends AbstractEntity{

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

    private String email;
    private String contract;
    private String linkedin;
    private String github;
    private String level;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user")
    private AppUser user;
}

package net.bozahouse.backend.model.entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.Date;

import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class AppUser extends AbstractEntity{

    private String account;
    private String howKnowUs;
    private String lastname;
    private String firstname;

    private String sex;
    private String username;
    private String password;
    private String confirmPassword;

    private Date birthday;
    private String email;
    private boolean activated;
    private boolean firstConnexion;
    private boolean acceptTerms;
    private long countConnexion;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<AppRole> roles;



}

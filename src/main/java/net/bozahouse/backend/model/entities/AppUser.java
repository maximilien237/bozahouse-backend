package net.bozahouse.backend.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@Table(name = "users")
public class AppUser {
    @Id
    private String id;
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
    private String referralCode;

    private String promoCode;
    private Date discountDate;
    private Date createdAt;
    private Date updatedAt;
    private boolean isActivated;
    private boolean firstConnexion;
    private boolean activatedHostSubscription;
    private boolean activatedNormalSubscription;
    private boolean acceptTerms;
    private long hostSubscriptionCounter;
    private long normalSubscriptionCounter;
    private long countConnexion;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AppRole> roles = new HashSet<>();

    public AppUser(){
        super();
        this.isActivated = false;
        this.firstConnexion=false;
        this.activatedHostSubscription=false;
        this.activatedNormalSubscription=false;
        this.acceptTerms=false;
        this.countConnexion=0;
        this.hostSubscriptionCounter=0;
        this.normalSubscriptionCounter=0;

    }


}

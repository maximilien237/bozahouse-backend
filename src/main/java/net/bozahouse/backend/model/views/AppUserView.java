package net.bozahouse.backend.model.views;

import lombok.Data;
import net.bozahouse.backend.model.entities.AppRole;


import java.util.Date;
import java.util.Set;


@Data
public class AppUserView {
    private String id;
    private String account;
    private String howKnowUs;
    private String lastname;
    private String firstname;
    private String sex;
    private String username;
    private Date birthday;
    private String email;
    private String referralCode;
    private String promoCode;
    private Date createdAt;
    private boolean isActivated;
    private boolean firstConnexion;
    private long countConnexion;
    private Date lastConnexion;
    private boolean activatedHostSubscription;
    private boolean activatedNormalSubscription;
    private boolean acceptTerms;
    private long hostSubscriptionCounter;
    private long normalSubscriptionCounter;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private int sizeActivated;
    private int sizeDisabled;
    private Set<AppRole> roles;





}

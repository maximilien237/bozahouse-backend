package net.bozahouse.backend.model.forms;

import lombok.Data;


@Data
public class AppUserForm {
    private String id;
    private String howKnowUs;
    private String account;
    private String lastname;
    private String firstname;
    private String sex;
    private String username;
    private String password;
    private String confirmPassword;
    private String birthday;
    private String email;
    private String referralCode;
    private boolean acceptTerms;


}

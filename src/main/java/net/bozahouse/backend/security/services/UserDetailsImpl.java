package net.bozahouse.backend.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import net.bozahouse.backend.model.entities.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private String id;
  private String account;
  private String howKnowUs;
  private String lastname;
  private String firstname;
  private String username;
  @JsonIgnore
  private String password;
  @JsonIgnore
  private String confirmPassword;
  private Date birthday;
  private String email;
  private String referralCode;
  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(String id, String account, String howKnowUs, String lastname, String firstname, String username, String password, String confirmPassword, Date birthday, String email, String referralCode, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.account = account;
    this.howKnowUs = howKnowUs;
    this.lastname = lastname;
    this.firstname = firstname;
    this.username = username;
    this.password = password;
    this.confirmPassword = confirmPassword;
    this.birthday = birthday;
    this.email = email;
    this.referralCode = referralCode;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(AppUser user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getId(),
            user.getAccount(),
            user.getHowKnowUs(),
            user.getLastname(),
            user.getFirstname(),
            user.getUsername(),
            user.getPassword(),
            user.getConfirmPassword(),
        user.getBirthday(),
            user.getEmail(),
            user.getReferralCode(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }


  public String getAccount() {
    return account;
  }

  public String getHowKnowUs() {
    return howKnowUs;
  }

  public String getLastname() {
    return lastname;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public Date getBirthday() {
    return birthday;
  }

  public String getReferralCode() {
    return referralCode;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}

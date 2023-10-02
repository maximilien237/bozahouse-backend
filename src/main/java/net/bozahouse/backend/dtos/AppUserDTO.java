package net.bozahouse.backend.dtos;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.model.entities.AppUser;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;


/**
 * @author maximilien kengne kongne
 * email : maximiliendenver@gmail.com
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AppUserDTO {
    private Long id;
    private String account;
    private String howKnowUs;
    private String lastname;
    private String firstname;
    private String sex;
    private String username;
    private Date birthday;
    private String email;
    private String password;
    private String confirmPassword;
    private Date createdAt;
    private boolean isActivated;
    private boolean firstConnexion;
    private long countConnexion;
    private Date lastConnexion;
    private boolean acceptTerms;
    private List<AppRole> roles;
    private Long totalAppUser;
    private Long totalAppUserActivated;
    private Long totalAppUserDisabled;

    public static AppUserDTO mapToDTO(AppUser appUser) {
        if(appUser==null){
            return null;
        }

        AppUserDTO appUserDTO = new AppUserDTO();
        BeanUtils.copyProperties(appUser,appUserDTO);

        appUserDTO.setRoles(appUser.getRoles());
        return appUserDTO;

    }

    public static AppUser mapToEntity(AppUserDTO appUserDTO) {
        if(appUserDTO==null){
            return null;
        }
        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(appUserDTO,appUser);

        return  appUser;
    }

}

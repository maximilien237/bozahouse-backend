package net.bozahouse.backend.services;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.dtos.AppUserDTO;
import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.exception.EntityNotFoundException;
import net.bozahouse.backend.exception.ErrorCodes;
import net.bozahouse.backend.exception.InvalidEntityException;
import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.validators.AppUserValidator;
import net.bozahouse.backend.repositories.AppUserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService{

    private AppUserRepo userRepo;
    private AppRoleService roleService;
    private PasswordEncoder passwordEncoder;


    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }


    @Override
    public AppUser getAppUser(Long userId){
        log.info("getting appUser by id :: " +userId + "...");
        Optional<AppUser> optionalAppUser = userRepo.findById(userId);

        if (optionalAppUser.isPresent()){
            return optionalAppUser.get();
        }

        throw new EntityNotFoundException("Aucun utilisateur avec l'ID = " + userId + " n'a ete trouve dans la BDD", ErrorCodes.NOT_FOUND);
    }

    @Override
    public AppUserDTO getAppUserDTO(Long userId){
        log.info("getting appUser by id :: " +userId + "...");
        return AppUserDTO.mapToDTO(getAppUser(userId));
 }



    @Override
    public AppUser getAppUserByEmail(String email){
        log.info("getting appUser by email :: " +email + "...");

        Optional<AppUser> optionalAppUser = userRepo.findByEmail(email);

        if (optionalAppUser.isPresent()){
            return optionalAppUser.get();
        }
        throw new EntityNotFoundException("Aucun utilisateur avec cet email = " + email + " n'a ete trouve dans la BDD", ErrorCodes.NOT_FOUND);
    }

    @Override
    public AppUserDTO getAppUserByEmailDTO(String email){
        log.info("getting appUser by email :: " +email + "...");
        return AppUserDTO.mapToDTO(getAppUserByEmail(email));
    }

    @Override
    public AppUserDTO updateAppUser(AppUserDTO dto){
        log.info("updating appUser...");

        List<String> errors = AppUserValidator.validate(dto);
        if (!errors.isEmpty() || !Objects.equals(dto.getPassword(), dto.getConfirmPassword())) {
            log.error("AppUser is not valid {}", dto);
            throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.BAD_REQUEST, errors);
        }


        AppUser appUser = AppUserDTO.mapToEntity(dto);
        AppUser updateUser = getAppUser(dto.getId());
        appUser.setCreatedAt(updateUser.getCreatedAt());
        appUser.setActivated(updateUser.isActivated());
        appUser.setRoles(updateUser.getRoles());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setConfirmPassword(passwordEncoder.encode(appUser.getConfirmPassword()));

        appUser = userRepo.save(appUser);
        return AppUserDTO.mapToDTO(appUser);
    }


    @Override
    public void deleteAppUser(Long userId) {
        log.info("deleting appUser by id :: " + userId + "...");
        if (userRepo.existsById(userId)) {
            userRepo.delete(getAppUser(userId));
        }

    }


    @Override
    public PageDTO<AppUserDTO> listAppUser(int page, int size) {
        log.info("list appUser view...");
        Page<AppUser> appUserPage = userRepo.findAllByActivatedTrueOrderByCreatedAtDesc(PageRequest.of(page, size));

        return PageDTO.mapToAppUserPageDTO(appUserPage);

    }

/*

    @Override
    public PageDTO<AppUserDTO> listAppUserWithRole(String roleName, int page, int size)  {
        log.info("list appUser with role ...");
        AppRole role = roleService.findByName(roleName);
        List<AppRole> appRoles = List.of(role);
        Page<AppUser> appUserPage = userRepo.findAllByActivatedTrueAndRolesOrderByCreatedAtDesc(appRoles, PageRequest.of(page, size));
        return PageDTO.mapToAppUserPageDTO(appUserPage);
    }
*/



    @Override
    public AppUserDTO addRoleToUser(String email, String roleName) {
        log.info("add role to appUser..." +email +"..." +roleName);
        if (!StringUtils.hasLength(email) || !StringUtils.hasLength(roleName)) {
            log.error("username or roleName is null");
            return null;
        }
        AppRole role = roleService.findByName(roleName);
        AppUser appUser = getAppUserByEmail(email);
        log.info("role and username found");

        appUser.getRoles().add(role);
        return AppUserDTO.mapToDTO(appUser);
    }

    @Override
    public AppUserDTO removeRoleToUser(String email, String roleName){
        log.info("delete role to appUser..." +email +"..." +roleName);

        if (!StringUtils.hasLength(email) || !StringUtils.hasLength(roleName)) {
            log.error("username or roleName is null");
            return null;
        }

        AppRole role = roleService.findByName(roleName);
        AppUser appUser = getAppUserByEmail(email);
        appUser.getRoles().remove(role);

        return AppUserDTO.mapToDTO(appUser);
    }

    @Override
    public PageDTO<AppUserDTO> findAllByUsername(String key, int page, int size){
        log.info("filter appUser by username :: " +key);
        Page<AppUser> appUserPage = userRepo.findAllByEmailContainingAndActivatedTrueOrderByCreatedAtDesc(key, PageRequest.of(page, size));
        return PageDTO.mapToAppUserPageDTO(appUserPage);
    }

    @Override
    public PageDTO<AppUserDTO> listAppUserDisabledByUsername(String key, int page, int size){
        log.info("filter disabled appUser by username :: " +key);
        Page<AppUser> appUserPage = userRepo.findAllByEmailContainingAndActivatedFalseOrderByCreatedAtDesc(key,PageRequest.of(page, size));
        return PageDTO.mapToAppUserPageDTO(appUserPage);
    }

    @Override
    public void enabledOrDisableAppUser(Long appUserId) {
        log.info("enabled or disable user with id ::" +appUserId);
        AppUser appUser = getAppUser(appUserId);
        if (appUser.isActivated()) {
            appUser.setActivated(false);
            userRepo.save(appUser);
        }
        appUser.setActivated(true);
        userRepo.save(appUser);
    }

}

package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.entitie.RoleNotFoundException;
import net.bozahouse.backend.mappers.EntityToViewConverter;
import net.bozahouse.backend.mappers.FormToEntityConverter;
import net.bozahouse.backend.mappers.ListEntityToListViewConverter;
import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.AppUserDates;
import net.bozahouse.backend.model.forms.AppUserForm;
import net.bozahouse.backend.model.views.AppUserDatesView;
import net.bozahouse.backend.model.views.AppUserView;
import net.bozahouse.backend.repositories.AppRoleRepo;
import net.bozahouse.backend.repositories.AppUserDatesRepo;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.security.ControllerVariables;
import net.bozahouse.backend.utils.DateUtils;
import net.bozahouse.backend.utils.RandomUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService{
    private AppUserRepo userRepo;
    private AppUserDatesRepo userDatesRepo;
    private AppRoleRepo roleRepo;
    //private SubscriptionRepo subscriptionRepo;
    private PasswordEncoder passwordEncoder;

    @Override
    public List<AppUserDatesView> appUserDatesList(String appUserId, int page, int size) throws AppUserNotFoundException {
        AppUser appUser = getAppUser(appUserId);
        List<AppUserDates> userDates = userDatesRepo.findByUserOrderByLastConnexionDesc(appUser, PageRequest.of(page, size));

        List<AppUserDates> userDates1 = userDatesRepo.findByUser(appUser);
        int realSize = userDates1.size();

        List<AppUserDatesView> userDatesViews = ListEntityToListViewConverter.paginateAppUserDateList(userDates, page, size,realSize);

        return userDatesViews;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public boolean existsByPromoCode(String code) {
        return userRepo.existsByPromoCode(code);
    }

    @Override
    public boolean existsByReferralCode(String referral) {
        return userRepo.existsByReferralCode(referral);
    }

    @Override
   public Double countAllByReferralCode(String referralCode){
        return userRepo.countAllByReferralCode(referralCode);
   }

    @Override
    public AppUser getAppUser(String userId) throws AppUserNotFoundException {
        log.info("getting appUser by id :: " +userId + "...");
        Optional<AppUser> optionalAppUser = userRepo.findById(userId);
        AppUser appUser;
        if (optionalAppUser.isPresent()){
            appUser = optionalAppUser.get();
        }else {
            throw new AppUserNotFoundException("user not found for id ::" +userId);
        }

        userRepo.save(appUser);
        return appUser;
    }


    @Override
    public AppUserView getAppUserView(String userId) throws AppUserNotFoundException {
        log.info("getting appUser view by id :: " + userId + "...");
        AppUser appUser = getAppUser(userId);
        AppUserView appUserView = EntityToViewConverter.convertEntityToAppUserView(appUser);
        return appUserView;
    }

    @Override
    public AppUser getAppUserByUsername(String username) throws AppUserNotFoundException {
        log.info("getting appUser by username :: " +username + "...");
        Optional<AppUser> optionalAppUser = userRepo.findByUsername(username);
        AppUser appUser;
        if (optionalAppUser.isPresent()){
            appUser = optionalAppUser.get();
        }else {
            throw new AppUserNotFoundException("user not found for this username ::" +username);
        }
        userRepo.save(appUser);
        return appUser;
    }

    @Override
    public AppUser getAppUserByEmail(String email) throws AppUserNotFoundException {
        log.info("getting appUser by email :: " +email + "...");
        Optional<AppUser> optionalAppUser = userRepo.findByEmail(email);
        AppUser appUser;
        if (optionalAppUser.isPresent()){
            appUser = optionalAppUser.get();
        }else {
            throw new AppUserNotFoundException("user not found for id ::" +email);
        }
        return appUser;
    }


    @Override
    public AppRole getRoleByRoleName(String roleName) throws RoleNotFoundException {
        log.info("getting appRole by role name :: " +roleName +"...");
        Optional<AppRole> optionalAppRole= roleRepo.findByName(roleName);
        AppRole role;
        if (optionalAppRole.isPresent()){
            role = optionalAppRole.get();
        }else {
            throw new RoleNotFoundException("role not found for id ::" +roleName);
        }
        return role;
    }


    @Override
    public AppUser createAppUser(AppUser appUser) throws RoleNotFoundException, AppUserNotFoundException {
        log.info("creating appUser...");

        appUser.setId(RandomUtils.id());
        appUser.setActivated(true);
        appUser.setCreatedAt(new Date());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setConfirmPassword(passwordEncoder.encode(appUser.getConfirmPassword()));
        appUser.setPromoCode(appUser.getUsername() + RandomUtils.generateMin3Int());

        AppUser savedAppUser = userRepo.save(appUser);
        addRoleToUser(savedAppUser.getUsername(), ControllerVariables.USER_ROLE_NAME);
        addRoleToUser(savedAppUser.getUsername(), ControllerVariables.EDITOR_ROLE_NAME);

        return savedAppUser;
    }

    @Override
    public AppUserView createAppUserView(AppUser appUser) throws RoleNotFoundException,AppUserNotFoundException {
        log.info("creating appUser view...");
        AppUserView view = EntityToViewConverter.convertEntityToAppUserView(createAppUser(appUser));
        return view;
    }

    @Override
    public AppUser updateAppUser(AppUserForm form) throws AppUserNotFoundException {
        log.info("updating appUser...");
        AppUser appUser = FormToEntityConverter.convertFormToAppUser(form);
        AppUser updateUser = getAppUser(appUser.getId());
        appUser.setCreatedAt(updateUser.getCreatedAt());
        appUser.setActivated(updateUser.isActivated());
        appUser.setRoles(updateUser.getRoles());
        appUser.setUpdatedAt(DateUtils.currentDate());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setConfirmPassword(passwordEncoder.encode(appUser.getConfirmPassword()));

        AppUser saveUser = userRepo.save(appUser);
        return saveUser;
    }

    @Override
    public AppUserView updateAppUserView(AppUserForm form) throws AppUserNotFoundException {
        log.info("updating appUser view...");
        return EntityToViewConverter.convertEntityToAppUserView(updateAppUser(form));
    }

    @Override
    public void deleteAppUser(String userId) throws AppUserNotFoundException {
        log.info("deleting appUser by id :: " + userId + "...");
        userRepo.findById(userId).orElseThrow(()-> new AppUserNotFoundException("user not found"));
        userRepo.deleteById(userId);
    }

    @Override
    public void deleteAppUserDates(Long userId) throws AppUserNotFoundException {
        log.info("deleting appUser by id :: " + userId + "...");
        AppUserDates userDates = userDatesRepo.findById(userId).orElseThrow(()-> new AppUserNotFoundException("user not found"));
        userDatesRepo.delete(userDates);
    }

    @Override
    public List<AppUserView> listAppUserView(int page, int size) {
        log.info("list appUser view...");
        List<AppUser> appUserPage = userRepo.listAppUser(PageRequest.of(page, size));

        List<AppUser> appUsers = userRepo.listAppUserNotPageable();

        List<AppUserView> appUserViews = ListEntityToListViewConverter.paginateAppUserViewList(appUserPage,page,size, appUsers.size());

    return appUserViews;

    }


    @Override
    public List<AppUserView> listAppUserWithRoleEditor(int page, int size) {
        log.info("list appUser with role admin...");
        List<AppUser> appUsers = userRepo.listAppUser(PageRequest.of(page, size));
        List<AppUser> appUserList = new ArrayList<>();
        for (AppUser appUser : appUsers){
            userRepo.save(appUser);
            for (AppRole role : appUser.getRoles()){
                if (role.getName().equals(ControllerVariables.EDITOR_ROLE_NAME)){
                    appUserList.add(appUser);
                }
            }
        }
        List<AppUserView> userViews = appUserList.stream().map(appUser -> EntityToViewConverter.convertEntityToAppUserView(appUser)).collect(Collectors.toList());
        return userViews;
    }

    @Override
    public AppUserView appUserAdmin() throws AppUserNotFoundException {
        log.info("appUser admin...");
        boolean isAdmin = existsByUsername("admin");
        if (isAdmin){
            AppUser appUser = getAppUserByUsername("admin");
            return EntityToViewConverter.convertEntityToAppUserView(appUser);
        }
        return null;
    }

    @Override
    public AppUserView addRoleToUser(String username, String roleName) throws RoleNotFoundException, AppUserNotFoundException {
        log.info("add role to appUser..." +username +"..." +roleName);
        AppRole role = getRoleByRoleName(roleName);
        AppUser appUser = getAppUserByUsername(username);
        log.info("role and username found");

        appUser.getRoles().add(role);
        AppUserView view = EntityToViewConverter.convertEntityToAppUserView(appUser);

        return view;

    }

    @Override
    public AppUserView removeRoleToUser(String username, String roleName) throws RoleNotFoundException, AppUserNotFoundException {
        log.info("delete role to appUser..." +username +"..." +roleName);
        AppRole role = getRoleByRoleName(roleName);
        AppUser appUser = getAppUserByUsername(username);
        appUser.getRoles().remove(role);

        AppUserView view = EntityToViewConverter.convertEntityToAppUserView(appUser);

        return view;
    }

    @Override
    public List<AppUserView> findAllByUsername(String key, int page, int size) {
        log.info("filter appUser by username :: " +key);

        List<AppUser> appUsers = userRepo.findAllByUsername(key,PageRequest.of(page, size));

        List<AppUser> appUserList = userRepo.findAllByUsernameOrderByCreatedAtDesc(key);
        List<AppUserView> views = ListEntityToListViewConverter.paginateAppUserViewList(appUsers,page,size,appUserList.size());

        return views;
    }

    @Override
    public List<AppUserView> listAppUserDisabledByUsername(String key, int page, int size) {
        log.info("filter disabled appUser by username :: " +key);

        List<AppUser> appUsers = userRepo.listAppUserDisabledByUsername(key,PageRequest.of(page, size));

        List<AppUser> appUserList = userRepo.listAppUserDisabledByUsernameNotPageable(key);
        List<AppUserView> views = ListEntityToListViewConverter.paginateAppUserViewList(appUsers,page,size,appUserList.size());

        return views;
    }

    @Override
    public void enabledAppUser(String appUserId) throws AppUserNotFoundException {
        log.info("enabled user with id ::" +appUserId);
        AppUser appUser = getAppUser(appUserId);
        appUser.setActivated(true);
        userRepo.save(appUser);
    }

    @Override
    public void disabledAppUser(String appUserId) throws AppUserNotFoundException {
        log.info("disabled user with id ::" +appUserId);
        AppUser appUser = getAppUser(appUserId);
        appUser.setActivated(false);
        userRepo.save(appUser);
    }

}

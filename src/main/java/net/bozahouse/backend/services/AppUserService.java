package net.bozahouse.backend.services;

import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.entitie.NewsletterNotFoundException;
import net.bozahouse.backend.exception.entitie.RoleNotFoundException;
import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.forms.AppUserForm;
import net.bozahouse.backend.model.views.AppUserDatesView;
import net.bozahouse.backend.model.views.AppUserView;

import java.util.List;


public interface AppUserService {
    AppUser getAppUser(String userId) throws AppUserNotFoundException;

    AppUser getAppUserByUsername(String username) throws AppUserNotFoundException;

    AppRole getRoleByRoleName(String roleName) throws RoleNotFoundException;

    AppUser createAppUser(AppUser appUser) throws RoleNotFoundException, AppUserNotFoundException, NewsletterNotFoundException;

    AppUserView createAppUserView(AppUser appUser) throws RoleNotFoundException, NewsletterNotFoundException, AppUserNotFoundException;

    AppUser updateAppUser(AppUserForm form) throws AppUserNotFoundException;

    AppUserView getAppUserView(String userId) throws AppUserNotFoundException;

    AppUserView updateAppUserView(AppUserForm form) throws AppUserNotFoundException;

    void deleteAppUser(String userId) throws AppUserNotFoundException;

    void deleteAppUserDates(Long userId) throws AppUserNotFoundException;

    List<AppUserView> listAppUserView(int page, int size);

    boolean existsByUsername(String username);

    List<AppUserDatesView> appUserDatesList(String appUserId, int page, int size) throws AppUserNotFoundException;

    boolean existsByEmail(String email);

    boolean existsByPromoCode(String code);

    boolean existsByReferralCode(String referral);

    Double countAllByReferralCode(String referralCode);

    List<AppUserView> listAppUserWithRoleEditor(int page, int size);

    AppUserView appUserAdmin() throws AppUserNotFoundException;

    AppUserView addRoleToUser(String username, String roleName) throws RoleNotFoundException, AppUserNotFoundException;

    AppUserView removeRoleToUser(String username, String roleName) throws RoleNotFoundException, AppUserNotFoundException;

    List<AppUserView> findAllByUsername(String key, int page, int size);

    List<AppUserView> listAppUserDisabledByUsername(String key, int page, int size);

    AppUser getAppUserByEmail(String email) throws AppUserNotFoundException;

    void enabledAppUser(String appUserId) throws AppUserNotFoundException;

    void disabledAppUser(String appUserId) throws AppUserNotFoundException;
}

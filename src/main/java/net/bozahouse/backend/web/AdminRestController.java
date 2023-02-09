package net.bozahouse.backend.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.exception.entitie.*;
import net.bozahouse.backend.exception.form.ValidationFormException;
import net.bozahouse.backend.mappers.FormToEntityConverter;
import net.bozahouse.backend.model.entities.AppRole;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Newsletter;
import net.bozahouse.backend.model.entities.stats.AppUserStatistic;
import net.bozahouse.backend.model.entities.stats.OfferStatistic;
import net.bozahouse.backend.model.entities.stats.SubscriptionStatistic;
import net.bozahouse.backend.model.entities.stats.TalentStatistic;
import net.bozahouse.backend.model.forms.AppUserForm;
import net.bozahouse.backend.model.forms.NewsletterForm;
import net.bozahouse.backend.model.views.*;
import net.bozahouse.backend.security.ControllerVariables;
import net.bozahouse.backend.services.*;
import net.bozahouse.backend.services.email.EmailSender;
import net.bozahouse.backend.services.statistic.StatisticService;
import net.bozahouse.backend.utils.DateUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/api/admin/v1/")
@Tag(name = "AdminRestController", description = "rest api for admin")
public class AdminRestController {

    private StatisticService statisticService;
    private AppUserService userService;
    private AppRoleService roleService;
    private OfferService offerService;
    private TalentService talentService;
    private SubscriptionService subscriptionService;
    private AppUserService appUserService;
    private EmailSender sender;
    private NewsletterService newsletterService;
    private TestimonyService testimonyService;

    //about statistics
    @GetMapping("subscriptions/stats")
    List<SubscriptionStatistic> listSubscriptionStatistic(@RequestParam(name = "page",defaultValue = "0") int page,
                                                          @RequestParam(name = "size",defaultValue = "5") int size,
                                                          Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            return statisticService.listSubscriptionStatistic(page, size);
        }
        throw new AppUserNotFoundException("missing credentials") ;
    }

    @GetMapping("talents/stats")
    List<TalentStatistic> listTalentStatistic(@RequestParam(name = "page",defaultValue = "0") int page,
                                              @RequestParam(name = "size",defaultValue = "5") int size,
                                              Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            return statisticService.listTalentStatistic(page, size);
        }
        throw new AppUserNotFoundException("missing credentials") ;
    }

    @GetMapping("offers/stats")
    List<OfferStatistic> listOfferStatistic(@RequestParam(name = "page",defaultValue = "0") int page,
                                            @RequestParam(name = "size",defaultValue = "5") int size,
                                            Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            return statisticService.listOfferStatistic(page, size);
        }
        throw new AppUserNotFoundException("missing credentials") ;
    }

    @GetMapping("users/stats")
    List<AppUserStatistic> listAppUserStatisticList(@RequestParam(name = "page",defaultValue = "0") int page,
                                                    @RequestParam(name = "size",defaultValue = "5") int size,
                                                    Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            return statisticService.listAppUserStatistic(page, size);
        }
        throw new AppUserNotFoundException("missing credentials") ;
    }

    @DeleteMapping("talents/enable/{id}")
    public void enableTalent(@PathVariable(name = "id") String talentId,Authentication authentication) throws TalentNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            talentService.enableTalent(talentId);

        }
    }

    @DeleteMapping("talents/{id}")
    public void deleteTalent(@PathVariable(name = "id") String talentId,Authentication authentication) throws TalentNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            talentService.deleteTalent(talentId);
        }
    }

    @DeleteMapping("offers/enable/{id}")
    public void enableOffer(@PathVariable(name = "id") String offerId,Authentication authentication) throws OfferNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            offerService.enableOffer(offerId);

        }
    }

    @DeleteMapping("offers/{id}")
    public void deleteOffer(@PathVariable(name = "id") String offerId,Authentication authentication) throws OfferNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            offerService.deleteOffer(offerId);
        }
    }

    //about News
    //testOK
    @PostMapping("news")
    public NewsletterView saveNewsView(@RequestBody NewsletterForm form, Authentication authentication) throws AppUserNotFoundException, ValidationFormException, NewsletterNotFoundException, ParseException, NewsletterNotFoundException {
/*        if (!FormsValidator.validateNewsForm(form)) {
            throw new ValidationFormException("error validation form");
        }*/

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            if (form.getSendingDate().isEmpty()){

                Newsletter newsletter = FormToEntityConverter.convertFormToNews(form);
                newsletter.setUser(appUserService.getAppUserByUsername(authentication.getName()));
                //send email to user
                NewsletterView view = newsletterService.createNewsView(newsletter);
                sender.newsletters(newsletter);

                return view;
            }else {
                Date sendingDate = DateUtils.convertStringToDate(form.getSendingDate());
                Newsletter newsletter = FormToEntityConverter.convertFormToNews(form);
                newsletter.setSendingDate(sendingDate);
                newsletter.setUser(appUserService.getAppUserByUsername(authentication.getName()));

                NewsletterView view = newsletterService.createNewsView(newsletter);

                return view;
            }

        }
        throw new NewsletterNotFoundException("missing authorization for save this email...") ;
    }

    //testOK
    @PutMapping("news/{id}")
    public NewsletterView updateNewsView(@RequestBody NewsletterForm form,
                                         @PathVariable(name = "id") Long id,
                                         Authentication authentication) throws NewsletterNotFoundException, ParseException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            form.setId(id);

            return newsletterService.updateNewsView(form);
        }
        throw new NewsletterNotFoundException("error update news") ;
    }


    //testOK
    @GetMapping("news/{id}")
    public NewsletterView getNewsView(@PathVariable(name = "id") Long id, Authentication authentication) throws NewsletterNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            return newsletterService.getNewsView(id);
        }
        throw new NewsletterNotFoundException("error get news") ;
    }

    //testOK
    @GetMapping("news")
    public List<NewsletterView> listNewsView(@RequestParam(name = "page",defaultValue = "0") int page,
                                             @RequestParam(name = "size",defaultValue = "5") int size,
                                             Authentication authentication) throws NewsletterNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            return newsletterService.listNewsViewPageable(page, size);
        }
        throw new NewsletterNotFoundException("error list news") ;
    }


    //testOK
    @DeleteMapping("news/{id}")
    public void deleteNewsView(@PathVariable(name = "id") Long id, Authentication authentication) {

    try {
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            newsletterService.deleteNews(id);
        }
    }catch (NewsletterNotFoundException exception){
        log.error(exception.getMessage());
    }

    }


    //test ok
    @GetMapping("users/dates/{id}")
    public List<AppUserDatesView> appUserDatesViewList(@PathVariable(name = "id") String appUserId,
                                                       @RequestParam(name = "page",defaultValue = "0") int page,
                                                       @RequestParam(name = "size",defaultValue = "5") int size,
                                                       Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            return appUserService.appUserDatesList(appUserId,page, size);

        }
        throw new AppUserNotFoundException("missing credentials");
    }



    //testOK
    @PutMapping("users/add")
    public AppUserView addRoleToUser(@RequestParam(name = "username") String username,
                                     @RequestParam(name = "roleName") String roleName,
                                     Authentication authentication) throws AppUserNotFoundException, RoleNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            return  appUserService.addRoleToUser(username, roleName);
        }
        throw new AppUserNotFoundException("user or role not found");
    }

    //testOK
    @PutMapping("users/remove")
    public AppUserView removeRoleToUser(@RequestParam(name = "username") String username,
                                        @RequestParam(name = "roleName") String roleName,
                                        Authentication authentication) throws AppUserNotFoundException, RoleNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            return appUserService.removeRoleToUser(username, roleName);
        }
        throw new AppUserNotFoundException("user or role not found");
    }

    //testOK
    @GetMapping("users/admin")
    public AppUserView appUserViewAdmin(Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            return appUserService.appUserAdmin();
        }
        throw new AppUserNotFoundException("missing authorization for this resource");
    }

    //testOK
    @GetMapping("users/editor")
    public List<AppUserView> listAppUserWithRoleEditor(@RequestParam(name = "page",defaultValue = "0") int page,
                                                      @RequestParam(name = "size",defaultValue = "5") int size,
                                                      Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            return appUserService.listAppUserWithRoleEditor(page, size);
        }
        throw new AppUserNotFoundException("missing authorization for this resource");
    }

    //testOK
    @DeleteMapping("users/{id}")
    public void deleteAppUser(@PathVariable(name = "id") String userId, Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            appUserService.deleteAppUser(userId);
        }

    }

    //testOK
    @DeleteMapping("users/dates/{id}")
    public void deleteAppUserDates(@PathVariable(name = "id") Long userId, Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            appUserService.deleteAppUserDates(userId);
        }

    }


    //testOK
    @GetMapping("users/{id}")
    public AppUserView getAppUserView(@PathVariable(name = "id") String userId, Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME)
                        || a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME) ) ) {
            return userService.getAppUserView(userId);
        }
        throw new AppUserNotFoundException("missing authorization for this resource");
    }

    //testOK
    @PostMapping("users")
    public AppUserView saveAppUserView(@RequestBody AppUserForm form, Authentication authentication) throws RoleNotFoundException, NewsletterNotFoundException, AppUserNotFoundException, ParseException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {

            AppUser appUser = FormToEntityConverter.convertFormToAppUser(form);
            appUser.setBirthday(DateUtils.convertStringToDate(form.getBirthday()));
            return userService.createAppUserView(appUser);
        }
        throw new AppUserNotFoundException("missing authorization for this resource");
    }

    //testOK
    @PutMapping("users/{id}")
    public AppUserView updateAppUserView(@PathVariable(name = "id") String userId,
                                         @RequestBody AppUserForm form,
                                         Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {

            form.setId(userId);
            return userService.updateAppUserView(form);
        }
        throw new AppUserNotFoundException("missing authorization for this resource");
    }


    //testOK
    @GetMapping("users/{referralCode}/code")
    public Double countAllByReferralCode(@PathVariable String referralCode, Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            return userService.countAllByReferralCode(referralCode);
        }
        throw new AppUserNotFoundException("missing authorization for this resource");
    }


    @DeleteMapping("users/enabled/{id}")
    public void enabledAppUser(@PathVariable(name = "id") String appUserId,Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            userService.enabledAppUser(appUserId);

        }
    }

    @DeleteMapping("users/disabled/{id}")
    public void disabledAppUser(@PathVariable(name = "id") String appUserId,Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            userService.disabledAppUser(appUserId);

        }
    }

    //testOK
    @GetMapping("users/disabled/username")
    public List<AppUserView> searchAppUserDisabledByUsernameView(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                            @RequestParam(name = "page",defaultValue = "0") int page,
                                            @RequestParam(name = "size",defaultValue = "5") int size,
                                            Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            return userService.listAppUserDisabledByUsername("%" + keyword + "%",page, size);
        }
        throw new AppUserNotFoundException("missing authorization for this resource");
    }

    //about role
    //testOK
    @GetMapping("roles")
    public List<AppRole> listRole(@RequestParam(name = "page",defaultValue = "0") int page,
                                  @RequestParam(name = "size",defaultValue = "5") int size,
                                  Authentication authentication) throws RoleNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            return roleService.listRole(page, size);
        }
        throw new RoleNotFoundException("role not found or missing credentials");
    }


    //testOK
    @GetMapping("roles/{id}")
    public AppRole getRole(@PathVariable(name = "id") Integer roleId, Authentication authentication) throws RoleNotFoundException {
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            return roleService.getRole(roleId);
        }
        throw new RoleNotFoundException("role not found or missing credentials");
    }

    //testOK
    @PostMapping("roles")
    public void saveRole(@RequestBody AppRole role, Authentication authentication)  {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            roleService.createRole(role);
        }
    }

    //testOk
    @PutMapping("roles/{id}")
    public AppRole updateRole(@PathVariable(name = "id") Integer roleId, @RequestBody AppRole role, Authentication authentication) throws RoleNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            role.setId(roleId);
            return roleService.updateRole(role);
        }
        throw new RoleNotFoundException("role not found or missing credentials");
    }

    //testOK
    @DeleteMapping("roles/{id}")
    public void deleteRole(@PathVariable(name = "id") Integer roleId, Authentication authentication) throws RoleNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            roleService.deleteRole(roleId);
        }
    }

    //avoid
    @DeleteMapping("roles")
    public void deleteAllRole(Authentication authentication)  {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            roleService.deleteAllRole();
        }

    }

    //testOK
    @DeleteMapping("subscriptions/{id}")
    public void deleteSubscription(@PathVariable(name = "id") long paymentId, Authentication authentication) throws SubscriptionNotFoundException {
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
            subscriptionService.deleteSubscription(paymentId);
        }
    }



    //testOK
    @DeleteMapping("testimonies/{id}")
    public void deleteTestimonyView(@PathVariable(name = "id") Long id, Authentication authentication) {

        try {
            if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                    a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME) )) {
                testimonyService.deleteTestimony(id);
            }
        }catch (TestimonyNotFoundException exception){
            log.error(exception.getMessage());
        }

    }


    //
    @GetMapping("offers/disabled/filter")
    public List<OfferView> filterOfferDisabledView(@RequestParam(name = "title", defaultValue = "") String title,
                                           @RequestParam(name = "contract", defaultValue = "") String contract,
                                           @RequestParam(name = "workMode", defaultValue = "") String workMode,
                                           @RequestParam(name = "address", defaultValue = "") String address,
                                           @RequestParam(name = "experience", defaultValue = "") String experience,
                                           @RequestParam(name = "type", defaultValue = "") String type,
                                           @RequestParam(name = "domain", defaultValue = "") String domain,
                                           @RequestParam(name = "page",defaultValue = "0") int page,
                                           @RequestParam(name = "size",defaultValue = "5") int size,
                                           Authentication authentication) throws  AppUserNotFoundException {

        log.info(" beforeController " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            return offerService.filterOfferNotValidView(title, contract, workMode, address,  experience, type,domain, page, size);
        }

        throw new AppUserNotFoundException("missing credentials");
    }



    @GetMapping("talents/disabled/filter")
    public List<TalentView> filterTalentDisabledView(@RequestParam(name = "title", defaultValue = "") String title,
                                             @RequestParam(name = "contract", defaultValue = "") String contract,
                                             @RequestParam(name = "workMode", defaultValue = "") String workMode,
                                             @RequestParam(name = "address", defaultValue = "") String address,
                                             @RequestParam(name = "experience", defaultValue = "") String experience,
                                             @RequestParam(name = "type", defaultValue = "") String type,
                                             @RequestParam(name = "domain", defaultValue = "") String domain,
                                             @RequestParam(name = "page",defaultValue = "0") int page,
                                             @RequestParam(name = "size",defaultValue = "5") int size,
                                             Authentication authentication) throws  AppUserNotFoundException {

        log.info(" beforeController " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            return talentService.filterTalentNotValidView(title, contract, workMode, address,  experience, type,domain, page, size);
        }

        throw new AppUserNotFoundException("missing credentials");
    }

}

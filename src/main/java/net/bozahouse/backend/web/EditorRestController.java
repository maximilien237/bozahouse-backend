package net.bozahouse.backend.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.entitie.SubscriptionNotFoundException;
import net.bozahouse.backend.mappers.FormToEntityConverter;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Subscription;
import net.bozahouse.backend.model.forms.SubscriptionForm;
import net.bozahouse.backend.model.views.AppUserView;
import net.bozahouse.backend.model.views.SubscriptionView;
import net.bozahouse.backend.security.ControllerVariables;
import net.bozahouse.backend.services.AppUserService;
import net.bozahouse.backend.services.SubscriptionService;
import net.bozahouse.backend.services.email.EmailSender;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/api/editor/v1/")
@Tag(name = "EditorRestController", description = "rest api for editor")
public class EditorRestController {

    private AppUserService userService;
    private SubscriptionService subscriptionService;
    private AppUserService appUserService;
    private EmailSender sender;

    //private NewsletterService newsletterService;



    //about subscriptions
    //testOK
    @GetMapping("subscriptions")
    public List<SubscriptionView> listSubscriptionView(@RequestParam(name = "page",defaultValue = "0") int page,
                                                       @RequestParam(name = "size",defaultValue = "5") int size, Authentication authentication) throws  SubscriptionNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME) )) {
            return subscriptionService.listSubscriptionView(page, size);
        }
        throw new SubscriptionNotFoundException("subscription not found or missing credentials");
    }

    //testOK
    @GetMapping("subscriptions/inactive")
    public List<SubscriptionView> listInactiveSubscription(@RequestParam(name = "page",defaultValue = "0") int page,
                                                           @RequestParam(name = "size",defaultValue = "5") int size, Authentication authentication) throws SubscriptionNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME) )) {
            return subscriptionService.listInactiveSubscription(page, size);
        }
        throw new SubscriptionNotFoundException("subscription not found or missing credentials");
    }

    //testOK
    @GetMapping("subscriptions/{id}")
    public SubscriptionView getSubscriptionView(@PathVariable(name = "id") long paymentId, Authentication authentication) throws SubscriptionNotFoundException {
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME))) {
            return subscriptionService.getSubscriptionView(paymentId);
        }
        throw new SubscriptionNotFoundException("subscription not found or missing credentials");
    }

    //testOK
    @PostMapping("subscriptions")
    public SubscriptionView saveSubscriptionView(@RequestBody SubscriptionForm form, Authentication authentication) throws AppUserNotFoundException, SubscriptionNotFoundException, ParseException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME) )) {

            log.info(form.getPeriod() +" "+ form.getType() +" "+  form.getUsername());

            AppUser appUser = appUserService.getAppUserByUsername(form.getUsername());
            Subscription subscription = FormToEntityConverter.convertFormToSubscription(form);
            subscription.setInitiator(appUserService.getAppUserByUsername(authentication.getName()));
            subscription.setBeneficiary(appUser);

            log.info(subscription.getPeriod() +" "+ subscription.getType() +" "+  subscription.getBeneficiary().getUsername());

            //Subscription subscription1 = subscriptionService.currentSubscription(subscription);
            SubscriptionView view = subscriptionService.createSubscriptionView(subscription);

            sender.notifyRootAboutSubscription(subscription);
            sender.notifyAppUserAboutSubscription(subscription);

            return view;
        }
        throw new SubscriptionNotFoundException("subscription not found or missing credentials");
    }

    //testOK
    @GetMapping("subscriptions/{id}/lastUser")
    public SubscriptionView lastSubscriptionByUserView(@PathVariable(name = "id") String appUserId, Authentication authentication) throws AppUserNotFoundException, SubscriptionNotFoundException {
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME))) {
            return subscriptionService.lastSubscriptionByUserView(appUserId);
        }
        throw new SubscriptionNotFoundException("subscription not found or missing credentials");
    }

    //testOK
    @GetMapping("subscriptions/search")
    public List<SubscriptionView> listSubscriptionByPeriod(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                           @RequestParam(name = "page",defaultValue = "0") int page,
                                                           @RequestParam(name = "size",defaultValue = "5") int size, Authentication authentication) throws SubscriptionNotFoundException {
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME))) {
            return subscriptionService.listSubscriptionByType("%" + keyword + "%",page,size);
        }
        throw new SubscriptionNotFoundException("subscription not found or missing credentials");

    }

    //testOK
    @GetMapping("subscriptions/monthly")
    public List<SubscriptionView> listMonthlySubscription(@RequestParam(name = "page",defaultValue = "0") int page,
                                                          @RequestParam(name = "size",defaultValue = "5") int size, Authentication authentication) throws SubscriptionNotFoundException {


        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME))) {
            return subscriptionService.listMonthlySubscription(page,size);
        }
        throw new SubscriptionNotFoundException("subscription not found or missing credentials");

    }

    //testOK
    @GetMapping("subscriptions/annually")
    public List<SubscriptionView> listAnnuallySubscription(@RequestParam(name = "page",defaultValue = "0") int page,
                                                           @RequestParam(name = "size",defaultValue = "5") int size, Authentication authentication) throws SubscriptionNotFoundException {


        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME))) {
            return subscriptionService.listAnnuallySubscription(page,size);
        }
        throw new SubscriptionNotFoundException("subscription not found or missing credentials");

    }


    //testOK
    @GetMapping("users/username")
    public List<AppUserView> searchUserView(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                            @RequestParam(name = "page",defaultValue = "0") int page,
                                            @RequestParam(name = "size",defaultValue = "5") int size,
                                            Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME))) {
            return userService.findAllByUsername("%" + keyword + "%",page, size);
        }
        throw new AppUserNotFoundException("missing authorization for this resource");
    }

    //testOK
    @GetMapping("users")
    public List<AppUserView> listAppUserViews(@RequestParam(name = "page",defaultValue = "0") int page,
                                              @RequestParam(name = "size",defaultValue = "5") int size,
                                              Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME) )) {
            return userService.listAppUserView(page, size);
        }
        throw new AppUserNotFoundException("missing authorization for this resource");
    }

}

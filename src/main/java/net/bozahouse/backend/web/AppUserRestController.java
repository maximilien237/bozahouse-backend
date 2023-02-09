package net.bozahouse.backend.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.exception.entitie.*;
import net.bozahouse.backend.exception.form.OfferFormException;
import net.bozahouse.backend.exception.form.TalentFormException;
import net.bozahouse.backend.exception.form.ValidationFormException;
import net.bozahouse.backend.mappers.EntityToViewConverter;
import net.bozahouse.backend.mappers.FormToEntityConverter;
import net.bozahouse.backend.model.entities.Offer;
import net.bozahouse.backend.model.entities.Talent;
import net.bozahouse.backend.model.entities.Testimony;
import net.bozahouse.backend.model.forms.OfferForm;
import net.bozahouse.backend.model.forms.TalentForm;
import net.bozahouse.backend.model.forms.TestimonyForm;
import net.bozahouse.backend.model.views.*;
import net.bozahouse.backend.security.ControllerVariables;
import net.bozahouse.backend.services.*;
import net.bozahouse.backend.services.email.EmailSender;
import net.bozahouse.backend.utils.Constant;
import net.bozahouse.backend.utils.DateUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin
@RequestMapping("/api/user/v1/")
@Tag(name = "AppUserRestController", description = "rest api for user")
public class AppUserRestController {

    private TestimonyService testimonyService;
    private OfferService offerService;
    private TalentService talentService;
    private SubscriptionService subscriptionService;
    private AppUserService appUserService;
    private EmailSender sender;
    //private NewsletterService newsletterService;


    @GetMapping("users/account")
    public AppUserView profile(Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {

            AppUserView view = EntityToViewConverter.convertEntityToAppUserView(appUserService.getAppUserByUsername(authentication.getName()));
            return view;
        }
        throw new AppUserNotFoundException("missing credentials");

    }


    //
    @GetMapping("offers/filter")
    public List<OfferView> filterOfferView(@RequestParam(name = "title", defaultValue = "") String title,
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
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {
            return offerService.filterOfferView(title, contract, workMode, address,  experience, type,domain, page, size);
        }

        throw new AppUserNotFoundException("missing credentials");
    }


    @PostMapping("offers")
    public OfferView saveOfferView(@RequestBody OfferForm form, Authentication authentication) throws AppUserNotFoundException, OfferFormException, ParseException {
/*
        if (!FormsValidator.validateOfferForm(form)) {
            throw new OfferFormException("validation form error !");
        }*/

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME))) {


            Offer offer = FormToEntityConverter.convertFormToOffer(form);
            System.out.println(form.getEndOffer());
            offer.setEndOffer(DateUtils.convertStringToDate(form.getEndOffer()));
            offer.setUser(appUserService.getAppUserByUsername(authentication.getName()));
            OfferView view = offerService.createOfferView(offer);
            //send email
            sender.jobNotification(view);
            return view;

        }else if (authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.enterprise)
                || authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.employer) ) {

            Offer offer = FormToEntityConverter.convertFormToOffer(form);
            System.out.println("before send in offer " +form.getEndOffer());
            offer.setEndOffer(DateUtils.convertStringToDate(form.getEndOffer()));
            offer.setUser(appUserService.getAppUserByUsername(authentication.getName()));
            OfferView view = offerService.createOfferView(offer);
            //send email
            sender.jobNotification(view);
            return view;
        }
        throw new AppUserNotFoundException("missing credentials or subscription");

    }


    @PutMapping("offers/{id}")
    public OfferView updateOfferView(@RequestBody OfferForm form,
                                     @PathVariable(name = "id") String offerId,
                                     Authentication authentication) throws OfferNotFoundException, ValidationFormException, AppUserNotFoundException, ParseException {

  /*      if (!FormsValidator.validateOfferForm(form)) {
            throw new ValidationFormException("validation form error !");
        }*/

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME))) {
            form.setId(offerId);
            return offerService.updateOfferView(form);

        }else if ( authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.enterprise)
                || authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.employer)) {

            form.setId(offerId);
            return offerService.updateOfferView(form);
        }
        throw new AppUserNotFoundException("missing credentials or subscription");
    }

    @DeleteMapping("offers/{id}")
    public void deleteOffer(@PathVariable(name = "id") String offerId, Authentication authentication) throws OfferNotFoundException, AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME))) {
            offerService.deleteOffer(offerId);

        }else if ( authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.enterprise)
                || authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.employer)) {

            offerService.deleteOffer(offerId);
        }
    }

    @GetMapping("offers/{offerId}")
    public OfferView getOfferView(@PathVariable(name = "offerId") String offerId, Authentication authentication) throws OfferNotFoundException, AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME))) {

            return offerService.getOfferView(offerId);

        }else if (authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.enterprise)
                || authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.employer)
                || authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedNormalSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.talent) ) {

            return offerService.getOfferView(offerId);
        }
        throw new AppUserNotFoundException("missing credentials or subscription");
    }

    @GetMapping("offers")
    public List<OfferView> listOfferView(@RequestParam(name = "page",defaultValue = "0") int page,
                                         @RequestParam(name = "size",defaultValue = "5") int size,
                                         Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {
            return offerService.listOfferView(page, size);
        }

        throw new AppUserNotFoundException("missing credentials");
    }

    @GetMapping("offers/three")
    public List<OfferView> lastThreeOffer(Authentication authentication) throws AppUserNotFoundException {
        log.info("last three offer controller");
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {

          List<OfferView> offerViews = offerService.lastThreeOfferView();
          if (offerViews.isEmpty()){
              return null;
          }
          return offerViews;
        }
        throw new AppUserNotFoundException("missing credentials");
    }



    @GetMapping("talents/filter")
    public List<TalentView> filterTalentView(@RequestParam(name = "title", defaultValue = "") String title,
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
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {
            return talentService.filterTalentView(title, contract, workMode, address,  experience, type,domain, page, size);
        }

        throw new AppUserNotFoundException("missing credentials");
    }

    //about talent
    @PostMapping("talents")
    public TalentView saveTalentView(@RequestBody TalentForm form, Authentication authentication) throws AppUserNotFoundException, TalentFormException {
log.info("pass well");

    /*    if (!FormsValidator.validateTalentForm(form)) {
            log.error("error validation form");
            throw new TalentFormException("error talent form validation");
        }*/

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {

            Talent talent = FormToEntityConverter.convertFormToTalent(form);
            talent.setUser(appUserService.getAppUserByUsername(authentication.getName()));
            TalentView view = talentService.createTalentView(talent);

            // Send email to employer and firm
            sender.talentNotification(view);
            return view;

        }else if (authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription()
                && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.talent) ) {

            Talent talent = FormToEntityConverter.convertFormToTalent(form);
            talent.setUser(appUserService.getAppUserByUsername(authentication.getName()));
            TalentView view = talentService.createTalentView(talent);
            System.out.println("pass well two");
            // Send email to employer and firm
            sender.talentNotification(view);
            return view;
        }
        throw new AppUserNotFoundException("missing credentials or subscription");
    }

    @GetMapping("talents/{talentId}")
    public TalentView getTalentView(@PathVariable(name = "talentId") String talentId, Authentication authentication) throws TalentNotFoundException, AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.EDITOR_ROLE_NAME))) {

            return talentService.getTalentView(talentId);

        }else if (authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.talent)
                  || authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedNormalSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.enterprise)
                  || authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedNormalSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.employer) ) {
            return talentService.getTalentView(talentId);
        }
        throw new AppUserNotFoundException("missing credentials or subscription");
    }

    @GetMapping("talents")
    public List<TalentView> listTalentView(@RequestParam(name = "page",defaultValue = "0") int page,
                                           @RequestParam(name = "size",defaultValue = "5") int size, Authentication authentication) throws AppUserNotFoundException {


        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {
            return talentService.listTalentView(page, size);
        }

        throw new AppUserNotFoundException("missing credentials");
    }

    @GetMapping("talents/free")
    public List<TalentView> listFreeTalent(@RequestParam(name = "page",defaultValue = "0") int page,
                                           @RequestParam(name = "size",defaultValue = "5") int size, Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {
            return talentService.listFreeTalentView(page, size);
        }
        throw new AppUserNotFoundException("missing credentials");

    }


    @GetMapping("talents/formal")
    public List<TalentView> listFormalTalent(@RequestParam(name = "page",defaultValue = "0") int page,
                                             @RequestParam(name = "size",defaultValue = "5") int size,Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {
            return talentService.listFormalTalentView(page, size);
        }
        throw new AppUserNotFoundException("missing credentials");

    }

    @GetMapping("talents/three")
    public List<TalentView> lastThreeTalent(Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {
            return talentService.lastThreeTalentView();
        }
        throw new AppUserNotFoundException("missing credentials ");
    }


    @GetMapping("talents/search")
    public List<TalentView> searchTalentByTitleView(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                    @RequestParam(name = "page",defaultValue = "0") int page,
                                                    @RequestParam(name = "size",defaultValue = "5") int size,
                                                    Authentication authentication) throws AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {
            return talentService.listByTalentsTitleView("%" + keyword + "%",page,size);
        }
        throw new AppUserNotFoundException("missing credentials");
    }


    @PutMapping("talents/{id}")
    public TalentView updateTalentView(@RequestBody TalentForm form,
                                       @PathVariable(name = "id") String talentId, Authentication authentication) throws TalentNotFoundException, AppUserNotFoundException, ValidationFormException {

    /*    if (!FormsValidator.validateTalentForm(form)) {
            log.error("error validation form");
            throw new ValidationFormException("error validation form");
        }*/

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            form.setId(talentId);
            return  talentService.updateTalentView(form);

        }else if (authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription()
                && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.talent)) {
            form.setId(talentId);
            return  talentService.updateTalentView(form);
        }
        throw new AppUserNotFoundException("missing credentials or subscription");

    }


    @DeleteMapping("talents/disable/{id}")
    public void disableTalent(@PathVariable(name = "id") String talentId,Authentication authentication) throws TalentNotFoundException, AppUserNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            talentService.disableTalent(talentId);

        }else if ( authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.talent) ) {

            talentService.disableTalent(talentId);
        }
    }

    @DeleteMapping("offers/disable/{id}")
    public void disableOffer(@PathVariable(name = "id") String offerId,Authentication authentication) throws AppUserNotFoundException, OfferNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.ADMIN_ROLE_NAME))) {
            offerService.disableOffer(offerId);

        }else if ( authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.enterprise)
                || authentication != null && appUserService.getAppUserByUsername(authentication.getName()).isActivatedHostSubscription() && appUserService.getAppUserByUsername(authentication.getName()).getAccount().equals(Constant.employer)) {

            offerService.disableOffer(offerId);
        }
    }


    //testOK
    @GetMapping("subscriptions/user/{id}")
    public List<SubscriptionView> listSubscriptionByUser(@PathVariable(name = "id")String appUserId,
                                                         @RequestParam(name = "page",defaultValue = "0") int page,
                                                         @RequestParam(name = "size",defaultValue = "5") int size, Authentication authentication) throws AppUserNotFoundException, SubscriptionNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {
            return subscriptionService.listSubscriptionByUser(appUserId,page,size);
        }
        throw new SubscriptionNotFoundException("subscription not found or missing credentials");

    }


    @GetMapping("offers/user/{id}")
    public List<OfferView> listOfferByAppUserView(@PathVariable(name = "id")String appUserId,
                                                         @RequestParam(name = "page",defaultValue = "0") int page,
                                                         @RequestParam(name = "size",defaultValue = "5") int size, Authentication authentication) throws AppUserNotFoundException, SubscriptionNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {
            return offerService.listOfferByAppUserView(appUserId,page,size);
        }
        throw new SubscriptionNotFoundException("offers not found or missing credentials");

    }


    @GetMapping("testimonies/user/{id}")
    public List<TestimonyView> listTestimonyByAppUserView(@PathVariable(name = "id")String appUserId,
                                                  @RequestParam(name = "page",defaultValue = "0") int page,
                                                  @RequestParam(name = "size",defaultValue = "5") int size, Authentication authentication) throws AppUserNotFoundException, SubscriptionNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {
            return testimonyService.listTestimonyByUserView(appUserId,page,size);
        }
        throw new SubscriptionNotFoundException("testimonies not found or missing credentials");

    }


    @GetMapping("talents/user/{id}")
    public List<TalentView> listTalentByAppUserView(@PathVariable(name = "id")String appUserId,
                                                         @RequestParam(name = "page",defaultValue = "0") int page,
                                                         @RequestParam(name = "size",defaultValue = "5") int size, Authentication authentication) throws AppUserNotFoundException, SubscriptionNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME))) {
            return talentService.listTalentByAppUserView(appUserId,page,size);
        }
        throw new SubscriptionNotFoundException("talent not found or missing credentials");

    }

    //about Testimony
    //testOK
    @PostMapping("testimonies")
    public TestimonyView saveTestimonyView(@RequestBody TestimonyForm form, Authentication authentication) throws AppUserNotFoundException, ValidationFormException, TestimonyNotFoundException {
/*        if (!FormsValidator.validateTestimonyForm(form)) {
            throw new ValidationFormException("error validation form");
        }*/

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME) )) {

                Testimony testimony = FormToEntityConverter.convertFormToTestimony(form);
                testimony.setAuthor(appUserService.getAppUserByUsername(authentication.getName()));
                //send email to user
                TestimonyView view = testimonyService.createTestimonyView(testimony);
                sender.testimonies(testimony);

                return view;

        }
        throw new TestimonyNotFoundException("missing authorization for save this email...") ;
    }

    //testOK
    @PutMapping("testimonies/{id}")
    public TestimonyView updateTestimonyView(@RequestBody TestimonyForm form,
                                         @PathVariable(name = "id") Long id,
                                         Authentication authentication) throws TestimonyNotFoundException, ParseException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME) )) {
            form.setId(id);

            return testimonyService.updateTestimonyView(form);
        }
        throw new TestimonyNotFoundException("error update news") ;
    }


    //testOK
    @GetMapping("testimonies/{id}")
    public TestimonyView getTestimonyView(@PathVariable(name = "id") Long id, Authentication authentication) throws TestimonyNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME) )) {
            return testimonyService.getTestimonyView(id);
        }
        throw new TestimonyNotFoundException("error get testimony") ;
    }

    //testOK
    @GetMapping("testimonies")
    public List<TestimonyView> listTestimonyView(@RequestParam(name = "page",defaultValue = "0") int page,
                                             @RequestParam(name = "size",defaultValue = "5") int size,
                                             Authentication authentication) throws TestimonyNotFoundException {

        if (authentication != null && authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals(ControllerVariables.USER_ROLE_NAME) )) {
            return testimonyService.listTestimonyViewPageable(page, size);
        }
        throw new TestimonyNotFoundException("error list testimony") ;
    }



}

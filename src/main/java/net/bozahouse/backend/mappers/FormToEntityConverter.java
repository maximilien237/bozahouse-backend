package net.bozahouse.backend.mappers;

import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.model.entities.*;
import net.bozahouse.backend.model.forms.*;
import org.springframework.beans.BeanUtils;

@Slf4j
public class FormToEntityConverter {

    public static AppUser convertFormToAppUser(AppUserForm form) {
        log.info("convert form to AppUser...");
        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(form,appUser);
        return appUser;
    }

    public static Talent convertFormToTalent(TalentForm form) {
        log.info("convert form to talent...");
        Talent talent = new Talent();
        BeanUtils.copyProperties(form,talent);
        return talent;
    }

    public static Offer convertFormToOffer(OfferForm form) {
        log.info("convert form to offer...");
        Offer offer = new Offer();
        BeanUtils.copyProperties(form,offer);
        return offer;
    }

    public static Subscription convertFormToSubscription(SubscriptionForm form) {
        log.info("convert form to Subscription...");
        Subscription subscription = new Subscription();
        BeanUtils.copyProperties(form, subscription);
        return subscription;
    }

    public static Newsletter convertFormToNews(NewsletterForm form) {
        log.info("convert form to news...");
        Newsletter newsletter = new Newsletter();
        BeanUtils.copyProperties(form, newsletter);
        return newsletter;
    }

    public static Testimony convertFormToTestimony(TestimonyForm form) {
        log.info("convert form to news...");
        Testimony testimony = new Testimony();
        BeanUtils.copyProperties(form, testimony);
        return testimony;
    }


}

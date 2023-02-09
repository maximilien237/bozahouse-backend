package net.bozahouse.backend.mappers;

import net.bozahouse.backend.model.entities.*;
import net.bozahouse.backend.model.views.*;
import org.springframework.beans.BeanUtils;

public class EntityToViewConverter {

    public static AppUserView convertEntityToAppUserView(AppUser appUser) {
        AppUserView view = new AppUserView();
        BeanUtils.copyProperties(appUser,view);
        view.setRoles(appUser.getRoles());
        return view;
    }


    public static TalentView convertEntityToTalentView(Talent talent) {
        TalentView view = new TalentView();
        BeanUtils.copyProperties(talent,view);
        view.setLastname(talent.getUser().getLastname());
        view.setFirstname(talent.getUser().getFirstname());
        view.setUsername(talent.getUser().getUsername());
        view.setSex(talent.getUser().getSex());
        return view;
    }

    public static OfferView convertEntityToOfferView(Offer offer) {
        OfferView view = new OfferView();
        BeanUtils.copyProperties(offer,view);
        view.setSex(offer.getUser().getSex());
        view.setUsername(offer.getUser().getUsername());
        view.setLastname(offer.getUser().getLastname());
        view.setFirstname(offer.getUser().getFirstname());

        return view;
    }

    public static SubscriptionView convertEntityToSubscriptionView(Subscription subscription) {
        SubscriptionView view = new SubscriptionView();
        BeanUtils.copyProperties(subscription,view);
        view.setInitiatorLastname(subscription.getInitiator().getLastname());
        view.setBeneficiaryUsername(subscription.getBeneficiary().getUsername());
        view.setBeneficiaryAccount(subscription.getBeneficiary().getAccount());
        view.setBeneficiaryEmail(subscription.getBeneficiary().getEmail());
        return view;
    }

    public static NewsletterView convertEntityToNewsView(Newsletter newsletter) {
        NewsletterView view = new NewsletterView();
        BeanUtils.copyProperties(newsletter,view);
        view.setUsername(newsletter.getUser().getUsername());
        return view;
    }

    public static AppUserDatesView convertEntityToAppUserDatesView(AppUserDates userDates) {
        AppUserDatesView view = new AppUserDatesView();
        BeanUtils.copyProperties(userDates,view);
        view.setUsername(userDates.getUser().getUsername());
        return view;
    }

    public static TestimonyView convertEntityToTestimonyView(Testimony testimony) {
        TestimonyView view = new TestimonyView();
        BeanUtils.copyProperties(testimony,view);
        view.setAuthorUsername(testimony.getAuthor().getUsername());
        view.setAuthorLastname(testimony.getAuthor().getLastname());
        view.setAuthorFirstname(testimony.getAuthor().getFirstname());
        return view;
    }



}

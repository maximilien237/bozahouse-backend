package net.bozahouse.backend.services.email;

import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Newsletter;
import net.bozahouse.backend.model.entities.Subscription;
import net.bozahouse.backend.model.entities.Testimony;
import net.bozahouse.backend.model.views.AppUserView;
import net.bozahouse.backend.model.views.OfferView;
import net.bozahouse.backend.model.views.TalentView;

public interface EmailSender {
    void notifyAppUserAboutSubscription(Subscription subscription);

    void notifyRootAboutSubscription(Subscription subscription);

    void jobNotification(OfferView offerView);

    void talentNotification(TalentView talentView);

    void newsletters(Newsletter newsletter);

    void newslettersPlan();

    void validateAccount(AppUserView appUserView);

    void resetPassword(AppUser appUser);

    void testimonies(Testimony testimony);



    // void dailyGift();

    //void sendEmailRegister(AppUserView appUserView);

   //void resetPassword(AppUserView appUserView);

   //void firmNotification();
}

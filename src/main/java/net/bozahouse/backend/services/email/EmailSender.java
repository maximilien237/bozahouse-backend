package net.bozahouse.backend.services.email;


import net.bozahouse.backend.dtos.NewsletterDTO;
import net.bozahouse.backend.dtos.OfferDTO;
import net.bozahouse.backend.dtos.TalentDTO;
import net.bozahouse.backend.dtos.TestimonyDTO;
import net.bozahouse.backend.model.entities.*;

public interface EmailSender {

    void newsletters(NewsletterDTO newsletterDTO);

    void newslettersPlan();

    void validateAccount(AppUser appUser);

    void jobNotification(OfferDTO offerDTO);

    void talentNotification(TalentDTO talentDTO);

    void testimonies(TestimonyDTO testimonyDTO);
}

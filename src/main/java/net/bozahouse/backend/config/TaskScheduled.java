package net.bozahouse.backend.config;

import lombok.AllArgsConstructor;
import net.bozahouse.backend.services.OfferService;
import net.bozahouse.backend.services.email.EmailSender;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@AllArgsConstructor
public class TaskScheduled {
private EmailSender sender;
private OfferService offerService;



//TaskScheduled


    @Scheduled(cron = "0 0 0 * * *")
    public void reduceDurationOffer(){
        offerService.disabledOffer();
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void newsletters(){
        sender.newslettersPlan();
    }


/*    @Scheduled(cron = "0 0 0 * * *")
    public void dailyGift(){
        sender.dailyGift();
    }*/

/*    @Scheduled(fixedDelay = 120000)
    public void dailyGift(){
        sender.dailyGift();
    }*/

}

package net.bozahouse.backend.config;

import lombok.AllArgsConstructor;
import net.bozahouse.backend.services.OfferService;
import net.bozahouse.backend.services.SubscriptionService;
import net.bozahouse.backend.services.email.EmailSender;
import net.bozahouse.backend.services.statistic.StatisticService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@AllArgsConstructor
public class TaskScheduled {
private SubscriptionService subscriptionService;
private EmailSender sender;
private OfferService offerService;
private StatisticService statisticService;


//TaskScheduled
    @Scheduled(cron = "0 0 0 * * *")
    public void reduceAmountSubscription(){
        subscriptionService.reduceAmountSubscription();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void reduceDurationOffer(){
        offerService.reduceDurationOffer();
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void newsletters(){
        sender.newslettersPlan();
    }



    @Scheduled(cron = "0 0 20 * * *")
    public void subscriptionStatistics()  {
       statisticService.subscriptionStatistics();
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void talentStatistics() {
        statisticService.talentStatistics();
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void offerStatistics() {
        statisticService.offerStatistics();
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void appUserStatistics() {
        statisticService.appUserStatistics();
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

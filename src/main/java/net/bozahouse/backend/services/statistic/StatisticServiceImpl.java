package net.bozahouse.backend.services.statistic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Offer;
import net.bozahouse.backend.model.entities.Subscription;
import net.bozahouse.backend.model.entities.Talent;
import net.bozahouse.backend.model.entities.stats.AppUserStatistic;
import net.bozahouse.backend.model.entities.stats.OfferStatistic;
import net.bozahouse.backend.model.entities.stats.SubscriptionStatistic;
import net.bozahouse.backend.model.entities.stats.TalentStatistic;
import net.bozahouse.backend.repositories.*;
import net.bozahouse.backend.utils.DateUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService{

    private SubscriptionRepo subscriptionRepo;
    private SubscriptionStatisticRepo subscriptionStatisticRepo;
    private TalentRepo talentRepo;
    private TalentStatisticRepo talentStatisticRepo;
    private OfferRepo offerRepo;
    private OfferStatisticRepo offerStatisticRepo;
    private AppUserRepo userRepo;
    private AppUserStatisticRepo userStatisticRepo;



    @Override
    public void subscriptionStatistics(){
        log.info("task scheduled for subscription statistics, every midnight of every day");

        List<Subscription> subscriptions = subscriptionRepo.listSubscriptionNotPageable();
        List<Subscription> subscriptionList = new ArrayList<>();
        SubscriptionStatistic subscriptionStatistic = new SubscriptionStatistic();

        double totalDailyAmount =0.0;
        double totalAmount=0.0;
        int totalNumberOfSubscription=0;

        Date today = Calendar.getInstance().getTime();
        for (Subscription subscription : subscriptions){
            if (DateUtils.convertDateToString(subscription.getCreatedAt()).equalsIgnoreCase(DateUtils.convertDateToString(today))){

                System.out.println(DateUtils.convertDateToString(subscription.getCreatedAt()) + " "+DateUtils.convertDateToString(today));

                subscriptionList.add(subscription);
                totalDailyAmount = totalDailyAmount + subscription.getInitAmount();
                subscriptionStatistic.setTotalDailyAmount(totalDailyAmount);

            }

        }

        System.out.println(subscriptionStatistic.getTotalDailyAmount() + " " +subscriptionStatistic.getNumberOfSubscriptionPerDay());

        subscriptionStatistic.setNumberOfSubscriptionPerDay(subscriptionList.size());

       // subscriptionStatistic= subscriptionStatisticRepo.save(subscriptionStatistic);

        System.out.println(subscriptionList.size() + " " +totalDailyAmount);


        subscriptionStatistic.setCreatedAt(DateUtils.currentDate());
        subscriptionStatistic = subscriptionStatisticRepo.save(subscriptionStatistic);

        List<SubscriptionStatistic> subscriptionStatistics = subscriptionStatisticRepo.listSubscriptionStatisticNotPageable();
        for (SubscriptionStatistic s: subscriptionStatistics){
            totalAmount = totalAmount + s.getTotalDailyAmount();
            totalNumberOfSubscription = totalNumberOfSubscription + s.getNumberOfSubscriptionPerDay();

            System.out.println(totalNumberOfSubscription + " "+totalAmount);

            subscriptionStatistic.setTotalNumberOfSubscription(totalNumberOfSubscription);
            subscriptionStatistic.setTotalAmount(totalAmount);
            subscriptionStatistic.setTotalSubscription(subscriptions.size());
            subscriptionStatisticRepo.save(subscriptionStatistic);
        }

    }

    @Override
    public List<SubscriptionStatistic> listSubscriptionStatistic(int page, int size){
        List<SubscriptionStatistic> statistics = subscriptionStatisticRepo.listSubscriptionStatistic(PageRequest.of(page, size));
        SubscriptionStatistic statistic = statistics.get(0);
        statistic.setTotalSubscription(subscriptionRepo.listSubscriptionNotPageable().size());
        statistic.setCurrentPage(page);
        statistic.setPageSize(size);

        int realSize = subscriptionStatisticRepo.listSubscriptionStatisticNotPageable().size();
        if (realSize % 5 == 0){
            statistic.setTotalPages(realSize/5);
        }else {
            statistic.setTotalPages((realSize/5) +1);
        }

     
        return statistics;
    }

    @Override
    public void talentStatistics() {
        log.info("task scheduled for talent statistics, every midnight of every day");

        List<Talent> talents = talentRepo.listTalentNotPageable();
        List<Talent> talentList = new ArrayList<>();


        Date today = Calendar.getInstance().getTime();
        for (Talent talent : talents){
            if (DateUtils.convertDateToString(talent.getPublishedAt()).equalsIgnoreCase(DateUtils.convertDateToString(today))){
                talentList.add(talent);
            }

            }

        TalentStatistic talentStatistic = new TalentStatistic();
        talentStatistic.setCreatedAt(DateUtils.currentDate());
        talentStatistic.setTotalTalentPerDay(talentList.size());
        talentStatistic.setTotalTalent(talentRepo.count());
        talentStatistic.setTotalTalentNotValid(talentRepo.listTalentValidFalseNotPageable().size());
        talentStatistic.setTotalTalentValid(talentRepo.listTalentNotPageable().size());
        talentStatisticRepo.save(talentStatistic);
    }

    @Override
    public List<TalentStatistic> listTalentStatistic(int page, int size){
        List<TalentStatistic> statistics = talentStatisticRepo.listTalentStatistic(PageRequest.of(page, size));
        TalentStatistic statistic = statistics.get(0);
        statistic.setCurrentPage(page);
        statistic.setPageSize(size);

        int realSize = talentStatisticRepo.listTalentStatisticNotPageable().size();
        if (realSize % 5 == 0){
            statistic.setTotalPages(realSize/5);
        }else {
            statistic.setTotalPages((realSize/5) +1);
        }

        return statistics;
    }
    @Override
    public void offerStatistics() {
        log.info("task scheduled for offer statistics, every midnight of every day");

        List<Offer> offers = offerRepo.listOfferNotPageable();
        List<Offer> offerList = new ArrayList<>();

        Date today = Calendar.getInstance().getTime();
        for (Offer offer : offers){
            if (DateUtils.convertDateToString(offer.getPublishedAt()).equalsIgnoreCase(DateUtils.convertDateToString(today))){
                offerList.add(offer);
            }

        }

        OfferStatistic offerStatistic = new OfferStatistic();
        offerStatistic.setCreatedAt(DateUtils.currentDate());
        offerStatistic.setTotalOfferPerDay(offerList.size());
        offerStatistic.setTotalOffer(offerRepo.count());
        offerStatistic.setTotalOfferNotValid(offerRepo.listOfferValidFalseNotPageable().size());
        offerStatistic.setTotalOfferValid(offerRepo.listOfferNotPageable().size());
        offerStatisticRepo.save(offerStatistic);
    }

    @Override
    public List<OfferStatistic> listOfferStatistic(int page, int size){
        List<OfferStatistic> statistics = offerStatisticRepo.listOfferStatistic(PageRequest.of(page, size));
        OfferStatistic statistic = statistics.get(0);
        statistic.setCurrentPage(page);
        statistic.setPageSize(size);

        int realSize = offerStatisticRepo.listOfferStatisticNotPageable().size();
        if (realSize % 5 == 0){
            statistic.setTotalPages(realSize/5);
        }else {
            statistic.setTotalPages((realSize/5) +1);
        }

        return statistics;
    }


    @Override
    public void appUserStatistics() {
        log.info("task scheduled for user statistics, every midnight of every day");

       List<AppUser> appUsers = userRepo.listAppUserNotPageable();
       List<AppUser> appUserList = new ArrayList<>();

        Date today = Calendar.getInstance().getTime();
        for (AppUser appUser : appUsers){
            if (DateUtils.convertDateToString(appUser.getCreatedAt()).equalsIgnoreCase(DateUtils.convertDateToString(today))){
                appUserList.add(appUser);
            }
        }

        AppUserStatistic userStatistic = new AppUserStatistic();
        userStatistic.setCreatedAt(DateUtils.currentDate());
        userStatistic.setTotalAppUserPerDay(appUserList.size());
        userStatistic.setTotalAppUser(userRepo.count());
        userStatistic.setTotalAppUserDisabled(userRepo.listAppUserByActivatedFalseNotPageable().size());
        userStatistic.setTotalAppUserActivated(userRepo.listAppUserNotPageable().size());
        userStatistic.setTotalAppUserHostSubscription(userRepo.listAppUserHost().size());
        userStatistic.setTotalAppUserNormalSubscription(userRepo.listAppUserNormal().size());
        userStatisticRepo.save(userStatistic);
    }

    @Override
    public List<AppUserStatistic> listAppUserStatistic(int page, int size){
        List<AppUserStatistic> statistics = userStatisticRepo.listAppUserStatistic(PageRequest.of(page, size));
        AppUserStatistic statistic = statistics.get(0);
        statistic.setCurrentPage(page);
        statistic.setPageSize(size);

        int realSize = userStatisticRepo.listAppUserStatisticNotPageable().size();
        if (realSize % 5 == 0){
            statistic.setTotalPages(realSize/5);
        }else {
            statistic.setTotalPages((realSize/5) +1);
        }

        return statistics;
    }











}

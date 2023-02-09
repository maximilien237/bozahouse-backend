package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.entitie.SubscriptionNotFoundException;
import net.bozahouse.backend.mappers.EntityToViewConverter;
import net.bozahouse.backend.mappers.ListEntityToListViewConverter;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Offer;
import net.bozahouse.backend.model.entities.Subscription;
import net.bozahouse.backend.model.entities.Talent;
import net.bozahouse.backend.model.views.SubscriptionView;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.repositories.OfferRepo;
import net.bozahouse.backend.repositories.SubscriptionRepo;
import net.bozahouse.backend.repositories.TalentRepo;
import net.bozahouse.backend.utils.Constant;
import net.bozahouse.backend.utils.DateUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    private SubscriptionRepo subscriptionRepo;
    private AppUserRepo userRepo;
    private TalentRepo talentRepo;
    private OfferRepo offerRepo;

    @Override
    public Subscription getSubscription(long id) throws SubscriptionNotFoundException {
        log.info("getting Subscription by id :: " +id + "...");
        Optional<Subscription> optionalSubscription = subscriptionRepo.findById(id);
        Subscription subscription;
        if (optionalSubscription.isPresent()){
            subscription = optionalSubscription.get();
        }else {
            throw new SubscriptionNotFoundException("Subscription not found for id ::" +id);
        }
        return subscription;
    }
    @Override
    public SubscriptionView getSubscriptionView(long id) throws SubscriptionNotFoundException {
        log.info("getting Subscription View  by id :: " + id + "...");
        Subscription subscription = getSubscription(id);
        SubscriptionView subscriptionView = EntityToViewConverter.convertEntityToSubscriptionView(subscription);
        return subscriptionView;
    }

    @Override
    public Subscription currentSubscription(Subscription subscription){
        log.info(subscription.getPeriod() +" "+ subscription.getType() +" "+  subscription.getBeneficiary().getUsername());
        return subscriptionRepo.save(subscription);
    }
    @Override
    public Subscription createSubscription(Subscription subscription) throws AppUserNotFoundException {
        log.info("creating Subscription...");
        AppUser appUser = subscription.getBeneficiary();
        switch (appUser.getAccount()) {
            case Constant.enterprise:
                switch (subscription.getType()) {
                    case Constant.host:
                        switch (subscription.getPeriod()) {
                            case Constant.monthlySubscription:
                                subscription.setAmount(1000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus1Month());
                                break;
                            case Constant.twoMonthlySubscription:
                                subscription.setAmount(2000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus2Months());
                                break;
                            case Constant.trimesterSubscription:
                                subscription.setAmount(3000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus3Months());
                                break;
                            case Constant.fourMonthlySubscription:
                                subscription.setAmount(4000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus4Months());
                                break;
                            case Constant.fiveMonthlySubscription:
                                subscription.setAmount(5000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus5Months());
                                break;
                            case Constant.semesterSubscription:
                                subscription.setAmount(6000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus6Months());
                                break;
                            case Constant.sevenMonthlySubscription:
                                subscription.setAmount(7000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus7Months());
                                break;
                            case Constant.eightMonthlySubscription:
                                subscription.setAmount(8000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus8Months());
                                break;
                            case Constant.nineMonthlySubscription:
                                subscription.setAmount(9000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus9Months());
                                break;
                            case Constant.tenSubscription:
                                subscription.setAmount(10000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus10Months());
                                break;
                            case Constant.elevenMonthlySubscription:
                                subscription.setAmount(11000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus11Months());
                                break;
                            case Constant.annuallySubscription:
                                subscription.setAmount(11500);
                                subscription.setEndSubscription(DateUtils.currentDatePlus1Year());
                                break;
                        }
                    break;

                    case Constant.normal:
                        switch (subscription.getPeriod()) {

                            case Constant.monthlySubscription:
                                subscription.setAmount(2000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus1Month());
                                break;
                            case Constant.twoMonthlySubscription:
                                subscription.setAmount(4000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus2Months());
                                break;
                            case Constant.trimesterSubscription:
                                subscription.setAmount(6000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus3Months());
                                break;
                            case Constant.fourMonthlySubscription:
                                subscription.setAmount(8000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus4Months());
                                break;
                            case Constant.fiveMonthlySubscription:
                                subscription.setAmount(10000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus5Months());
                                break;
                            case Constant.semesterSubscription:
                                subscription.setAmount(12000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus6Months());
                                break;
                            case Constant.sevenMonthlySubscription:
                                subscription.setAmount(14000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus7Months());
                                break;
                            case Constant.eightMonthlySubscription:
                                subscription.setAmount(16000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus8Months());
                                break;
                            case Constant.nineMonthlySubscription:
                                subscription.setAmount(18000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus9Months());
                                break;
                            case Constant.tenSubscription:
                                subscription.setAmount(20000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus10Months());
                                break;
                            case Constant.elevenMonthlySubscription:
                                subscription.setAmount(22000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus11Months());
                                break;
                            case Constant.annuallySubscription:
                                subscription.setAmount(23000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus1Year());
                                break;
                        }
                    break;
                }
            break;

            case Constant.employer:
                switch (subscription.getType()) {
                    case Constant.host:
                        switch (subscription.getPeriod()) {

                            case Constant.monthlySubscription:
                                subscription.setAmount(450);
                                subscription.setEndSubscription(DateUtils.currentDatePlus1Month());
                                break;
                            case Constant.twoMonthlySubscription:
                                subscription.setAmount(900);
                                subscription.setEndSubscription(DateUtils.currentDatePlus2Months());
                                break;
                            case Constant.trimesterSubscription:
                                subscription.setAmount(1350);
                                subscription.setEndSubscription(DateUtils.currentDatePlus3Months());
                                break;
                            case Constant.fourMonthlySubscription:
                                subscription.setAmount(1800);
                                subscription.setEndSubscription(DateUtils.currentDatePlus4Months());
                                break;
                            case Constant.fiveMonthlySubscription:
                                subscription.setAmount(2250);
                                subscription.setEndSubscription(DateUtils.currentDatePlus5Months());
                                break;
                            case Constant.semesterSubscription:
                                subscription.setAmount(2700);
                                subscription.setEndSubscription(DateUtils.currentDatePlus6Months());
                                break;
                            case Constant.sevenMonthlySubscription:
                                subscription.setAmount(3150);
                                subscription.setEndSubscription(DateUtils.currentDatePlus7Months());
                                break;
                            case Constant.eightMonthlySubscription:
                                subscription.setAmount(3600);
                                subscription.setEndSubscription(DateUtils.currentDatePlus8Months());
                                break;
                            case Constant.nineMonthlySubscription:
                                subscription.setAmount(4050);
                                subscription.setEndSubscription(DateUtils.currentDatePlus9Months());
                                break;
                            case Constant.tenSubscription:
                                subscription.setAmount(4500);
                                subscription.setEndSubscription(DateUtils.currentDatePlus10Months());
                                break;
                            case Constant.elevenMonthlySubscription:
                                subscription.setAmount(4950);
                                subscription.setEndSubscription(DateUtils.currentDatePlus11Months());
                                break;
                            case Constant.annuallySubscription:
                                subscription.setAmount(5000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus1Year());
                                break;
                        }
                    break;

                    case Constant.normal:
                        switch (subscription.getPeriod()) {

                            case Constant.monthlySubscription:
                                subscription.setAmount(1500);
                                subscription.setEndSubscription(DateUtils.currentDatePlus1Month());
                                break;
                            case Constant.twoMonthlySubscription:
                                subscription.setAmount(3000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus2Months());
                                break;
                            case Constant.trimesterSubscription:
                                subscription.setAmount(4500);
                                subscription.setEndSubscription(DateUtils.currentDatePlus3Months());
                                break;
                            case Constant.fourMonthlySubscription:
                                subscription.setAmount(6000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus4Months());
                                break;
                            case Constant.fiveMonthlySubscription:
                                subscription.setAmount(7500);
                                subscription.setEndSubscription(DateUtils.currentDatePlus5Months());
                                break;
                            case Constant.semesterSubscription:
                                subscription.setAmount(9000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus6Months());
                                break;
                            case Constant.sevenMonthlySubscription:
                                subscription.setAmount(10500);
                                subscription.setEndSubscription(DateUtils.currentDatePlus7Months());
                                break;
                            case Constant.eightMonthlySubscription:
                                subscription.setAmount(12000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus8Months());
                                break;
                            case Constant.nineMonthlySubscription:
                                subscription.setAmount(13500);
                                subscription.setEndSubscription(DateUtils.currentDatePlus9Months());
                                break;
                            case Constant.tenSubscription:
                                subscription.setAmount(15000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus10Months());
                                break;
                            case Constant.elevenMonthlySubscription:
                                subscription.setAmount(16500);
                                subscription.setEndSubscription(DateUtils.currentDatePlus11Months());
                                break;
                            case Constant.annuallySubscription:
                                subscription.setAmount(17000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus1Year());
                                break;
                        }
                    break;
                }
            break;

            case Constant.talent:
                switch (subscription.getType()) {
                    case Constant.host:
                        switch (subscription.getPeriod()) {
                            case Constant.monthlySubscription:
                                subscription.setAmount(350);
                                subscription.setEndSubscription(DateUtils.currentDatePlus1Month());
                                break;
                            case Constant.twoMonthlySubscription:
                                subscription.setAmount(700);
                                subscription.setEndSubscription(DateUtils.currentDatePlus2Months());
                                break;
                            case Constant.trimesterSubscription:
                                subscription.setAmount(1050);
                                subscription.setEndSubscription(DateUtils.currentDatePlus3Months());
                                break;
                            case Constant.fourMonthlySubscription:
                                subscription.setAmount(1400);
                                subscription.setEndSubscription(DateUtils.currentDatePlus4Months());
                                break;
                            case Constant.fiveMonthlySubscription:
                                subscription.setAmount(1750);
                                subscription.setEndSubscription(DateUtils.currentDatePlus5Months());
                                break;
                            case Constant.semesterSubscription:
                                subscription.setAmount(2100);
                                subscription.setEndSubscription(DateUtils.currentDatePlus6Months());
                                break;
                            case Constant.sevenMonthlySubscription:
                                subscription.setAmount(2450);
                                subscription.setEndSubscription(DateUtils.currentDatePlus7Months());
                                break;
                            case Constant.eightMonthlySubscription:
                                subscription.setAmount(2800);
                                subscription.setEndSubscription(DateUtils.currentDatePlus8Months());
                                break;
                            case Constant.nineMonthlySubscription:
                                subscription.setAmount(3150);
                                subscription.setEndSubscription(DateUtils.currentDatePlus9Months());
                                break;
                            case Constant.tenSubscription:
                                subscription.setAmount(3500);
                                subscription.setEndSubscription(DateUtils.currentDatePlus10Months());
                                break;
                            case Constant.elevenMonthlySubscription:
                                subscription.setAmount(3850);
                                subscription.setEndSubscription(DateUtils.currentDatePlus11Months());
                                break;
                            case Constant.annuallySubscription:
                                subscription.setAmount(4000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus1Year());
                                break;
                        }
                    break;

                    case Constant.normal:
                        switch (subscription.getPeriod()) {

                            case Constant.monthlySubscription:
                                subscription.setAmount(1000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus1Month());
                                break;
                            case Constant.twoMonthlySubscription:
                                subscription.setAmount(2000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus2Months());
                                break;
                            case Constant.trimesterSubscription:
                                subscription.setAmount(3000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus3Months());
                                break;
                            case Constant.fourMonthlySubscription:
                                subscription.setAmount(4000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus4Months());
                                break;
                            case Constant.fiveMonthlySubscription:
                                subscription.setAmount(5000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus5Months());
                                break;
                            case Constant.semesterSubscription:
                                subscription.setAmount(6000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus6Months());
                                break;
                            case Constant.sevenMonthlySubscription:
                                subscription.setAmount(7000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus7Months());
                                break;
                            case Constant.eightMonthlySubscription:
                                subscription.setAmount(8000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus8Months());
                                break;
                            case Constant.nineMonthlySubscription:
                                subscription.setAmount(9000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus9Months());
                                break;
                            case Constant.tenSubscription:
                                subscription.setAmount(10000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus10Months());
                                break;
                            case Constant.elevenMonthlySubscription:
                                subscription.setAmount(11000);
                                subscription.setEndSubscription(DateUtils.currentDatePlus11Months());
                                break;
                            case Constant.annuallySubscription:
                                subscription.setAmount(11500);
                                subscription.setEndSubscription(DateUtils.currentDatePlus1Year());
                                break;
                        }
                    break;
                }
            break;
        }


        subscription.setInitAmount(subscription.getAmount());
        subscription.setCreatedAt(DateUtils.currentDate());
        subscription.setInitDuration(DateUtils.diff_in_days(DateUtils.currentDate(),subscription.getEndSubscription()));
        subscription.setDuration(subscription.getInitDuration());
        subscription.setDailyAmount(subscription.getInitAmount()/ subscription.getInitDuration());
        subscription.setActivated(true);

        Subscription subscriptionSave = subscriptionRepo.save(subscription);

        if (subscriptionSave.getType().equals(Constant.host)){
            appUser.setActivatedHostSubscription(true);
            appUser.setHostSubscriptionCounter(appUser.getHostSubscriptionCounter() + 1);
            userRepo.save(appUser);
        }

        if (subscriptionSave.getType().equals(Constant.normal)){
            appUser.setActivatedNormalSubscription(true);
            appUser.setNormalSubscriptionCounter(appUser.getNormalSubscriptionCounter() + 1);
            userRepo.save(appUser);
        }

        if (userRepo.existsByPromoCode(subscriptionSave.getBeneficiary().getReferralCode())) {
            AppUser appUser1 = userRepo.findByPromoCode(subscriptionSave.getBeneficiary().getReferralCode()).orElseThrow(()-> new AppUserNotFoundException("user not found"));

            if (appUser1.getDiscountDate() == null) {
                appUser1.setDiscountDate(DateUtils.currentDate());
                appUser1 = userRepo.save(appUser);
                List<Subscription> subscriptions = subscriptionRepo.listSubscriptionByBeneficiaryNotPageable(appUser1);
                Subscription subscription1 = subscriptions.get(0);

                if (subscription1 != null) {


                    if (!subscription1.isActivated()){
                        Date today = Calendar.getInstance().getTime();
                        subscription1.setActivated(true);
                        subscription1.setEndSubscription(today);
                    }

                    subscription1.setUpdatedAt(DateUtils.currentDate());

                    switch (appUser1.getAccount()){
                        case Constant.enterprise:
                            if (Constant.normal.equals(subscription1.getType())) {
                                subscription1.setAmount(subscription1.getAmount() + 67);
                                subscription1.setEndSubscription(DateUtils.givenDatePlus1Day(subscription1.getEndSubscription()));
                                subscription1.setInitAmount(subscription1.getAmount());
                            } else if (Constant.host.equals(subscription1.getType())) {
                                subscription1.setAmount(subscription1.getAmount() + 34);
                                subscription1.setEndSubscription(DateUtils.givenDatePlus1Day(subscription1.getEndSubscription()));
                                subscription1.setInitAmount(subscription1.getAmount());
                            }
                            break;
                        case Constant.employer:
                            switch (subscription1.getType()) {
                                case Constant.normal:
                                    subscription1.setAmount(subscription1.getAmount() + 50);
                                    subscription1.setEndSubscription(DateUtils.givenDatePlus1Day(subscription1.getEndSubscription()));
                                    subscription1.setInitAmount(subscription1.getAmount());
                                    break;
                                case Constant.host:
                                    subscription1.setAmount(subscription1.getAmount() + 15);
                                    subscription1.setEndSubscription(DateUtils.givenDatePlus1Day(subscription1.getEndSubscription()));
                                    subscription1.setInitAmount(subscription1.getAmount());
                                    break;
                            }
                            break;

                        case Constant.talent:
                            if (Constant.normal.equals(subscription1.getType())) {
                                subscription1.setAmount(subscription1.getAmount() + 34);
                                subscription1.setEndSubscription(DateUtils.givenDatePlus1Day(subscription1.getEndSubscription()));
                                subscription1.setInitAmount(subscription1.getAmount());

                            } else if (Constant.host.equals(subscription1.getType())) {
                                subscription1.setAmount(subscription1.getAmount() + 11);
                                subscription1.setEndSubscription(DateUtils.givenDatePlus1Day(subscription1.getEndSubscription()));
                                subscription1.setInitAmount(subscription1.getAmount());
                            }
                            break;
                    }


                    subscription1.setInitDuration(DateUtils.diff_in_days(DateUtils.currentDate(), subscription1.getEndSubscription()));
                    subscription1.setDuration(subscription1.getInitDuration());
                    subscription1.setDailyAmount(subscription1.getInitAmount() / subscription1.getInitDuration());
                    subscription1.setNumberOfUpdate(subscription1.getNumberOfUpdate() + 1);

                    Subscription subscriptionSave1 = subscriptionRepo.save(subscription1);

                    if (subscriptionSave1.getType().equals(Constant.host)){
                        appUser1.setActivatedHostSubscription(true);
                        appUser1.setHostSubscriptionCounter(appUser1.getHostSubscriptionCounter() + 1);
                        userRepo.save(appUser1);
                    }

                    if (subscriptionSave1.getType().equals(Constant.normal)){
                        appUser1.setActivatedNormalSubscription(true);
                        appUser1.setNormalSubscriptionCounter(appUser1.getNormalSubscriptionCounter() + 1);
                        userRepo.save(appUser1);
                    }

                    log.info("send email to talk about prorogation because your promote code is used date!" + subscription1.getEndSubscription());
                }

            }
        }

        return subscriptionSave;
    }
    @Override
    public SubscriptionView createSubscriptionView(Subscription subscription) throws AppUserNotFoundException, ParseException {
        log.info("creating Subscription view...");
        SubscriptionView view = EntityToViewConverter.convertEntityToSubscriptionView(createSubscription(subscription));
        return view;
    }

    @Override
    public void deleteSubscription(long id) throws SubscriptionNotFoundException {
        log.info("deleting Subscription by id :: " + id + "...");
       Subscription subscription = subscriptionRepo.findById(id).orElseThrow(()-> new SubscriptionNotFoundException("Subscription not found"));
       AppUser appUser = subscription.getBeneficiary();

       if (subscription.getType().equals(Constant.normal)){
           appUser.setActivatedNormalSubscription(false);
           appUser.setNormalSubscriptionCounter(appUser.getNormalSubscriptionCounter() - 1);
           userRepo.save(appUser);
       }

       if (subscription.getType().equals(Constant.host)){
           appUser.setActivatedHostSubscription(false);
           appUser.setHostSubscriptionCounter(appUser.getHostSubscriptionCounter() - 1);
           userRepo.save(appUser);
       }

        subscriptionRepo.deleteById(id);

    }

    @Override
    public List<SubscriptionView> listSubscriptionView(int page, int size) {

        log.info("list appUser view...");
        List<Subscription> subscriptions = subscriptionRepo.listSubscription(PageRequest.of(page, size));

        List<Subscription> subscriptionList = subscriptionRepo.listSubscriptionNotPageable();

        List<SubscriptionView> subscriptionViews = ListEntityToListViewConverter.paginateSubscriptionView(subscriptions,page, size, subscriptionList.size());

        return subscriptionViews;
    }

    @Override
    public List<SubscriptionView> listInactiveSubscription(int page, int size) {

        log.info("list appUser view...");
        List<Subscription> subscriptions = subscriptionRepo.listSubscriptionByActivatedFalse(PageRequest.of(page, size));
        List<Subscription> subscriptionList = subscriptionRepo.listSubscriptionActivatedFalseNotPageable();
        List<SubscriptionView> subscriptionViews = ListEntityToListViewConverter.paginateSubscriptionView(subscriptions, page, size, subscriptionList.size());
        return subscriptionViews;
    }

    @Override
    public List<SubscriptionView> listSubscriptionByType(String subscriptionType, int page, int size) {
        log.info("list Payment view by subscription  :: " +subscriptionType);
        List<Subscription> subscriptions = subscriptionRepo.listSubscriptionByPeriod(subscriptionType, PageRequest.of(page, size));
        List<SubscriptionView> views = subscriptions.stream()
                .map(subscription -> EntityToViewConverter.convertEntityToSubscriptionView(subscription))
                .collect(Collectors.toList());
        return views;
    }

    @Override
    public List<SubscriptionView> listMonthlySubscription(int page, int size) {
        log.info("list Payment view by user reference  :: " );
        List<Subscription> subscriptions = subscriptionRepo.listSubscriptionByPeriod(Constant.monthlySubscription, PageRequest.of(page, size));
        List<SubscriptionView> views = subscriptions.stream()
                .map(subscription -> EntityToViewConverter.convertEntityToSubscriptionView(subscription))
                .collect(Collectors.toList());
        return views;
    }

    @Override
    public List<SubscriptionView> listAnnuallySubscription(int page, int size) {
        log.info("list Payment view by user reference  :: " );
        List<Subscription> subscriptions = subscriptionRepo.listSubscriptionByPeriod(Constant.annuallySubscription, PageRequest.of(page, size));
        List<Subscription> subscriptionList = subscriptionRepo.listSubscriptionByPeriodNotPageable(Constant.annuallySubscription);
        List<SubscriptionView> views = ListEntityToListViewConverter.paginateSubscriptionView(subscriptions, page, size, subscriptionList.size());
        return views;
    }

    @Override
    public List<SubscriptionView> listSubscriptionByUser(String appUserId, int page, int size) throws AppUserNotFoundException {
        AppUser appUser = userRepo.findById(appUserId).orElseThrow(()-> new AppUserNotFoundException("user not found"));
        List<Subscription> subscriptions = subscriptionRepo.listSubscriptionByBeneficiary(appUser, PageRequest.of(page, size));
        List<Subscription> subscriptionList = subscriptionRepo.listSubscriptionByBeneficiaryNotPageable(appUser);
        List<SubscriptionView> views = ListEntityToListViewConverter.paginateSubscriptionView(subscriptions, page, size, subscriptionList.size());
        return views;
    }

    @Override
    public Subscription lastSubscriptionByUser(String appUserId) throws AppUserNotFoundException {
        AppUser appUser = userRepo.findById(appUserId).orElseThrow(()-> new AppUserNotFoundException("user not found"));
        List<Subscription> subscriptions = subscriptionRepo.listSubscriptionByBeneficiaryNotPageable(appUser);
        Subscription subscription = subscriptions.get(0);
        return subscription;
    }

    @Override
    public SubscriptionView lastSubscriptionByUserView(String appUserId) throws AppUserNotFoundException {
        Subscription subscription = lastSubscriptionByUser(appUserId);
        SubscriptionView view = EntityToViewConverter.convertEntityToSubscriptionView(subscription);
        return view;
    }

    @Override
    public void reduceAmountSubscription() {
        log.info("task scheduled for reduce amount in action, every midnight of every day");
        List<Subscription> subscriptions = subscriptionRepo.listSubscriptionNotPageable();
        for (Subscription subscription : subscriptions){

            AppUser subscriber = subscription.getBeneficiary();

            subscription.setDuration(DateUtils.diff_in_days(DateUtils.currentDate(), subscription.getEndSubscription()));

            if (subscription.getDuration() != 0){
                subscription.setAmount(subscription.getAmount() - subscription.getDailyAmount());
                subscriptionRepo.save(subscription);
            }else {

                List<Offer> offers = offerRepo.listOfferByUserNotPageable(subscriber);
                List<Talent> talents = talentRepo.listTalentByUserNotPageable(subscriber);

                if (subscription.getType().equals(Constant.host)){
                    subscription.setActivated(false);
                    subscriptionRepo.save(subscription);

                    subscriber.setActivatedHostSubscription(false);
                    userRepo.save(subscriber);

                    if (subscriber.getAccount().equals(Constant.employer) || subscriber.getAccount().equals(Constant.enterprise)){
                        for (Offer offer : offers){
                            offer.setValid(false);
                            offerRepo.save(offer);
                        }
                    }


                    if (subscriber.getAccount().equals(Constant.talent)){
                        for (Talent talent : talents){
                            talent.setValid(false);
                            talentRepo.save(talent);
                        }
                    }

                }

                if (subscription.getType().equals(Constant.normal)){
                    subscription.setActivated(false);
                    subscriptionRepo.save(subscription);

                    subscriber.setActivatedNormalSubscription(false);
                    userRepo.save(subscriber);
                }
            }
        }
    }

    @Override
    public boolean existsByHostTypeAndUserAndActivatedTrue(AppUser appUser){
        return subscriptionRepo.existsByTypeAndBeneficiaryAndActivatedTrue(Constant.host,appUser);
    }


    @Override
    public boolean existsByNormalTypeAndUserAndActivatedTrue(AppUser appUser){
        return subscriptionRepo.existsByTypeAndBeneficiaryAndActivatedTrue(Constant.normal,appUser);
    }


}

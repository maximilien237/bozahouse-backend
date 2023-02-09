package net.bozahouse.backend.services;

import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.entitie.SubscriptionNotFoundException;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Subscription;
import net.bozahouse.backend.model.views.SubscriptionView;

import java.text.ParseException;
import java.util.List;

public interface SubscriptionService {

    Subscription getSubscription(long id) throws SubscriptionNotFoundException;

    SubscriptionView getSubscriptionView(long id) throws SubscriptionNotFoundException;

    Subscription currentSubscription(Subscription subscription);

    Subscription createSubscription(Subscription subscription) throws AppUserNotFoundException, ParseException;

    SubscriptionView createSubscriptionView(Subscription subscription) throws AppUserNotFoundException, ParseException;

    void deleteSubscription(long id) throws SubscriptionNotFoundException;

    List<SubscriptionView> listSubscriptionView(int page, int size);

    List<SubscriptionView> listInactiveSubscription(int page, int size);

    List<SubscriptionView> listSubscriptionByType(String subscriptionType, int page, int size);

    List<SubscriptionView> listMonthlySubscription(int page, int size);

    List<SubscriptionView> listAnnuallySubscription(int page, int size);

    List<SubscriptionView> listSubscriptionByUser(String appUserId, int page, int size) throws AppUserNotFoundException;

    Subscription lastSubscriptionByUser(String appUserId) throws AppUserNotFoundException;

    SubscriptionView lastSubscriptionByUserView(String appUserId) throws AppUserNotFoundException;

    void reduceAmountSubscription();

    boolean existsByHostTypeAndUserAndActivatedTrue(AppUser appUser);

    boolean existsByNormalTypeAndUserAndActivatedTrue(AppUser appUser);
}

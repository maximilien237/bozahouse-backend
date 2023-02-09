package net.bozahouse.backend.mappers;

import net.bozahouse.backend.model.entities.*;
import net.bozahouse.backend.model.views.*;

import java.util.List;
import java.util.stream.Collectors;

public class ListEntityToListViewConverter {

    public static List<AppUserView> paginateAppUserViewList(List<AppUser> appUsers, int page, int size, int realSize) {
        List<AppUserView> appUserViews = appUsers
                .stream().map(appUser -> EntityToViewConverter.convertEntityToAppUserView(appUser)).collect(Collectors.toList());
        AppUserView view = appUserViews.get(0);
        view.setCurrentPage(page);
        view.setPageSize(size);

        if (realSize % 5 == 0){
            view.setTotalPages(realSize/5);
        }else {
            view.setTotalPages((realSize/5) +1);
        }

        return appUserViews;
    }

    public static List<OfferView> paginateOfferView(List<Offer> offers, int page, int size, int realSize) {
        List<OfferView> offerViews = offers.stream().map(offer -> EntityToViewConverter.convertEntityToOfferView(offer)).collect(Collectors.toList());
        OfferView view = offerViews.get(0);
        view.setCurrentPage(page);
        view.setPageSize(size);

        if (realSize % 5 == 0){
            view.setTotalPages(realSize/5);
        }else {
            view.setTotalPages((realSize/5) +1);
        }

        return offerViews;
    }

    public static List<TalentView> paginateTalentView(List<Talent> talents, int page, int size, int realSize) {
        List<TalentView> talentViews = talents.stream().map(talent -> EntityToViewConverter.convertEntityToTalentView(talent)).collect(Collectors.toList());
        TalentView view = talentViews.get(0);
        view.setCurrentPage(page);
        view.setPageSize(size);

        if (realSize % 5 == 0){
            view.setTotalPages(realSize/5);
        }else {
            view.setTotalPages((realSize/5) +1);
        }

        return talentViews;
    }

    public static List<SubscriptionView> paginateSubscriptionView(List<Subscription> subscriptions, int page, int size, int realSize) {
        List<SubscriptionView> subscriptionViews = subscriptions.stream().map(subscription -> EntityToViewConverter.convertEntityToSubscriptionView(subscription)).collect(Collectors.toList());
        SubscriptionView view = subscriptionViews.get(0);
        view.setCurrentPage(page);
        view.setPageSize(size);

        if (realSize % 5 == 0){
            view.setTotalPages(realSize/5);
        }else {
            view.setTotalPages((realSize/5) +1);
        }

        return subscriptionViews;
    }

    public static List<NewsletterView> paginateNewsletterViewList(List<Newsletter> newsletterList, int page, int size, int realSize) {
        List<NewsletterView> newsletterViews = newsletterList.stream().map(news -> EntityToViewConverter.convertEntityToNewsView(news)).collect(Collectors.toList());
        NewsletterView view = newsletterViews.get(0);
        view.setCurrentPage(page);
        view.setPageSize(size);

        if (realSize % 5 == 0){
            view.setTotalPages(realSize/5);
        }else {
            view.setTotalPages((realSize/5) +1);
        }

        return newsletterViews;
    }


    public static List<AppUserDatesView> paginateAppUserDateList(List<AppUserDates> userDates, int page, int size, int realSize) {
        List<AppUserDatesView> userDatesViews = userDates.stream().map(userDates1 -> EntityToViewConverter.convertEntityToAppUserDatesView(userDates1)).collect(Collectors.toList());
        AppUserDatesView view = userDatesViews.get(0);
        view.setCurrentPage(page);
        view.setPageSize(size);

        if (realSize % 5 == 0){
            view.setTotalPages(realSize/5);
        }else {
            view.setTotalPages((realSize/5) +1);
        }
        view.setRealSize(realSize);

        //System.out.println(realSize);

        return userDatesViews;
    }


    public static List<TestimonyView> paginateTestimonyViewList(List<Testimony> testimonies, int page, int size, int realSize) {
        List<TestimonyView> testimonyViews = testimonies.stream().map(testimony -> EntityToViewConverter.convertEntityToTestimonyView(testimony)).collect(Collectors.toList());
        TestimonyView view = testimonyViews.get(0);
        view.setCurrentPage(page);
        view.setPageSize(size);

        if (realSize % 5 == 0){
            view.setTotalPages(realSize/5);
        }else {
            view.setTotalPages((realSize/5) +1);
        }

        return testimonyViews;
    }




}

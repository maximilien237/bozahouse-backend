package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.entitie.OfferNotFoundException;
import net.bozahouse.backend.mappers.EntityToViewConverter;
import net.bozahouse.backend.mappers.FormToEntityConverter;
import net.bozahouse.backend.mappers.ListEntityToListViewConverter;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Offer;
import net.bozahouse.backend.model.forms.OfferForm;
import net.bozahouse.backend.model.views.OfferView;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.repositories.OfferRepo;
import net.bozahouse.backend.utils.DateUtils;
import net.bozahouse.backend.utils.RandomUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class OfferServiceImpl implements OfferService{

    private OfferRepo offerRepo;
    private AppUserRepo userRepo;

    @Override
    public List<OfferView> filterOfferView(String title, String contract, String workMode, String address, String experience, String type, String domain, int page, int size)  {
        log.info("filer offer");
        log.info(" before " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        List<Offer> offers = offerRepo.listOfferByFiltering(title, contract, workMode,address, experience, type, domain, PageRequest.of(page, size));

        List<Offer> offerList = offerRepo.listOfferByFilteringNotPageable(title, contract, workMode,address, experience, type, domain);

        if (offers.isEmpty()){
            return listOfferView(page, size);
        }

        List<OfferView> offerViews = ListEntityToListViewConverter.paginateOfferView(offers,page,size,offerList.size());
        System.out.println("je passe bien ici");
        return offerViews;
    }

    @Override
    public List<OfferView> filterOfferNotValidView(String title, String contract, String workMode, String address, String experience, String type, String domain, int page, int size)  {
        log.info("filer offer");
        log.info(" before " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        List<Offer> offers = offerRepo.listOfferNotValidByFiltering(title, contract, workMode,address, experience, type, domain, PageRequest.of(page, size));

        List<Offer> offerList = offerRepo.listOfferNotValidByFilteringNotPageable(title, contract, workMode,address, experience, type, domain);

        if (offers.isEmpty()){
            return listOfferNotValidView(page, size);
        }

        List<OfferView> offerViews = ListEntityToListViewConverter.paginateOfferView(offers,page,size,offerList.size());
        System.out.println("je passe bien ici");
        return offerViews;
    }


    @Override
    public List<OfferView> listOfferByAppUserView(String appUserId, int page, int size) throws AppUserNotFoundException {
        log.info("list Offer By AppUser View");
        AppUser appUser = userRepo.findById(appUserId).orElseThrow(()-> new AppUserNotFoundException("user not found"));
        List<Offer> offers = offerRepo.listOfferByUser(appUser,PageRequest.of(page, size));
        List<Offer> offerList = offerRepo.listOfferByUserNotPageable(appUser);
        List<OfferView> offerViews = ListEntityToListViewConverter.paginateOfferView(offers, page, size, offerList.size());

        return offerViews;
    }

    @Override
    public void reduceDurationOffer(){
        log.info("reduce duration of offer");
        List<Offer> offers = offerRepo.listOfferNotPageable();
        for (Offer offer : offers){

            Date today = Calendar.getInstance().getTime();
            offer.setDuration(DateUtils.diff_in_days(today, offer.getEndOffer()));
            Offer offer1 = offerRepo.save(offer);

            if (offer1.getDuration() == 0){
                offer1.setValid(false);
                offerRepo.save(offer1);
            }
        }

    }

    @Override
    public Offer getOffer(String offerId) throws OfferNotFoundException {
        log.info("getting offer by id ::"+offerId);
        Offer offer = offerRepo.findById(offerId).
                orElseThrow(()->new OfferNotFoundException("offer not found for this id ::"+offerId));

        return offer;
    }

    @Override
    public OfferView getOfferView(String offerId) throws OfferNotFoundException {
        log.info("get offer view by id :: "+offerId);
        Offer offer = getOffer(offerId);
        OfferView view = EntityToViewConverter.convertEntityToOfferView(offer);
        return view;
    }

    @Override
    public Offer createOffer(Offer offer) {
        log.info("creating offer...");

        Date today = Calendar.getInstance().getTime();

        offer.setId(RandomUtils.id());
        offer.setPublishedAt(today);
        offer.setReference("job-" + RandomUtils.unique().substring(4,8));
        offer.setValid(true);
        System.out.println("today is " + today);
        System.out.println("end offer " + offer.getEndOffer());

        offer.setDuration(DateUtils.diff_in_days(today,offer.getEndOffer()));
        System.out.println("the duration is " +offer.getDuration());
        offer.setCountryCode("+237");

        Offer savedOffer = offerRepo.save(offer);

        return savedOffer;

    }

    @Override
    public OfferView createOfferView(Offer offer) {
        log.info("creating offer view...");
        OfferView view = EntityToViewConverter.convertEntityToOfferView(createOffer(offer));
        return view;
    }

    @Override
    public Offer updateOffer(OfferForm form) throws OfferNotFoundException, ParseException {
        log.info("updating enterprise offer...");
        //String whatsUrl = "https://wa.me/237";

        Offer offer = FormToEntityConverter.convertFormToOffer(form);
        offer.setUpdatedAt(new Date());
        System.out.println(form.getEndOffer());
        offer.setEndOffer(DateUtils.convertStringToDate(form.getEndOffer()));
        offer.setDuration(DateUtils.diff_in_days(DateUtils.currentDate(),offer.getEndOffer()));

        Offer offerUpdated  = getOffer(offer.getId());
        offer.setReference(offerUpdated.getReference());
        offer.setPublishedAt(offerUpdated.getPublishedAt());
        offer.setValid(true);
        offer.setUser(offerUpdated.getUser());

        return offerRepo.save(offer);
    }

    @Override
    public OfferView updateOfferView(OfferForm form) throws OfferNotFoundException, ParseException {
        log.info("updating enterprise offer...");
        return EntityToViewConverter.convertEntityToOfferView(updateOffer(form));
    }

    @Override
    public List<OfferView> listOfferView(int page, int size) {
        log.info("list offer view");
        List<Offer> offers = offerRepo.listOffer(PageRequest.of(page, size));
        List<Offer> offerList = offerRepo.listOfferNotPageable();
        List<OfferView> offerViews = ListEntityToListViewConverter.paginateOfferView(offers, page, size, offerList.size());

        return offerViews;
    }


    public List<OfferView> listOfferNotValidView(int page, int size) {
        log.info("list offer not valid view");
        List<Offer> offers = offerRepo.listOfferValidFalse(PageRequest.of(page, size));
        List<Offer> offerList = offerRepo.listOfferValidFalseNotPageable();
        List<OfferView> offerViews = ListEntityToListViewConverter.paginateOfferView(offers, page, size, offerList.size());

        return offerViews;
    }


    @Override
    public List<OfferView> lastThreeOfferView() {
        log.info("last three offer view...");
        List<Offer> offers = offerRepo.listOfferNotPageable();
        List<OfferView> offerViews = offers.stream().limit(3).map(offer -> EntityToViewConverter.convertEntityToOfferView(offer)).collect(Collectors.toList());
        return offerViews;
    }


    @Override
    public void deleteOffer(String offerId) throws OfferNotFoundException {
        log.info("delete offer with id ::" +offerId);
        Offer offer = getOffer(offerId);
        offerRepo.delete(offer);
    }

    @Override
    public void disableOffer(String offerId) throws OfferNotFoundException {
        log.info("disable offer with id ::" +offerId);
        Offer offer = getOffer(offerId);
            offer.setValid(false);
            offerRepo.save(offer);
    }

    @Override
    public void enableOffer(String offerId) throws OfferNotFoundException {
        log.info("enable offer with id ::" +offerId);
        Offer offer = getOffer(offerId);
        offer.setValid(true);
        offerRepo.save(offer);
    }

}

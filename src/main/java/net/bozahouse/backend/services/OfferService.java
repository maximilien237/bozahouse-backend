package net.bozahouse.backend.services;

import net.bozahouse.backend.exception.entitie.AppUserNotFoundException;
import net.bozahouse.backend.exception.entitie.OfferNotFoundException;
import net.bozahouse.backend.model.entities.Offer;
import net.bozahouse.backend.model.forms.OfferForm;
import net.bozahouse.backend.model.views.OfferView;

import java.text.ParseException;

import java.util.List;

public interface OfferService {

    List<OfferView> filterOfferNotValidView(String title, String contract, String workMode, String address, String experience, String type, String domain, int page, int size);

    List<OfferView> listOfferByAppUserView(String appUserId, int page, int size) throws AppUserNotFoundException;

    List<OfferView> filterOfferView(String title, String contract, String workMode, String address, String experience, String type, String domain, int page, int size);

    void reduceDurationOffer();

    Offer getOffer(String offerId) throws OfferNotFoundException;

    Offer createOffer(Offer offer) throws ParseException;

    Offer updateOffer(OfferForm form) throws OfferNotFoundException, ParseException;

    OfferView getOfferView(String offerId) throws OfferNotFoundException;

    OfferView createOfferView(Offer offer) throws ParseException;

    OfferView updateOfferView(OfferForm form) throws OfferNotFoundException, ParseException;

    List<OfferView> listOfferView(int page, int size);

    List<OfferView> lastThreeOfferView();

    void deleteOffer(String offerId) throws OfferNotFoundException;

    void disableOffer(String offerId) throws OfferNotFoundException;

    void enableOffer(String offerId) throws OfferNotFoundException;
}

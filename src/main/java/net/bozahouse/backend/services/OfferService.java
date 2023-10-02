package net.bozahouse.backend.services;


import net.bozahouse.backend.dtos.OfferDTO;
import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.model.entities.Offer;

import java.util.Date;
import java.util.List;

public interface OfferService {


    PageDTO<OfferDTO> listOffer(String title, String contract, String workMode, String address, String experience, String type, String domain, Date startDate, Date endDate, int page, int size);

    PageDTO<OfferDTO> listOfferNotValid(String title, String contract, String workMode, String address, String experience, String type, String domain, Date startDate, Date endDate, int page, int size);

    PageDTO<OfferDTO> listOfferByAppUser(Long appUserId, int page, int size);

    void disabledOffer();

    Offer getOffer(Long offerId);

    OfferDTO getOfferDTO(Long offerId);

    OfferDTO createOffer(OfferDTO offerDTO, Long userId);

    OfferDTO updateOffer(OfferDTO offerDTO);

    List<OfferDTO> lastThreeOffer();

    void deleteOffer(Long offerId);

    void activateOrDisableOffer(Long offerId);
}

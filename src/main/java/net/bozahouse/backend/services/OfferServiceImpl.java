package net.bozahouse.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.dtos.OfferDTO;
import net.bozahouse.backend.dtos.PageDTO;
import net.bozahouse.backend.exception.EntityNotFoundException;
import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Offer;
import net.bozahouse.backend.repositories.AppUserRepo;
import net.bozahouse.backend.repositories.OfferRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class OfferServiceImpl implements OfferService{

    private OfferRepo offerRepo;
    private AppUserRepo userRepo;



    @Override
    public PageDTO<OfferDTO> listOffer(String title, String contract, String workMode, String address, String experience, String type, String domain, Date startDate, Date endDate, int page, int size)  {
        log.info("filer offer");
        log.info(" before " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        Page<Offer> offerPage = offerRepo.findAllByValidTrueAndTitleOrContractOrWorkModeOrAddressOrExperienceOrTypeOrDomainOrCreatedAtBetweenOrderByCreatedAtDesc(title,contract,workMode,address,experience,type,domain, startDate, endDate, PageRequest.of(page, size));
        return PageDTO.mapToOfferPageDTO(offerPage);
    }

    @Override
    public PageDTO<OfferDTO> listOfferNotValid(String title, String contract, String workMode, String address, String experience, String type, String domain, Date startDate, Date endDate, int page, int size) {
        log.info("filer offer");
        log.info(" before " + title +" "+contract +" "+workMode +" "+address +" "+experience +" "+type +" "+domain +" "+page +" "+size);

        Page<Offer> offerPage = offerRepo.findAllByValidFalseAndTitleOrContractOrWorkModeOrAddressOrExperienceOrTypeOrDomainOrCreatedAtBetweenOrderByCreatedAtDesc(title,contract,workMode,address,experience,type,domain, startDate, endDate, PageRequest.of(page, size));
        return PageDTO.mapToOfferPageDTO(offerPage);
    }


    @Override
    public PageDTO<OfferDTO> listOfferByAppUser(Long appUserId, int page, int size) {
        log.info("list Offer By AppUser View");
        AppUser appUser = userRepo.findById(appUserId).orElseThrow(()-> new EntityNotFoundException("user not found"));

        Page<Offer> offerPage = offerRepo.findByValidTrueAndUserOrderByCreatedAtDesc(appUser, PageRequest.of(page, size));
        return PageDTO.mapToOfferPageDTO(offerPage);
    }

    @Override
    public void disabledOffer(){
        log.info("disable offer");

        List<Offer> offers = offerRepo.findAllByValidTrueAndEndOfferBefore(new Date());
        offers.forEach(offer -> {
                offer.setValid(false);
                offerRepo.save(offer);
        });

    }

    @Override
    public Offer getOffer(Long offerId)  {
        log.info("getting offer by id ::"+offerId);
        Optional<Offer> optionalOffer = offerRepo.findById(offerId);
        if (optionalOffer.isPresent()) {
            return optionalOffer.get();
        }
    throw new  EntityNotFoundException("offer not found for this id ::"+offerId);
    }

    @Override
    public OfferDTO getOfferDTO(Long offerId) {
        log.info("get offer DTO by id :: "+offerId);
        return OfferDTO.mapToDTO(getOffer(offerId));
    }

    @Override
    public OfferDTO createOffer(OfferDTO offerDTO, Long userId) {
        log.info("creating offer...");
        AppUser appUser = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found " +userId));
        Offer offer = OfferDTO.mapToEntity(offerDTO);
        offer.setUser(appUser);
        offer.setValid(true);
        offer.setCountryCode("+237");
        offer.setReference("job-n° " + offerRepo.count());
        offer = offerRepo.save(offer);
        return OfferDTO.mapToDTO(offer);

    }

    @Override
    public OfferDTO updateOffer(OfferDTO offerDTO) {
        log.info("updating enterprise offer...");
        //String whatsUrl = "https://wa.me/237";

        Offer offer = OfferDTO.mapToEntity(offerDTO);

        Offer offerUpdated  = getOffer(offer.getId());
        offer.setReference(offerUpdated.getReference());
        offer.setCreatedAt(offerUpdated.getCreatedAt());
        offer.setValid(true);
        offer.setUser(offerUpdated.getUser());

        return OfferDTO.mapToDTO(offer);
    }

    @Override
    public List<OfferDTO> lastThreeOffer() {
        log.info("last three offer view...");
        return offerRepo.findTop3ByValidTrueOrderByCreatedAtDesc().stream().map(OfferDTO::mapToDTO).toList();
    }


    @Override
    public void deleteOffer(Long offerId) {
        log.info("delete offer with id ::" +offerId);
        Offer offer = getOffer(offerId);
        offerRepo.delete(offer);
    }

    @Override
    public void activateOrDisableOffer(Long offerId)  {
        log.info("activate Or Disable Offer with id ::" +offerId);
        Offer offer = getOffer(offerId);

        if (offer.isValid()) {
            offer.setValid(false);
            offerRepo.save(offer);
        }
        offer.setValid(true);
        offerRepo.save(offer);
    }

}

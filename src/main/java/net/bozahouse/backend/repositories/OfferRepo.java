package net.bozahouse.backend.repositories;


import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface OfferRepo extends JpaRepository<Offer, Long> {

    Long countAllByValidTrue();

    Long countAllByValidFalse();

    Long countAllByCreatedAt(Date publishedAt);

    List<Offer> findTop3ByValidTrueOrderByCreatedAtDesc();

    List<Offer> findAllByValidTrue();

    List<Offer> findAllByValidTrueAndEndOfferBefore(Date date);

    Page<Offer> findByValidTrueAndUserOrderByCreatedAtDesc(AppUser appUser, Pageable pageable);

    Page<Offer> findAllByValidTrueAndTitleOrContractOrWorkModeOrAddressOrExperienceOrTypeOrDomainOrCreatedAtBetweenOrderByCreatedAtDesc(String title, String contract, String workMode, String address, String experience, String type, String domain, Date startDate, Date endDate, Pageable pageable);

    Page<Offer> findAllByValidFalseAndTitleOrContractOrWorkModeOrAddressOrExperienceOrTypeOrDomainOrCreatedAtBetweenOrderByCreatedAtDesc(String title, String contract, String workMode, String address, String experience, String type, String domain, Date startDate, Date endDate, Pageable pageable);


}

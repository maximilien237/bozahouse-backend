package net.bozahouse.backend.repositories;




import net.bozahouse.backend.model.entities.AppUser;
import net.bozahouse.backend.model.entities.Talent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Date;
import java.util.List;

public interface TalentRepo extends JpaRepository<Talent, Long> {

    Long countAllByValidTrue();

    Long countAllByValidFalse();

    Long countAllByCreatedAt(Date publishedAt);

    List<Talent> findTop3ByValidTrueOrderByCreatedAtDesc();

    List<Talent> findAllByValidTrue();

    Page<Talent> findByValidTrueAndUserOrderByCreatedAtDesc(AppUser appUser, Pageable pageable);

    Page<Talent> findAllByValidTrueAndTitleOrContractOrWorkModeOrAddressOrExperienceOrTypeOrDomainOrCreatedAtBetweenOrderByCreatedAtDesc(String title, String contract, String workMode, String address, String experience, String type, String domain, Date startDate, Date endDate, Pageable pageable);

    Page<Talent> findAllByValidFalseAndTitleOrContractOrWorkModeOrAddressOrExperienceOrTypeOrDomainOrCreatedAtBetweenOrderByCreatedAtDesc(String title, String contract, String workMode, String address, String experience, String type, String domain, Date startDate, Date endDate, Pageable pageable);

}
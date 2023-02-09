package net.bozahouse.backend.repositories;

import net.bozahouse.backend.model.entities.stats.OfferStatistic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferStatisticRepo extends JpaRepository<OfferStatistic, Long> {

    @Query("select o from OfferStatistic o order by o.createdAt desc")
    List<OfferStatistic> listOfferStatisticNotPageable();

    @Query("select o from OfferStatistic o order by o.createdAt desc")
    List<OfferStatistic> listOfferStatistic(Pageable pageable);
}

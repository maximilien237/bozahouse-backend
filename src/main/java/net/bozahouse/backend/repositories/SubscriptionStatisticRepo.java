package net.bozahouse.backend.repositories;

import net.bozahouse.backend.model.entities.stats.SubscriptionStatistic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionStatisticRepo extends JpaRepository<SubscriptionStatistic, Long> {

    @Query("select s from SubscriptionStatistic s order by s.createdAt desc")
    List<SubscriptionStatistic> listSubscriptionStatisticNotPageable();

    @Query("select s from SubscriptionStatistic s order by s.createdAt desc")
    List<SubscriptionStatistic> listSubscriptionStatistic(Pageable pageable);
}
